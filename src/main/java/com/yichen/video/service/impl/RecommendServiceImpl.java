package com.yichen.video.service.impl;

import com.yichen.video.model.Neighbor;
import com.yichen.video.model.item;
import com.yichen.video.dao.CollectMapper;
import com.yichen.video.dao.ScoreMapper;
import com.yichen.video.dao.VideoMapper;
import com.yichen.video.model.*;
import com.yichen.video.security.LoginUser;
import com.yichen.video.service.RecommendService;
import com.yichen.video.vo.Result;
import com.yichen.video.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("RecommendService")
public class RecommendServiceImpl implements RecommendService {


    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    CollectMapper collectMapper;

    //热门推荐和算法推荐的分界线
    private static final int recommendDivideLine = 5;

    //定义最近邻为10
    private static final int NEIGHBORS = 10;


    @Override
    public Result getRecommend() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = loginUser.getUser();
        Long userid = user.getUserId();
        
        //返回热门列表 --start
        ScoreExample idExample = new ScoreExample();
        idExample.createCriteria().andUserIdEqualTo(userid);
        List<Score> userScoreList = scoreMapper.selectByExample(idExample);

        //当用户评分量太少的时候视为新用户,返回热门的视频
        if(userScoreList.size() <= recommendDivideLine){

            System.out.println("less");

            //随机推荐popular榜上的视频
            int i = (int) (Math.random() * 5 );
            List<VideoVo> videoVoList = videoMapper.getRandomVideoList(i * 10,17);


            for(VideoVo videoVo:videoVoList){
                videoVo.setPicturePath("http://127.0.0.1:8080/video/picture?picturePath="+videoVo.getPicturePath().replace('\\','/'));

            }
            return Result.ok(videoVoList);
        }
        //--end


        //开始使用基于用户的过滤算法进行推荐
        Long startTime = System.currentTimeMillis();

        //获取所有的scoreList
        ScoreExample scoreExample = new ScoreExample();
        List<Score> scoreList = scoreMapper.selectByExample(scoreExample);


        /**
         * 需要将数据库中的用户和视频信息都传入
         * 之后放入map中
         * 使用真实的id作为map的Long参数
         * 使用虚拟id作为数组的下标进行运算
         * 在运算结束后使用虚拟id在map中找出真实的id
         */

        HashMap<Long,Integer> userMap = new HashMap<>();
        HashMap<Long,Integer> videoMap = new HashMap<>();

        //分配实际id 和运行时的 id   key为实际id  value为运行时使用的id
        for (int i = 0; i < scoreList.size(); i++) {

            if(userMap.containsKey(scoreList.get(i).getUserId())==false){
                userMap.put(scoreList.get(i).getUserId(),userMap.size()+1);
            }

            if(videoMap.containsKey(scoreList.get(i).getVideoId())==false){
                videoMap.put(scoreList.get(i).getVideoId(),videoMap.size()+1);

            }

        }

        int USERSIZE=userMap.size();
        int VIDEOSIZE=videoMap.size();


        int [] num=new int[USERSIZE+1];//每个用户为几部评了分
        double[] average=new double[USERSIZE+1];//每个user的平均打分
        double[][] rate=new double[USERSIZE+1][VIDEOSIZE+1];//评分矩阵
        double[][] DeclaredRate=new double[USERSIZE+1][VIDEOSIZE+1];//针对稀疏问题处理后的评分矩阵

        Neighbor[] NofUser =new Neighbor[NEIGHBORS+1];//每个用户的最近的UN个邻居


        //1 构建rate矩阵
        for (int i = 0; i < scoreList.size(); i++) {
            Score score = scoreList.get(i);
            int userID=userMap.get(score.getUserId());
            int itemID=videoMap.get(score.getVideoId());
//            int userID = scoreList.get(i).getUserId().intValue();
//            int itemID = scoreList.get(i).getVideoId().intValue();

            rate[userID][itemID] = score.getScore();
        }

//        for (int i = 0; i < rate.length; i++) {
//            for (int j = 0; j < rate[i].length; j++) {
//                System.out.print(rate[i][j] + " ");
//            }
//            System.out.println();
//        }

        //2 计算每个用户的平均分
        //2.1 计算每个用户为几部电影打分
        int n;
        for(int i=1;i<=USERSIZE;i++){
            n=0;
            for(int j=1;j<=VIDEOSIZE;j++){
                if(rate[i][j]!=0)
                    n++;
            }
            num[i]=n;
        }

        // 2.2计算平均分
        double sum;
        for(int i=1;i<=USERSIZE;i++){
            sum=0.0;
            for(int j=1;j<rate[i].length;j++){//每个length都是ITEMSIZE=1682
                sum+=rate[i][j];
            }
            average[i]=sum/num[i];
        }

        //1-3处理评分矩阵的稀疏问题
        //重点处理该user对没有被评分的item，会打几分
        //暂时用1-2中计算出的平均分
        for(int i=1;i<=USERSIZE;i++){
            for(int j=1;j<=VIDEOSIZE;j++){
                if(rate[i][j]==0)
                    DeclaredRate[i][j]=average[i];
                else
                    DeclaredRate[i][j]=rate[i][j];
            }
        }

        //获取最近邻
        System.out.println("realId:"+userid);

        //虚拟出的用户id
        int userID = userMap.get(userid);
//        int userID = 37;

        System.out.println("virtualId:"+userID);

        Set<Neighbor> neighborList=new TreeSet();//会将压入的Neighbor排好序存放
        Neighbor[] tmpNeighbor=new Neighbor[USERSIZE+1];
        for(int id=1;id<=USERSIZE;id++){
            if(id!=userID){
                double sim=Pearson(DeclaredRate[userID],DeclaredRate[id]);
                tmpNeighbor[id]=new Neighbor(id,sim);
                neighborList.add(tmpNeighbor[id]);

//                System.out.println(tmpNeighbor[id].getValue());
            }
        }

        int k=1;
        Iterator it=neighborList.iterator();
        while(k<=NEIGHBORS&&it.hasNext()){
            Neighbor tmp=(Neighbor) it.next();
            NofUser[k]=tmp;
            k++;
        }


//        for(int i=1;i<=UN;i++){
//            System.out.println(NofUser[i].getID()+":"+NofUser[i].getValue());
//        }

        List<item> itemList = new ArrayList<>();
        //遍历出所有 最近邻访问过的视频 并剔除自己访问过的
        for (int i = 1; i <= VIDEOSIZE; i++) {
            if(rate[userID][i]!=0){
                continue;
            }
            for (int j = 1; j <= NEIGHBORS; j++) {
                if(rate[NofUser[j].getID()][i]!=0){


                    itemList.add(new item(i,predict(userID,i,NEIGHBORS,NofUser,DeclaredRate,average)));
                    break;
                }
            }
        }

        Collections.sort(itemList, new Comparator<item>() {
            @Override
            public int compare(item o1, item o2) {
                if(o1.value - o2.value> 0){
                    return -1;
                }else {
                    return 1;
                }

            }
        });


        Random random = new Random();

        int randomSeed = random.nextInt()%10;   //0到9



        HashSet<Integer> hashSet = new HashSet<>(17);
        for (int i = 0; i < 17; i++) {


            //将结果排序后随机选取17个加入list
            hashSet.add(itemList.get(i).id+randomSeed);
//            System.out.println("virtualID:"+itemList.get(i).id);
        }

        List<Long> result = new ArrayList<>(17);

        Stack<Integer> stack = new Stack<Integer>();


        //key为真实id value为虚拟id
        //到这里已经得到了需要推荐视频的虚拟id 需要转为真实的视频id
        //外层for循环遍历所有的视频 只遍历一次
        //内层遍历需要推荐视频的虚拟id是否匹配 如果匹配将真实id加入result中
        //并使用栈保存虚拟id
        //内层遍历结束后  将栈中保存的需要清理的虚拟id清除
        //最后清空栈
        for (Long key : videoMap.keySet()) {




            for (Integer virtualId:hashSet){
                if(videoMap.get(key).equals(virtualId)){

                    result.add(key);
                    stack.push(virtualId);
                }
            }

            for(Integer virtualId:stack){
                hashSet.remove(virtualId);
            }
            stack.clear();
        }


        List<VideoVo> videoList = new ArrayList<>(result.size());
        for (int i = 0; i < result.size(); i++) {

//            System.out.println(result.get(i));
            VideoVo videoVo = videoMapper.getVideoVo(result.get(i));
            videoList.add(videoVo);
        }


//        for (int i = 0; i < 10; i++) {
//            System.out.println(itemList.get(i).id);
//            System.out.println(itemList.get(i).value);
//        }


        System.out.println(System.currentTimeMillis() - startTime);


        for(VideoVo videoVo:videoList){
            videoVo.setPicturePath("http://127.0.0.1:8080/video/picture?picturePath="+videoVo.getPicturePath().replace('\\','/'));

        }

        return Result.ok(videoList);
    }





    //2：聚类，找和某一用户有相同喜好的一类用户
    //2-1：:Pearson计算向量的相似度
    public double Sum(double[] arr){
        double total=0.0;
        for(double ele:arr)
            total+=ele;
        return total;
    }
    public double Multiply(double[] arr1, double[] arr2, int len){
        double total=0.0;
        for(int i=0;i<len;i++)
            total+=arr1[i]*arr2[i];
        return total;
    }
    public double Pearson(double[] x,double[] y){
        int lenx=x.length;
        int leny=y.length;
        int len=lenx;//小容错
        if(lenx<leny) len=lenx;
        else len=leny;
        double sumX=Sum(x);
        double sumY=Sum(y);
        double sumXX= Multiply(x,x,len);
        double sumYY= Multiply(y,y,len);
        double sumXY= Multiply(x,y,len);
        double upside=sumXY-sumX*sumY/len;
        //double downside=(double) Math.sqrt((sumXX-(Math.pow(sumX, 2))/len)*(sumYY-(Math.pow(sumY, 2))/len));
        double downside= Math.sqrt((sumXX-Math.pow(sumX, 2)/len)*(sumYY-Math.pow(sumY, 2)/len));

        //System.out.println(len+" "+sumX+" "+sumY+" "+sumXX+" "+sumYY+" "+sumXY);
        return upside/downside;
    }

    //3:根据最近邻居给出预测评分
    public double predict(int userID, int itemID,int UN,Neighbor[] NofUser,double[][] DealedOfRate,double[] average){//这里的userID为用户输入，减1后为数组下标！
        double sum1=0;
        double sum2=0;
        for(int i=1;i<=UN;i++){//对最近的UN个邻居进行处理
            int neighborID=NofUser[i].getID();
            double neib_sim=NofUser[i].getValue();
            sum1+=neib_sim*(DealedOfRate[neighborID][itemID]-average[neighborID]);
            sum2+=Math.abs(neib_sim);
        }
        return average[userID]+sum1/sum2;
    }
}


