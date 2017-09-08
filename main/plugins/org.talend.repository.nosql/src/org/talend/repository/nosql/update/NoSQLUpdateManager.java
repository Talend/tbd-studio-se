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
package org.talend.repository.nosql.update;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.relationship.Relation;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.core.model.update.EUpdateItemType;
import org.talend.core.model.update.RepositoryUpdateManager;

/**
 * 
 * created by ycbai on 2014-6-16 Detailled comment
 * 
 */
public class NoSQLUpdateManager {

    /**
     * 
     * DOC ycbai Comment method "updateHadoopClusterConnection".
     * 
     * @param connection
     * @return
     */
    public static boolean updateNoSQLConnection(ConnectionItem connection) {
        return updateNoSQLConnection(connection, true, false);
    }

    /**
     * 
     * DOC ycbai Comment method "updateHadoopClusterConnection".
     * 
     * @param connectionItem
     * @param show
     * @param onlySimpleShow
     * @return
     */
    public static boolean updateNoSQLConnection(ConnectionItem connectionItem, boolean show, final boolean onlySimpleShow) {
        List<Relation> relations = RelationshipItemBuilder.getInstance().getItemsRelatedTo(connectionItem.getProperty().getId(),
                RelationshipItemBuilder.LATEST_VERSION, RelationshipItemBuilder.PROPERTY_RELATION);

        RepositoryUpdateManager repositoryUpdateManager = new RepositoryUpdateManager(connectionItem, relations) {

            @Override
            public Set<EUpdateItemType> getTypes() {
                Set<EUpdateItemType> types = new HashSet<EUpdateItemType>();
                types.add(EUpdateItemType.NODE_PROPERTY);

                return types;
            }

        };
        return repositoryUpdateManager.doWork(true, false);
    }

}
