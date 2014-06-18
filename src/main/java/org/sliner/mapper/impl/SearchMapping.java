package org.sliner.mapper.impl;

import com.google.common.collect.ImmutableSet;
import org.sliner.mapper.ConditionMapping;
import org.sliner.mapper.SorterMapping;

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
    private ConditionMapping identifierMapper;
    private ImmutableSet<SorterMapping> sorterMapper;
    private ImmutableSet<ConditionMapping> conditionMapper;

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

    public ConditionMapping getIdentifierMapper() {
        return identifierMapper;
    }

    public void setIdentifierMapper(ConditionMapping identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

    public ImmutableSet<SorterMapping> getSorterMapper() {
        return sorterMapper;
    }

    public void setSorterMapper(ImmutableSet<SorterMapping> sorterMapper) {
        this.sorterMapper = sorterMapper;
    }

    public ImmutableSet<ConditionMapping> getConditionMapper() {
        return conditionMapper;
    }

    public void setConditionMapper(ImmutableSet<ConditionMapping> conditionMapper) {
        this.conditionMapper = conditionMapper;
    }
}
