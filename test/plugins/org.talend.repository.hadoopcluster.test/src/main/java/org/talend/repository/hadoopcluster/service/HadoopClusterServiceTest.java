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
package org.talend.repository.hadoopcluster.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.hadoop.HadoopConstants;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.model.properties.ContextItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.repository.hadoopcluster.util.ClusterTestUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * created by ycbai on 2017年2月20日 Detailled comment
 *
 */
public class HadoopClusterServiceTest {

    private IHadoopClusterService service;

    private HadoopClusterConnectionItem hadoopClusterItem;

    @Before
    public void before() throws PersistenceException {
        service = new HadoopClusterService();
        hadoopClusterItem = ClusterTestUtil.createHadoopClusterItem("testHadoopCluster", true); //$NON-NLS-1$
    }

    @Test
    public void testUseCustomConfsJarIfNeeded() throws PersistenceException {
        List<ModuleNeeded> modulesNeeded = new ArrayList<>();
        HadoopClusterConnection hadoopClusterConnection = (HadoopClusterConnection) hadoopClusterItem.getConnection();
        String clusterId = hadoopClusterItem.getProperty().getId();
        String clusterLabel = hadoopClusterItem.getProperty().getLabel();
        service.useCustomConfsJarIfNeeded(modulesNeeded, clusterId);

        // useCustomConfs is false
        assertEquals(0, modulesNeeded.size());

        // useCustomConfs is true and not context mode
        hadoopClusterConnection.setUseCustomConfs(true);
        service.useCustomConfsJarIfNeeded(modulesNeeded, clusterId);
        assertEquals(1, modulesNeeded.size());
        String expectConfsJarName = HadoopParameterUtil.getConfsJarDefaultName(clusterLabel);
        assertEquals(expectConfsJarName, modulesNeeded.get(0).getModuleName());
        assertFalse(
                Boolean.valueOf(String.valueOf(modulesNeeded.get(0).getExtraAttributes().get(HadoopConstants.IS_DYNAMIC_JAR))));

        // useCustomConfs is true and context mode
        List<ContextType> contexts = new ArrayList<>();
        List<String> contextNames = Arrays.asList("TEST", "DEV", "PROD"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        for (String contextName : contextNames) {
            contexts.add(ClusterTestUtil.createContextType(contextName));
        }
        ContextItem contextItem = ClusterTestUtil.createContextItem(clusterLabel, contexts, true);
        hadoopClusterConnection.setContextMode(true);
        hadoopClusterConnection.setContextId(contextItem.getProperty().getId());
        modulesNeeded.clear();
        service.useCustomConfsJarIfNeeded(modulesNeeded, clusterId);
        assertEquals(3, modulesNeeded.size());
        List<String> moduleNames = new ArrayList<>();
        for (ModuleNeeded module : modulesNeeded) {
            moduleNames.add(module.getModuleName());
            assertTrue(Boolean
                    .valueOf(String.valueOf(modulesNeeded.get(0).getExtraAttributes().get(HadoopConstants.IS_DYNAMIC_JAR))));
        }
        for (String contextName : contextNames) {
            assertTrue(moduleNames.contains(HadoopParameterUtil.getConfsJarDefaultName(clusterLabel + "_" + contextName))); //$NON-NLS-1$
        }
        ClusterTestUtil.deleteItem(contextItem);
    }

    @Test
    public void testIsUseDynamicConfJar() {
        HadoopClusterConnection hadoopClusterConnection = (HadoopClusterConnection) hadoopClusterItem.getConnection();
        String id = hadoopClusterItem.getProperty().getId();

        // useCustomConfs is false and not context mode
        assertFalse(service.isUseDynamicConfJar(id));

        // useCustomConfs is true and not context mode
        hadoopClusterConnection.setUseCustomConfs(true);
        assertFalse(service.isUseDynamicConfJar(id));

        // useCustomConfs is false and context mode
        hadoopClusterConnection.setUseCustomConfs(false);
        hadoopClusterConnection.setContextMode(true);
        assertFalse(service.isUseDynamicConfJar(id));

        // useCustomConfs is true and context mode
        hadoopClusterConnection.setUseCustomConfs(true);
        hadoopClusterConnection.setContextMode(true);
        assertTrue(service.isUseDynamicConfJar(id));

        // useCustomConfs is true and context mode but exportAsOSGI is true
        ProcessorUtilities.setExportAsOSGI(true);
        assertFalse(service.isUseDynamicConfJar(id));
    }

    @After
    public void after() {
        try {
            ClusterTestUtil.deleteItem(hadoopClusterItem);
        } catch (PersistenceException e) {
            // Ignore it.
        }
        ProcessorUtilities.setExportAsOSGI(false);
    }

}
