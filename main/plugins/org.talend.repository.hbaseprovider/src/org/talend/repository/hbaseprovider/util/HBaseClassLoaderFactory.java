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
package org.talend.repository.hbaseprovider.util;

import org.apache.commons.lang.StringUtils;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.HadoopClassLoaderFactory2;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.runtime.hd.IHDConstants;

/**
 * created by ycbai on 2013-3-26 Detailled comment
 * 
 */
public class HBaseClassLoaderFactory {

    public static ClassLoader getClassLoader(IMetadataConnection metadataConnection) {
        ClassLoader loader = null;
        if (metadataConnection != null) {
            String clusterId = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
            String distribution = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_DISTRIBUTION);
            if (IHDConstants.DISTRIBUTION_CUSTOM.equals(distribution)) {
                return getCustomClassLoader(metadataConnection);
            }
            String version = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_VERSION);
            if (StringUtils.isNotEmpty(distribution) && StringUtils.isNotEmpty(version)) {
                boolean useKrb = Boolean.valueOf((String) metadataConnection
                        .getParameter(ConnParameterKeys.CONN_PARA_KEY_USE_KRB));
                loader = HadoopClassLoaderFactory2.getHBaseClassLoader(clusterId, distribution, version, useKrb);
            }
        }
        if (loader == null) {
            loader = HBaseClassLoaderFactory.class.getClassLoader();
        }

        return loader;
    }

    private static ClassLoader getCustomClassLoader(IMetadataConnection metadataConnection) {
        String hcId = metadataConnection.getId();
        String index = "HadoopCustomVersion:HBase:" + hcId; //$NON-NLS-1$ 
        return HadoopClassLoaderFactory2.getHadoopCustomClassLoader(index,
                metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS));
    }
}
