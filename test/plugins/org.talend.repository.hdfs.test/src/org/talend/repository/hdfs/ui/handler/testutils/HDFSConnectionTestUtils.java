// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.ui.handler.testutils;

import org.talend.commons.utils.VersionUtils;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSConnectionItem;
import org.talend.repository.model.hdfs.HDFSFactory;

/**
 * created by ycbai on 2017年1月13日 Detailled comment
 *
 */
public class HDFSConnectionTestUtils {

    public static HDFSConnectionItem createDefaultHDFSItem() {
        return createHDFSItem("testId", "testHDFS"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static HDFSConnectionItem createHDFSItem(String id, String name) {
        HDFSConnection connection = HDFSFactory.eINSTANCE.createHDFSConnection();
        connection.setRowSeparator("\\n"); //$NON-NLS-1$
        connection.setFieldSeparator(";"); //$NON-NLS-1$
        connection.setHeaderValue("2"); //$NON-NLS-1$

        Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
        connectionProperty.setAuthor(
                ((RepositoryContext) CoreRuntimePlugin.getInstance().getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY))
                        .getUser());
        connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
        connectionProperty.setStatusCode(""); //$NON-NLS-1$
        connectionProperty.setId(id);
        connectionProperty.setLabel(name);

        HDFSConnectionItem connectionItem = HDFSFactory.eINSTANCE.createHDFSConnectionItem();
        connectionItem.setProperty(connectionProperty);
        connectionItem.setConnection(connection);

        return connectionItem;
    }

}
