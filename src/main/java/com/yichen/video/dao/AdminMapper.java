package com.yichen.video.dao;

import com.yichen.video.model.Admin;
import com.yichen.video.model.AdminExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Long adminId);

    int insert(Admin row);

    int insertSelective(Admin row);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Long adminId);

    int updateByExampleSelective(@Param("row") Admin row, @Param("example") AdminExample example);

    int updateByExample(@Param("row") Admin row, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin row);

    int updateByPrimaryKey(Admin row);
}