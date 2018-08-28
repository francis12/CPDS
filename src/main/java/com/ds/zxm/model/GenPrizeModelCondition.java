package com.ds.zxm.model;

import java.util.ArrayList;
import java.util.List;

public class GenPrizeModelCondition {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GenPrizeModelCondition() {
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

        public Criteria andNoIsNull() {
            addCriterion("no is null");
            return (Criteria) this;
        }

        public Criteria andNoIsNotNull() {
            addCriterion("no is not null");
            return (Criteria) this;
        }

        public Criteria andNoEqualTo(String value) {
            addCriterion("no =", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotEqualTo(String value) {
            addCriterion("no <>", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThan(String value) {
            addCriterion("no >", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoGreaterThanOrEqualTo(String value) {
            addCriterion("no >=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThan(String value) {
            addCriterion("no <", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLessThanOrEqualTo(String value) {
            addCriterion("no <=", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoLike(String value) {
            addCriterion("no like", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotLike(String value) {
            addCriterion("no not like", value, "no");
            return (Criteria) this;
        }

        public Criteria andNoIn(List<String> values) {
            addCriterion("no in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotIn(List<String> values) {
            addCriterion("no not in", values, "no");
            return (Criteria) this;
        }

        public Criteria andNoBetween(String value1, String value2) {
            addCriterion("no between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andNoNotBetween(String value1, String value2) {
            addCriterion("no not between", value1, value2, "no");
            return (Criteria) this;
        }

        public Criteria andGenPrizeIsNull() {
            addCriterion("gen_prize is null");
            return (Criteria) this;
        }

        public Criteria andGenPrizeIsNotNull() {
            addCriterion("gen_prize is not null");
            return (Criteria) this;
        }

        public Criteria andGenPrizeEqualTo(String value) {
            addCriterion("gen_prize =", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeNotEqualTo(String value) {
            addCriterion("gen_prize <>", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeGreaterThan(String value) {
            addCriterion("gen_prize >", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeGreaterThanOrEqualTo(String value) {
            addCriterion("gen_prize >=", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeLessThan(String value) {
            addCriterion("gen_prize <", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeLessThanOrEqualTo(String value) {
            addCriterion("gen_prize <=", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeLike(String value) {
            addCriterion("gen_prize like", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeNotLike(String value) {
            addCriterion("gen_prize not like", value, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeIn(List<String> values) {
            addCriterion("gen_prize in", values, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeNotIn(List<String> values) {
            addCriterion("gen_prize not in", values, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeBetween(String value1, String value2) {
            addCriterion("gen_prize between", value1, value2, "genPrize");
            return (Criteria) this;
        }

        public Criteria andGenPrizeNotBetween(String value1, String value2) {
            addCriterion("gen_prize not between", value1, value2, "genPrize");
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

        public Criteria andRealPrizeIsNull() {
            addCriterion("real_prize is null");
            return (Criteria) this;
        }

        public Criteria andRealPrizeIsNotNull() {
            addCriterion("real_prize is not null");
            return (Criteria) this;
        }

        public Criteria andRealPrizeEqualTo(String value) {
            addCriterion("real_prize =", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeNotEqualTo(String value) {
            addCriterion("real_prize <>", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeGreaterThan(String value) {
            addCriterion("real_prize >", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeGreaterThanOrEqualTo(String value) {
            addCriterion("real_prize >=", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeLessThan(String value) {
            addCriterion("real_prize <", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeLessThanOrEqualTo(String value) {
            addCriterion("real_prize <=", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeLike(String value) {
            addCriterion("real_prize like", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeNotLike(String value) {
            addCriterion("real_prize not like", value, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeIn(List<String> values) {
            addCriterion("real_prize in", values, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeNotIn(List<String> values) {
            addCriterion("real_prize not in", values, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeBetween(String value1, String value2) {
            addCriterion("real_prize between", value1, value2, "realPrize");
            return (Criteria) this;
        }

        public Criteria andRealPrizeNotBetween(String value1, String value2) {
            addCriterion("real_prize not between", value1, value2, "realPrize");
            return (Criteria) this;
        }

        public Criteria andIsPrizedIsNull() {
            addCriterion("is_prized is null");
            return (Criteria) this;
        }

        public Criteria andIsPrizedIsNotNull() {
            addCriterion("is_prized is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrizedEqualTo(String value) {
            addCriterion("is_prized =", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedNotEqualTo(String value) {
            addCriterion("is_prized <>", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedGreaterThan(String value) {
            addCriterion("is_prized >", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedGreaterThanOrEqualTo(String value) {
            addCriterion("is_prized >=", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedLessThan(String value) {
            addCriterion("is_prized <", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedLessThanOrEqualTo(String value) {
            addCriterion("is_prized <=", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedLike(String value) {
            addCriterion("is_prized like", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedNotLike(String value) {
            addCriterion("is_prized not like", value, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedIn(List<String> values) {
            addCriterion("is_prized in", values, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedNotIn(List<String> values) {
            addCriterion("is_prized not in", values, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedBetween(String value1, String value2) {
            addCriterion("is_prized between", value1, value2, "isPrized");
            return (Criteria) this;
        }

        public Criteria andIsPrizedNotBetween(String value1, String value2) {
            addCriterion("is_prized not between", value1, value2, "isPrized");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeLikeInsensitive(String value) {
            addCriterion("upper(lottery_code) like", value.toUpperCase(), "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andNoLikeInsensitive(String value) {
            addCriterion("upper(no) like", value.toUpperCase(), "no");
            return (Criteria) this;
        }

        public Criteria andGenPrizeLikeInsensitive(String value) {
            addCriterion("upper(gen_prize) like", value.toUpperCase(), "genPrize");
            return (Criteria) this;
        }

        public Criteria andTypeLikeInsensitive(String value) {
            addCriterion("upper(type) like", value.toUpperCase(), "type");
            return (Criteria) this;
        }

        public Criteria andRealPrizeLikeInsensitive(String value) {
            addCriterion("upper(real_prize) like", value.toUpperCase(), "realPrize");
            return (Criteria) this;
        }

        public Criteria andIsPrizedLikeInsensitive(String value) {
            addCriterion("upper(is_prized) like", value.toUpperCase(), "isPrized");
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