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
package org.talend.repository.hadoopcluster.configurator;

import java.util.Map;
import java.util.Set;

/**
 * created by bchen on May 28, 2015 Detailled comment
 *
 */
public interface HadoopClusterService {

    public Map<String, String> getConfiguration();

    public String getConfigurationValue(String key);

    public void exportConfigurationToXml(String folderPath);

    public Set<String> getConfFiles();

    // return true if contains site.xml files
    public boolean hasConfigurations();
}
