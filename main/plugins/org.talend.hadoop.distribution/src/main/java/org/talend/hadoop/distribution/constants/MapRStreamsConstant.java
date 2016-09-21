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
package org.talend.hadoop.distribution.constants;

public final class MapRStreamsConstant {

    public static final String SERVICE = "org.talend.hadoop.distribution.component.MapRStreamsComponent";//$NON-NLS-1$

    public static final String DISTRIBUTION_PARAMETER = "DISTRIBUTION";//$NON-NLS-1$

    // MapRStreams "repository" is actually linked with the HDFS repository (MapR version, MapR tickets...)
    public static final String DISTRIBUTION_REPOSITORYVALUE = HDFSConstant.DISTRIBUTION_REPOSITORYVALUE;

    public static final String VERSION_PARAMETER = "MAPRSTREAMS_VERSION";//$NON-NLS-1$

    public static final String VERSION_REPOSITORYVALUE = HDFSConstant.VERSION_REPOSITORYVALUE;

    public static final String CREATE_STREAM_COMPONENT = "tMapRStreamsCreateStream"; //$NON-NLS-1$
}