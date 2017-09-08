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
package org.talend.repository.hadoopcluster.conf;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.EMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.core.model.properties.ContextItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.repository.hadoopcluster.util.ClusterTestUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * created by ycbai on 2016年6月16日 Detailled comment
 *
 */
public class HadoopConfsUtilsTest {

    private HadoopClusterConnectionItem hcConnectionItem;

    @Before
    public void before() throws PersistenceException {
        hcConnectionItem = ClusterTestUtil.createHadoopClusterItem("testHadoopCluster", true); //$NON-NLS-1$
    }

    @Test
    public void testGetConfsJarDefaultName() throws IOException {
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

    @Test
    public void testGetConfsJarDefaultNames() throws PersistenceException {
        HadoopClusterConnection hadoopClusterConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        String clusterLabel = hcConnectionItem.getProperty().getLabel();

        // not context mode
        Set<String> confsJarNames = HadoopConfsUtils.getConfsJarDefaultNames(hcConnectionItem);
        assertEquals(1, confsJarNames.size());
        String expectConfsJarName = HadoopParameterUtil.getConfsJarDefaultName(clusterLabel);
        assertEquals(expectConfsJarName, confsJarNames.toArray()[0]);

        // context mode
        List<ContextType> contexts = new ArrayList<>();
        List<String> contextNames = Arrays.asList("TEST", "DEV", "PROD"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        for (String contextName : contextNames) {
            contexts.add(ClusterTestUtil.createContextType(contextName));
        }
        ContextItem contextItem = ClusterTestUtil.createContextItem(clusterLabel, contexts, true);
        hadoopClusterConnection.setContextMode(true);
        hadoopClusterConnection.setContextId(contextItem.getProperty().getId());
        confsJarNames = HadoopConfsUtils.getConfsJarDefaultNames(hcConnectionItem);
        assertEquals(3, confsJarNames.size());
        for (String contextName : contextNames) {
            assertTrue(confsJarNames.contains(HadoopParameterUtil.getConfsJarDefaultName(clusterLabel + "_" + contextName))); //$NON-NLS-1$
        }
        ClusterTestUtil.deleteItem(contextItem);
    }

    @Test
    public void testBuildAndDeployConfsJar() throws IOException {
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();

        File testConfJarDir = File.createTempFile("testConfDir", "tmp"); //$NON-NLS-1$ //$NON-NLS-2$
        testConfJarDir.delete();
        testConfJarDir.mkdirs();
        testConfJarDir.deleteOnExit();

        String testConfJarName = "testConf"; //$NON-NLS-1$
        String testContextGroup = "TEST"; //$NON-NLS-1$

        HadoopConfsUtils.buildAndDeployConfsJar(hcConnectionItem, null, testConfJarDir.getAbsolutePath(),
                testConfJarName);
        assertNotNull(hcConnection.getConfFile());

        // Context mode
        hcConnection.setContextMode(true);
        HadoopConfsUtils.buildAndDeployConfsJar(hcConnectionItem, testContextGroup, testConfJarDir.getAbsolutePath(),
                testConfJarName);
        assertNotNull(hcConnection.getConfFiles());
        assertEquals(1, hcConnection.getConfFiles().size());

        hcConnection.getConfFiles().clear();
        // If connection is context mode but context group is null then will not save conf jar.
        HadoopConfsUtils.buildAndDeployConfsJar(hcConnectionItem, null, testConfJarDir.getAbsolutePath(), testConfJarName);
        assertEquals(0, hcConnection.getConfFiles().size());
    }

    @Test
    public void testRenameContextGroups_connectionIsNull() {
        assertFalse(HadoopConfsUtils.renameContextGroups(null, createTestRenamedMap()));
    }

    @Test
    public void testRenameContextGroups_ranamedMapIsNull() {
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        assertFalse(HadoopConfsUtils.renameContextGroups(hcConnection, null));
        assertFalse(HadoopConfsUtils.renameContextGroups(hcConnection, new HashMap<>()));
    }

    @Test
    public void testRenameContextGroups_nonContextMode() {
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        assertFalse(HadoopConfsUtils.renameContextGroups(hcConnection, createTestRenamedMap()));
    }

    @Test
    public void testRenameContextGroups_contextMode() {
        HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
        hcConnection.setContextMode(true);
        String oldContextGroup1 = "old"; //$NON-NLS-1$
        String newContextGroup1 = "new"; //$NON-NLS-1$
        byte[] jarContent1 = "jarcontent".getBytes(); //$NON-NLS-1$
        String oldContextGroup2 = "old2"; //$NON-NLS-1$
        byte[] jarContent2 = "jarcontent2".getBytes(); //$NON-NLS-1$
        hcConnection.getConfFiles().put(oldContextGroup1, jarContent1);
        hcConnection.getConfFiles().put(oldContextGroup2, jarContent2);
        Map<String, String> renamedMap = new HashMap<>();
        renamedMap.put(oldContextGroup1, newContextGroup1);
        assertTrue(HadoopConfsUtils.renameContextGroups(hcConnection, renamedMap));
        EMap<String, byte[]> newConfFiles = hcConnection.getConfFiles();
        assertEquals(2, newConfFiles.size());
        assertEquals(jarContent1, newConfFiles.get(newContextGroup1));
        assertEquals(jarContent2, newConfFiles.get(oldContextGroup2));
    }

    private Map<String, String> createTestRenamedMap() {
        Map<String, String> renamedMap = new HashMap<>();
        renamedMap.put("old", "new"); //$NON-NLS-1$ //$NON-NLS-2$
        return renamedMap;
    }

    @After
    public void after() {
        try {
            ClusterTestUtil.deleteItem(hcConnectionItem);
        } catch (PersistenceException e) {
            // Ignore it.
        }
    }

}
