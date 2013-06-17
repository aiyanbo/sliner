package jmotor.sliner.parser;

import jmotor.sliner.Condition;

/**
 * Component:
 * Description:
 * Date: 13-6-17
 *
 * @author Andy Ai
 */
public interface ConditionExpressionParser {
    Condition parseExpression(String expression);
}
