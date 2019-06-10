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
package org.talend.hadoop.distribution.dynamic.cdh;

import org.talend.hadoop.distribution.dynamic.AbstractDynamicDistribution;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicCDHDistribution extends AbstractDynamicDistribution implements IDynamicCDHDistribution {

    @Override
    public String getDistributionName() {
        return IDynamicCDHDistribution.DISTRIBUTION;
    }
}
