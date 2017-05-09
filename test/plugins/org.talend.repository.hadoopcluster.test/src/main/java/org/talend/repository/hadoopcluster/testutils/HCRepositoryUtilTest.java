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
package org.talend.repository.hadoopcluster.testutils;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;
import org.talend.repository.hadoopcluster.util.ClusterTestUtil;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;


/**
 * created by ycbai on 2016年7月6日 Detailled comment
 *
 */
@SuppressWarnings("nls")
public class HCRepositoryUtilTest {

    @Test
    public void testGetHadoopDbParameters() {
        HadoopClusterConnectionItem hcItem = ClusterTestUtil.createDefaultHadoopClusterItem();
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcItem.getConnection();
        String NAME_NODE = "namenode";
        hcConnection.setNameNodeURI(NAME_NODE);

        Map<String, String> dbParameters = HCRepositoryUtil.getHadoopDbParameters(hcItem);
        assertEquals(NAME_NODE, dbParameters.get(ConnParameterKeys.CONN_PARA_KEY_NAME_NODE_URL));
    }

    @Test
    public void testFillDefaultValuesOfHadoopCluster() {
        HadoopClusterConnectionItem hcItem = ClusterTestUtil.createDefaultHadoopClusterItem();
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcItem.getConnection();
        hcConnection.setDistribution(IMicrosoftHDInsightDistribution.DISTRIBUTION_NAME);
        hcConnection.setDfVersion("MICROSOFT_HD_INSIGHT_3_4");

        HCRepositoryUtil.fillDefaultValuesOfHadoopCluster(hcConnection);
        assertEquals("clustername.azurehdinsight.net",
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_HOSTNAME));
        assertEquals("443", hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_PORT));
        assertEquals("admin", hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_WEB_HCAT_USERNAME));
        assertEquals("admin", hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HDI_USERNAME));
        assertEquals("storage.blob.core.windows.net",
                hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_HOSTNAME));
        assertEquals("clustername", hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_CONTAINER));
        assertEquals("admin", hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_USERNAME));
        assertEquals("D:\\blobdir", hcConnection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_AZURE_DEPLOY_BLOB));
    }
}
