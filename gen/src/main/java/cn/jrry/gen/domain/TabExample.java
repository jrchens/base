package cn.jrry.gen.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TabExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TabExample() {
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

        protected void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
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

        public Criteria andTinyintBooleanIsNull() {
            addCriterion("tinyint_boolean is null");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanIsNotNull() {
            addCriterion("tinyint_boolean is not null");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanEqualTo(Boolean value) {
            addCriterion("tinyint_boolean =", value, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanNotEqualTo(Boolean value) {
            addCriterion("tinyint_boolean <>", value, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanGreaterThan(Boolean value) {
            addCriterion("tinyint_boolean >", value, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanGreaterThanOrEqualTo(Boolean value) {
            addCriterion("tinyint_boolean >=", value, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanLessThan(Boolean value) {
            addCriterion("tinyint_boolean <", value, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanLessThanOrEqualTo(Boolean value) {
            addCriterion("tinyint_boolean <=", value, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanIn(List<Boolean> values) {
            addCriterion("tinyint_boolean in", values, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanNotIn(List<Boolean> values) {
            addCriterion("tinyint_boolean not in", values, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanBetween(Boolean value1, Boolean value2) {
            addCriterion("tinyint_boolean between", value1, value2, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andTinyintBooleanNotBetween(Boolean value1, Boolean value2) {
            addCriterion("tinyint_boolean not between", value1, value2, "tinyintBoolean");
            return (Criteria) this;
        }

        public Criteria andIntIntegerIsNull() {
            addCriterion("int_integer is null");
            return (Criteria) this;
        }

        public Criteria andIntIntegerIsNotNull() {
            addCriterion("int_integer is not null");
            return (Criteria) this;
        }

        public Criteria andIntIntegerEqualTo(Integer value) {
            addCriterion("int_integer =", value, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerNotEqualTo(Integer value) {
            addCriterion("int_integer <>", value, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerGreaterThan(Integer value) {
            addCriterion("int_integer >", value, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerGreaterThanOrEqualTo(Integer value) {
            addCriterion("int_integer >=", value, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerLessThan(Integer value) {
            addCriterion("int_integer <", value, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerLessThanOrEqualTo(Integer value) {
            addCriterion("int_integer <=", value, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerIn(List<Integer> values) {
            addCriterion("int_integer in", values, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerNotIn(List<Integer> values) {
            addCriterion("int_integer not in", values, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerBetween(Integer value1, Integer value2) {
            addCriterion("int_integer between", value1, value2, "intInteger");
            return (Criteria) this;
        }

        public Criteria andIntIntegerNotBetween(Integer value1, Integer value2) {
            addCriterion("int_integer not between", value1, value2, "intInteger");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleIsNull() {
            addCriterion("double_double is null");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleIsNotNull() {
            addCriterion("double_double is not null");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleEqualTo(Double value) {
            addCriterion("double_double =", value, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleNotEqualTo(Double value) {
            addCriterion("double_double <>", value, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleGreaterThan(Double value) {
            addCriterion("double_double >", value, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleGreaterThanOrEqualTo(Double value) {
            addCriterion("double_double >=", value, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleLessThan(Double value) {
            addCriterion("double_double <", value, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleLessThanOrEqualTo(Double value) {
            addCriterion("double_double <=", value, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleIn(List<Double> values) {
            addCriterion("double_double in", values, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleNotIn(List<Double> values) {
            addCriterion("double_double not in", values, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleBetween(Double value1, Double value2) {
            addCriterion("double_double between", value1, value2, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDoubleDoubleNotBetween(Double value1, Double value2) {
            addCriterion("double_double not between", value1, value2, "doubleDouble");
            return (Criteria) this;
        }

        public Criteria andDateDateIsNull() {
            addCriterion("date_date is null");
            return (Criteria) this;
        }

        public Criteria andDateDateIsNotNull() {
            addCriterion("date_date is not null");
            return (Criteria) this;
        }

        public Criteria andDateDateEqualTo(Date value) {
            addCriterionForJDBCDate("date_date =", value, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("date_date <>", value, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateGreaterThan(Date value) {
            addCriterionForJDBCDate("date_date >", value, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date_date >=", value, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateLessThan(Date value) {
            addCriterionForJDBCDate("date_date <", value, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date_date <=", value, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateIn(List<Date> values) {
            addCriterionForJDBCDate("date_date in", values, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("date_date not in", values, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date_date between", value1, value2, "dateDate");
            return (Criteria) this;
        }

        public Criteria andDateDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date_date not between", value1, value2, "dateDate");
            return (Criteria) this;
        }

        public Criteria andTimeTimeIsNull() {
            addCriterion("time_time is null");
            return (Criteria) this;
        }

        public Criteria andTimeTimeIsNotNull() {
            addCriterion("time_time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeTimeEqualTo(Date value) {
            addCriterionForJDBCTime("time_time =", value, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("time_time <>", value, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeGreaterThan(Date value) {
            addCriterionForJDBCTime("time_time >", value, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_time >=", value, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeLessThan(Date value) {
            addCriterionForJDBCTime("time_time <", value, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("time_time <=", value, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeIn(List<Date> values) {
            addCriterionForJDBCTime("time_time in", values, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("time_time not in", values, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_time between", value1, value2, "timeTime");
            return (Criteria) this;
        }

        public Criteria andTimeTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("time_time not between", value1, value2, "timeTime");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampIsNull() {
            addCriterion("datetime_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampIsNotNull() {
            addCriterion("datetime_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampEqualTo(Date value) {
            addCriterion("datetime_timestamp =", value, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampNotEqualTo(Date value) {
            addCriterion("datetime_timestamp <>", value, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampGreaterThan(Date value) {
            addCriterion("datetime_timestamp >", value, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("datetime_timestamp >=", value, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampLessThan(Date value) {
            addCriterion("datetime_timestamp <", value, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampLessThanOrEqualTo(Date value) {
            addCriterion("datetime_timestamp <=", value, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampIn(List<Date> values) {
            addCriterion("datetime_timestamp in", values, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampNotIn(List<Date> values) {
            addCriterion("datetime_timestamp not in", values, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampBetween(Date value1, Date value2) {
            addCriterion("datetime_timestamp between", value1, value2, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andDatetimeTimestampNotBetween(Date value1, Date value2) {
            addCriterion("datetime_timestamp not between", value1, value2, "datetimeTimestamp");
            return (Criteria) this;
        }

        public Criteria andVarcharStringIsNull() {
            addCriterion("varchar_string is null");
            return (Criteria) this;
        }

        public Criteria andVarcharStringIsNotNull() {
            addCriterion("varchar_string is not null");
            return (Criteria) this;
        }

        public Criteria andVarcharStringEqualTo(String value) {
            addCriterion("varchar_string =", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringNotEqualTo(String value) {
            addCriterion("varchar_string <>", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringGreaterThan(String value) {
            addCriterion("varchar_string >", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringGreaterThanOrEqualTo(String value) {
            addCriterion("varchar_string >=", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringLessThan(String value) {
            addCriterion("varchar_string <", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringLessThanOrEqualTo(String value) {
            addCriterion("varchar_string <=", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringLike(String value) {
            addCriterion("varchar_string like", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringNotLike(String value) {
            addCriterion("varchar_string not like", value, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringIn(List<String> values) {
            addCriterion("varchar_string in", values, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringNotIn(List<String> values) {
            addCriterion("varchar_string not in", values, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringBetween(String value1, String value2) {
            addCriterion("varchar_string between", value1, value2, "varcharString");
            return (Criteria) this;
        }

        public Criteria andVarcharStringNotBetween(String value1, String value2) {
            addCriterion("varchar_string not between", value1, value2, "varcharString");
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