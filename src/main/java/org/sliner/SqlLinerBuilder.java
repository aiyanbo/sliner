package org.sliner;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.sliner.generator.impl.SelectionGeneratorImpl;
import org.sliner.impl.SqlLinerImpl;
import org.sliner.mapper.impl.SearchMapperImpl;
import org.sliner.parser.impl.SelectionExpressionParserImpl;

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
    private DateTimeFormatter dateTimeFormatter;

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
        selectionGenerator.setDateTimeFormatter(dateTimeFormatter == null ?
                ISODateTimeFormat.dateTime() : dateTimeFormatter);
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

    public SqlLinerBuilder dateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        return this;
    }
}
