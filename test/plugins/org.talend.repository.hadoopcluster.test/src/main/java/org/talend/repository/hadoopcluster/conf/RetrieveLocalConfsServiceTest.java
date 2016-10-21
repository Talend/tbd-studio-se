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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.talend.core.hadoop.conf.EHadoopConfs;
import org.talend.hadoop.distribution.constants.hdp.IHortonworksDistribution;
import org.talend.hadoop.distribution.constants.mapr.IMapRDistribution;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;

/**
 * created by ycbai on 2016年6月23日 Detailled comment
 *
 */
public class RetrieveLocalConfsServiceTest {

    static IRetrieveConfsService confsService;
    
    static File confDir;

    @BeforeClass
    public static void beforeClass() throws IOException {
        Bundle bundle = Platform.getBundle("org.talend.repository.hadoopcluster.test"); //$NON-NLS-1$
        URL confEntry = bundle.getEntry("/resources/conf"); //$NON-NLS-1$
        confDir = new File(FileLocator.toFileURL(confEntry).getFile());
        confsService = getServiceWithDistribution(IHortonworksDistribution.DISTRIBUTION_NAME, "HDP_2_4");
    }

    @Test
    public void testExportConfs() throws Exception {
        List<String> services = new ArrayList<>();
        services.add(EHadoopConfs.HDFS.getName());
        services.add(EHadoopConfs.MAPREDUCE2.getName());
        services.add(EHadoopConfs.YARN.getName());
        File exportedConfFolder = new File(HadoopConfsUtils.getConfsSitesTempFolder());

        confsService.exportConfs(services);
        checkFilterProperties(exportedConfFolder, false);

        confsService.applyFilter(getTestFilterProperties());
        confsService.exportConfs(services);
        checkFilterProperties(exportedConfFolder, true);
    }

    @Test
    public void testGetConfsMap() throws MalformedURLException {
        // test HDP
        Map<String, Map<String, String>> confsMap = confsService.getConfsMap();
        assertFalse(confsMap.containsKey("MAPRDB"));
        // test MR
        IRetrieveConfsService confsService2 = getServiceWithDistribution(IMapRDistribution.DISTRIBUTION_NAME, "MAPR310");
        confsMap = confsService2.getConfsMap();
        assertTrue(confsMap.containsKey("MAPRDB"));
    }
    
    private void checkFilterProperties(File confFolder, boolean hasFilter) {
        List<String> filterProperties = getTestFilterProperties();
        List<Element> propertyElements = new ArrayList<>();
        File[] confFiles = confFolder.listFiles();
        for (File file : confFiles) {
            propertyElements.addAll(HadoopConfsResolveUtils.getAllPropertyElements(file));
        }
        List<String> existProperties = new ArrayList<>();
        for (Element element : propertyElements) {
            existProperties.add(HadoopConfsResolveUtils.getPropertyName(element));
        }
        for (String property : filterProperties) {
            boolean containsProperty = existProperties.contains(property);
            if (hasFilter) {
                assertFalse(containsProperty);
            } else {
                assertTrue(containsProperty);
            }
        }
    }

    private List<String> getTestFilterProperties() {
        List<String> filterProps = new ArrayList<>();
        filterProps.add("fs.trash.interval"); //$NON-NLS-1$
        filterProps.add("dfs.https.address"); //$NON-NLS-1$
        filterProps.add("mapreduce.job.counters.max"); //$NON-NLS-1$
        filterProps.add("yarn.admin.acl"); //$NON-NLS-1$
        return filterProps;
    }
    
    private static IRetrieveConfsService getServiceWithDistribution(String distributionName, String versionName) {
        DistributionBean distribution = HadoopDistributionsHelper.HADOOP
                .getDistribution(distributionName, false);
        DistributionVersion version = distribution.getVersion(versionName, false);
        return new RetrieveLocalConfsService(confDir.getAbsolutePath(), version);
    }
}
