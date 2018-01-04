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
package org.talend.hadoop.distribution.dynamic.resolver;

import org.apache.commons.lang.StringUtils;
import org.eclipse.m2e.core.MavenPlugin;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.designer.maven.aether.util.DynamicDistributionAetherUtils;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionPreference;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public abstract class AbstractDependencyResolver implements IDependencyResolver {

    private DynamicConfiguration configuration;

    @Override
    public DependencyNode collectDependencies(DependencyNode baseNode, IDynamicMonitor monitor) throws Exception {
        String version = baseNode.getVersion();
        if (StringUtils.isEmpty(version)) {
            String groupId = baseNode.getGroupId();
            String artifactId = baseNode.getArtifactId();
            version = getDependencyVersionByHadoopVersion(groupId, artifactId, monitor);
            if (StringUtils.isEmpty(version)) {
                throw new Exception("Can't find version of " + groupId + ", " + artifactId);
            }
            baseNode.setVersion(version);
        }

        IDynamicDistributionPreference preference = configuration.getPreference();
        String remoteRepositoryUrl = preference.getRepository();
        String username = null;
        String password = null;
        if (!preference.isAnonymous()) {
            username = preference.getUsername();
            password = preference.getPassword();
        }
        String localRepositoryPath = getLocalRepositoryPath();
        DependencyNode node = DynamicDistributionAetherUtils.collectDepencencies(remoteRepositoryUrl, username, password,
                localRepositoryPath, baseNode, monitor);
        return node;
    }

    public DynamicConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(DynamicConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getLocalRepositoryPath() {
        return MavenPlugin.getMaven().getLocalRepositoryPath();
    }
}
