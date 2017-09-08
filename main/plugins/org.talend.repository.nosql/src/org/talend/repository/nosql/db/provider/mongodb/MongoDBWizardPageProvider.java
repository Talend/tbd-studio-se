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
package org.talend.repository.nosql.db.provider.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.db.common.mongodb.IMongoConstants;
import org.talend.repository.nosql.db.common.mongodb.IMongoDBAttributes;
import org.talend.repository.nosql.db.ui.mongodb.MongoDBConnForm;
import org.talend.repository.nosql.db.ui.mongodb.MongoDBRetrieveSchemaForm;
import org.talend.repository.nosql.db.ui.mongodb.MongoDBSchemaForm;
import org.talend.repository.nosql.db.util.mongodb.MongoDBConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.model.ENoSQLSchemaType;
import org.talend.repository.nosql.model.INoSQLSchemaNode;
import org.talend.repository.nosql.model.NoSQLSchemaNode;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLRetrieveSchemaForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaForm;
import org.talend.repository.nosql.ui.provider.AbstractWizardPageProvider;

public class MongoDBWizardPageProvider extends AbstractWizardPageProvider {

    @Override
    public AbstractNoSQLConnForm createConnectionForm(Composite parent, ConnectionItem connectionItem, String[] existingNames,
            boolean creation, WizardPage parentWizardPage) {
        return new MongoDBConnForm(parent, connectionItem, existingNames, creation, parentWizardPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.ui.provider.IWizardPageProvider#createRetrieveSchemaForm(org.eclipse.swt.widgets.
     * Composite, org.talend.core.model.properties.ConnectionItem, org.talend.repository.model.nosql.NoSQLConnection,
     * org.eclipse.jface.wizard.WizardPage)
     */
    @Override
    public AbstractNoSQLRetrieveSchemaForm createRetrieveSchemaForm(Composite parent, ConnectionItem connItem, boolean creation,
            WizardPage parentWizardPage) {
        return new MongoDBRetrieveSchemaForm(parent, connItem, null, creation, parentWizardPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.ui.provider.IWizardPageProvider#createSchemaForm(org.eclipse.swt.widgets.Composite,
     * org.talend.core.model.properties.ConnectionItem, org.talend.repository.model.nosql.NoSQLConnection,
     * org.talend.core.model.metadata.builder.connection.MetadataTable, org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public AbstractNoSQLSchemaForm createSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable,
            boolean creation, WizardPage parentWizardPage) {
        return new MongoDBSchemaForm(parent, connectionItem, metadataTable, creation, parentWizardPage);
    }

    @Override
    public List<INoSQLSchemaNode> createSchemaNodes(NoSQLConnection connection) throws NoSQLExtractSchemaException {
        List<INoSQLSchemaNode> schemaNodes = new ArrayList<INoSQLSchemaNode>();
        try {
            String dbName = connection.getAttributes().get(IMongoDBAttributes.DATABASE);
            if (connection.isContextMode()) {
                ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                dbName = ContextParameterUtils.getOriginalValue(contextType, dbName);
            }
            if (StringUtils.isEmpty(dbName)) {
                List<String> databaseNames = MongoDBConnectionUtil.getDatabaseNames(connection);
                for (String dbn : databaseNames) {
                    INoSQLSchemaNode dbNode = new NoSQLSchemaNode();
                    dbNode.setName(dbn);
                    dbNode.setNodeType(IMongoConstants.DATABASE);
                    dbNode.setSchemaType(ENoSQLSchemaType.DATABASE);
                    dbNode.addChildren(addSchemaNodes(connection, dbNode));
                    schemaNodes.add(dbNode);
                }
            } else {
                schemaNodes.addAll(addSchemaNodes(connection, null));
            }
        } catch (Exception e) {
            throw new NoSQLExtractSchemaException(e);
        }

        return schemaNodes;
    }

    private List<INoSQLSchemaNode> addSchemaNodes(NoSQLConnection connection, INoSQLSchemaNode parentNode)
            throws NoSQLServerException {
        List<INoSQLSchemaNode> schemaNodes = new ArrayList<INoSQLSchemaNode>();
        Set<String> collectionNames = null;
        String dbName = null;
        if (parentNode != null && StringUtils.isNotEmpty(parentNode.getName())) {
            dbName = parentNode.getName();
        }
        if (dbName != null) {
            collectionNames = MongoDBConnectionUtil.getCollectionNames(connection, dbName);
        } else {
            collectionNames = MongoDBConnectionUtil.getCollectionNames(connection);
        }
        for (String name : collectionNames) {
            NoSQLSchemaNode node = new NoSQLSchemaNode();
            node.setName(name);
            node.setNodeType(IMongoConstants.COLLECTION);
            node.setSchemaType(ENoSQLSchemaType.TABLE);
            if (parentNode != null) {
                node.setParent(parentNode);
                parentNode.addChild(node);
            } else {
                schemaNodes.add(node);
            }
        }

        return schemaNodes;
    }

}
