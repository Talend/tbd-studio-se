// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.handlers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.process.EParameterFieldType;
import org.talend.core.model.properties.Item;
import org.talend.core.model.relationship.AbstractJobParameterInRepositoryRelationshipHandler;
import org.talend.core.model.relationship.AbstractJobParameterRelationshipHandler;
import org.talend.core.model.relationship.Relation;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.core.repository.handlers.PropertyTypeParameterRelationshipHandler;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HadoopClusterPropertyTypeParameterRelationshipHandler extends AbstractJobParameterRelationshipHandler {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.relationship.AbstractParameterRelationshipHandler#collect(java.util.Map,
     * java.util.Map)
     */
    @Override
    protected Set<Relation> collect(Map<String, ElementParameterType> parametersMap, Map<?, ?> options) {
        if (parametersMap == null || parametersMap.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Relation> relationSet = new HashSet<Relation>();
        ElementParameterType repositoryTypeParam = parametersMap
                .get(PropertyTypeParameterRelationshipHandler.PROPERTY_PROPERTY_TYPE);

        if (repositoryTypeParam != null
                && AbstractJobParameterInRepositoryRelationshipHandler.IN_REPOSITORY.equals(repositoryTypeParam.getValue())) {

            ElementParameterType repositoryTypeValueParam = parametersMap
                    .get(PropertyTypeParameterRelationshipHandler.PROPERTY_REPOSITORY_PROPERTY_TYPE);

            if (repositoryTypeValueParam != null) {
                String repositoryIdOrValue = repositoryTypeValueParam.getValue();
                if (StringUtils.isNotEmpty(repositoryIdOrValue)) {

                    Item hadoopClusterItem = HCRepositoryUtil.getHadoopClusterItemBySubitemId(repositoryIdOrValue);
                    if (hadoopClusterItem != null) {
                        String hcId = hadoopClusterItem.getProperty().getId();

                        Relation addedRelation = new Relation();
                        addedRelation.setId(hcId);
                        addedRelation.setType(RelationshipItemBuilder.PROPERTY_RELATION);
                        addedRelation.setVersion(RelationshipItemBuilder.LATEST_VERSION);
                        relationSet.add(addedRelation);
                    }

                }
            }
        }

        for (ElementParameterType eleParamType : parametersMap.values()) {
            if (EParameterFieldType.HADOOP_DISTRIBUTION.name().equals(eleParamType.getField())) {
                String name = eleParamType.getName();
                if (StringUtils.isNotEmpty(name)) {
                    ComponentType componentType = ComponentType.getComponentType(name);
                    if (componentType != null) {
                        String versionParam = componentType.getVersionParameter();
                        if (StringUtils.isNotEmpty(versionParam)) {
                            ElementParameterType elemParam = parametersMap.get(versionParam);
                            if (elemParam != null) {
                                String value = elemParam.getValue();
                                if (StringUtils.isNotEmpty(value)) {
                                    if (DynamicDistributionManager.getInstance().isUsersDynamicDistribution(value)) {
                                        Relation addedRelation = new Relation();
                                        addedRelation.setId(value);
                                        addedRelation.setType(RelationshipItemBuilder.DYNAMIC_DISTRIBUTION_RELATION);
                                        addedRelation.setVersion(RelationshipItemBuilder.LATEST_VERSION);
                                        relationSet.add(addedRelation);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }

        return relationSet;
    }

}
