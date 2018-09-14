// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.pref.hdp;

import org.talend.hadoop.distribution.dynamic.pref.IDynamicDistributionPreference;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public interface IDynamicHDPDistributionPreference extends IDynamicDistributionPreference {

    static final String PREF_OVERRIDE_DEFAULT_SETUP = "distribution.dynamic.repository.hdp.overrideDefaultSetup"; //$NON-NLS-1$

    static final boolean PREF_OVERRIDE_DEFAULT_SETUP_DEFAULT = false;

    static final String PREF_REPOSITORY = "distribution.dynamic.repository.hdp.repository"; //$NON-NLS-1$

    // http://repo.hortonworks.com/content/repositories/releases/
    static final String PREF_REPOSITORY_DEFAULT = "https://talend-update.talend.com/nexus/content/groups/dynamicdistribution/"; //$NON-NLS-1$

    static final String PREF_ANONYMOUS = "distribution.dynamic.repository.hdp.isAnonymous"; //$NON-NLS-1$

    static final boolean PREF_ANONYMOUS_DEFAULT = false;

    static final String PREF_USERNAME = "distribution.dynamic.repository.hdp.username"; //$NON-NLS-1$

    static final String PREF_USERNAME_DEFAULT = "studio-dl-client"; //$NON-NLS-1$

    static final String PREF_PASSWORD = "distribution.dynamic.repository.hdp.password"; //$NON-NLS-1$

    static final String PREF_PASSWORD_DEFAULT = "LycrWORKBL7x80mU8ziB6dCFh+ZjAYX/"; //$NON-NLS-1$
}
