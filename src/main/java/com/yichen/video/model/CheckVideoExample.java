package com.yichen.video.model;

import java.util.ArrayList;
import java.util.List;

public class CheckVideoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CheckVideoExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCheckVideoIdIsNull() {
            addCriterion("check_video_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdIsNotNull() {
            addCriterion("check_video_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdEqualTo(Long value) {
            addCriterion("check_video_id =", value, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdNotEqualTo(Long value) {
            addCriterion("check_video_id <>", value, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdGreaterThan(Long value) {
            addCriterion("check_video_id >", value, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdGreaterThanOrEqualTo(Long value) {
            addCriterion("check_video_id >=", value, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdLessThan(Long value) {
            addCriterion("check_video_id <", value, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdLessThanOrEqualTo(Long value) {
            addCriterion("check_video_id <=", value, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdIn(List<Long> values) {
            addCriterion("check_video_id in", values, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdNotIn(List<Long> values) {
            addCriterion("check_video_id not in", values, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdBetween(Long value1, Long value2) {
            addCriterion("check_video_id between", value1, value2, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIdNotBetween(Long value1, Long value2) {
            addCriterion("check_video_id not between", value1, value2, "checkVideoId");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleIsNull() {
            addCriterion("check_video_title is null");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleIsNotNull() {
            addCriterion("check_video_title is not null");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleEqualTo(String value) {
            addCriterion("check_video_title =", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleNotEqualTo(String value) {
            addCriterion("check_video_title <>", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleGreaterThan(String value) {
            addCriterion("check_video_title >", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleGreaterThanOrEqualTo(String value) {
            addCriterion("check_video_title >=", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleLessThan(String value) {
            addCriterion("check_video_title <", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleLessThanOrEqualTo(String value) {
            addCriterion("check_video_title <=", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleLike(String value) {
            addCriterion("check_video_title like", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleNotLike(String value) {
            addCriterion("check_video_title not like", value, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleIn(List<String> values) {
            addCriterion("check_video_title in", values, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleNotIn(List<String> values) {
            addCriterion("check_video_title not in", values, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleBetween(String value1, String value2) {
            addCriterion("check_video_title between", value1, value2, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoTitleNotBetween(String value1, String value2) {
            addCriterion("check_video_title not between", value1, value2, "checkVideoTitle");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionIsNull() {
            addCriterion("check_video_introduction is null");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionIsNotNull() {
            addCriterion("check_video_introduction is not null");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionEqualTo(String value) {
            addCriterion("check_video_introduction =", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionNotEqualTo(String value) {
            addCriterion("check_video_introduction <>", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionGreaterThan(String value) {
            addCriterion("check_video_introduction >", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionGreaterThanOrEqualTo(String value) {
            addCriterion("check_video_introduction >=", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionLessThan(String value) {
            addCriterion("check_video_introduction <", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionLessThanOrEqualTo(String value) {
            addCriterion("check_video_introduction <=", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionLike(String value) {
            addCriterion("check_video_introduction like", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionNotLike(String value) {
            addCriterion("check_video_introduction not like", value, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionIn(List<String> values) {
            addCriterion("check_video_introduction in", values, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionNotIn(List<String> values) {
            addCriterion("check_video_introduction not in", values, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionBetween(String value1, String value2) {
            addCriterion("check_video_introduction between", value1, value2, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andCheckVideoIntroductionNotBetween(String value1, String value2) {
            addCriterion("check_video_introduction not between", value1, value2, "checkVideoIntroduction");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNull() {
            addCriterion("upload_time is null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIsNotNull() {
            addCriterion("upload_time is not null");
            return (Criteria) this;
        }

        public Criteria andUploadTimeEqualTo(Long value) {
            addCriterion("upload_time =", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotEqualTo(Long value) {
            addCriterion("upload_time <>", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThan(Long value) {
            addCriterion("upload_time >", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("upload_time >=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThan(Long value) {
            addCriterion("upload_time <", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeLessThanOrEqualTo(Long value) {
            addCriterion("upload_time <=", value, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeIn(List<Long> values) {
            addCriterion("upload_time in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotIn(List<Long> values) {
            addCriterion("upload_time not in", values, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeBetween(Long value1, Long value2) {
            addCriterion("upload_time between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andUploadTimeNotBetween(Long value1, Long value2) {
            addCriterion("upload_time not between", value1, value2, "uploadTime");
            return (Criteria) this;
        }

        public Criteria andVideoPathIsNull() {
            addCriterion("video_path is null");
            return (Criteria) this;
        }

        public Criteria andVideoPathIsNotNull() {
            addCriterion("video_path is not null");
            return (Criteria) this;
        }

        public Criteria andVideoPathEqualTo(String value) {
            addCriterion("video_path =", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathNotEqualTo(String value) {
            addCriterion("video_path <>", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathGreaterThan(String value) {
            addCriterion("video_path >", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathGreaterThanOrEqualTo(String value) {
            addCriterion("video_path >=", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathLessThan(String value) {
            addCriterion("video_path <", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathLessThanOrEqualTo(String value) {
            addCriterion("video_path <=", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathLike(String value) {
            addCriterion("video_path like", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathNotLike(String value) {
            addCriterion("video_path not like", value, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathIn(List<String> values) {
            addCriterion("video_path in", values, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathNotIn(List<String> values) {
            addCriterion("video_path not in", values, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathBetween(String value1, String value2) {
            addCriterion("video_path between", value1, value2, "videoPath");
            return (Criteria) this;
        }

        public Criteria andVideoPathNotBetween(String value1, String value2) {
            addCriterion("video_path not between", value1, value2, "videoPath");
            return (Criteria) this;
        }

        public Criteria andPicturePathIsNull() {
            addCriterion("picture_path is null");
            return (Criteria) this;
        }

        public Criteria andPicturePathIsNotNull() {
            addCriterion("picture_path is not null");
            return (Criteria) this;
        }

        public Criteria andPicturePathEqualTo(String value) {
            addCriterion("picture_path =", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotEqualTo(String value) {
            addCriterion("picture_path <>", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathGreaterThan(String value) {
            addCriterion("picture_path >", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathGreaterThanOrEqualTo(String value) {
            addCriterion("picture_path >=", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathLessThan(String value) {
            addCriterion("picture_path <", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathLessThanOrEqualTo(String value) {
            addCriterion("picture_path <=", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathLike(String value) {
            addCriterion("picture_path like", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotLike(String value) {
            addCriterion("picture_path not like", value, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathIn(List<String> values) {
            addCriterion("picture_path in", values, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotIn(List<String> values) {
            addCriterion("picture_path not in", values, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathBetween(String value1, String value2) {
            addCriterion("picture_path between", value1, value2, "picturePath");
            return (Criteria) this;
        }

        public Criteria andPicturePathNotBetween(String value1, String value2) {
            addCriterion("picture_path not between", value1, value2, "picturePath");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Long value) {
            addCriterion("check_time =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Long value) {
            addCriterion("check_time <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Long value) {
            addCriterion("check_time >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("check_time >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Long value) {
            addCriterion("check_time <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Long value) {
            addCriterion("check_time <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Long> values) {
            addCriterion("check_time in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Long> values) {
            addCriterion("check_time not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Long value1, Long value2) {
            addCriterion("check_time between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Long value1, Long value2) {
            addCriterion("check_time not between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdIsNull() {
            addCriterion("check_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdIsNotNull() {
            addCriterion("check_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdEqualTo(Long value) {
            addCriterion("check_admin_id =", value, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdNotEqualTo(Long value) {
            addCriterion("check_admin_id <>", value, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdGreaterThan(Long value) {
            addCriterion("check_admin_id >", value, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("check_admin_id >=", value, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdLessThan(Long value) {
            addCriterion("check_admin_id <", value, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("check_admin_id <=", value, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdIn(List<Long> values) {
            addCriterion("check_admin_id in", values, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdNotIn(List<Long> values) {
            addCriterion("check_admin_id not in", values, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdBetween(Long value1, Long value2) {
            addCriterion("check_admin_id between", value1, value2, "checkAdminId");
            return (Criteria) this;
        }

        public Criteria andCheckAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("check_admin_id not between", value1, value2, "checkAdminId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}