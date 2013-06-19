package jmotor.sliner.impl;

import jmotor.sliner.Condition;
import jmotor.sliner.Sorter;
import jmotor.sliner.SqlLiner;
import jmotor.sliner.SqlWrapper;
import jmotor.sliner.generator.SelectionGenerator;
import jmotor.util.CollectionUtils;
import jmotor.util.StringUtils;

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
    public SqlWrapper wrap(String key, Map<String, String> conditions, String operator, List<String> sorters) {
        StringBuilder sqlBuilder = new StringBuilder("select * from ");
        sqlBuilder.append(selectionGenerator.generateEntityName(key));
        List<Condition> _conditions = selectionGenerator.generateConditions(key, conditions);
        SqlWrapper sqlWrapper = new SqlWrapper();
        if (CollectionUtils.isNotEmpty(_conditions)) {
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
            sqlBuilder.append(StringUtils.join(lines, StringUtils.surround(operator, " ")));
            sqlWrapper.setNames(names.toArray(new String[names.size()]));
            sqlWrapper.setValues(values.toArray(new Object[values.size()]));
        }
        List<Sorter> _sorters = selectionGenerator.generateSorters(key, sorters);
        if (CollectionUtils.isNotEmpty(_sorters)) {
            sqlBuilder.append(" ORDER BY ");
            List<String> lines = new ArrayList<String>(_sorters.size());
            for (Sorter sorter : _sorters) {
                lines.add(sorter.getColumnName() + " " + sorter.getOperator().getOperator());
            }
            sqlBuilder.append(StringUtils.join(lines, StringUtils.COMMA));
        }
        sqlWrapper.setSql(sqlBuilder.toString());
        return sqlWrapper;
    }

    public void setSelectionGenerator(SelectionGenerator selectionGenerator) {
        this.selectionGenerator = selectionGenerator;
    }
}
