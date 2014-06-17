package org.sliner.mapper.impl;

import org.sliner.mapper.ConditionMapping;
import org.sliner.mapper.SearchMapper;
import org.sliner.mapper.SorterMapping;

import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 14-6-17
 *
 * @author Andy Ai
 */
public class JpaSearchMapperImpl implements SearchMapper {
    @Override
    public Set<String> getKeys() {
        return null;
    }

    @Override
    public Set<String> getSchemas() {
        return null;
    }

    @Override
    public String getSchema(String key) {
        return null;
    }

    @Override
    public ConditionMapping getIdentifier(String key) {
        return null;
    }

    @Override
    public Set<ConditionMapping> getConditionMapper(String key) {
        return null;
    }

    @Override
    public Set<SorterMapping> getSorterMapper(String key) {
        return null;
    }
}
