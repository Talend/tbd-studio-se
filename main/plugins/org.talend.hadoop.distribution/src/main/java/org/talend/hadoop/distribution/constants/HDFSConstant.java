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
package org.talend.hadoop.distribution.constants;

import java.util.Arrays;
import java.util.List;

public final class HDFSConstant {

    public static final String SERVICE = "org.talend.hadoop.distribution.component.HDFSComponent";//$NON-NLS-1$

    public static final String DISTRIBUTION_PARAMETER = "DISTRIBUTION";//$NON-NLS-1$

    public static final String DISTRIBUTION_REPOSITORYVALUE = "DISTRIBUTION";//$NON-NLS-1$

    public static final String VERSION_PARAMETER = "DB_VERSION";//$NON-NLS-1$

    public static final String VERSION_REPOSITORYVALUE = "DB_VERSION";//$NON-NLS-1$

    public static final String SCHEME_PARAMETER = "SCHEME"; //$NON-NLS-1$

    public static final String WebHDFS = "WebHDFS"; //$NON-NLS-1$

    public static final String ADLS = "ADLS"; //$NON-NLS-1$

    public static final List<String> hdfsComponents = Arrays.asList("tHDFSConnection", "tHDFSGet", "tHDFSPut",
            "tHDFSList", "tHDFSInput", "tHDFSOutput", "tHDFSCopy", "tHDFSRename", "tHDFSExist", "tHDFSDelete",
            "tHDFSRowCount", "tHDFSCompare", "tHDFSOutputRaw", "tHDFSProperties");
}
