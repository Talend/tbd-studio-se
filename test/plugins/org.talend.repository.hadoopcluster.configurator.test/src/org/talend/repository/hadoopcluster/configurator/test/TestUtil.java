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
package org.talend.repository.hadoopcluster.configurator.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;

/**
 * created by bchen on Nov 26, 2015 Detailled comment
 *
 */
public class TestUtil {

    public static void checkCluster(HadoopConfigurator configurator, String... clusterNames) throws Exception {
        String[] allClusters = configurator.getAllClusters().toArray(new String[configurator.getAllClusters().size()]);
        Arrays.sort(allClusters);
        Arrays.sort(clusterNames);
        assertArrayEquals(clusterNames, allClusters);
    }

    public static void checkService(Map<HadoopHostedService, HadoopClusterService> services,
            HadoopHostedService... expectedServices) throws Exception {
        assertEquals(expectedServices.length, services.size());
        Arrays.sort(expectedServices);
        HadoopHostedService[] array = services.keySet().toArray(new HadoopHostedService[services.keySet().size()]);
        Arrays.sort(array);
        assertArrayEquals(expectedServices, array);
    }

    public static void checkServiceConf(HadoopClusterService service, String... confFileNames) throws Exception {
        assertEquals(true, service.hasConfigurations());
        assertEquals(confFileNames.length, service.getConfFiles().size());
        String[] hdfsConfs = service.getConfFiles().toArray(new String[service.getConfFiles().size()]);
        Arrays.sort(confFileNames);
        Arrays.sort(hdfsConfs);
        assertArrayEquals(confFileNames, hdfsConfs);
    }
}
