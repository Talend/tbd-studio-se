// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.cdp;

import org.talend.hadoop.distribution.dynamic.AbstractDynamicDistributionsGroup;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.IDynamicDistribution;
import org.talend.hadoop.distribution.dynamic.pref.IDynamicDistributionPreferenceFactory;
import org.talend.hadoop.distribution.dynamic.pref.cdp.DynamicCDPDistributionPreferenceFactory;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.resolver.cdp.CDPDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDPDistributionsGroup extends AbstractDynamicDistributionsGroup implements IDynamicCDPDistributionsGroup {

    @Override
    public String getDistribution() {
        return DISTRIBUTION_NAME;
    }

    @Override
    public String getDistributionDisplay() {
        return DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    protected Class<? extends IDynamicDistribution> getDynamicDistributionClass() {
        return IDynamicCDPDistribution.class;
    }

    @Override
    public IDependencyResolver getDependencyResolver(DynamicConfiguration config) {
        CDPDependencyResolver resolver = new CDPDependencyResolver();
        resolver.setConfiguration(config);
        return resolver;
    }

    @Override
    public String generateVersionName(String version) {
        return "Cloudera CDP" + version + " (Dynamic)"; //$NON-NLS-1$//$NON-NLS-2$
    }

    @Override
    public String generateVersionId(String version) {
        String versionStr = DynamicDistributionUtils.formatId(version);
        return "Cloudera_CDP" + versionStr + "_dynamic"; //$NON-NLS-1$//$NON-NLS-2$
    }

    @Override
    protected IDynamicDistributionPreferenceFactory createPreferenceFactory() {
        return DynamicCDPDistributionPreferenceFactory.getInstance();
    }

}
