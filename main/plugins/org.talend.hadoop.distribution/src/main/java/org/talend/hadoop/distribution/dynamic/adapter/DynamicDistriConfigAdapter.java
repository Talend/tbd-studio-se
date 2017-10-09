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
package org.talend.hadoop.distribution.dynamic.adapter;

import org.apache.commons.lang.StringUtils;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistriConfigAdapter extends AbstractDynamicAdapter {

    public DynamicDistriConfigAdapter(TemplateBean templateBean, DynamicConfiguration configuration) {
        super(templateBean, configuration);
    }

    public IDynamicPluginConfiguration adapt(IDynamicMonitor monitor) throws Exception {
        resolve();

        IDynamicPluginConfiguration pluginConfiguration = DynamicFactory.getInstance().createDynamicPluginConfiguration();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();

        String description = templateBean.getDescription();
        String distribution = templateBean.getDistribution();
        String repository = templateBean.getRepository();

        if (!StringUtils.equals(distribution, configuration.getDistribution())) {
            throw new Exception("Different distribution: " + distribution + "<>" + configuration.getDistribution());
        }

        pluginConfiguration.setId(configuration.getId());
        pluginConfiguration.setName(configuration.getName());
        pluginConfiguration.setVersion(configuration.getVersion());
        pluginConfiguration.setDescription(description);
        pluginConfiguration.setDistribution(distribution);
        pluginConfiguration.setRepository(repository);

        return pluginConfiguration;
    }

    @Override
    protected void resolve() throws Exception {
        setResolved(true);
    }

}
