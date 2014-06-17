package org.sliner.mapper.impl;

/**
 * Component:
 * Description:
 * Date: 14-6-17
 *
 * @author Andy Ai
 */
public class JpaSearchMapperImpl extends AbstractSearchMapper {
    private String basePackage;

    @Override
    protected SearchMapping parseSearchMapping(String key) {
        return null;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

}
