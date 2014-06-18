package org.sliner.mapper.impl;

import com.google.common.collect.ImmutableSet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.jmotor.util.CollectionUtilities;
import org.jmotor.util.XmlUtilities;
import org.sliner.mapper.ConditionMapping;
import org.sliner.mapper.SearchMapperXPath;
import org.sliner.mapper.SorterMapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 13-6-18
 *
 * @author Andy Ai
 */
public class XmlSearchMapperImpl extends AbstractSearchMapper implements SearchMapperXPath {
    private String suffix = ".xml";
    private String workingPath = "config/mapper";

    @SuppressWarnings("unchecked")
    public SearchMapping parseSearchMapping(String key) throws Exception {
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
        searchMapping.setConditionMapper(ImmutableSet.<ConditionMapping>builder().addAll(conditionMappings).build());
        searchMapping.setSorterMapper(ImmutableSet.<SorterMapping>builder().addAll(sorterMappings).build());
        return searchMapping;
    }

    private ConditionMapping parseIdentifier(Node conditionsNode) {
        Node node = conditionsNode.selectSingleNode(IDENTIFIER_NODE);
        if (node != null) {
            ConditionMapping mapping = new ConditionMapping();
            XmlUtilities.fillProperties(mapping, node);
            return mapping;
        }
        return null;
    }

    private Set<ConditionMapping> parseConditionMappings(List<Node> conditionNodes) {
        Set<ConditionMapping> mappings = new HashSet<ConditionMapping>(conditionNodes.size());
        for (Node node : conditionNodes) {
            ConditionMapping mapping = new ConditionMapping();
            XmlUtilities.fillProperties(mapping, node);
            mappings.add(mapping);
        }
        return mappings;
    }

    private Set<SorterMapping> parseSorterMappings(List<Node> sorterNodes) {
        if (CollectionUtilities.isNotEmpty(sorterNodes)) {
            Set<SorterMapping> mappings = new HashSet<SorterMapping>(sorterNodes.size());
            for (Node node : sorterNodes) {
                SorterMapping mapping = new SorterMapping();
                XmlUtilities.fillProperties(mapping, node);
                mappings.add(mapping);
            }
            return mappings;
        }
        return Collections.emptySet();
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setWorkingPath(String workingPath) {
        this.workingPath = workingPath;
    }
}
