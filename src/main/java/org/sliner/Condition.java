package org.sliner;

import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 13-6-17
 *
 * @author Andy Ai
 */
public class Condition {
    private String name;
    private String expression;
    private String columnName;
    private SqlOperator operator;
    private Object value;
    private boolean multiple;
    private Set<Object> values;
    private ValueWrapper valueWrapper;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public SqlOperator getOperator() {
        return operator;
    }

    public void setOperator(SqlOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public Set<Object> getValues() {
        return values;
    }

    public void setValues(Set<Object> values) {
        this.values = values;
    }

    public ValueWrapper getValueWrapper() {
        return valueWrapper;
    }

    public void setValueWrapper(ValueWrapper valueWrapper) {
        this.valueWrapper = valueWrapper;
    }
}
