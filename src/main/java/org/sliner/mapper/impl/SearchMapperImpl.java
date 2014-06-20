package org.sliner.mapper.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.jmotor.util.exception.XMLParserException;
import org.sliner.exception.MappingNotFoundException;
import org.sliner.exception.MappingParseException;
import org.sliner.mapper.ConditionMapping;
import org.sliner.mapper.MappingParser;
import org.sliner.mapper.SearchMapper;
import org.sliner.mapper.SorterMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class SearchMapperImpl implements SearchMapper {
    private LoadingCache<String, SearchMapping> searchMappingCache;
    private Long cacheInSeconds = 10L;
    private List<MappingParser> mappingParsers;

    {
        initComponent();
    }

    private void initComponent() {
        searchMappingCache = CacheBuilder.newBuilder().
                expireAfterWrite(cacheInSeconds, TimeUnit.SECONDS).
                maximumSize(1024).
                build(new CacheLoader<String, SearchMapping>() {
                    @Override
                    public SearchMapping load(String key) throws Exception {
                        for (MappingParser parser : mappingParsers) {
                            try {
                                SearchMapping searchMapping = parser.parseSearchMapping(key);
                                if (null != searchMapping) {
                                    return searchMapping;
                                }
                            } catch (NullPointerException e) {
                                //ignore
                            }
                        }
                        return null;
                    }
                });
    }

    @Override
    public Set<String> getKeys() {
        return searchMappingCache.asMap().keySet();
    }

    @Override
    public Set<String> getSchemas() {
        ConcurrentMap<String, SearchMapping> mappingConcurrentMap = searchMappingCache.asMap();
        Set<String> schemas = new HashSet<String>(mappingConcurrentMap.size());
        for (Map.Entry<String, SearchMapping> entry : mappingConcurrentMap.entrySet()) {
            schemas.add(entry.getValue().getSchema());
        }
        return schemas;
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
                throw new MappingNotFoundException("Mapping not found. key: " + key, e);
            } else if (cause instanceof XMLParserException) {
                throw new MappingParseException("Mapping parse failure. key: " + key, e);
            }
            throw new MappingParseException("Mapping parse failure. key: " + key, e);
        } catch (ExecutionException | CacheLoader.InvalidCacheLoadException e) {
            throw new MappingNotFoundException("Mapping not found. key: " + key, e);
        }
    }

    public void setCacheInSeconds(Long cacheInSeconds) {
        this.cacheInSeconds = cacheInSeconds;
    }

    public void setMappingParsers(List<MappingParser> mappingParsers) {
        this.mappingParsers = mappingParsers;
    }
}
