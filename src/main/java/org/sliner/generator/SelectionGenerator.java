package org.sliner.generator;

import org.sliner.Condition;
import org.sliner.Sorter;

import java.util.List;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public interface SelectionGenerator {
    String generateEntityName(String key);

    Condition generateIdentifier(String key, String identity);

    List<Condition> generateIdentifier(String key, String identity, Map<String, String> arguments);

    List<Condition> generateConditions(String key, Map<String, String> parameters);

    List<Sorter> generateSorters(String key, List<String> expressions);
}
