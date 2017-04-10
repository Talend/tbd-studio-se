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
package org.talend.repository.hadoopcluster.ui.context.handler;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.talend.repository.hadoopcluster.util.ClusterTestUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * created by ycbai on 2017年4月10日 Detailled comment
 *
 */
public class HadoopClusterContextHandlerTest {

    private HadoopClusterContextHandler contextHandler;

    @Before
    public void before() {
        contextHandler = new HadoopClusterContextHandler();
    }

    @Test
    public void testGetConAdditionPropertiesForContextMode() {
        String hadoopPropertiesJsonStr = "[{\"PROPERTY\":\"hadoopProp\",\"VALUE\":\"hadoopPropValue\"}]"; //$NON-NLS-1$
        String sparkPropertiesJsonStr = "[{\"PROPERTY\":\"sparkProp\",\"VALUE\":\"sparkPropValue\"}]"; //$NON-NLS-1$
        HadoopClusterConnectionItem hadoopClusterItem = ClusterTestUtil.createDefaultHadoopClusterItem();
        HadoopClusterConnection hadoopClusterConn = (HadoopClusterConnection) hadoopClusterItem.getConnection();
        hadoopClusterConn.setHadoopProperties(hadoopPropertiesJsonStr);
        hadoopClusterConn.setSparkProperties(sparkPropertiesJsonStr);

        Set<String> additionProperties = contextHandler.getConAdditionPropertiesForContextMode(hadoopClusterConn);
        assertTrue(additionProperties.contains("hadoopProp")); //$NON-NLS-1$
        assertTrue(additionProperties.contains("sparkProp")); //$NON-NLS-1$
    }

}
