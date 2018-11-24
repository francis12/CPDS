package com.ssc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TCFFCPRIZECondition  {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TCFFCPRIZECondition() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andPrizeIsNull() {
            addCriterion("prizeschedule is null");
            return (Criteria) this;
        }

        public Criteria andPrizeIsNotNull() {
            addCriterion("prizeschedule is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeEqualTo(String value) {
            addCriterion("prizeschedule =", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeNotEqualTo(String value) {
            addCriterion("prizeschedule <>", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThan(String value) {
            addCriterion("prizeschedule >", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeGreaterThanOrEqualTo(String value) {
            addCriterion("prizeschedule >=", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThan(String value) {
            addCriterion("prizeschedule <", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeLessThanOrEqualTo(String value) {
            addCriterion("prizeschedule <=", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeLike(String value) {
            addCriterion("prizeschedule like", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeNotLike(String value) {
            addCriterion("prizeschedule not like", value, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeIn(List<String> values) {
            addCriterion("prizeschedule in", values, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeNotIn(List<String> values) {
            addCriterion("prizeschedule not in", values, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeBetween(String value1, String value2) {
            addCriterion("prizeschedule between", value1, value2, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andPrizeNotBetween(String value1, String value2) {
            addCriterion("prizeschedule not between", value1, value2, "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andWanIsNull() {
            addCriterion("wan is null");
            return (Criteria) this;
        }

        public Criteria andWanIsNotNull() {
            addCriterion("wan is not null");
            return (Criteria) this;
        }

        public Criteria andWanEqualTo(Integer value) {
            addCriterion("wan =", value, "wan");
            return (Criteria) this;
        }

        public Criteria andWanNotEqualTo(Integer value) {
            addCriterion("wan <>", value, "wan");
            return (Criteria) this;
        }

        public Criteria andWanGreaterThan(Integer value) {
            addCriterion("wan >", value, "wan");
            return (Criteria) this;
        }

        public Criteria andWanGreaterThanOrEqualTo(Integer value) {
            addCriterion("wan >=", value, "wan");
            return (Criteria) this;
        }

        public Criteria andWanLessThan(Integer value) {
            addCriterion("wan <", value, "wan");
            return (Criteria) this;
        }

        public Criteria andWanLessThanOrEqualTo(Integer value) {
            addCriterion("wan <=", value, "wan");
            return (Criteria) this;
        }

        public Criteria andWanIn(List<Integer> values) {
            addCriterion("wan in", values, "wan");
            return (Criteria) this;
        }

        public Criteria andWanNotIn(List<Integer> values) {
            addCriterion("wan not in", values, "wan");
            return (Criteria) this;
        }

        public Criteria andWanBetween(Integer value1, Integer value2) {
            addCriterion("wan between", value1, value2, "wan");
            return (Criteria) this;
        }

        public Criteria andWanNotBetween(Integer value1, Integer value2) {
            addCriterion("wan not between", value1, value2, "wan");
            return (Criteria) this;
        }

        public Criteria andQianIsNull() {
            addCriterion("qian is null");
            return (Criteria) this;
        }

        public Criteria andQianIsNotNull() {
            addCriterion("qian is not null");
            return (Criteria) this;
        }

        public Criteria andQianEqualTo(Integer value) {
            addCriterion("qian =", value, "qian");
            return (Criteria) this;
        }

        public Criteria andQianNotEqualTo(Integer value) {
            addCriterion("qian <>", value, "qian");
            return (Criteria) this;
        }

        public Criteria andQianGreaterThan(Integer value) {
            addCriterion("qian >", value, "qian");
            return (Criteria) this;
        }

        public Criteria andQianGreaterThanOrEqualTo(Integer value) {
            addCriterion("qian >=", value, "qian");
            return (Criteria) this;
        }

        public Criteria andQianLessThan(Integer value) {
            addCriterion("qian <", value, "qian");
            return (Criteria) this;
        }

        public Criteria andQianLessThanOrEqualTo(Integer value) {
            addCriterion("qian <=", value, "qian");
            return (Criteria) this;
        }

        public Criteria andQianIn(List<Integer> values) {
            addCriterion("qian in", values, "qian");
            return (Criteria) this;
        }

        public Criteria andQianNotIn(List<Integer> values) {
            addCriterion("qian not in", values, "qian");
            return (Criteria) this;
        }

        public Criteria andQianBetween(Integer value1, Integer value2) {
            addCriterion("qian between", value1, value2, "qian");
            return (Criteria) this;
        }

        public Criteria andQianNotBetween(Integer value1, Integer value2) {
            addCriterion("qian not between", value1, value2, "qian");
            return (Criteria) this;
        }

        public Criteria andBaiIsNull() {
            addCriterion("bai is null");
            return (Criteria) this;
        }

        public Criteria andBaiIsNotNull() {
            addCriterion("bai is not null");
            return (Criteria) this;
        }

        public Criteria andBaiEqualTo(Integer value) {
            addCriterion("bai =", value, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiNotEqualTo(Integer value) {
            addCriterion("bai <>", value, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiGreaterThan(Integer value) {
            addCriterion("bai >", value, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiGreaterThanOrEqualTo(Integer value) {
            addCriterion("bai >=", value, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiLessThan(Integer value) {
            addCriterion("bai <", value, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiLessThanOrEqualTo(Integer value) {
            addCriterion("bai <=", value, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiIn(List<Integer> values) {
            addCriterion("bai in", values, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiNotIn(List<Integer> values) {
            addCriterion("bai not in", values, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiBetween(Integer value1, Integer value2) {
            addCriterion("bai between", value1, value2, "bai");
            return (Criteria) this;
        }

        public Criteria andBaiNotBetween(Integer value1, Integer value2) {
            addCriterion("bai not between", value1, value2, "bai");
            return (Criteria) this;
        }

        public Criteria andShiIsNull() {
            addCriterion("shi is null");
            return (Criteria) this;
        }

        public Criteria andShiIsNotNull() {
            addCriterion("shi is not null");
            return (Criteria) this;
        }

        public Criteria andShiEqualTo(Integer value) {
            addCriterion("shi =", value, "shi");
            return (Criteria) this;
        }

        public Criteria andShiNotEqualTo(Integer value) {
            addCriterion("shi <>", value, "shi");
            return (Criteria) this;
        }

        public Criteria andShiGreaterThan(Integer value) {
            addCriterion("shi >", value, "shi");
            return (Criteria) this;
        }

        public Criteria andShiGreaterThanOrEqualTo(Integer value) {
            addCriterion("shi >=", value, "shi");
            return (Criteria) this;
        }

        public Criteria andShiLessThan(Integer value) {
            addCriterion("shi <", value, "shi");
            return (Criteria) this;
        }

        public Criteria andShiLessThanOrEqualTo(Integer value) {
            addCriterion("shi <=", value, "shi");
            return (Criteria) this;
        }

        public Criteria andShiIn(List<Integer> values) {
            addCriterion("shi in", values, "shi");
            return (Criteria) this;
        }

        public Criteria andShiNotIn(List<Integer> values) {
            addCriterion("shi not in", values, "shi");
            return (Criteria) this;
        }

        public Criteria andShiBetween(Integer value1, Integer value2) {
            addCriterion("shi between", value1, value2, "shi");
            return (Criteria) this;
        }

        public Criteria andShiNotBetween(Integer value1, Integer value2) {
            addCriterion("shi not between", value1, value2, "shi");
            return (Criteria) this;
        }

        public Criteria andGeIsNull() {
            addCriterion("ge is null");
            return (Criteria) this;
        }

        public Criteria andGeIsNotNull() {
            addCriterion("ge is not null");
            return (Criteria) this;
        }

        public Criteria andGeEqualTo(Integer value) {
            addCriterion("ge =", value, "ge");
            return (Criteria) this;
        }

        public Criteria andGeNotEqualTo(Integer value) {
            addCriterion("ge <>", value, "ge");
            return (Criteria) this;
        }

        public Criteria andGeGreaterThan(Integer value) {
            addCriterion("ge >", value, "ge");
            return (Criteria) this;
        }

        public Criteria andGeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ge >=", value, "ge");
            return (Criteria) this;
        }

        public Criteria andGeLessThan(Integer value) {
            addCriterion("ge <", value, "ge");
            return (Criteria) this;
        }

        public Criteria andGeLessThanOrEqualTo(Integer value) {
            addCriterion("ge <=", value, "ge");
            return (Criteria) this;
        }

        public Criteria andGeIn(List<Integer> values) {
            addCriterion("ge in", values, "ge");
            return (Criteria) this;
        }

        public Criteria andGeNotIn(List<Integer> values) {
            addCriterion("ge not in", values, "ge");
            return (Criteria) this;
        }

        public Criteria andGeBetween(Integer value1, Integer value2) {
            addCriterion("ge between", value1, value2, "ge");
            return (Criteria) this;
        }

        public Criteria andGeNotBetween(Integer value1, Integer value2) {
            addCriterion("ge not between", value1, value2, "ge");
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

        public Criteria andOnlineNumEqualTo(Integer value) {
            addCriterion("online_num =", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumNotEqualTo(Integer value) {
            addCriterion("online_num <>", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumGreaterThan(Integer value) {
            addCriterion("online_num >", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("online_num >=", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumLessThan(Integer value) {
            addCriterion("online_num <", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumLessThanOrEqualTo(Integer value) {
            addCriterion("online_num <=", value, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumIn(List<Integer> values) {
            addCriterion("online_num in", values, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumNotIn(List<Integer> values) {
            addCriterion("online_num not in", values, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumBetween(Integer value1, Integer value2) {
            addCriterion("online_num between", value1, value2, "onlineNum");
            return (Criteria) this;
        }

        public Criteria andOnlineNumNotBetween(Integer value1, Integer value2) {
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

        public Criteria andAdjustNumEqualTo(Integer value) {
            addCriterion("adjust_num =", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumNotEqualTo(Integer value) {
            addCriterion("adjust_num <>", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumGreaterThan(Integer value) {
            addCriterion("adjust_num >", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("adjust_num >=", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumLessThan(Integer value) {
            addCriterion("adjust_num <", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumLessThanOrEqualTo(Integer value) {
            addCriterion("adjust_num <=", value, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumIn(List<Integer> values) {
            addCriterion("adjust_num in", values, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumNotIn(List<Integer> values) {
            addCriterion("adjust_num not in", values, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumBetween(Integer value1, Integer value2) {
            addCriterion("adjust_num between", value1, value2, "adjustNum");
            return (Criteria) this;
        }

        public Criteria andAdjustNumNotBetween(Integer value1, Integer value2) {
            addCriterion("adjust_num not between", value1, value2, "adjustNum");
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

        public Criteria andLotteryDateEqualTo(Date value) {
            addCriterionForJDBCDate("lottery_date =", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("lottery_date <>", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("lottery_date >", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lottery_date >=", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateLessThan(Date value) {
            addCriterionForJDBCDate("lottery_date <", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("lottery_date <=", value, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateIn(List<Date> values) {
            addCriterionForJDBCDate("lottery_date in", values, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("lottery_date not in", values, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lottery_date between", value1, value2, "lotteryDate");
            return (Criteria) this;
        }

        public Criteria andLotteryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("lottery_date not between", value1, value2, "lotteryDate");
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

        public Criteria andLotteryCodeLikeInsensitive(String value) {
            addCriterion("upper(lottery_code) like", value.toUpperCase(), "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andNoLikeInsensitive(String value) {
            addCriterion("upper(no) like", value.toUpperCase(), "no");
            return (Criteria) this;
        }

        public Criteria andPrizeLikeInsensitive(String value) {
            addCriterion("upper(prizeschedule) like", value.toUpperCase(), "prizeschedule");
            return (Criteria) this;
        }

        public Criteria andAliasNoLikeInsensitive(String value) {
            addCriterion("upper(alias_no) like", value.toUpperCase(), "aliasNo");
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