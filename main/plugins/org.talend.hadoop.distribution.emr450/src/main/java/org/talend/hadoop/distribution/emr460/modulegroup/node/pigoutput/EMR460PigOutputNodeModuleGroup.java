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
package org.talend.hadoop.distribution.emr460.modulegroup.node.pigoutput;

import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.emr450.modulegroup.node.pigoutput.EMR450PigOutputNodeModuleGroup;

public class EMR460PigOutputNodeModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) {
        // Re-use EMR 4.5.0 module groups
        return EMR450PigOutputNodeModuleGroup.getModuleGroups(distribution, version);
    }

}
