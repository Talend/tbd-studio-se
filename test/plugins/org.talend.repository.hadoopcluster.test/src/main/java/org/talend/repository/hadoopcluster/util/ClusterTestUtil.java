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

import org.talend.commons.utils.VersionUtils;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;

/**
 * created by ycbai on 2016年7月6日 Detailled comment
 *
 */
@SuppressWarnings("nls")
public class ClusterTestUtil {

    public static HadoopClusterConnectionItem createDefaultHadoopClusterItem() {
        return createHadoopClusterItem("testId", "testCluster");
    }

    public static HadoopClusterConnectionItem createHadoopClusterItem(String id, String name) {
        HadoopClusterConnection connection = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnection();
        Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
        connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
        connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
        connectionProperty.setStatusCode(""); //$NON-NLS-1$
        connectionProperty.setId(id);
        connectionProperty.setLabel(name);

        HadoopClusterConnectionItem connectionItem = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnectionItem();
        connectionItem.setProperty(connectionProperty);
        connectionItem.setConnection(connection);

        return connectionItem;
    }

}
