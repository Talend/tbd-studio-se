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

package org.talend.hadoop.distribution.cdh550;

import org.talend.hadoop.distribution.ComponentType;

// This class extends {@see CDH550Distribution} as it shares the same behaviour and dependencies.
@SuppressWarnings("nls")
public class CDH560Distribution extends CDH550Distribution {

    public final static String VERSION_56 = "Cloudera_CDH5_6";

    public static final String VERSION_DISPLAY_56 = "Cloudera CDH5.6(YARN mode)";

    public CDH560Distribution() {
        super();
    }

    @Override
    public String getVersion() {
        return VERSION_56;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        return VERSION_DISPLAY_56;
    }

    @Override
    public boolean doSupportSSLwithKerberos() {
        return true;
    }
}
