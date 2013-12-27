package org.sliner.parser;

import org.sliner.Condition;
import org.sliner.Sorter;

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
