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
package org.talend.repository.nosql.repository.migration;

import java.util.ArrayList;
import java.util.List;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.model.migration.UnifyPasswordEncryption4ItemMigrationTask;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.nosql.db.common.cassandra.ICassandraAttributies;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;

/**
 * created by ggu on Sep 1, 2014 Detailled comment
 *
 */
public class UnifyPasswordEncryption4NoSQLConnectionMigrationTask extends UnifyPasswordEncryption4ItemMigrationTask {

    @Override
    public List<ERepositoryObjectType> getTypes() {
        List<ERepositoryObjectType> toReturn = new ArrayList<ERepositoryObjectType>();
        toReturn.add(NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS);
        return toReturn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.migration.AbstractItemMigrationTask#execute(org .talend.core.model.properties.Item)
     */
    @Override
    public ExecutionResult execute(Item item) {
        if (item instanceof NoSQLConnectionItem) {
            Connection connection = ((NoSQLConnectionItem) item).getConnection();
            if (connection instanceof NoSQLConnection) {
                NoSQLConnection noSqlConn = (NoSQLConnection) connection;
                try {
                    if (!noSqlConn.isContextMode()) {
                        String pass = noSqlConn.getAttributes().get(ICassandraAttributies.PASSWORD);
                        //
                        noSqlConn.getAttributes().put(ICassandraAttributies.PASSWORD, noSqlConn.getValue(pass, true));
                        factory.save(item, true);
                        return ExecutionResult.SUCCESS_NO_ALERT;
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                    return ExecutionResult.FAILURE;
                }
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

}
