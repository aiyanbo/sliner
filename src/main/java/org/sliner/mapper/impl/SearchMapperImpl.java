package org.sliner.mapper.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.jmotor.util.CollectionUtilities;
import org.jmotor.util.XmlUtilities;
import org.jmotor.util.exception.XMLParserException;
import org.sliner.exception.MappingNotFoundException;
import org.sliner.exception.MappingParseException;
import org.sliner.mapper.ConditionMapping;
import org.sliner.mapper.SearchMapper;
import org.sliner.mapper.SearchMapperXPath;
import org.sliner.mapper.SorterMapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class SearchMapperImpl implements SearchMapper, SearchMapperXPath {
    private LoadingCache<String, SearchMapping> searchMappingCache;
    private String suffix = ".xml";
    private Long cacheInSeconds = 10L;
    private String workingPath = "config/mapper";

    public SearchMapperImpl() {
        initComponent();
    }

    private void initComponent() {
        searchMappingCache = CacheBuilder.newBuilder().
                expireAfterWrite(cacheInSeconds, TimeUnit.SECONDS).
                maximumSize(1024).
                build(new CacheLoader<String, SearchMapping>() {
                    @Override
                    public SearchMapping load(String key) throws Exception {
                        return parseSearchMapping(key);
                    }
                });

    }

    @Override
    public String getSchema(String key) {
        return getSearchMappingInCache(key).getSchema();
    }

    @Override
    public ConditionMapping getIdentifier(String key) {
        return getSearchMappingInCache(key).getIdentifierMapper();
    }

    @Override
    public Set<ConditionMapping> getConditionMapper(String key) {
        return getSearchMappingInCache(key).getConditionMapper();
    }

    @Override
    public Set<SorterMapping> getSorterMapper(String key) {
        return getSearchMappingInCache(key).getSorterMapper();
    }

    private SearchMapping getSearchMappingInCache(String key) {
        try {
            return searchMappingCache.get(key);
        } catch (UncheckedExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NullPointerException) {
                throw new MappingNotFoundException("Mapping not found. key:" + key, e);
            } else if (cause instanceof XMLParserException) {
                throw new MappingParseException("Mapping parse failure. key:" + key, e);
            }
            throw new MappingParseException("Mapping parse failure. key:" + key, e);
        } catch (ExecutionException e) {
            throw new MappingNotFoundException("Mapping not found. key:" + key, e);
        }
    }

    @SuppressWarnings("unchecked")
    private SearchMapping parseSearchMapping(String key) {
        SearchMapping searchMapping = new SearchMapping();
        String fileName = workingPath.replace("\\", "/") + "/" + key + suffix;
        Document document = XmlUtilities.loadDocument(fileName);
        Element rootElement = document.getRootElement();
        String schema = XmlUtilities.getAttribute(rootElement, SCHEMA_ATTR);
        Node conditionsNode = rootElement.selectSingleNode(CONDITIONS_NODE);
        List<Node> conditionNodes = conditionsNode.selectNodes(CONDITION_NODE);
        Set<ConditionMapping> conditionMappings = parseConditionMappings(conditionNodes);
        ConditionMapping identifierMapper = parseIdentifier(conditionsNode);
        if (identifierMapper != null) {
            conditionMappings.add(identifierMapper);
            searchMapping.setIdentifierMapper(identifierMapper);
        }
        Node sortersNode = rootElement.selectSingleNode(SORTERS_NODE);
        List<Node> sorterNodes = sortersNode.selectNodes(SORTER_NODE);
        Set<SorterMapping> sorterMappings = parseSorterMappings(sorterNodes);
        searchMapping.setKey(key);
        searchMapping.setSchema(schema);
        searchMapping.setConditionMapper(conditionMappings);
        searchMapping.setSorterMapper(sorterMappings);
        return searchMapping;
    }

    private ConditionMapping parseIdentifier(Node conditionsNode) {
        Node node = conditionsNode.selectSingleNode(IDENTIFIER_NODE);
        if (node != null) {
            return parseConditionMapping(node);
        }
        return null;
    }

    private Set<ConditionMapping> parseConditionMappings(List<Node> conditionNodes) {
        Set<ConditionMapping> mappings = new HashSet<ConditionMapping>(conditionNodes.size());
        for (Node node : conditionNodes) {
            ConditionMapping mapping = parseConditionMapping(node);
            mappings.add(mapping);
        }
        return mappings;
    }

    private ConditionMapping parseConditionMapping(Node node) {
        String name = XmlUtilities.getAttribute(node, NAME_ATTR);
        String column = XmlUtilities.getAttribute(node, COLUMN_ATTR);
        String type = XmlUtilities.getAttribute(node, TYPE_ATTR);
        ConditionMapping mapping = new ConditionMapping();
        mapping.setName(name);
        mapping.setColumnName(column);
        mapping.setType(type);
        return mapping;
    }

    private Set<SorterMapping> parseSorterMappings(List<Node> sorterNodes) {
        if (CollectionUtilities.isNotEmpty(sorterNodes)) {
            Set<SorterMapping> mappings = new HashSet<SorterMapping>(sorterNodes.size());
            for (Node node : sorterNodes) {
                String name = XmlUtilities.getAttribute(node, NAME_ATTR);
                String column = XmlUtilities.getAttribute(node, COLUMN_ATTR);
                SorterMapping mapping = new SorterMapping();
                mapping.setName(name);
                mapping.setColumnName(column);
                mappings.add(mapping);
            }
            return mappings;
        }
        return Collections.emptySet();
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setCacheInSeconds(Long cacheInSeconds) {
        this.cacheInSeconds = cacheInSeconds;
    }

    public void setWorkingPath(String workingPath) {
        this.workingPath = workingPath;
    }
}
