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
package org.talend.hadoop.distribution.dynamic.pref.cdh;

import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.talend.hadoop.distribution.dynamic.pref.AbstractDynamicDistributionPreferenceFactory;
import org.talend.hadoop.distribution.dynamic.pref.IDynamicDistributionPreference;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHDistributionPreferenceFactory extends AbstractDynamicDistributionPreferenceFactory {

    private static DynamicCDHDistributionPreferenceFactory instance;

    private DynamicCDHDistributionPreferenceFactory() {
        // nothing to do
    }

    public static DynamicCDHDistributionPreferenceFactory getInstance() {
        if (instance == null) {
            instance = new DynamicCDHDistributionPreferenceFactory();
        }
        return instance;
    }

    @Override
    protected IDynamicDistributionPreference newPreferenceInstance(ScopedPreferenceStore store) {
        return new DynamicCDHDistributionPreference(store);
    }

}
