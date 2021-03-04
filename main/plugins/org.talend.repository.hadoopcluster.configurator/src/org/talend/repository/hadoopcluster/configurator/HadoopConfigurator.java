// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.configurator;

import java.util.List;

/**
 * created by bchen on May 27, 2015 Detailled comment
 *
 */
public interface HadoopConfigurator {

    // This separator is used to separate cluster display name and name
    public static final String NAME_SEPARATOR = "??@##@??";

    public static final String NAME_SEPARATOR_PATTERN = "\\?\\?@##@\\?\\?";

    public List<String> getAllClusters();

    public HadoopCluster getCluster(String name);

}
