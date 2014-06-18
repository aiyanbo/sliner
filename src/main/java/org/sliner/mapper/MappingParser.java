package org.sliner.mapper;

import org.sliner.mapper.impl.SearchMapping;

/**
 * Component:
 * Description:
 * Date: 14-6-18
 *
 * @author Andy Ai
 */
public interface MappingParser {
    SearchMapping parseSearchMapping(String key) throws Exception;
}
