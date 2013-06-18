package jmotor.sliner.mapper.impl;

import jmotor.sliner.mapper.ConditionMapping;
import jmotor.sliner.mapper.SorterMapping;

import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class SearchMapping {
    private String key;
    private String schema;
    private Set<SorterMapping> sorterMapper;
    private Set<ConditionMapping> conditionMapper;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Set<SorterMapping> getSorterMapper() {
        return sorterMapper;
    }

    public void setSorterMapper(Set<SorterMapping> sorterMapper) {
        this.sorterMapper = sorterMapper;
    }

    public Set<ConditionMapping> getConditionMapper() {
        return conditionMapper;
    }

    public void setConditionMapper(Set<ConditionMapping> conditionMapper) {
        this.conditionMapper = conditionMapper;
    }
}
