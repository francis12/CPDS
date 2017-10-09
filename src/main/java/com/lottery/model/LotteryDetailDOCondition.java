package com.lottery.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LotteryDetailDOCondition {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LotteryDetailDOCondition() {
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

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andNum1IsNull() {
            addCriterion("num1 is null");
            return (Criteria) this;
        }

        public Criteria andNum1IsNotNull() {
            addCriterion("num1 is not null");
            return (Criteria) this;
        }

        public Criteria andNum1EqualTo(String value) {
            addCriterion("num1 =", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1NotEqualTo(String value) {
            addCriterion("num1 <>", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1GreaterThan(String value) {
            addCriterion("num1 >", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1GreaterThanOrEqualTo(String value) {
            addCriterion("num1 >=", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1LessThan(String value) {
            addCriterion("num1 <", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1LessThanOrEqualTo(String value) {
            addCriterion("num1 <=", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1Like(String value) {
            addCriterion("num1 like", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1NotLike(String value) {
            addCriterion("num1 not like", value, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1In(List<String> values) {
            addCriterion("num1 in", values, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1NotIn(List<String> values) {
            addCriterion("num1 not in", values, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1Between(String value1, String value2) {
            addCriterion("num1 between", value1, value2, "num1");
            return (Criteria) this;
        }

        public Criteria andNum1NotBetween(String value1, String value2) {
            addCriterion("num1 not between", value1, value2, "num1");
            return (Criteria) this;
        }

        public Criteria andNum2IsNull() {
            addCriterion("num2 is null");
            return (Criteria) this;
        }

        public Criteria andNum2IsNotNull() {
            addCriterion("num2 is not null");
            return (Criteria) this;
        }

        public Criteria andNum2EqualTo(String value) {
            addCriterion("num2 =", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2NotEqualTo(String value) {
            addCriterion("num2 <>", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2GreaterThan(String value) {
            addCriterion("num2 >", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2GreaterThanOrEqualTo(String value) {
            addCriterion("num2 >=", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2LessThan(String value) {
            addCriterion("num2 <", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2LessThanOrEqualTo(String value) {
            addCriterion("num2 <=", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2Like(String value) {
            addCriterion("num2 like", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2NotLike(String value) {
            addCriterion("num2 not like", value, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2In(List<String> values) {
            addCriterion("num2 in", values, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2NotIn(List<String> values) {
            addCriterion("num2 not in", values, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2Between(String value1, String value2) {
            addCriterion("num2 between", value1, value2, "num2");
            return (Criteria) this;
        }

        public Criteria andNum2NotBetween(String value1, String value2) {
            addCriterion("num2 not between", value1, value2, "num2");
            return (Criteria) this;
        }

        public Criteria andNum3IsNull() {
            addCriterion("num3 is null");
            return (Criteria) this;
        }

        public Criteria andNum3IsNotNull() {
            addCriterion("num3 is not null");
            return (Criteria) this;
        }

        public Criteria andNum3EqualTo(String value) {
            addCriterion("num3 =", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3NotEqualTo(String value) {
            addCriterion("num3 <>", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3GreaterThan(String value) {
            addCriterion("num3 >", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3GreaterThanOrEqualTo(String value) {
            addCriterion("num3 >=", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3LessThan(String value) {
            addCriterion("num3 <", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3LessThanOrEqualTo(String value) {
            addCriterion("num3 <=", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3Like(String value) {
            addCriterion("num3 like", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3NotLike(String value) {
            addCriterion("num3 not like", value, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3In(List<String> values) {
            addCriterion("num3 in", values, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3NotIn(List<String> values) {
            addCriterion("num3 not in", values, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3Between(String value1, String value2) {
            addCriterion("num3 between", value1, value2, "num3");
            return (Criteria) this;
        }

        public Criteria andNum3NotBetween(String value1, String value2) {
            addCriterion("num3 not between", value1, value2, "num3");
            return (Criteria) this;
        }

        public Criteria andNum4IsNull() {
            addCriterion("num4 is null");
            return (Criteria) this;
        }

        public Criteria andNum4IsNotNull() {
            addCriterion("num4 is not null");
            return (Criteria) this;
        }

        public Criteria andNum4EqualTo(String value) {
            addCriterion("num4 =", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4NotEqualTo(String value) {
            addCriterion("num4 <>", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4GreaterThan(String value) {
            addCriterion("num4 >", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4GreaterThanOrEqualTo(String value) {
            addCriterion("num4 >=", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4LessThan(String value) {
            addCriterion("num4 <", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4LessThanOrEqualTo(String value) {
            addCriterion("num4 <=", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4Like(String value) {
            addCriterion("num4 like", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4NotLike(String value) {
            addCriterion("num4 not like", value, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4In(List<String> values) {
            addCriterion("num4 in", values, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4NotIn(List<String> values) {
            addCriterion("num4 not in", values, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4Between(String value1, String value2) {
            addCriterion("num4 between", value1, value2, "num4");
            return (Criteria) this;
        }

        public Criteria andNum4NotBetween(String value1, String value2) {
            addCriterion("num4 not between", value1, value2, "num4");
            return (Criteria) this;
        }

        public Criteria andNum5IsNull() {
            addCriterion("num5 is null");
            return (Criteria) this;
        }

        public Criteria andNum5IsNotNull() {
            addCriterion("num5 is not null");
            return (Criteria) this;
        }

        public Criteria andNum5EqualTo(String value) {
            addCriterion("num5 =", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5NotEqualTo(String value) {
            addCriterion("num5 <>", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5GreaterThan(String value) {
            addCriterion("num5 >", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5GreaterThanOrEqualTo(String value) {
            addCriterion("num5 >=", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5LessThan(String value) {
            addCriterion("num5 <", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5LessThanOrEqualTo(String value) {
            addCriterion("num5 <=", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5Like(String value) {
            addCriterion("num5 like", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5NotLike(String value) {
            addCriterion("num5 not like", value, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5In(List<String> values) {
            addCriterion("num5 in", values, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5NotIn(List<String> values) {
            addCriterion("num5 not in", values, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5Between(String value1, String value2) {
            addCriterion("num5 between", value1, value2, "num5");
            return (Criteria) this;
        }

        public Criteria andNum5NotBetween(String value1, String value2) {
            addCriterion("num5 not between", value1, value2, "num5");
            return (Criteria) this;
        }

        public Criteria andNum6IsNull() {
            addCriterion("num6 is null");
            return (Criteria) this;
        }

        public Criteria andNum6IsNotNull() {
            addCriterion("num6 is not null");
            return (Criteria) this;
        }

        public Criteria andNum6EqualTo(String value) {
            addCriterion("num6 =", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6NotEqualTo(String value) {
            addCriterion("num6 <>", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6GreaterThan(String value) {
            addCriterion("num6 >", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6GreaterThanOrEqualTo(String value) {
            addCriterion("num6 >=", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6LessThan(String value) {
            addCriterion("num6 <", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6LessThanOrEqualTo(String value) {
            addCriterion("num6 <=", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6Like(String value) {
            addCriterion("num6 like", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6NotLike(String value) {
            addCriterion("num6 not like", value, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6In(List<String> values) {
            addCriterion("num6 in", values, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6NotIn(List<String> values) {
            addCriterion("num6 not in", values, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6Between(String value1, String value2) {
            addCriterion("num6 between", value1, value2, "num6");
            return (Criteria) this;
        }

        public Criteria andNum6NotBetween(String value1, String value2) {
            addCriterion("num6 not between", value1, value2, "num6");
            return (Criteria) this;
        }

        public Criteria andNum7IsNull() {
            addCriterion("num7 is null");
            return (Criteria) this;
        }

        public Criteria andNum7IsNotNull() {
            addCriterion("num7 is not null");
            return (Criteria) this;
        }

        public Criteria andNum7EqualTo(String value) {
            addCriterion("num7 =", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7NotEqualTo(String value) {
            addCriterion("num7 <>", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7GreaterThan(String value) {
            addCriterion("num7 >", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7GreaterThanOrEqualTo(String value) {
            addCriterion("num7 >=", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7LessThan(String value) {
            addCriterion("num7 <", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7LessThanOrEqualTo(String value) {
            addCriterion("num7 <=", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7Like(String value) {
            addCriterion("num7 like", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7NotLike(String value) {
            addCriterion("num7 not like", value, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7In(List<String> values) {
            addCriterion("num7 in", values, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7NotIn(List<String> values) {
            addCriterion("num7 not in", values, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7Between(String value1, String value2) {
            addCriterion("num7 between", value1, value2, "num7");
            return (Criteria) this;
        }

        public Criteria andNum7NotBetween(String value1, String value2) {
            addCriterion("num7 not between", value1, value2, "num7");
            return (Criteria) this;
        }

        public Criteria andNum8IsNull() {
            addCriterion("num8 is null");
            return (Criteria) this;
        }

        public Criteria andNum8IsNotNull() {
            addCriterion("num8 is not null");
            return (Criteria) this;
        }

        public Criteria andNum8EqualTo(String value) {
            addCriterion("num8 =", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8NotEqualTo(String value) {
            addCriterion("num8 <>", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8GreaterThan(String value) {
            addCriterion("num8 >", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8GreaterThanOrEqualTo(String value) {
            addCriterion("num8 >=", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8LessThan(String value) {
            addCriterion("num8 <", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8LessThanOrEqualTo(String value) {
            addCriterion("num8 <=", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8Like(String value) {
            addCriterion("num8 like", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8NotLike(String value) {
            addCriterion("num8 not like", value, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8In(List<String> values) {
            addCriterion("num8 in", values, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8NotIn(List<String> values) {
            addCriterion("num8 not in", values, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8Between(String value1, String value2) {
            addCriterion("num8 between", value1, value2, "num8");
            return (Criteria) this;
        }

        public Criteria andNum8NotBetween(String value1, String value2) {
            addCriterion("num8 not between", value1, value2, "num8");
            return (Criteria) this;
        }

        public Criteria andAliasNoIsNull() {
            addCriterion("alias_no is null");
            return (Criteria) this;
        }

        public Criteria andAliasNoIsNotNull() {
            addCriterion("alias_no is not null");
            return (Criteria) this;
        }

        public Criteria andAliasNoEqualTo(String value) {
            addCriterion("alias_no =", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoNotEqualTo(String value) {
            addCriterion("alias_no <>", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoGreaterThan(String value) {
            addCriterion("alias_no >", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoGreaterThanOrEqualTo(String value) {
            addCriterion("alias_no >=", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoLessThan(String value) {
            addCriterion("alias_no <", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoLessThanOrEqualTo(String value) {
            addCriterion("alias_no <=", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoLike(String value) {
            addCriterion("alias_no like", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoNotLike(String value) {
            addCriterion("alias_no not like", value, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoIn(List<String> values) {
            addCriterion("alias_no in", values, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoNotIn(List<String> values) {
            addCriterion("alias_no not in", values, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoBetween(String value1, String value2) {
            addCriterion("alias_no between", value1, value2, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andAliasNoNotBetween(String value1, String value2) {
            addCriterion("alias_no not between", value1, value2, "aliasNo");
            return (Criteria) this;
        }

        public Criteria andLotteryDateIsNull() {
            addCriterion("lottery_date is null");
            return (Criteria) this;
        }

        public Criteria andLotteryDateIsNotNull() {
            addCriterion("lottery_date is not null");
            return (Criteria) this;
        }

        public Criteria andLotteryDateEqualTo(String value) {
            addCriterion("lottery_date =", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateNotEqualTo(String value) {
            addCriterion("lottery_date <>", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateGreaterThan(String value) {
            addCriterion("lottery_date >", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateGreaterThanOrEqualTo(String value) {
            addCriterion("lottery_date >=", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateLessThan(String value) {
            addCriterion("lottery_date <", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateLessThanOrEqualTo(String value) {
            addCriterion("lottery_date <=", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateLike(String value) {
            addCriterion("lottery_date like", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateNotLike(String value) {
            addCriterion("lottery_date not like", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateIn(List<String> values) {
            addCriterion("lottery_date in", values, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateNotIn(List<String> values) {
            addCriterion("lottery_date not in", values, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateBetween(String value1, String value2) {
            addCriterion("lottery_date between", value1, value2, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateNotBetween(String value1, String value2) {
            addCriterion("lottery_date not between", value1, value2, "lotteryDate");
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

        public Criteria andNum1LikeInsensitive(String value) {
            addCriterion("upper(num1) like", value.toUpperCase(), "num1");
            return (Criteria) this;
        }

        public Criteria andNum2LikeInsensitive(String value) {
            addCriterion("upper(num2) like", value.toUpperCase(), "num2");
            return (Criteria) this;
        }

        public Criteria andNum3LikeInsensitive(String value) {
            addCriterion("upper(num3) like", value.toUpperCase(), "num3");
            return (Criteria) this;
        }

        public Criteria andNum4LikeInsensitive(String value) {
            addCriterion("upper(num4) like", value.toUpperCase(), "num4");
            return (Criteria) this;
        }

        public Criteria andNum5LikeInsensitive(String value) {
            addCriterion("upper(num5) like", value.toUpperCase(), "num5");
            return (Criteria) this;
        }

        public Criteria andNum6LikeInsensitive(String value) {
            addCriterion("upper(num6) like", value.toUpperCase(), "num6");
            return (Criteria) this;
        }

        public Criteria andNum7LikeInsensitive(String value) {
            addCriterion("upper(num7) like", value.toUpperCase(), "num7");
            return (Criteria) this;
        }

        public Criteria andNum8LikeInsensitive(String value) {
            addCriterion("upper(num8) like", value.toUpperCase(), "num8");
            return (Criteria) this;
        }

        public Criteria andAliasNoLikeInsensitive(String value) {
            addCriterion("upper(alias_no) like", value.toUpperCase(), "aliasNo");
            return (Criteria) this;
        }

        public Criteria andLotteryDateLikeInsensitive(String value) {
            addCriterion("upper(lottery_date) like", value.toUpperCase(), "lotteryDate");
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