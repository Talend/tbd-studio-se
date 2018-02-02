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
package org.talend.hadoop.distribution.dynamic.comparator;

import java.util.Comparator;

import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicPluginComparator implements Comparator<IDynamicPlugin> {

    @Override
    public int compare(IDynamicPlugin o1, IDynamicPlugin o2) {
        IDynamicPluginConfiguration config1 = o1.getPluginConfiguration();
        IDynamicPluginConfiguration config2 = o2.getPluginConfiguration();
        return config1.getName().compareTo(config2.getName());
    }

}
