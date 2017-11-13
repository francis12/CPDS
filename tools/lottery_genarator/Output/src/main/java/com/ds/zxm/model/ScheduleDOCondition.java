package com.ds.zxm.model;

import com.netfinworks.restx.persist.jdbc.QueryConditionBase;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDOCondition extends QueryConditionBase {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScheduleDOCondition() {
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

        public Criteria andGenIdIsNull() {
            addCriterion("gen_id is null");
            return (Criteria) this;
        }

        public Criteria andGenIdIsNotNull() {
            addCriterion("gen_id is not null");
            return (Criteria) this;
        }

        public Criteria andGenIdEqualTo(String value) {
            addCriterion("gen_id =", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdNotEqualTo(String value) {
            addCriterion("gen_id <>", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdGreaterThan(String value) {
            addCriterion("gen_id >", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdGreaterThanOrEqualTo(String value) {
            addCriterion("gen_id >=", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdLessThan(String value) {
            addCriterion("gen_id <", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdLessThanOrEqualTo(String value) {
            addCriterion("gen_id <=", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdLike(String value) {
            addCriterion("gen_id like", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdNotLike(String value) {
            addCriterion("gen_id not like", value, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdIn(List<String> values) {
            addCriterion("gen_id in", values, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdNotIn(List<String> values) {
            addCriterion("gen_id not in", values, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdBetween(String value1, String value2) {
            addCriterion("gen_id between", value1, value2, "genId");
            return (Criteria) this;
        }

        public Criteria andGenIdNotBetween(String value1, String value2) {
            addCriterion("gen_id not between", value1, value2, "genId");
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

        public Criteria andWinNoIsNull() {
            addCriterion("win_no is null");
            return (Criteria) this;
        }

        public Criteria andWinNoIsNotNull() {
            addCriterion("win_no is not null");
            return (Criteria) this;
        }

        public Criteria andWinNoEqualTo(String value) {
            addCriterion("win_no =", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoNotEqualTo(String value) {
            addCriterion("win_no <>", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoGreaterThan(String value) {
            addCriterion("win_no >", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoGreaterThanOrEqualTo(String value) {
            addCriterion("win_no >=", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoLessThan(String value) {
            addCriterion("win_no <", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoLessThanOrEqualTo(String value) {
            addCriterion("win_no <=", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoLike(String value) {
            addCriterion("win_no like", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoNotLike(String value) {
            addCriterion("win_no not like", value, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoIn(List<String> values) {
            addCriterion("win_no in", values, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoNotIn(List<String> values) {
            addCriterion("win_no not in", values, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoBetween(String value1, String value2) {
            addCriterion("win_no between", value1, value2, "winNo");
            return (Criteria) this;
        }

        public Criteria andWinNoNotBetween(String value1, String value2) {
            addCriterion("win_no not between", value1, value2, "winNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoIsNull() {
            addCriterion("lose_no is null");
            return (Criteria) this;
        }

        public Criteria andLoseNoIsNotNull() {
            addCriterion("lose_no is not null");
            return (Criteria) this;
        }

        public Criteria andLoseNoEqualTo(String value) {
            addCriterion("lose_no =", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoNotEqualTo(String value) {
            addCriterion("lose_no <>", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoGreaterThan(String value) {
            addCriterion("lose_no >", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoGreaterThanOrEqualTo(String value) {
            addCriterion("lose_no >=", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoLessThan(String value) {
            addCriterion("lose_no <", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoLessThanOrEqualTo(String value) {
            addCriterion("lose_no <=", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoLike(String value) {
            addCriterion("lose_no like", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoNotLike(String value) {
            addCriterion("lose_no not like", value, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoIn(List<String> values) {
            addCriterion("lose_no in", values, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoNotIn(List<String> values) {
            addCriterion("lose_no not in", values, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoBetween(String value1, String value2) {
            addCriterion("lose_no between", value1, value2, "loseNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoNotBetween(String value1, String value2) {
            addCriterion("lose_no not between", value1, value2, "loseNo");
            return (Criteria) this;
        }

        public Criteria andMultipleIsNull() {
            addCriterion("multiple is null");
            return (Criteria) this;
        }

        public Criteria andMultipleIsNotNull() {
            addCriterion("multiple is not null");
            return (Criteria) this;
        }

        public Criteria andMultipleEqualTo(String value) {
            addCriterion("multiple =", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleNotEqualTo(String value) {
            addCriterion("multiple <>", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleGreaterThan(String value) {
            addCriterion("multiple >", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleGreaterThanOrEqualTo(String value) {
            addCriterion("multiple >=", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleLessThan(String value) {
            addCriterion("multiple <", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleLessThanOrEqualTo(String value) {
            addCriterion("multiple <=", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleLike(String value) {
            addCriterion("multiple like", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleNotLike(String value) {
            addCriterion("multiple not like", value, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleIn(List<String> values) {
            addCriterion("multiple in", values, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleNotIn(List<String> values) {
            addCriterion("multiple not in", values, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleBetween(String value1, String value2) {
            addCriterion("multiple between", value1, value2, "multiple");
            return (Criteria) this;
        }

        public Criteria andMultipleNotBetween(String value1, String value2) {
            addCriterion("multiple not between", value1, value2, "multiple");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeLikeInsensitive(String value) {
            addCriterion("upper(lottery_code) like", value.toUpperCase(), "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andGenIdLikeInsensitive(String value) {
            addCriterion("upper(gen_id) like", value.toUpperCase(), "genId");
            return (Criteria) this;
        }

        public Criteria andNoLikeInsensitive(String value) {
            addCriterion("upper(no) like", value.toUpperCase(), "no");
            return (Criteria) this;
        }

        public Criteria andWinNoLikeInsensitive(String value) {
            addCriterion("upper(win_no) like", value.toUpperCase(), "winNo");
            return (Criteria) this;
        }

        public Criteria andLoseNoLikeInsensitive(String value) {
            addCriterion("upper(lose_no) like", value.toUpperCase(), "loseNo");
            return (Criteria) this;
        }

        public Criteria andMultipleLikeInsensitive(String value) {
            addCriterion("upper(multiple) like", value.toUpperCase(), "multiple");
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