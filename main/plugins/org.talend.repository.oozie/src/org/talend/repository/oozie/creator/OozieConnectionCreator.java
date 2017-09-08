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
package org.talend.repository.oozie.creator;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.talend.core.hadoop.conf.EHadoopConfProperties;
import org.talend.core.hadoop.conf.EHadoopConfs;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.repository.hadoopcluster.creator.AbstractHadoopSubConnectionCreator;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;
import org.talend.repository.model.oozie.OozieConnection;
import org.talend.repository.model.oozie.OozieFactory;

/**
 * 
 * created by ycbai on 2015年7月1日 Detailled comment
 *
 */
public class OozieConnectionCreator extends AbstractHadoopSubConnectionCreator {

    @Override
    public ConnectionItem create(Map<String, Map<String, String>> initParams) throws CoreException {
        OozieConnection connection = OozieFactory.eINSTANCE.createOozieConnection();
        Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
        setPropertyParameters(connectionProperty);

        HadoopSubConnectionItem connectionItem = OozieFactory.eINSTANCE.createOozieConnectionItem();
        connectionItem.setProperty(connectionProperty);
        connectionItem.setConnection(connection);

        setParameters(connection, initParams);
        initializeConnectionParameters(connection);
        appendToHadoopCluster(connectionItem);

        return connectionItem;
    }

    @Override
    public String getTypeName() {
        return EHadoopConfs.OOZIE.getName();
    }

    protected void setParameters(OozieConnection connection, Map<String, Map<String, String>> initParams) {
        if (connection == null || initParams.size() == 0) {
            return;
        }
        Map<String, String> params = initParams.get(getTypeName());
        if (params != null) {
            String authType = params.get(EHadoopConfProperties.OOZIE_AUTHENTICATION_TYPE.getName());
            if (StringUtils.isNotEmpty(authType)) {
                connection.setEnableKerberos("kerberos".equals(authType)); //$NON-NLS-1$
            }
            String oozieUrl = params.get(EHadoopConfProperties.OOZIE_BASE_URL.getName());
            if (StringUtils.isNotEmpty(oozieUrl)) {
                connection.setOozieEndPoind(oozieUrl);
            }
        }
    }

    @Override
    protected void initializeConnectionParameters(Connection connection) {
        // nothing need to do
    }

}
