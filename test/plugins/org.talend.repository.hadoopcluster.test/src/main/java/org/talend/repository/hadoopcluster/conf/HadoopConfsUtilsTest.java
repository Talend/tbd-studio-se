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
package org.talend.repository.hadoopcluster.conf;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.repository.hadoopcluster.util.ClusterTestUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * created by ycbai on 2016年6月16日 Detailled comment
 *
 */
public class HadoopConfsUtilsTest {

    @Test
    public void testGetConfsJarDefaultName() throws IOException {
        HadoopClusterConnectionItem hcConnectionItem = ClusterTestUtil.createDefaultHadoopClusterItem();
        String expectedConfJarName = HadoopParameterUtil.getConfsJarDefaultName(hcConnectionItem.getProperty().getLabel());
        HadoopConfsUtils.removeFromDeployedCache(hcConnectionItem, expectedConfJarName);

        // If parameter value of "createJarIfNotExist" is false will not deploy the jar. So will not update the
        // deployed cache too.
        String confsJarName = HadoopConfsUtils.getConfsJarDefaultName(hcConnectionItem, false);
        assertEquals(expectedConfJarName, confsJarName);
        assertFalse(HadoopConfsUtils.containsInDeployedCache(hcConnectionItem, confsJarName));

        // Although parameter value of "createJarIfNotExist" is true, but "confFile" is null in HadoopClusterConnection
        // still will not deploy the jar. Of course will not update the deployed cache too.
        HadoopConfsUtils.getConfsJarDefaultName(hcConnectionItem);
        assertEquals(expectedConfJarName, confsJarName);
        assertFalse(HadoopConfsUtils.containsInDeployedCache(hcConnectionItem, confsJarName));

        // Store conf jar file into HadoopClusterConnection.
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        File testConfJarFile = File.createTempFile("testConf", "jar"); //$NON-NLS-1$ //$NON-NLS-2$
        testConfJarFile.deleteOnExit();
        hcConnection.setConfFile(FileUtils.readFileToByteArray(testConfJarFile));
        HadoopConfsUtils.getConfsJarDefaultName(hcConnectionItem);
        assertEquals(expectedConfJarName, confsJarName);
        boolean isNeedToBeDeployed = false;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibraryManagerService.class)) {
            ILibraryManagerService libService = (ILibraryManagerService) GlobalServiceRegister.getDefault().getService(
                    ILibraryManagerService.class);
            if (libService != null && libService.isJarNeedToBeDeployed(testConfJarFile)) {
                isNeedToBeDeployed = true;
            }
        }
        if (isNeedToBeDeployed) { // If need to be deployed then will deploy the jar and update the deployed cache.
            assertTrue(HadoopConfsUtils.containsInDeployedCache(hcConnectionItem, confsJarName));
        } else { // Otherwise will not deploy the jar and update the deployed cache.
            assertFalse(HadoopConfsUtils.containsInDeployedCache(hcConnectionItem, confsJarName));
        }
    }

}
