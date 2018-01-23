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

import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.designer.maven.aether.util.DynamicDistributionAetherUtils;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionPreference;
import org.talend.hadoop.distribution.dynamic.VersionNotFoundException;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public abstract class AbstractDependencyResolver implements IDependencyResolver {

    private static final String CONFIGURATION_TALEND_FOLDER = "talend"; //$NON-NLS-1$

    private static final String CONFIGURATION_TALEND_DYNAMIC_DISTRIBUTION_FOLDER = "dynamicDistribution"; //$NON-NLS-1$

    private static final String CONFIGURATION_M2 = ".m2"; //$NON-NLS-1$

    private static final String CONFIGURATION_REPOSITORY = "repository"; //$NON-NLS-1$

    private DynamicConfiguration configuration;

    @Override
    public DependencyNode collectDependencies(DependencyNode baseNode, IDynamicMonitor monitor) throws Exception {
        String version = baseNode.getVersion();
        if (StringUtils.isEmpty(version)) {
            String groupId = baseNode.getGroupId();
            String artifactId = baseNode.getArtifactId();
            version = getDependencyVersionByHadoopVersion(groupId, artifactId, monitor);
            if (StringUtils.isEmpty(version)) {
                VersionNotFoundException versionNotFound = new VersionNotFoundException();
                versionNotFound.setVersion(configuration.getVersion());
                versionNotFound.setBaseNode(baseNode);
                throw versionNotFound;
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

    @Override
    public List<String> listVersions(String groupId, String artifactId, String baseVersion, String topVersion,
            IDynamicMonitor monitor) throws Exception {
        IDynamicDistributionPreference preference = configuration.getPreference();
        String remoteRepositoryUrl = preference.getRepository();
        String username = null;
        String password = null;
        if (!preference.isAnonymous()) {
            username = preference.getUsername();
            password = preference.getPassword();
        }
        String localRepositoryPath = getLocalRepositoryPath();
        return DynamicDistributionAetherUtils.versionRange(remoteRepositoryUrl, username, password, localRepositoryPath, groupId,
                artifactId, null, null, monitor);
    }

    @Override
    public String getLatestVersion(String groupId, String artifactId, String baseVersion, String topVersion,
            IDynamicMonitor monitor) throws Exception {
        IDynamicDistributionPreference preference = configuration.getPreference();
        String remoteRepositoryUrl = preference.getRepository();
        String username = null;
        String password = null;
        if (!preference.isAnonymous()) {
            username = preference.getUsername();
            password = preference.getPassword();
        }
        String localRepositoryPath = getLocalRepositoryPath();
        return DynamicDistributionAetherUtils.getHighestVersion(remoteRepositoryUrl, username, password, localRepositoryPath,
                groupId, artifactId, null, null, monitor);
    }

    public DynamicConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(DynamicConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getLocalRepositoryPath() {
        Location configurationLocation = Platform.getConfigurationLocation();
        URL configurationUrl = configurationLocation.getURL();
        String localMavenRepositoryPath = configurationUrl.getPath() + "/" + CONFIGURATION_TALEND_FOLDER + "/" //$NON-NLS-1$ //$NON-NLS-2$
                + CONFIGURATION_TALEND_DYNAMIC_DISTRIBUTION_FOLDER + "/" + CONFIGURATION_M2 + "/" + CONFIGURATION_REPOSITORY; //$NON-NLS-1$//$NON-NLS-2$
        return localMavenRepositoryPath;
    }
}
