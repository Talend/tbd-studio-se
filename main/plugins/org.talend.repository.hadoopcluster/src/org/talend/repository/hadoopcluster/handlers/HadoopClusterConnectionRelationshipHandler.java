// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.designerproperties.RepositoryToComponentProperty;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.relationship.AbstractItemRelationshipHandler;
import org.talend.core.model.relationship.Relation;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.designer.core.model.components.EParameterName;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class HadoopClusterConnectionRelationshipHandler extends AbstractItemRelationshipHandler {

    @Override
    public boolean valid(Item baseItem) {
        if (baseItem instanceof HadoopClusterConnectionItem) {
            return true;
        } else if (baseItem instanceof DatabaseConnectionItem) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getBaseItemType(Item baseItem) {
        if (baseItem instanceof HadoopClusterConnectionItem) {
            return RelationshipItemBuilder.HADOOP_CLUSTER_RELATION;
        } else if (baseItem instanceof DatabaseConnectionItem) {
            return RelationshipItemBuilder.DB_CONNECTION_RELATION;
        } else {
            return null;
        }
    }

    @Override
    protected Set<Relation> collect(Item baseItem) {
        if (baseItem instanceof HadoopClusterConnectionItem) {
            HadoopClusterConnectionItem hcItem = (HadoopClusterConnectionItem) baseItem;

            HadoopClusterConnection connection = (HadoopClusterConnection) hcItem.getConnection();
            if (connection != null) {
                String version = connection.getDfVersion();
                if (StringUtils.isNotEmpty(version)) {
                    if (DynamicDistributionManager.getInstance().isUsersDynamicDistribution(version)) {
                        Set<Relation> relationSet = new HashSet<Relation>();
                        Relation addedRelation = new Relation();
                        addedRelation.setId(version);
                        addedRelation.setType(RelationshipItemBuilder.DYNAMIC_DISTRIBUTION_RELATION);
                        addedRelation.setVersion(RelationshipItemBuilder.LATEST_VERSION);
                        relationSet.add(addedRelation);
                        return relationSet;
                    }
                }
            }
        }
        if (baseItem instanceof DatabaseConnectionItem) {
            Connection connection = ((DatabaseConnectionItem) baseItem).getConnection();
            if (connection != null) {
                Object value = RepositoryToComponentProperty.getValue(connection, EParameterName.DB_VERSION.getName(), null);
                if (value instanceof String) {
                    String version = (String) value;
                    if (DynamicDistributionManager.getInstance().isUsersDynamicDistribution(version)) {
                        Set<Relation> relationSet = new HashSet<Relation>();
                        Relation addedRelation = new Relation();
                        addedRelation.setId(version);
                        addedRelation.setType(RelationshipItemBuilder.DYNAMIC_DISTRIBUTION_RELATION);
                        addedRelation.setVersion(RelationshipItemBuilder.LATEST_VERSION);
                        relationSet.add(addedRelation);
                        return relationSet;
                    }
                }
            }
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<ERepositoryObjectType> getSupportReoObjTypes(String relationType) {
        Collection<ERepositoryObjectType> repoTypes = new ArrayList<>();
        if (RelationshipItemBuilder.HADOOP_CLUSTER_RELATION.equals(relationType)) {
            ERepositoryObjectType hadoopClusterRepType = ERepositoryObjectType.valueOf("HADOOPCLUSTER"); //$NON-NLS-1$
            if (hadoopClusterRepType != null) {
                repoTypes.add(hadoopClusterRepType);
            }
        }
        if (RelationshipItemBuilder.DB_CONNECTION_RELATION.equals(relationType)) {
            if (ERepositoryObjectType.METADATA_CONNECTIONS != null) {
                repoTypes.add(ERepositoryObjectType.METADATA_CONNECTIONS);
            }
        }
        return repoTypes;
    }
}
