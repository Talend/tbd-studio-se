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
package org.talend.repository.nosql.db.ui.neo4j;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.db.util.neo4j.Neo4jConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaForm;

/**
 * 
 * created by ycbai on Jul 22, 2014 Detailled comment
 * 
 */
public class Neo4jSchemaForm extends AbstractNoSQLSchemaForm {

    public Neo4jSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, connectionItem, metadataTable, creation, parentWizardPage);
        setupForm();
    }

    @Override
    protected void addFields() {
        createMainCompositeField();
        createHeaderField();
        createGroupMetaDataField();
    }

    @Override
    public void processWhenShowPage(WizardPage page) {
        super.processWhenShowPage(page);
        if (!creation) {
            return;
        }
        try {
            page.getWizard().getContainer().run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                monitor.beginTask(
                                        Messages.getString("Neo4jSchemaForm.cypher.genSchema"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                                generateSchema();
                                initializeForm();
                            } catch (Exception e) {
                                updateStatus(IStatus.ERROR, Messages.getString("RetrieveSchema.status.fail")); //$NON-NLS-1$
                                ExceptionHandler.process(e);
                            } finally {
                                monitor.done();
                            }
                        }
                    });
                }

            });
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    private void generateSchema() throws NoSQLExtractSchemaException {
        if (metadataTable == null) {
            return;
        }
        String cypher = metadataTable.getAdditionalProperties().get(INeo4jConstants.CYPHER);
        if (cypher == null) {
            return;
        }
        IMetadataProvider metadataProvider = NoSQLRepositoryFactory.getInstance()
                .getMetadataProvider(getConnection().getDbType());
        if (metadataProvider == null) {
            return;
        }

        List<MetadataColumn> columns = metadataProvider.extractColumns(getConnection(), cypher);
        metadataTable.getColumns().clear();
        metadataTable.getColumns().addAll(columns);
    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            Neo4jConnectionUtil.closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

}
