package com.ds.zxm.model;

import java.util.ArrayList;
import java.util.List;

public class TecentOnlineDOCondition  {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TecentOnlineDOCondition() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(String value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(String value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(String value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(String value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(String value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(String value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLike(String value) {
            addCriterion("time like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotLike(String value) {
            addCriterion("time not like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<String> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<String> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(String value1, String value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(String value1, String value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andOnlineNumIsNull() {
            addCriterion("online_num is null");
            return (Criteria) this;
        }

        public Criteria andOnlineNumIsNotNull() {
            addCriterion("online_num is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineNumEqualTo(Long value) {
            addCriterion("online_num =", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumNotEqualTo(Long value) {
            addCriterion("online_num <>", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumGreaterThan(Long value) {
            addCriterion("online_num >", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumGreaterThanOrEqualTo(Long value) {
            addCriterion("online_num >=", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumLessThan(Long value) {
            addCriterion("online_num <", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumLessThanOrEqualTo(Long value) {
            addCriterion("online_num <=", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumIn(List<Long> values) {
            addCriterion("online_num in", values, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumNotIn(List<Long> values) {
            addCriterion("online_num not in", values, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumBetween(Long value1, Long value2) {
            addCriterion("online_num between", value1, value2, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumNotBetween(Long value1, Long value2) {
            addCriterion("online_num not between", value1, value2, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumIsNull() {
            addCriterion("adjust_num is null");
            return (Criteria) this;
        }

        public Criteria andAdjustNumIsNotNull() {
            addCriterion("adjust_num is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustNumEqualTo(String value) {
            addCriterion("adjust_num =", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumNotEqualTo(String value) {
            addCriterion("adjust_num <>", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumGreaterThan(String value) {
            addCriterion("adjust_num >", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumGreaterThanOrEqualTo(String value) {
            addCriterion("adjust_num >=", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumLessThan(String value) {
            addCriterion("adjust_num <", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumLessThanOrEqualTo(String value) {
            addCriterion("adjust_num <=", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumLike(String value) {
            addCriterion("adjust_num like", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumNotLike(String value) {
            addCriterion("adjust_num not like", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumIn(List<String> values) {
            addCriterion("adjust_num in", values, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumNotIn(List<String> values) {
            addCriterion("adjust_num not in", values, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumBetween(String value1, String value2) {
            addCriterion("adjust_num between", value1, value2, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumNotBetween(String value1, String value2) {
            addCriterion("adjust_num not between", value1, value2, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andTimeLikeInsensitive(String value) {
            addCriterion("upper(time) like", value.toUpperCase(), "time");
            return (Criteria) this;
        }

        public Criteria andAdjustNumLikeInsensitive(String value) {
            addCriterion("upper(adjust_num) like", value.toUpperCase(), "adjustNum");
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