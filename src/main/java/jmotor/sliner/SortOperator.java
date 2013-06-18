package jmotor.sliner;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public enum SortOperator {
    ASC("ASC"),
    DESC("DESC");

    private String operator;

    private SortOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
