package jmotor.sliner.generator.impl;

import jmotor.sliner.Condition;
import jmotor.sliner.Sorter;
import jmotor.sliner.SqlOperator;
import jmotor.sliner.ValueWrapper;
import jmotor.sliner.generator.SelectionGenerator;
import jmotor.sliner.mapper.ConditionMapping;
import jmotor.sliner.mapper.SearchMapper;
import jmotor.sliner.mapper.SorterMapping;
import jmotor.sliner.parser.SelectionExpressionParser;
import jmotor.util.StringUtils;
import jmotor.util.converter.SimpleValueConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class SelectionGeneratorImpl implements SelectionGenerator {
    private SearchMapper searchMapper;
    private SelectionExpressionParser expressionParser;

    @Override
    public String generateEntityName(String key) {
        return searchMapper.getSchema(key);
    }

    @Override
    public List<Condition> generateConditions(String key, Map<String, String> parameters) {
        List<Condition> result = new ArrayList<Condition>(parameters.size());
        Set<ConditionMapping> conditionMappings = searchMapper.getConditionMapper(key);
        for (String expression : parameters.keySet()) {
            Condition condition = expressionParser.parseCondition(expression);
            String name = condition.getName();
            boolean exists = false;
            ConditionMapping conditionMapping = null;
            for (ConditionMapping mapping : conditionMappings) {
                if (mapping.getName().equals(name)) {
                    exists = true;
                    conditionMapping = mapping;
                    break;
                }
            }
            if (exists) {
                condition.setColumnName(conditionMapping.getColumnName());
                String parameter = parameters.get(expression);
                if (null != condition.getValueWrapper()) {
                    String value = wrapValue(condition.getValueWrapper(), parameter);
                    condition.setValue(value);
                } else if (SqlOperator.IS == condition.getOperator()) {
                    condition.setValue(getAssertValue(parameter));
                } else {
                    Object value = SimpleValueConverter.convert(conditionMapping.getType(), parameter);
                    condition.setValue(value);
                }
                result.add(condition);
            }
        }
        return result;
    }

    @Override
    public List<Sorter> generateSorters(String key, List<String> expressions) {
        List<Sorter> result = new ArrayList<Sorter>(expressions.size());
        Set<SorterMapping> sorterMappings = searchMapper.getSorterMapper(key);
        for (String expression : expressions) {
            Sorter sorter = expressionParser.parseSorter(expression);
            boolean exits = false;
            SorterMapping sorterMapping = null;
            for (SorterMapping mapping : sorterMappings) {
                if (mapping.getName().equals(sorter.getName())) {
                    exits = true;
                    sorterMapping = mapping;
                    break;
                }
            }
            if (exits) {
                sorter.setColumnName(sorterMapping.getColumnName());
                result.add(sorter);
            }
        }
        return result;
    }

    private String wrapValue(ValueWrapper valueWrapper, String value) {
        String _val = value;
        switch (valueWrapper) {
            case R:
                _val += "%";
                break;
            case L:
                _val = "%" + _val;
                break;
            case A:
                _val = StringUtils.surround(_val, "%");
                break;
            default:
                _val = StringUtils.surround(_val, "%");
                break;
        }
        return _val;
    }

    private String getAssertValue(String value) {
        if ("null".equalsIgnoreCase(value)) {
            return "NULL";
        }
        if ("not null".equalsIgnoreCase(value)
                || "notnull".equalsIgnoreCase(value)
                || "not_null".equalsIgnoreCase(value)) {
            return "NOT NULL";
        }
        return "NULL";
    }

    public void setSearchMapper(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    public void setExpressionParser(SelectionExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }
}
