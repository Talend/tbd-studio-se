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
package org.talend.repository.hadoopcluster.util;

import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.ItemState;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.core.model.utils.emf.talendfile.ContextParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.core.model.utils.emf.talendfile.TalendFileFactory;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;

/**
 * created by ycbai on 2016年7月6日 Detailled comment
 *
 */
public class ClusterTestUtil {

    public static HadoopClusterConnectionItem createDefaultHadoopClusterItem() {
        return createHadoopClusterItem("testCluster"); //$NON-NLS-1$
    }

    public static HadoopClusterConnectionItem createHadoopClusterItem(String name) {
        HadoopClusterConnectionItem hadoopClusterItem = null;
        try {
            hadoopClusterItem = createHadoopClusterItem(name, false);
        } catch (PersistenceException e) {
            // will not happen normally.
        }
        return hadoopClusterItem;
    }

    public static HadoopClusterConnectionItem createHadoopClusterItem(String name, boolean persistence)
            throws PersistenceException {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        HadoopClusterConnection connection = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnection();
        Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
        connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
        connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
        connectionProperty.setStatusCode(""); //$NON-NLS-1$
        connectionProperty.setId(factory.getNextId());
        connectionProperty.setLabel(name);

        HadoopClusterConnectionItem connectionItem = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnectionItem();
        connectionItem.setProperty(connectionProperty);
        connectionItem.setConnection(connection);

        if (persistence) {
            factory.create(connectionItem, new Path("")); //$NON-NLS-1$
        }

        return connectionItem;
    }

    public static ContextItem createContextItem(String name) {
        ContextItem contextItem = null;
        try {
            contextItem = createContextItem(name, null, false);
        } catch (PersistenceException e) {
            // will not happen normally.
        }
        return contextItem;
    }

    public static ContextItem createContextItem(String name, List<ContextType> contextTypes, boolean persistence)
            throws PersistenceException {
        ContextItem contextItem = PropertiesFactory.eINSTANCE.createContextItem();
        Property contextProperty = PropertiesFactory.eINSTANCE.createProperty();
        contextProperty.setId(ProxyRepositoryFactory.getInstance().getNextId());
        ItemState itemState = PropertiesFactory.eINSTANCE.createItemState();
        itemState.setDeleted(false);
        itemState.setPath(""); //$NON-NLS-1$
        contextItem.setState(itemState);
        contextItem.setProperty(contextProperty);
        contextProperty.setLabel(name);
        contextProperty.setVersion("0.1"); //$NON-NLS-1$

        if (contextTypes != null) {
            EList<ContextType> contexts = contextItem.getContext();
            for (ContextType contextType : contextTypes) {
                contexts.add(contextType);
            }
        }

        if (persistence) {
            ProxyRepositoryFactory.getInstance().create(contextItem, Path.EMPTY);
        }

        return contextItem;
    }

    public static ContextType createContextType(String contextGroupName, String... paramNames) {
        ContextType context = TalendFileFactory.eINSTANCE.createContextType();
        context.setName(contextGroupName);
        if (paramNames != null) {
            for (String paramName : paramNames) {
                ContextParameterType param = TalendFileFactory.eINSTANCE.createContextParameterType();
                param.setName(paramName);
                param.setType("id_String"); //$NON-NLS-1$
                context.getContextParameter().add(param);
            }
        }
        return context;
    }

    public static void deleteItem(Item item) throws PersistenceException {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        IRepositoryViewObject repObj = factory.getLastVersion(item.getProperty().getId());
        if (repObj != null) {
            factory.deleteObjectPhysical(repObj);
        }
    }

}
