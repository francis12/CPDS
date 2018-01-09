package com.ds.zxm.model;

import com.netfinworks.restx.persist.jdbc.QueryConditionBase;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StrategyDOCondition extends QueryConditionBase {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StrategyDOCondition() {
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

        public Criteria andStartNoIsNull() {
            addCriterion("start_no is null");
            return (Criteria) this;
        }

        public Criteria andStartNoIsNotNull() {
            addCriterion("start_no is not null");
            return (Criteria) this;
        }

        public Criteria andStartNoEqualTo(String value) {
            addCriterion("start_no =", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoNotEqualTo(String value) {
            addCriterion("start_no <>", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoGreaterThan(String value) {
            addCriterion("start_no >", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoGreaterThanOrEqualTo(String value) {
            addCriterion("start_no >=", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoLessThan(String value) {
            addCriterion("start_no <", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoLessThanOrEqualTo(String value) {
            addCriterion("start_no <=", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoLike(String value) {
            addCriterion("start_no like", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoNotLike(String value) {
            addCriterion("start_no not like", value, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoIn(List<String> values) {
            addCriterion("start_no in", values, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoNotIn(List<String> values) {
            addCriterion("start_no not in", values, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoBetween(String value1, String value2) {
            addCriterion("start_no between", value1, value2, "startNo");
            return (Criteria) this;
        }

        public Criteria andStartNoNotBetween(String value1, String value2) {
            addCriterion("start_no not between", value1, value2, "startNo");
            return (Criteria) this;
        }

        public Criteria andEndNoIsNull() {
            addCriterion("end_no is null");
            return (Criteria) this;
        }

        public Criteria andEndNoIsNotNull() {
            addCriterion("end_no is not null");
            return (Criteria) this;
        }

        public Criteria andEndNoEqualTo(String value) {
            addCriterion("end_no =", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoNotEqualTo(String value) {
            addCriterion("end_no <>", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoGreaterThan(String value) {
            addCriterion("end_no >", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoGreaterThanOrEqualTo(String value) {
            addCriterion("end_no >=", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoLessThan(String value) {
            addCriterion("end_no <", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoLessThanOrEqualTo(String value) {
            addCriterion("end_no <=", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoLike(String value) {
            addCriterion("end_no like", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoNotLike(String value) {
            addCriterion("end_no not like", value, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoIn(List<String> values) {
            addCriterion("end_no in", values, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoNotIn(List<String> values) {
            addCriterion("end_no not in", values, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoBetween(String value1, String value2) {
            addCriterion("end_no between", value1, value2, "endNo");
            return (Criteria) this;
        }

        public Criteria andEndNoNotBetween(String value1, String value2) {
            addCriterion("end_no not between", value1, value2, "endNo");
            return (Criteria) this;
        }

        public Criteria andTotalAmtIsNull() {
            addCriterion("total_amt is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmtIsNotNull() {
            addCriterion("total_amt is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmtEqualTo(BigDecimal value) {
            addCriterion("total_amt =", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtNotEqualTo(BigDecimal value) {
            addCriterion("total_amt <>", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtGreaterThan(BigDecimal value) {
            addCriterion("total_amt >", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amt >=", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtLessThan(BigDecimal value) {
            addCriterion("total_amt <", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amt <=", value, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtIn(List<BigDecimal> values) {
            addCriterion("total_amt in", values, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtNotIn(List<BigDecimal> values) {
            addCriterion("total_amt not in", values, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amt between", value1, value2, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andTotalAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amt not between", value1, value2, "totalAmt");
            return (Criteria) this;
        }

        public Criteria andCurProfitIsNull() {
            addCriterion("cur_profit is null");
            return (Criteria) this;
        }

        public Criteria andCurProfitIsNotNull() {
            addCriterion("cur_profit is not null");
            return (Criteria) this;
        }

        public Criteria andCurProfitEqualTo(BigDecimal value) {
            addCriterion("cur_profit =", value, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitNotEqualTo(BigDecimal value) {
            addCriterion("cur_profit <>", value, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitGreaterThan(BigDecimal value) {
            addCriterion("cur_profit >", value, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cur_profit >=", value, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitLessThan(BigDecimal value) {
            addCriterion("cur_profit <", value, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cur_profit <=", value, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitIn(List<BigDecimal> values) {
            addCriterion("cur_profit in", values, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitNotIn(List<BigDecimal> values) {
            addCriterion("cur_profit not in", values, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cur_profit between", value1, value2, "curProfit");
            return (Criteria) this;
        }

        public Criteria andCurProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cur_profit not between", value1, value2, "curProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitIsNull() {
            addCriterion("max_profit is null");
            return (Criteria) this;
        }

        public Criteria andMaxProfitIsNotNull() {
            addCriterion("max_profit is not null");
            return (Criteria) this;
        }

        public Criteria andMaxProfitEqualTo(BigDecimal value) {
            addCriterion("max_profit =", value, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitNotEqualTo(BigDecimal value) {
            addCriterion("max_profit <>", value, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitGreaterThan(BigDecimal value) {
            addCriterion("max_profit >", value, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_profit >=", value, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitLessThan(BigDecimal value) {
            addCriterion("max_profit <", value, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_profit <=", value, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitIn(List<BigDecimal> values) {
            addCriterion("max_profit in", values, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitNotIn(List<BigDecimal> values) {
            addCriterion("max_profit not in", values, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_profit between", value1, value2, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMaxProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_profit not between", value1, value2, "maxProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitIsNull() {
            addCriterion("min_profit is null");
            return (Criteria) this;
        }

        public Criteria andMinProfitIsNotNull() {
            addCriterion("min_profit is not null");
            return (Criteria) this;
        }

        public Criteria andMinProfitEqualTo(BigDecimal value) {
            addCriterion("min_profit =", value, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitNotEqualTo(BigDecimal value) {
            addCriterion("min_profit <>", value, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitGreaterThan(BigDecimal value) {
            addCriterion("min_profit >", value, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_profit >=", value, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitLessThan(BigDecimal value) {
            addCriterion("min_profit <", value, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_profit <=", value, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitIn(List<BigDecimal> values) {
            addCriterion("min_profit in", values, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitNotIn(List<BigDecimal> values) {
            addCriterion("min_profit not in", values, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_profit between", value1, value2, "minProfit");
            return (Criteria) this;
        }

        public Criteria andMinProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_profit not between", value1, value2, "minProfit");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNull() {
            addCriterion("seq_no is null");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNotNull() {
            addCriterion("seq_no is not null");
            return (Criteria) this;
        }

        public Criteria andSeqNoEqualTo(String value) {
            addCriterion("seq_no =", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotEqualTo(String value) {
            addCriterion("seq_no <>", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThan(String value) {
            addCriterion("seq_no >", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThanOrEqualTo(String value) {
            addCriterion("seq_no >=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThan(String value) {
            addCriterion("seq_no <", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThanOrEqualTo(String value) {
            addCriterion("seq_no <=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLike(String value) {
            addCriterion("seq_no like", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotLike(String value) {
            addCriterion("seq_no not like", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoIn(List<String> values) {
            addCriterion("seq_no in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotIn(List<String> values) {
            addCriterion("seq_no not in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoBetween(String value1, String value2) {
            addCriterion("seq_no between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotBetween(String value1, String value2) {
            addCriterion("seq_no not between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andLotteryCodeLikeInsensitive(String value) {
            addCriterion("upper(lottery_code) like", value.toUpperCase(), "lotteryCode");
            return (Criteria) this;
        }

        public Criteria andStartNoLikeInsensitive(String value) {
            addCriterion("upper(start_no) like", value.toUpperCase(), "startNo");
            return (Criteria) this;
        }

        public Criteria andEndNoLikeInsensitive(String value) {
            addCriterion("upper(end_no) like", value.toUpperCase(), "endNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLikeInsensitive(String value) {
            addCriterion("upper(seq_no) like", value.toUpperCase(), "seqNo");
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