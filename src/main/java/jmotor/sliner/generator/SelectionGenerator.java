package jmotor.sliner.generator;

import jmotor.sliner.Condition;
import jmotor.sliner.Sorter;

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

    List<Condition> generateConditions(String key, Map<String, String> parameters);

    List<Sorter> generateSorters(String key,List<String> expressions);
}
