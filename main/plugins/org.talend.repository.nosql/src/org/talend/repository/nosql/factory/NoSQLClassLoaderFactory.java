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
package org.talend.repository.nosql.factory;

import org.talend.core.classloader.ClassLoaderFactory;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;

/**
 * DOC PLV class global comment. Detailled comment
 */
public class NoSQLClassLoaderFactory {

    public static ClassLoader getClassLoader(NoSQLConnection connection) {
        ClassLoader loader = null;
        if (connection == null) {
            return loader;
        }
        String dbType = connection.getDbType();
        if (dbType == null) {
            return loader;
        }
        String dbVersion = connection.getAttributes().get(INoSQLCommonAttributes.DB_VERSION);
        if (dbVersion == null) {
            return getClassLoader(dbType, true);
        }
        return getClassLoader(dbType, dbVersion, true);
    }

    public static ClassLoader getClassLoader(String dbType, boolean showDownloadIfNotExist) {
        String index = "NOSQL:" + dbType; //$NON-NLS-1$
        ClassLoader loader = ClassLoaderFactory.getClassLoader(index, showDownloadIfNotExist);
        if (loader == null) {
            loader = NoSQLClassLoaderFactory.class.getClassLoader();
        }
        return loader;
    }

    public static ClassLoader getClassLoader(String dbType, String dbVersion, boolean showDownloadIfNotExist) {
        String index = "NOSQL:" + dbType + ":" + dbVersion; //$NON-NLS-1$ //$NON-NLS-2$
        ClassLoader loader = ClassLoaderFactory.getClassLoader(index, showDownloadIfNotExist);
        if (loader == null) {
            loader = NoSQLClassLoaderFactory.class.getClassLoader();
        }
        return loader;
    }
}
