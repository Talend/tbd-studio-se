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
package org.talend.repository.nosql.db.provider.cassandra;

import java.util.ArrayList;
import java.util.HashSet;
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
import org.talend.repository.nosql.db.common.cassandra.ICassandraAttributies;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;
import org.talend.repository.nosql.db.handler.cassandra.ICassandraMetadataHandler;
import org.talend.repository.nosql.db.ui.cassandra.CassandraConnForm;
import org.talend.repository.nosql.db.ui.cassandra.CassandraRetrieveSchemaForm;
import org.talend.repository.nosql.db.ui.cassandra.CassandraSchemaForm;
import org.talend.repository.nosql.db.util.cassandra.CassandraConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.model.ENoSQLSchemaType;
import org.talend.repository.nosql.model.INoSQLSchemaNode;
import org.talend.repository.nosql.model.NoSQLSchemaNode;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLRetrieveSchemaForm;
import org.talend.repository.nosql.ui.common.AbstractNoSQLSchemaForm;
import org.talend.repository.nosql.ui.provider.AbstractWizardPageProvider;

public class CassandraWizardPageProvider extends AbstractWizardPageProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.ui.provider.IWizardPageProvider#createConnectionForm(org.eclipse.swt.widgets.Composite
     * , org.talend.core.model.properties.ConnectionItem, java.lang.String[])
     */
    @Override
    public AbstractNoSQLConnForm createConnectionForm(Composite parent, ConnectionItem connectionItem, String[] existingNames,
            boolean creation, WizardPage parentWizardPage) {
        return new CassandraConnForm(parent, connectionItem, existingNames, creation, parentWizardPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.ui.provider.AbstractWizardPageProvider#createRetrieveSchemaForm(org.eclipse.swt.widgets
     * .Composite, org.talend.core.model.properties.ConnectionItem, org.eclipse.jface.wizard.WizardPage)
     */
    @Override
    public AbstractNoSQLRetrieveSchemaForm createRetrieveSchemaForm(Composite parent, ConnectionItem connectionItem,
            boolean creation, WizardPage parentWizardPage) {
        return new CassandraRetrieveSchemaForm(parent, connectionItem, null, creation, parentWizardPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.ui.provider.AbstractWizardPageProvider#createSchemaForm(org.eclipse.swt.widgets.Composite
     * , org.talend.core.model.properties.ConnectionItem,
     * org.talend.core.model.metadata.builder.connection.MetadataTable, org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
    public AbstractNoSQLSchemaForm createSchemaForm(Composite parent, ConnectionItem connectionItem, MetadataTable metadataTable,
            boolean creation, WizardPage parentWizardPage) {
        return new CassandraSchemaForm(parent, connectionItem, metadataTable, creation, parentWizardPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.nosql.ui.provider.IWizardPageProvider#createSchemaNodes(org.talend.repository.model.nosql
     * .NoSQLConnection)
     */
    @Override
    public List<INoSQLSchemaNode> createSchemaNodes(NoSQLConnection connection) throws NoSQLExtractSchemaException {
        List<INoSQLSchemaNode> schemaNodes = new ArrayList<INoSQLSchemaNode>();
        try {
            String ksName = connection.getAttributes().get(ICassandraAttributies.DATABASE);
            if (connection.isContextMode()) {
                ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
                ksName = ContextParameterUtils.getOriginalValue(contextType, ksName);
            }
            if (StringUtils.isEmpty(ksName)) {
                List<String> databaseNames = CassandraConnectionUtil.getMetadataHandler(connection).getKeySpaceNames(connection);
                for (String dbn : databaseNames) {
                    INoSQLSchemaNode dbNode = new NoSQLSchemaNode();
                    dbNode.setName(dbn);
                    dbNode.setNodeType(ICassandraConstants.KEY_SPACE);
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
        Set<String> cfNames = null;
        Set<String> scfNames = null;
        String ksName = connection.getAttributes().get(ICassandraAttributies.DATABASE);
        if (parentNode != null && StringUtils.isNotEmpty(parentNode.getName())) {
            ksName = parentNode.getName();
        }
        ICassandraMetadataHandler metadataHandler = CassandraConnectionUtil.getMetadataHandler(connection);
        if (ksName != null) {
            cfNames = metadataHandler.getColumnFamilyNames(connection, ksName);
            if(!CassandraConnectionUtil.isUpgradeVersion(connection)){
                scfNames = metadataHandler.getSuperColumnFamilyNames(connection, ksName);
            }else{
                scfNames = new HashSet<String>();
                metadataHandler.closeConnections();            
            }
        } else {
            cfNames = metadataHandler.getColumnFamilyNames(connection);
            if(!CassandraConnectionUtil.isUpgradeVersion(connection)){
                scfNames = metadataHandler.getSuperColumnFamilyNames(connection);
            }else{
                scfNames = new HashSet<String>();
                metadataHandler.closeConnections();
            }
        }
        if (CassandraConnectionUtil.isOldVersion(connection)) {
            collectNodesForOldVersion(schemaNodes, parentNode, cfNames, scfNames);
        } else {
            collectNodes(schemaNodes, parentNode, cfNames, scfNames);
        }

        return schemaNodes;
    }

    private void collectNodes(List<INoSQLSchemaNode> schemaNodes, INoSQLSchemaNode parentNode, Set<String> cfNames,
            Set<String> scfNames) {
        for (String name : cfNames) {
            NoSQLSchemaNode node = new NoSQLSchemaNode();
            node.setName(name);
            node.setNodeType(ICassandraConstants.COLUMN_FAMILY);
            node.getParameters().put(ICassandraAttributies.COLUMN_FAMILY_TYPE, ICassandraAttributies.COLUMN_FAMILY_TYPE_STANDARD);
            if (scfNames.size() > 0) {
                for (String scfName : scfNames) {
                    if (name.equals(scfName)) {
                        node.setNodeType(ICassandraConstants.SUPER_COLUMN_FAMILY);
                        node.getParameters().put(ICassandraAttributies.COLUMN_FAMILY_TYPE,
                                ICassandraAttributies.COLUMN_FAMILY_TYPE_SUPER);
                        break;
                    }
                }
            }
            node.setSchemaType(ENoSQLSchemaType.TABLE);
            if (parentNode != null) {
                node.setParent(parentNode);
                parentNode.addChild(node);
            } else {
                schemaNodes.add(node);
            }
        }
    }

    private void collectNodesForOldVersion(List<INoSQLSchemaNode> schemaNodes, INoSQLSchemaNode parentNode, Set<String> cfNames,
            Set<String> scfNames) {
        for (String name : cfNames) {
            addNode(name, schemaNodes, parentNode, false);
        }
        for (String name : scfNames) {
            addNode(name, schemaNodes, parentNode, true);
        }
    }

    private void addNode(String nodeName, List<INoSQLSchemaNode> schemaNodes, INoSQLSchemaNode parentNode, boolean isSuper) {
        NoSQLSchemaNode node = new NoSQLSchemaNode();
        node.setName(nodeName);
        if (isSuper) {
            node.setNodeType(ICassandraConstants.SUPER_COLUMN_FAMILY);
            node.getParameters().put(ICassandraAttributies.COLUMN_FAMILY_TYPE, ICassandraAttributies.COLUMN_FAMILY_TYPE_SUPER);
        } else {
            node.setNodeType(ICassandraConstants.COLUMN_FAMILY);
            node.getParameters().put(ICassandraAttributies.COLUMN_FAMILY_TYPE, ICassandraAttributies.COLUMN_FAMILY_TYPE_STANDARD);
        }
        node.setSchemaType(ENoSQLSchemaType.TABLE);
        if (parentNode != null) {
            node.setParent(parentNode);
            parentNode.addChild(node);
        } else {
            schemaNodes.add(node);
        }
    }

}
