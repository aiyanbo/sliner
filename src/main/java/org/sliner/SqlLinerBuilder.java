package org.sliner;

import org.jmotor.util.CollectionUtilities;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.sliner.generator.impl.SelectionGeneratorImpl;
import org.sliner.impl.SqlLinerImpl;
import org.sliner.mapper.MappingParser;
import org.sliner.mapper.SearchMapper;
import org.sliner.mapper.impl.JpaMappingParserImpl;
import org.sliner.mapper.impl.SearchMapperImpl;
import org.sliner.mapper.impl.XmlMappingParserImpl;
import org.sliner.parser.impl.SelectionExpressionParserImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 13-6-19
 *
 * @author Andy Ai
 */
public class SqlLinerBuilder {
    private String suffix = ".xml";
    private List<Class> classes;
    private Long cacheInSeconds = 10L;
    private SearchMapper searchMapper;
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
        if (null == searchMapper) {
            SearchMapperImpl _searchMapper = new SearchMapperImpl();
            List<MappingParser> parsers = new ArrayList<>();
            XmlMappingParserImpl xmlMappingParser = new XmlMappingParserImpl();
            xmlMappingParser.setSuffix(suffix);
            xmlMappingParser.setWorkingPath(workingPath);
            parsers.add(xmlMappingParser);
            if (null != classes) {
                JpaMappingParserImpl jpaMappingParser = new JpaMappingParserImpl();
                jpaMappingParser.register(classes);
                parsers.add(jpaMappingParser);
            }
            _searchMapper.setMappingParsers(parsers);
            _searchMapper.setCacheInSeconds(cacheInSeconds);
            this.searchMapper = _searchMapper;
        }
        selectionGenerator.setDateTimeFormatter(dateTimeFormatter == null ?
                ISODateTimeFormat.dateTime() : dateTimeFormatter);
        selectionGenerator.setSearchMapper(searchMapper);
        sqlLiner.setSelectionGenerator(selectionGenerator);
        return sqlLiner;
    }

    public SearchMapper mapper() {
        return this.searchMapper;
    }

    public SqlLinerBuilder suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public SqlLinerBuilder classes(List<Class> classes) {
        this.classes = classes;
        return this;
    }


    public SqlLinerBuilder classes(Class... classes) {
        if (CollectionUtilities.isNotEmpty(classes)) {
            this.classes = new ArrayList<>(classes.length);
            this.classes.addAll(Arrays.asList(classes));
        }
        return this;
    }

    public SqlLinerBuilder mapper(SearchMapper mapper) {
        this.searchMapper = mapper;
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
