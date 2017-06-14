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
package org.talend.repository.nosql.repository.migration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.nosql.db.common.cassandra.ICassandraAttributies;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;

/**
 * 
 * created by hcyi on Aug 6, 2015 Detailled comment
 *
 */
public class ChangeMongoDBConnectionMinimalVersionMigrationTask extends AbstractItemMigrationTask {

    private String latestMajor2X = "MONGODB_2_6_X";//$NON-NLS-1$

    private String[] unsupportedVersion = { "MONGODB_2_1_2", "MONGODB_2_2_3", "MONGODB_2_4_X" };//$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$

    @Override
    public List<ERepositoryObjectType> getTypes() {
        List<ERepositoryObjectType> toReturn = new ArrayList<ERepositoryObjectType>();
        toReturn.add(NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS);
        return toReturn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.migration.AbstractItemMigrationTask#execute(org.talend.core.model.properties.Item)
     */
    @Override
    public ExecutionResult execute(Item item) {
        if (item instanceof NoSQLConnectionItem) {
            boolean modify = false;
            Connection connection = ((NoSQLConnectionItem) item).getConnection();
            if (connection instanceof NoSQLConnection) {
                NoSQLConnection noSqlConn = (NoSQLConnection) connection;
                try {
                    if (!noSqlConn.isContextMode()) {
                        String version = noSqlConn.getAttributes().get(ICassandraAttributies.DB_VERSION);
                        if (version == null || Arrays.asList(unsupportedVersion).contains(version)) {
                            // 1,No DB version => add one.
                            // 2,Has DB version, check if we still use it, if not change to the "lastestVersion".
                            noSqlConn.getAttributes().put(ICassandraAttributies.DB_VERSION, latestMajor2X);
                            modify = true;
                        }
                    }
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                    return ExecutionResult.FAILURE;
                }
            }
            if (modify) {
                try {
                    ProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
                    factory.save(item, true);
                    return ExecutionResult.SUCCESS_WITH_ALERT;
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                    return ExecutionResult.FAILURE;
                }
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2015, 8, 6, 17, 0, 0);
        return gc.getTime();
    }
}