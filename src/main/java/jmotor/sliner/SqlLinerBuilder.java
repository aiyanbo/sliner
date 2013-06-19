package jmotor.sliner;

import jmotor.sliner.generator.impl.SelectionGeneratorImpl;
import jmotor.sliner.impl.SqlLinerImpl;
import jmotor.sliner.mapper.impl.SearchMapperImpl;
import jmotor.sliner.parser.impl.SelectionExpressionParserImpl;

/**
 * Component:
 * Description:
 * Date: 13-6-19
 *
 * @author Andy Ai
 */
public class SqlLinerBuilder {
    private static SqlLiner sqlLiner;

    private SqlLinerBuilder() {
    }

    public static SqlLiner build() {
        if (null == sqlLiner) {
            synchronized (SqlLinerBuilder.class) {
                if (null == sqlLiner) {
                    SqlLinerImpl ins = new SqlLinerImpl();
                    SelectionGeneratorImpl selectionGenerator = new SelectionGeneratorImpl();
                    selectionGenerator.setExpressionParser(new SelectionExpressionParserImpl());
                    selectionGenerator.setSearchMapper(new SearchMapperImpl());
                    ins.setSelectionGenerator(selectionGenerator);
                    sqlLiner = ins;
                }
            }
        }
        return sqlLiner;
    }
}
