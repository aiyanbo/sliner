package org.sliner.mapper;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class ConditionMapping {
    private String name;
    private String type;
    private String column;
    private boolean multiple;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

}
