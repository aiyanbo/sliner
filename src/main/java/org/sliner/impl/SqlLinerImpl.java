package org.sliner.impl;

import org.jmotor.util.CollectionUtilities;
import org.jmotor.util.StringUtilities;
import org.sliner.Condition;
import org.sliner.Sorter;
import org.sliner.SqlLiner;
import org.sliner.SqlOperator;
import org.sliner.SqlWrapper;
import org.sliner.generator.SelectionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 13-6-19
 *
 * @author Andy Ai
 */
public class SqlLinerImpl implements SqlLiner {
    private SelectionGenerator selectionGenerator;

    @Override
    public SqlWrapper wrapIdentifier(String key, String identity) {
        Condition condition = selectionGenerator.generateIdentifier(key, identity);
        SqlWrapper sqlWrapper = new SqlWrapper();
        sqlWrapper.setSql("select * from " + selectionGenerator.generateEntityName(key)
                + " WHERE " + condition.getColumnName() + " " + SqlOperator.EQ.getOperator() + " ?");
        sqlWrapper.setNames(new String[]{condition.getColumnName()});
        sqlWrapper.setValues(new Object[]{condition.getValue()});
        return sqlWrapper;
    }

    @Override
    public SqlWrapper wrapIdentifier(String key, String identity, Map<String, String> arguments) {
        SqlWrapper sqlWrapper = new SqlWrapper();
        List<Condition> conditions = selectionGenerator.generateIdentifier(key, identity, arguments);
        SqlWrapper result = flattenConditions(conditions, StringUtilities.AND_MARK);
        sqlWrapper.setSql("select * from " + selectionGenerator.generateEntityName(key) + result.getSql());
        sqlWrapper.setNames(result.getNames());
        sqlWrapper.setValues(result.getValues());
        return sqlWrapper;
    }

    @Override
    public SqlWrapper wrap(String key, Map<String, String> conditions, String operator, List<String> sorters) {
        SqlWrapper sqlWrapper = new SqlWrapper();
        StringBuilder sqlBuilder = new StringBuilder("select * from ");
        sqlBuilder.append(selectionGenerator.generateEntityName(key));
        if (CollectionUtilities.isNotEmpty(conditions)) {
            List<Condition> _conditions = selectionGenerator.generateConditions(key, conditions);
            SqlWrapper result = flattenConditions(_conditions, operator);
            sqlBuilder.append(result.getSql());
            sqlWrapper.setNames(result.getNames());
            sqlWrapper.setValues(result.getValues());
        }
        if (CollectionUtilities.isNotEmpty(sorters)) {
            List<Sorter> _sorters = selectionGenerator.generateSorters(key, sorters);
            if (CollectionUtilities.isNotEmpty(_sorters)) {
                sqlBuilder.append(" ORDER BY ");
                List<String> lines = new ArrayList<String>(_sorters.size());
                for (Sorter sorter : _sorters) {
                    lines.add(sorter.getColumnName() + " " + sorter.getOperator().getOperator());
                }
                sqlBuilder.append(StringUtilities.join(lines, StringUtilities.COMMA));
            }
        }
        sqlWrapper.setSql(sqlBuilder.toString());
        return sqlWrapper;
    }

    private SqlWrapper flattenConditions(List<Condition> conditions, String operator) {
        SqlWrapper result = new SqlWrapper();
        if (CollectionUtilities.isNotEmpty(conditions)) {
            List<String> lines = new ArrayList<>(conditions.size());
            List<String> names = new ArrayList<>(conditions.size());
            List<Object> values = new ArrayList<>(conditions.size());
            for (Condition condition : conditions) {
                StringBuilder lineBuilder = new StringBuilder();
                lineBuilder.append(condition.getColumnName());
                lineBuilder.append(StringUtilities.BLANK_SPACE);
                lineBuilder.append(condition.getOperator().getOperator());
                if (condition.isMultiple()) {
                    lineBuilder.append('(');
                    Set<Object> multiValues = condition.getValues();
                    lineBuilder.append(StringUtilities.repeat(StringUtilities.QUESTION_MARK,
                            StringUtilities.COMMA, multiValues.size()));
                    lineBuilder.append(')');
                    for (Object _value : multiValues) {
                        values.add(_value);
                        names.add(condition.getName());
                    }
                } else {
                    lineBuilder.append(" ?");
                    names.add(condition.getName());
                    values.add(condition.getValue());
                }
                lines.add(lineBuilder.toString());
            }
            result.setSql(" WHERE " + StringUtilities.join(lines,
                    StringUtilities.surround(operator, StringUtilities.BLANK_SPACE)));
            result.setNames(names.toArray(new String[names.size()]));
            result.setValues(values.toArray(new Object[values.size()]));
        } else {
            result.setSql(StringUtilities.EMPTY);
        }
        return result;
    }

    public void setSelectionGenerator(SelectionGenerator selectionGenerator) {
        this.selectionGenerator = selectionGenerator;
    }
}
