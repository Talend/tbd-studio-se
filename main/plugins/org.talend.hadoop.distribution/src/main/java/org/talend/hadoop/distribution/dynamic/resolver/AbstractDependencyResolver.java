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
package org.talend.hadoop.distribution.dynamic.resolver;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.designer.maven.aether.util.DynamicDistributionAetherUtils;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.VersionNotFoundException;
import org.talend.hadoop.distribution.dynamic.pref.IDynamicDistributionPreference;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public abstract class AbstractDependencyResolver implements IDependencyResolver {

    private static final String CONFIGURATION_TALEND_FOLDER = "talend"; //$NON-NLS-1$

    private static final String CONFIGURATION_TALEND_DYNAMIC_DISTRIBUTION_FOLDER = "dynamicDistribution"; //$NON-NLS-1$

    private static final String CONFIGURATION_M2 = ".m2"; //$NON-NLS-1$

    private static final String CONFIGURATION_REPOSITORY = "repository"; //$NON-NLS-1$

    private DynamicConfiguration configuration;

    abstract protected List<Pattern> getDistributionPatterns();

    @Override
    public DependencyNode collectDependencies(DependencyNode baseNode, IDynamicMonitor monitor, boolean multiThread)
            throws Exception {
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
                localRepositoryPath, baseNode, monitor, multiThread);
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

    @Override
    public String getDependencyVersionByHadoopVersion(String groupId, String artifactId, IDynamicMonitor monitor)
            throws Exception {
        DynamicConfiguration configuration = getConfiguration();
        String distributionVersion = configuration.getVersion();
        IDynamicDistributionPreference preference = configuration.getPreference();
        String remoteRepositoryUrl = preference.getRepository();
        String username = null;
        String password = null;
        if (!preference.isAnonymous()) {
            username = preference.getUsername();
            password = preference.getPassword();
        }
        String localRepositoryPath = getLocalRepositoryPath();

        List<String> versionRange = DynamicDistributionAetherUtils.versionRange(remoteRepositoryUrl, username, password,
                localRepositoryPath, groupId, artifactId, null, null, monitor);

        String dependencyVersion = getVersionByHadoopVersion(versionRange, distributionVersion);

        return dependencyVersion;
    }

    @Override
    public List<String> listHadoopVersions(String baseVersion, String topVersion, IDynamicMonitor monitor) throws Exception {
        String groupId = "org.apache.hadoop"; //$NON-NLS-1$
        String artifactId = "hadoop-client"; //$NON-NLS-1$
        DynamicConfiguration configuration = getConfiguration();
        IDynamicDistributionPreference preference = configuration.getPreference();
        String remoteRepositoryUrl = preference.getRepository();
        String username = null;
        String password = null;
        if (!preference.isAnonymous()) {
            username = preference.getUsername();
            password = preference.getPassword();
        }
        String localRepositoryPath = getLocalRepositoryPath();
        List<String> versionRange = DynamicDistributionAetherUtils.versionRange(remoteRepositoryUrl, username, password,
                localRepositoryPath, groupId, artifactId, baseVersion, topVersion, monitor);
        return getCleanHadoopVersion(versionRange);
    }

    protected List<String> getCleanHadoopVersion(List<String> versionRange) throws Exception {
        List<Pattern> patterns = getDistributionPatterns();
        Set<String> cleanVersions = new HashSet<>();
        for (String version : versionRange) {
            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(version);
                if (!matcher.find()) {
                    continue;
                }
                String cleanVersion = matcher.group(1);
                cleanVersions.add(cleanVersion);
                break;
            }
        }
        return new ArrayList<String>(cleanVersions);
    }

    protected String getVersionByHadoopVersion(List<String> versionRange, String hadoopVersion) throws Exception {
        List<Pattern> patterns = getDistributionPatterns();
        for (String version : versionRange) {
            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(version);
                if (matcher.find()) {
                    String group = matcher.group(1);
                    if (group.equals(hadoopVersion)) {
                        return version;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public DynamicConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void setConfiguration(DynamicConfiguration configuration) {
        this.configuration = configuration;
    }

    protected String getLocalRepositoryPath() {
        Location configurationLocation = Platform.getConfigurationLocation();
        URL configurationUrl = configurationLocation.getURL();
        String localMavenRepositoryPath = configurationUrl.getPath() + "/" + CONFIGURATION_TALEND_FOLDER + "/" //$NON-NLS-1$ //$NON-NLS-2$
                + CONFIGURATION_TALEND_DYNAMIC_DISTRIBUTION_FOLDER + "/" + getDistribution() + "/" + CONFIGURATION_M2 + "/" //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
                + CONFIGURATION_REPOSITORY;
        return localMavenRepositoryPath;
    }
}
