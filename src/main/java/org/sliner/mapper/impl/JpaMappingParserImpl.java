package org.sliner.mapper.impl;

import com.google.common.collect.ImmutableSet;
import org.jmotor.util.ClassUtilities;
import org.jmotor.util.CollectionUtilities;
import org.jmotor.util.ObjectUtilities;
import org.jmotor.util.StringUtilities;
import org.jmotor.util.Validator;
import org.sliner.annotation.Criteria;
import org.sliner.annotation.Managed;
import org.sliner.annotation.Sorting;
import org.sliner.mapper.ConditionMapping;
import org.sliner.mapper.MappingParser;
import org.sliner.mapper.SorterMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 14-6-17
 *
 * @author Andy Ai
 */
public class JpaMappingParserImpl implements MappingParser {
    private List<Class> classes;

    @Override
    public SearchMapping parseSearchMapping(final String key) throws Exception {
        Class clazz = CollectionUtilities.find(classes, new Validator<Class>() {
            @Override
            public boolean validate(Class clazz) {
                Managed managed = (Managed) clazz.getAnnotation(Managed.class);
                return null != managed && ObjectUtilities.equals(key, managed.key());
            }
        });
        if (null == clazz) {
            return null;
        }
        return parse(clazz);
    }

    @SuppressWarnings("unchecked")
    private SearchMapping parse(final Class clazz) throws Exception {
        SearchMapping searchMapping = new SearchMapping();
        Managed managed = (Managed) clazz.getAnnotation(Managed.class);
        searchMapping.setKey(managed.key());
        Entity entity = (Entity) clazz.getAnnotation(Entity.class);
        searchMapping.setSchema(entity.name());
        List descs = new ArrayList();
        descs.addAll(Arrays.asList(ClassUtilities.getFields(clazz)));
        descs.addAll(Arrays.asList(ClassUtilities.getPropertyDescriptors(clazz)));
        ConditionMapping identifier = null;
        ImmutableSet.Builder<SorterMapping> sorterMappingBuilder = ImmutableSet.<SorterMapping>builder();
        ImmutableSet.Builder<ConditionMapping> conditionMappingBuilder = ImmutableSet.<ConditionMapping>builder();
        for (Object desc : descs) {
            Id id;
            String name, type, column;
            Sorting sorting;
            Criteria criteria;
            if (desc instanceof Field) {
                Field field = (Field) desc;
                Column _column = field.getAnnotation(Column.class);
                if (null != _column) {
                    column = _column.name();
                } else {
                    column = StringUtilities.nameOfDatabase(field.getName());
                }
                type = field.getType().getName();
                name = StringUtilities.nameOfDatabase(field.getName());
                id = field.getAnnotation(Id.class);
                sorting = field.getAnnotation(Sorting.class);
                criteria = field.getAnnotation(Criteria.class);
            } else {
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) desc;
                Method method = propertyDescriptor.getReadMethod();
                Column _column = method.getAnnotation(Column.class);
                if (null != _column) {
                    column = _column.name();
                } else {
                    column = StringUtilities.nameOfDatabase(propertyDescriptor.getName());
                }
                type = propertyDescriptor.getPropertyType().getName();
                name = StringUtilities.nameOfDatabase(propertyDescriptor.getName());
                id = method.getAnnotation(Id.class);
                sorting = method.getAnnotation(Sorting.class);
                criteria = method.getAnnotation(Criteria.class);
            }

            if (null != id) {
                identifier = new ConditionMapping();
                identifier.setName(name);
                identifier.setType(type);
                identifier.setColumn(column);
                conditionMappingBuilder.add(identifier);
            }
            if (null != sorting) {
                SorterMapping sorterMapping = new SorterMapping();
                sorterMapping.setName(name);
                sorterMapping.setColumn(column);
                sorterMappingBuilder.add(sorterMapping);
            }
            if (null != criteria) {
                ConditionMapping conditionMapping = new ConditionMapping();
                conditionMapping.setName(name);
                conditionMapping.setType(type);
                conditionMapping.setColumn(column);
                conditionMapping.setMultiple(criteria.multiple());
                conditionMappingBuilder.add(conditionMapping);
            }
        }
        searchMapping.setIdentifierMapper(identifier);
        searchMapping.setSorterMapper(sorterMappingBuilder.build());
        searchMapping.setConditionMapper(conditionMappingBuilder.build());
        return searchMapping;
    }


    //todo: scan by package name
    public void register(List<Class> classes) {
        this.classes = classes;
    }
}
