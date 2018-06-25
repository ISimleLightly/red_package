package com.cailife.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRedPackageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserRedPackageExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
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

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdIsNull() {
            addCriterion("red_package_id is null");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdIsNotNull() {
            addCriterion("red_package_id is not null");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdEqualTo(String value) {
            addCriterion("red_package_id =", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdNotEqualTo(String value) {
            addCriterion("red_package_id <>", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdGreaterThan(String value) {
            addCriterion("red_package_id >", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdGreaterThanOrEqualTo(String value) {
            addCriterion("red_package_id >=", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdLessThan(String value) {
            addCriterion("red_package_id <", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdLessThanOrEqualTo(String value) {
            addCriterion("red_package_id <=", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdLike(String value) {
            addCriterion("red_package_id like", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdNotLike(String value) {
            addCriterion("red_package_id not like", value, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdIn(List<String> values) {
            addCriterion("red_package_id in", values, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdNotIn(List<String> values) {
            addCriterion("red_package_id not in", values, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdBetween(String value1, String value2) {
            addCriterion("red_package_id between", value1, value2, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andRedPackageIdNotBetween(String value1, String value2) {
            addCriterion("red_package_id not between", value1, value2, "redPackageId");
            return (Criteria) this;
        }

        public Criteria andGrabTimeIsNull() {
            addCriterion("grab_time is null");
            return (Criteria) this;
        }

        public Criteria andGrabTimeIsNotNull() {
            addCriterion("grab_time is not null");
            return (Criteria) this;
        }

        public Criteria andGrabTimeEqualTo(Date value) {
            addCriterion("grab_time =", value, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeNotEqualTo(Date value) {
            addCriterion("grab_time <>", value, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeGreaterThan(Date value) {
            addCriterion("grab_time >", value, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("grab_time >=", value, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeLessThan(Date value) {
            addCriterion("grab_time <", value, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeLessThanOrEqualTo(Date value) {
            addCriterion("grab_time <=", value, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeIn(List<Date> values) {
            addCriterion("grab_time in", values, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeNotIn(List<Date> values) {
            addCriterion("grab_time not in", values, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeBetween(Date value1, Date value2) {
            addCriterion("grab_time between", value1, value2, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabTimeNotBetween(Date value1, Date value2) {
            addCriterion("grab_time not between", value1, value2, "grabTime");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyIsNull() {
            addCriterion("grab_money is null");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyIsNotNull() {
            addCriterion("grab_money is not null");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyEqualTo(Double value) {
            addCriterion("grab_money =", value, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyNotEqualTo(Double value) {
            addCriterion("grab_money <>", value, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyGreaterThan(Double value) {
            addCriterion("grab_money >", value, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyGreaterThanOrEqualTo(Double value) {
            addCriterion("grab_money >=", value, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyLessThan(Double value) {
            addCriterion("grab_money <", value, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyLessThanOrEqualTo(Double value) {
            addCriterion("grab_money <=", value, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyIn(List<Double> values) {
            addCriterion("grab_money in", values, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyNotIn(List<Double> values) {
            addCriterion("grab_money not in", values, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyBetween(Double value1, Double value2) {
            addCriterion("grab_money between", value1, value2, "grabMoney");
            return (Criteria) this;
        }

        public Criteria andGrabMoneyNotBetween(Double value1, Double value2) {
            addCriterion("grab_money not between", value1, value2, "grabMoney");
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