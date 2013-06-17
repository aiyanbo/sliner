package jmotor.sliner;

/**
 * Component:
 * Description:
 * Date: 13-6-17
 *
 * @author Andy Ai
 */
public enum SqlOperator {
    EQ("="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    IS("is"),
    LIKE("like");

    private String operator;

    private SqlOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
