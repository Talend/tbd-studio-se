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
package org.talend.hadoop.distribution.dynamic.resolver;

import java.util.List;

import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public interface IDependencyResolver {

    public DependencyNode collectDependencies(DependencyNode baseNode, IDynamicMonitor monitor, boolean multiThread)
            throws Exception;

    public String getDependencyVersionByHadoopVersion(String groupId, String artifactId, IDynamicMonitor monitor)
            throws Exception;

    public List<String> listHadoopVersions(String baseVersion, String topVersion, IDynamicMonitor monitor) throws Exception;

    public List<String> listVersions(String groupId, String artifactId, String baseVersion, String topVersion,
            IDynamicMonitor monitor) throws Exception;

    public String getLatestVersion(String groupId, String artifactId, String baseVersion, String topVersion,
            IDynamicMonitor monitor) throws Exception;

    public String getDistribution();

    public DynamicConfiguration getConfiguration();

    public void setConfiguration(DynamicConfiguration configuration);

}
