package jmotor.sliner.parser.impl;

import jmotor.sliner.Condition;
import jmotor.sliner.SortOperator;
import jmotor.sliner.Sorter;
import jmotor.sliner.SqlOperator;
import jmotor.sliner.ValueWrapper;
import jmotor.sliner.parser.SelectionExpressionParser;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class SelectionExpressionParserImpl implements SelectionExpressionParser {
    private static final char FOR = '4';
    private static final char TOO = '2';

    @Override
    public Condition parseCondition(String expression) {
        int forIndex = expression.indexOf(FOR);
        Condition condition = new Condition();
        condition.setExpression(expression);
        if (forIndex < 0) {
            condition.setName(expression);
            condition.setOperator(SqlOperator.EQ);
        } else {
            int tooIndex = expression.indexOf(TOO);
            String name = expression.substring(0, forIndex);
            String operator;
            if (tooIndex > 0) {
                operator = expression.substring(forIndex + 1, tooIndex);
                String wrapper = expression.substring(tooIndex + 1);
                ValueWrapper valueWrapper = ValueWrapper.valueOf(wrapper.toUpperCase());
                condition.setValueWrapper(valueWrapper);
            } else {
                operator = expression.substring(forIndex + 1);
            }
            condition.setName(name);
            SqlOperator sqlOperator = SqlOperator.valueOf(operator.toUpperCase());
            condition.setOperator(sqlOperator);
        }
        return condition;
    }

    @Override
    public Sorter parseSorter(String expression) {
        int forIndex = expression.indexOf(FOR);
        Sorter sorter = new Sorter();
        if (forIndex < 0) {
            sorter.setName(expression);
            sorter.setOperator(SortOperator.ASC);
        } else {
            String name = expression.substring(0, forIndex);
            String operator = expression.substring(forIndex + 1);
            sorter.setName(name);
            sorter.setOperator(SortOperator.valueOf(operator.toUpperCase()));
        }
        return sorter;
    }
}
