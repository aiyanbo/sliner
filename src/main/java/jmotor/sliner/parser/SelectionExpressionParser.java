package jmotor.sliner.parser;

import jmotor.sliner.Condition;
import jmotor.sliner.Sorter;

/**
 * Component:
 * Description:
 * Date: 13-6-17
 *
 * @author Andy Ai
 */
public interface SelectionExpressionParser {
    Condition parseCondition(String expression);

    Sorter parseSorter(String expression);
}
