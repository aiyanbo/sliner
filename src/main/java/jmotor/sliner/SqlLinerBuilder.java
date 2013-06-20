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
    private String suffix = ".xml";
    private Long cacheInSeconds = 10L;
    private String workingPath = "config/mapper";

    private SqlLinerBuilder() {
    }

    public static SqlLinerBuilder newBuilder() {
        return new SqlLinerBuilder();
    }

    public SqlLiner build() {
        SqlLinerImpl sqlLiner = new SqlLinerImpl();
        SelectionGeneratorImpl selectionGenerator = new SelectionGeneratorImpl();
        selectionGenerator.setExpressionParser(new SelectionExpressionParserImpl());
        SearchMapperImpl searchMapper = new SearchMapperImpl();
        searchMapper.setSuffix(suffix);
        searchMapper.setWorkingPath(workingPath);
        searchMapper.setCacheInSeconds(cacheInSeconds);
        selectionGenerator.setSearchMapper(searchMapper);
        sqlLiner.setSelectionGenerator(selectionGenerator);
        return sqlLiner;
    }


    public SqlLinerBuilder suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public SqlLinerBuilder cacheInSeconds(Long cacheInSeconds) {
        this.cacheInSeconds = cacheInSeconds;
        return this;
    }

    public SqlLinerBuilder workingPath(String workingPath) {
        this.workingPath = workingPath;
        return this;
    }
}
