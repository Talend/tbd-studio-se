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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.metadata.MappingTypeRetriever;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;

/**
 * created by cmeng on Oct 14, 2015 Detailled comment
 *
 */
public class FixCassandraDataStaxDbtypesProblemMigrationTask extends AbstractItemMigrationTask {

    private static final String CASSANDRA_2_0_0 = "CASSANDRA_2_0_0"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.migration.IMigrationTask#getOrder()
     */
    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2015, 10, 14, 17, 51, 0);
        return gc.getTime();
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
                EMap<String, String> attributes = noSqlConn.getAttributes();
                String dbVersion = attributes.get(INoSQLCommonAttributes.DB_VERSION);
                if (!CASSANDRA_2_0_0.equals(dbVersion)) {
                    return ExecutionResult.NOTHING_TO_DO;
                }
                boolean isDatastaxApiType = ICassandraConstants.API_TYPE_DATASTAX.equals(attributes
                        .get(INoSQLCommonAttributes.API_TYPE));
                if (!isDatastaxApiType) {
                    return ExecutionResult.NOTHING_TO_DO;
                }
                Set<MetadataTable> tables = ConnectionHelper.getTables(noSqlConn);
                if (tables == null || tables.isEmpty()) {
                    return ExecutionResult.NOTHING_TO_DO;
                }
                MappingTypeRetriever mappingTypeRetriever = MetadataTalendType
                        .getMappingTypeRetriever(ICassandraConstants.DBM_DATASTAX_ID);
                for (MetadataTable table : tables) {
                    EList<MetadataColumn> columns = table.getColumns();
                    if (columns == null || columns.isEmpty()) {
                        continue;
                    }
                    for (MetadataColumn column : columns) {
                        String sourceType = column.getSourceType();
                        if (sourceType == null || sourceType.isEmpty()) {
                            continue;
                        }
                        modify = true;
                        sourceType = sourceType.toLowerCase();
                        column.setSourceType(sourceType);
                        String talendType = mappingTypeRetriever.getDefaultSelectedTalendType(sourceType);
                        if (talendType == null) {
                            talendType = JavaTypesManager.STRING.getId();
                        }
                        column.setTalendType(talendType);
                    }
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

}
