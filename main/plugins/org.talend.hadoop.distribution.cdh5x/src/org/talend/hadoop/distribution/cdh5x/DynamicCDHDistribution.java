// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5x;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.dynamic.IDynamicDistribution;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHDistribution implements IDynamicDistribution {

    private CDH5xDistributionTemplate cdhService;

    private IDynamicPlugin runtimePlugin;

    private ServiceRegistration osgiService;

    @Override
    public void regist() throws Exception {
        if (true) {
            return;
        }
        CDH5xPlugin cdh5xPlugin = CDH5xPlugin.getInstance();

        Bundle bundle = cdh5xPlugin.getBundle();

        URL resourceURL = bundle.getEntry("resources/cdh5x.json");
        String cdh5xPath = FileLocator.toFileURL(resourceURL).getPath();
        String xmlJsonString = DynamicServiceUtil.readFile(new File(cdh5xPath));
        runtimePlugin = DynamicFactory.getInstance().createPluginFromJson(xmlJsonString);
        IDynamicPluginConfiguration pluginConfiguration = runtimePlugin.getPluginConfiguration();

        String id = pluginConfiguration.getId();
        String displayName = pluginConfiguration.getName();

        cdhService = new CDH5xDistributionTemplate(null) {
        };
        // AbstractDynamicAdapter adapter = DynamicAdapterFactory.getInstance().create(runtimePlugin.getTagName(),
        // runtimePlugin,
        // id);
        // adapter.adapt();

        DynamicServiceUtil.addContribution(bundle, runtimePlugin);

        BundleContext context = bundle.getBundleContext();
        osgiService = DynamicServiceUtil.registOSGiService(context,
                new String[] { HadoopComponent.class.getName(), HDFSComponent.class.getName(), HBaseComponent.class.getName(),
                        HCatalogComponent.class.getName(), HiveComponent.class.getName(), HiveOnSparkComponent.class.getName(),
                        ImpalaComponent.class.getName(), MRComponent.class.getName(), PigComponent.class.getName(),
                        SqoopComponent.class.getName(), SparkBatchComponent.class.getName(),
                        SparkStreamingComponent.class.getName() },
                cdhService, null);

    }

    @Override
    public void unregist() throws Exception {
        DynamicServiceUtil.unregistOSGiService(osgiService);
        DynamicServiceUtil.removeContribution(runtimePlugin);
    }

}
