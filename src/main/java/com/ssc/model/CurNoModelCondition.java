package com.ssc.model;

import java.util.ArrayList;
import java.util.List;

public class CurNoModelCondition  {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurNoModelCondition() {
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeIsNull() {
            addCriterion("lottery_code is null");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeIsNotNull() {
            addCriterion("lottery_code is not null");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeEqualTo(String value) {
            addCriterion("lottery_code =", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeNotEqualTo(String value) {
            addCriterion("lottery_code <>", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeGreaterThan(String value) {
            addCriterion("lottery_code >", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("lottery_code >=", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeLessThan(String value) {
            addCriterion("lottery_code <", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeLessThanOrEqualTo(String value) {
            addCriterion("lottery_code <=", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeLike(String value) {
            addCriterion("lottery_code like", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeNotLike(String value) {
            addCriterion("lottery_code not like", value, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeIn(List<String> values) {
            addCriterion("lottery_code in", values, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeNotIn(List<String> values) {
            addCriterion("lottery_code not in", values, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeBetween(String value1, String value2) {
            addCriterion("lottery_code between", value1, value2, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeNotBetween(String value1, String value2) {
            addCriterion("lottery_code not between", value1, value2, "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andCurNoIsNull() {
            addCriterion("cur_no is null");
            return (Criteria) this;
        }

        public Criteria andCurNoIsNotNull() {
            addCriterion("cur_no is not null");
            return (Criteria) this;
        }

        public Criteria andCurNoEqualTo(String value) {
            addCriterion("cur_no =", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoNotEqualTo(String value) {
            addCriterion("cur_no <>", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoGreaterThan(String value) {
            addCriterion("cur_no >", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoGreaterThanOrEqualTo(String value) {
            addCriterion("cur_no >=", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoLessThan(String value) {
            addCriterion("cur_no <", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoLessThanOrEqualTo(String value) {
            addCriterion("cur_no <=", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoLike(String value) {
            addCriterion("cur_no like", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoNotLike(String value) {
            addCriterion("cur_no not like", value, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoIn(List<String> values) {
            addCriterion("cur_no in", values, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoNotIn(List<String> values) {
            addCriterion("cur_no not in", values, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoBetween(String value1, String value2) {
            addCriterion("cur_no between", value1, value2, "curNo");
            return (Criteria) this;
        }

        public Criteria andCurNoNotBetween(String value1, String value2) {
            addCriterion("cur_no not between", value1, value2, "curNo");
            return (Criteria) this;
        }

        public Criteria andNextNoIsNull() {
            addCriterion("next_no is null");
            return (Criteria) this;
        }

        public Criteria andNextNoIsNotNull() {
            addCriterion("next_no is not null");
            return (Criteria) this;
        }

        public Criteria andNextNoEqualTo(String value) {
            addCriterion("next_no =", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoNotEqualTo(String value) {
            addCriterion("next_no <>", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoGreaterThan(String value) {
            addCriterion("next_no >", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoGreaterThanOrEqualTo(String value) {
            addCriterion("next_no >=", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoLessThan(String value) {
            addCriterion("next_no <", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoLessThanOrEqualTo(String value) {
            addCriterion("next_no <=", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoLike(String value) {
            addCriterion("next_no like", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoNotLike(String value) {
            addCriterion("next_no not like", value, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoIn(List<String> values) {
            addCriterion("next_no in", values, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoNotIn(List<String> values) {
            addCriterion("next_no not in", values, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoBetween(String value1, String value2) {
            addCriterion("next_no between", value1, value2, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextNoNotBetween(String value1, String value2) {
            addCriterion("next_no not between", value1, value2, "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextSecIsNull() {
            addCriterion("next_sec is null");
            return (Criteria) this;
        }

        public Criteria andNextSecIsNotNull() {
            addCriterion("next_sec is not null");
            return (Criteria) this;
        }

        public Criteria andNextSecEqualTo(String value) {
            addCriterion("next_sec =", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecNotEqualTo(String value) {
            addCriterion("next_sec <>", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecGreaterThan(String value) {
            addCriterion("next_sec >", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecGreaterThanOrEqualTo(String value) {
            addCriterion("next_sec >=", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecLessThan(String value) {
            addCriterion("next_sec <", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecLessThanOrEqualTo(String value) {
            addCriterion("next_sec <=", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecLike(String value) {
            addCriterion("next_sec like", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecNotLike(String value) {
            addCriterion("next_sec not like", value, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecIn(List<String> values) {
            addCriterion("next_sec in", values, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecNotIn(List<String> values) {
            addCriterion("next_sec not in", values, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecBetween(String value1, String value2) {
            addCriterion("next_sec between", value1, value2, "nextSec");
            return (Criteria) this;
        }

        public Criteria andNextSecNotBetween(String value1, String value2) {
            addCriterion("next_sec not between", value1, value2, "nextSec");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNull() {
            addCriterion("prize is null");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNotNull() {
            addCriterion("prize is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeEqualTo(String value) {
            addCriterion("prize =", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotEqualTo(String value) {
            addCriterion("prize <>", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThan(String value) {
            addCriterion("prize >", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThanOrEqualTo(String value) {
            addCriterion("prize >=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThan(String value) {
            addCriterion("prize <", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThanOrEqualTo(String value) {
            addCriterion("prize <=", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeLike(String value) {
            addCriterion("prize like", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotLike(String value) {
            addCriterion("prize not like", value, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeIn(List<String> values) {
            addCriterion("prize in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotIn(List<String> values) {
            addCriterion("prize not in", values, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeBetween(String value1, String value2) {
            addCriterion("prize between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andPrizeNotBetween(String value1, String value2) {
            addCriterion("prize not between", value1, value2, "prize");
            return (Criteria) this;
        }

        public Criteria andTypeLikeInsensitive(String value) {
            addCriterion("upper(type) like", value.toUpperCase(), "type");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeLikeInsensitive(String value) {
            addCriterion("upper(lottery_code) like", value.toUpperCase(), "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andCurNoLikeInsensitive(String value) {
            addCriterion("upper(cur_no) like", value.toUpperCase(), "curNo");
            return (Criteria) this;
        }

        public Criteria andNextNoLikeInsensitive(String value) {
            addCriterion("upper(next_no) like", value.toUpperCase(), "nextNo");
            return (Criteria) this;
        }

        public Criteria andNextSecLikeInsensitive(String value) {
            addCriterion("upper(next_sec) like", value.toUpperCase(), "nextSec");
            return (Criteria) this;
        }

        public Criteria andPrizeLikeInsensitive(String value) {
            addCriterion("upper(prize) like", value.toUpperCase(), "prize");
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