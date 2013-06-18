package jmotor.sliner.mapper.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jmotor.sliner.mapper.ConditionMapping;
import jmotor.sliner.mapper.SearchMapper;
import jmotor.sliner.mapper.SearchMapperXPath;
import jmotor.sliner.mapper.SorterMapping;
import jmotor.util.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

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

    private void initComponent() {
        searchMappingCache = CacheBuilder.newBuilder().
                expireAfterWrite(cacheInSeconds, TimeUnit.SECONDS).
                maximumSize(1024).

                build(new CacheLoader<String, SearchMapping>() {
                    @Override
                    public SearchMapping load(String key) throws Exception {
                        return null;
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
        Set<ConditionMapping> conditionMappings = parseConditionMappings(conditionsNode);
        Node sortersNode = rootElement.selectSingleNode(SORTERS_NODE);
        Set<SorterMapping> sorterMappings = parseSorterMappings(sortersNode);
        SearchMapping searchMapping = new SearchMapping();
        searchMapping.setKey(key);
        searchMapping.setSchema(schema);
        searchMapping.setConditionMapper(conditionMappings);
        searchMapping.setSorterMapper(sorterMappings);
        return searchMapping;
    }

    private Set<ConditionMapping> parseConditionMappings(Node conditionsNode) {
        return null;
    }

    private Set<SorterMapping> parseSorterMappings(Node sortersNode) {
        return null;
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
