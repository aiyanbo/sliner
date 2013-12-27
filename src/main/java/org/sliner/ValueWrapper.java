package org.sliner;

/**
 * Component:
 * Description:
 * Date: 13-6-17
 *
 * @author Andy Ai
 */
public enum ValueWrapper {
    L("l"),
    R("r"),
    A("a");

    private String wrapper;

    private ValueWrapper(String wrapper) {
        this.wrapper = wrapper;
    }

    public String getWrapper() {
        return wrapper;
    }
}
