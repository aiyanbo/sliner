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
    String getSchema(String key);

    Set<ConditionMapping> getConditionMapper(String key);

    Set<SorterMapping> getSorterMapper(String key);
}
