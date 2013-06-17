package jmotor.sliner;

/**
 * Component:
 * Description:
 * Date: 13-6-17
 *
 * @author Andy Ai
 */
public class Condition {
    private String expression;
    private String columnName;
    private SqlOperator operator;
    private Object value;
    private ValueWrapper valueWrapper;

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

    public ValueWrapper getValueWrapper() {
        return valueWrapper;
    }

    public void setValueWrapper(ValueWrapper valueWrapper) {
        this.valueWrapper = valueWrapper;
    }
}
