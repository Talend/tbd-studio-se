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
package org.talend.repository.nosql.ui.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.nosql.metadata.NoSQLSchemaUtil;
import org.talend.repository.nosql.model.INoSQLSchemaNode;
import org.talend.repository.nosql.util.NoSQLRepositoryUtil;

/**
 * created by ycbai on Jul 7, 2014 Detailled comment
 * 
 */
public class RetrieveColumnRunnable implements Runnable {

    private final Logger log = Logger.getLogger(RetrieveColumnRunnable.class);

    private final static String SUCCESS = Messages.getString("RetrieveSchema.status.success"); //$NON-NLS-1$

    private final static String PENDING = Messages.getString("RetrieveSchema.status.pending"); //$NON-NLS-1$

    private final static String ERROR_MSG = Messages.getString("RetrieveSchema.status.fail"); //$NON-NLS-1$

    private final IMetadataProvider metadataProvider;

    private final NoSQLConnection connection;

    private final INoSQLSchemaNode node;

    private Map<String, MetadataTable> hitTablesMap;

    volatile boolean canceled = false;

    private final ReentrantLock lock = new ReentrantLock();

    public RetrieveColumnRunnable(IMetadataProvider metadataProvider, NoSQLConnection connection, INoSQLSchemaNode node,
            Map<String, MetadataTable> hitTablesMap) {
        this.metadataProvider = metadataProvider;
        this.connection = connection;
        this.node = node;
        this.hitTablesMap = hitTablesMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (isCanceled()) {
            return;
        }

        lock.lock();
        node.setStatus(PENDING);
        node.setRetrieved(true);
        try {
            MetadataTable table = RelationalFactory.eINSTANCE.createTdTable();
            String nodeName = node.getName();
            String tableName = MetadataToolHelper.validateTableName(nodeName);
            table.setName(nodeName);
            table.setLabel(tableName);
            table.setId(ProxyRepositoryFactory.getInstance().getNextId());
            List<MetadataColumn> columns = metadataProvider.extractColumns(connection, node);
            table.getColumns().addAll(columns);
            if (!ConnectionHelper.getTables(connection).contains(table)) {
                NoSQLSchemaUtil.addTable2Connection(connection, table, NoSQLRepositoryUtil.getDBName(node));
                hitTablesMap.put(tableName, table);
            }
            table.getAdditionalProperties().putAll(node.getParameters());
            if (columns != null) {
                node.setColNum(columns.size());
            }
            node.setHasProblem(false);
            node.setStatus(SUCCESS);
        } catch (Exception e) {
            NoSQLExtractSchemaException exception = new NoSQLExtractSchemaException(e);
            node.setHasProblem(true);
            node.setStatus(ERROR_MSG);
            log.error("Retrieve '" + node.getName() + "' unsuccessfully.", exception); //$NON-NLS-1$ //$NON-NLS-2$
        } finally {
            lock.unlock();
            refresh(node);
        }
    }

    protected void refresh(INoSQLSchemaNode schemaNode) {
        // Do nothing by default.
    }

    public INoSQLSchemaNode getNode() {
        return this.node;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
