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
        StringBuilder sqlBuilder = new StringBuilder("select * from ");
        sqlBuilder.append(selectionGenerator.generateEntityName(key));
        sqlBuilder.append(" WHERE ");
        sqlBuilder.append(condition.getColumnName());
        sqlBuilder.append(" ");
        sqlBuilder.append(SqlOperator.EQ.getOperator());
        sqlBuilder.append(" ?");
        SqlWrapper sqlWrapper = new SqlWrapper();
        sqlWrapper.setSql(sqlBuilder.toString());
        sqlWrapper.setNames(new String[]{condition.getColumnName()});
        sqlWrapper.setValues(new Object[]{condition.getValue()});
        return sqlWrapper;
    }

    @Override
    public SqlWrapper wrap(String key, Map<String, String> conditions, String operator, List<String> sorters) {
        SqlWrapper sqlWrapper = new SqlWrapper();
        StringBuilder sqlBuilder = new StringBuilder("select * from ");
        sqlBuilder.append(selectionGenerator.generateEntityName(key));
        if (CollectionUtilities.isNotEmpty(conditions)) {
            List<Condition> _conditions = selectionGenerator.generateConditions(key, conditions);
            if (CollectionUtilities.isNotEmpty(_conditions)) {
                sqlBuilder.append(" WHERE ");
                List<String> lines = new ArrayList<String>(_conditions.size());
                List<String> names = new ArrayList<String>(_conditions.size());
                List<Object> values = new ArrayList<Object>(_conditions.size());
                for (Condition condition : _conditions) {
                    StringBuilder lineBuilder = new StringBuilder();
                    lineBuilder.append(condition.getColumnName());
                    lineBuilder.append(" ");
                    lineBuilder.append(condition.getOperator().getOperator());
                    lineBuilder.append(" ?");
                    lines.add(lineBuilder.toString());
                    names.add(condition.getName());
                    values.add(condition.getValue());
                }
                sqlBuilder.append(StringUtilities.join(lines, StringUtilities.surround(operator, " ")));
                sqlWrapper.setNames(names.toArray(new String[names.size()]));
                sqlWrapper.setValues(values.toArray(new Object[values.size()]));
            }
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

    public void setSelectionGenerator(SelectionGenerator selectionGenerator) {
        this.selectionGenerator = selectionGenerator;
    }
}
