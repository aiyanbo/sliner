package jmotor.sliner.mapper.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jmotor.sliner.mapper.ConditionMapping;
import jmotor.sliner.mapper.SearchMapper;
import jmotor.sliner.mapper.SearchMapperXPath;
import jmotor.sliner.mapper.SorterMapping;
import jmotor.util.CollectionUtils;
import jmotor.util.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

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
        } catch (ExecutionException e) {
            throw new NullPointerException("Can't find key: " + key);
        }
    }

    @SuppressWarnings("unchecked")
    private SearchMapping parseSearchMapping(String key) {
        String fileName = workingPath.replace("\\", "/") + "/" + key + suffix;
        Document document = XmlUtils.loadDocument(fileName);
        Element rootElement = document.getRootElement();
        String schema = XmlUtils.getAttribute(rootElement, SCHEMA_ATTR);
        Node conditionsNode = rootElement.selectSingleNode(CONDITIONS_NODE);
        List<Node> conditionNodes = conditionsNode.selectNodes(CONDITION_NODE);
        Set<ConditionMapping> conditionMappings = parseConditionMappings(conditionNodes);
        Node sortersNode = rootElement.selectSingleNode(SORTERS_NODE);
        List<Node> sorterNodes = sortersNode.selectNodes(SORTER_NODE);
        Set<SorterMapping> sorterMappings = parseSorterMappings(sorterNodes);
        SearchMapping searchMapping = new SearchMapping();
        searchMapping.setKey(key);
        searchMapping.setSchema(schema);
        searchMapping.setConditionMapper(conditionMappings);
        searchMapping.setSorterMapper(sorterMappings);
        return searchMapping;
    }

    private Set<ConditionMapping> parseConditionMappings(List<Node> conditionNodes) {
        Set<ConditionMapping> mappings = new HashSet<ConditionMapping>(conditionNodes.size());
        for (Node node : conditionNodes) {
            String name = XmlUtils.getAttribute(node, NAME_ATTR);
            String column = XmlUtils.getAttribute(node, COLUMN_ATTR);
            String type = XmlUtils.getAttribute(node, TYPE_ATTR);
            ConditionMapping mapping = new ConditionMapping();
            mapping.setName(name);
            mapping.setColumnName(column);
            mapping.setType(type);
            mappings.add(mapping);
        }
        return mappings;
    }

    private Set<SorterMapping> parseSorterMappings(List<Node> sorterNodes) {
        if (CollectionUtils.isNotEmpty(sorterNodes)) {
            Set<SorterMapping> mappings = new HashSet<SorterMapping>(sorterNodes.size());
            for (Node node : sorterNodes) {
                String name = XmlUtils.getAttribute(node, NAME_ATTR);
                String column = XmlUtils.getAttribute(node, COLUMN_ATTR);
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
