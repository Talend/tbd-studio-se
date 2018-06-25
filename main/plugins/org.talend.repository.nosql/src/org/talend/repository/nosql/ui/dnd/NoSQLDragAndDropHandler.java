// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.ui.dnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsService;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.repository.RepositoryComponentSetting;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NoSQLConnectionItem;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;

/**
 * 
 * created by ycbai on 2014-6-18 Detailled comment
 * 
 */
public class NoSQLDragAndDropHandler extends AbstractNoSQLDragAndDropHandler {

    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof NoSQLConnection;
    }

    @Override
    public Object getComponentValue(Connection connection, String value, IMetadataTable table, String targetComponent, Map<Object, Object> contextMap) {
        if (value != null && canHandle(connection)) {
            return getNoSQLRepositoryValue((NoSQLConnection) connection, value, table, targetComponent);
        }
        return null;
    }

    private Object getNoSQLRepositoryValue(NoSQLConnection connection, String value, IMetadataTable table, String targetComponent) {
        String dbType = connection.getDbType();
        IDNDProvider dndProvider = NoSQLRepositoryFactory.getInstance().getDNDProvider(dbType);
        if (dndProvider != null) {
            return dndProvider.getRepositoryValue(connection, value, table, targetComponent);
        }

        return null;
    }

    @Override
    public List<IComponent> filterNeededComponents(Item item, RepositoryNode seletetedNode, ERepositoryObjectType type) {
        List<IComponent> neededComponents = new ArrayList<IComponent>();
        if (!(item instanceof NoSQLConnectionItem)) {
            return neededComponents;
        }
        NoSQLConnection connection = (NoSQLConnection) ((NoSQLConnectionItem) item).getConnection();
        IComponentsService service = (IComponentsService) GlobalServiceRegister.getDefault().getService(IComponentsService.class);
        Collection<IComponent> components = service.getComponentsFactory().readComponents();
        for (IComponent component : components) {
            if (isValid(item, type, seletetedNode, component, getRepType(connection)) && !neededComponents.contains(component)) {
                neededComponents.add(component);
            }
        }

        return neededComponents;
    }

    private boolean isValid(Item item, ERepositoryObjectType type, RepositoryNode seletetedNode, IComponent component,
            String repositoryType) {
        if (component == null || repositoryType == null) {
            return false;
        }

        String componentProductname = component.getRepositoryType();
        if (componentProductname != null && componentProductname.contains(repositoryType)
                && isSubValid(item, type, seletetedNode, component, repositoryType)) {
            return true;
        }
        return false;
    }

    protected boolean isSubValid(Item item, ERepositoryObjectType type, RepositoryNode seletetedNode, IComponent component,
            String repositoryType) {
        boolean tableWithMap = true;
        if (type == ERepositoryObjectType.METADATA_CON_TABLE) {
            if (component.getName().toUpperCase().endsWith(MAP)) {
                tableWithMap = false;

            }
        }
        return tableWithMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        RepositoryComponentSetting setting = null;
        if (item instanceof NoSQLConnectionItem) {
            NoSQLConnection connection = (NoSQLConnection) ((NoSQLConnectionItem) item).getConnection();
            String repType = getRepType(connection);
            setting = new RepositoryComponentSetting();
            setting.setName(repType);
            setting.setRepositoryType(repType);
            setting.setWithSchema(true);
            setting.setInputComponent(getInputComponentName(connection));
            setting.setOutputComponent(getOutputComponentName(connection));
            List<Class<Item>> list = new ArrayList<Class<Item>>();
            Class clazz = null;
            try {
                clazz = Class.forName(NoSQLConnectionItem.class.getName());
            } catch (ClassNotFoundException e) {
                ExceptionHandler.process(e);
            }
            list.add(clazz);
            setting.setClasses(list.toArray(new Class[0]));
        }

        return setting;
    }

    @Override
    public void setComponentValue(Connection connection, INode node, IElementParameter param) {
        if (node != null && canHandle(connection)) {
            setNoSQLRepositoryValue((NoSQLConnection) connection, node, param);
        }
    }

    private void setNoSQLRepositoryValue(NoSQLConnection connection, INode node, IElementParameter param) {
        String dbType = connection.getDbType();
        IDNDProvider dndProvider = NoSQLRepositoryFactory.getInstance().getDNDProvider(dbType);
        if (dndProvider != null) {
            dndProvider.setRepositoryValue(connection, node, param);
        }
    }

    @Override
    public void handleTableRelevantParameters(Connection connection, IElement ele, IMetadataTable metadataTable) {
        if (canHandle(connection)) {
            NoSQLConnection conn = (NoSQLConnection) connection;
            String dbType = conn.getDbType();
            IDNDProvider dndProvider = NoSQLRepositoryFactory.getInstance().getDNDProvider(dbType);
            if (dndProvider != null) {
                dndProvider.handleTableRelevantParameters(conn, ele, metadataTable);
            }
        }
    }

}
