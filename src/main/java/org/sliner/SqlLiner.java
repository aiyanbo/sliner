package org.sliner;

import java.util.List;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 13-6-19
 *
 * @author Andy Ai
 */
public interface SqlLiner {
    SqlWrapper wrapIdentifier(String key, String identity);

    SqlWrapper wrap(String key, Map<String, String> conditions, String operator, List<String> sorters);
}
