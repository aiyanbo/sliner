package org.sliner.mapper.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.sliner.mapper.ConditionMapping;
import org.sliner.mapper.SearchMapper;
import org.sliner.mapper.SorterMapping;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Component:
 * Description:
 * Date: 14-6-17
 *
 * @author Andy Ai
 */
public class JpaSearchMapperImpl implements SearchMapper {
    private LoadingCache<String, SearchMapping> searchMappingCache;
    private String basePackage;
    private Long cacheInSeconds = 10L;

    public JpaSearchMapperImpl() {
        initComponent();
    }

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
    public Set<String> getKeys() {
        return null;
    }

    @Override
    public Set<String> getSchemas() {
        return null;
    }

    @Override
    public String getSchema(String key) {
        return null;
    }

    @Override
    public ConditionMapping getIdentifier(String key) {
        return null;
    }

    @Override
    public Set<ConditionMapping> getConditionMapper(String key) {
        return null;
    }

    @Override
    public Set<SorterMapping> getSorterMapper(String key) {
        return null;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setCacheInSeconds(Long cacheInSeconds) {
        this.cacheInSeconds = cacheInSeconds;
    }

}
