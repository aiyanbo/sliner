package jmotor.sliner;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class Sorter {
    private String name;
    private String columnName;
    private SortOperator operator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public SortOperator getOperator() {
        return operator;
    }

    public void setOperator(SortOperator operator) {
        this.operator = operator;
    }
}
