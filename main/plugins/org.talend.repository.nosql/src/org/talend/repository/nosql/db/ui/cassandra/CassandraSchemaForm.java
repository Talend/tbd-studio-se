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
package org.talend.repository.nosql.db.ui.cassandra;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.db.util.cassandra.CassandraConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaForm;

public class CassandraSchemaForm extends AbstractNoSQLSchemaForm {

    private boolean readOnlyStatus = false;

    private boolean isInitialized = false;

    public CassandraSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, connectionItem, metadataTable, creation, parentWizardPage);
        setShowDbTypeColumn(true);
        setDbmID(ICassandraConstants.DBM_ID);
        // setupForm();
    }

    @Override
    public void initializeForm() {
        if (!isInitialized) {
            boolean isDatastaxApiType = ICassandraConstants.API_TYPE_DATASTAX.equals(getConnection().getAttributes().get(
                    INoSQLCommonAttributes.API_TYPE));
            if(CassandraConnectionUtil.isUpgradeVersion(getConnection())){
                setDbmID(ICassandraConstants.DBM22_DATASTAX_ID);
            }else if (isDatastaxApiType) {
                setDbmID(ICassandraConstants.DBM_DATASTAX_ID);
            }
            setupForm();
            super.setReadOnly(readOnlyStatus);
            isInitialized = true;
            this.layout();
        }
        super.initializeForm();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        this.readOnlyStatus = readOnly;
        if (isInitialized) {
            super.setReadOnly(readOnly);
        }
    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            CassandraConnectionUtil.getMetadataHandler(getConnection()).closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

    @Override
    protected String getTableDisplayName() {
        return ICassandraConstants.COLUMN_FAMILY;
    }

}
