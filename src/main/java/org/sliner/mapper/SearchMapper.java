package org.sliner.mapper;

import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public interface SearchMapper {
    Set<String> getKeys();

    Set<String> getSchemas();

    String getSchema(String key);

    ConditionMapping getIdentifier(String key);

    Set<ConditionMapping> getConditionMapper(String key);

    Set<SorterMapping> getSorterMapper(String key);
}
