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
package org.talend.repository.nosql.db.provider.neo4j;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.nosql.db.ui.neo4j.Neo4jConnForm;
import org.talend.repository.nosql.db.ui.neo4j.Neo4jRetrieveSchemaForm;
import org.talend.repository.nosql.db.ui.neo4j.Neo4jSchemaForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLRetrieveSchemaForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaForm;
import org.talend.repository.nosql.ui.provider.AbstractWizardPageProvider;

/**
 * 
 * created by ycbai on Jul 22, 2014 Detailled comment
 * 
 */
public class Neo4jWizardPageProvider extends AbstractWizardPageProvider {

    @Override
    public AbstractNoSQLConnForm createConnectionForm(Composite parent, ConnectionItem connectionItem, String[] existingNames,
            boolean creation, WizardPage parentWizardPage) {
        return new Neo4jConnForm(parent, connectionItem, existingNames, creation, parentWizardPage);
    }

    @Override
    public AbstractNoSQLRetrieveSchemaForm createRetrieveSchemaForm(Composite parent, ConnectionItem connItem, boolean creation,
            WizardPage parentWizardPage) {
        return new Neo4jRetrieveSchemaForm(parent, connItem, null, creation, parentWizardPage);
    }

    @Override
    public AbstractNoSQLSchemaForm createSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable,
            boolean creation, WizardPage parentWizardPage) {
        return new Neo4jSchemaForm(parent, connectionItem, metadataTable, creation, parentWizardPage);
    }

}
