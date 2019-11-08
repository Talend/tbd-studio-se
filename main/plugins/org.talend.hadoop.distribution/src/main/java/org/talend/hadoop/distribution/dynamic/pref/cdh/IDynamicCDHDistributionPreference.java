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

import org.talend.hadoop.distribution.dynamic.pref.IDynamicDistributionPreference;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public interface IDynamicCDHDistributionPreference extends IDynamicDistributionPreference {

    static final String PREF_OVERRIDE_DEFAULT_SETUP = "distribution.dynamic.repository.cdh.overrideDefaultSetup"; //$NON-NLS-1$

    static final boolean PREF_OVERRIDE_DEFAULT_SETUP_DEFAULT = false;

    static final String PREF_REPOSITORY = "distribution.dynamic.repository.cdh.repository"; //$NON-NLS-1$

    static final String PREF_REPOSITORY_DEFAULT = "https://talend-update.talend.com/nexus/content/groups/dynamicdistribution/"; //$NON-NLS-1$

    static final String PREF_ANONYMOUS = "distribution.dynamic.repository.cdh.isAnonymous"; //$NON-NLS-1$

    static final boolean PREF_ANONYMOUS_DEFAULT = false;

    static final String PREF_USERNAME = "distribution.dynamic.repository.cdh.username"; //$NON-NLS-1$

    static final String PREF_USERNAME_DEFAULT = "studio-dl-client"; //$NON-NLS-1$

    static final String PREF_PASSWORD = "distribution.dynamic.repository.cdh.password"; //$NON-NLS-1$

    static final String PREF_PASSWORD_DEFAULT = PREF_USERNAME_DEFAULT; // $NON-NLS-1$
}
