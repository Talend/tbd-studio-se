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
package org.talend.hadoop.distribution.dynamic.resolver.cdh;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.util.DynamicDistributionAetherUtils;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionPreference;
import org.talend.hadoop.distribution.dynamic.resolver.AbstractDependencyResolver;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class ClouderaDependencyResolver extends AbstractDependencyResolver implements IClouderaDependencyResolver {

    private List<Pattern> clouderaDistributionPatterns;

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

    public List<String> getCleanHadoopVersion(List<String> versionRange) throws Exception {
        List<Pattern> patterns = getClouderaDistributionPatterns();
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

    public String getVersionByHadoopVersion(List<String> versionRange, String hadoopVersion) throws Exception {
        List<Pattern> patterns = getClouderaDistributionPatterns();
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

    private List<Pattern> getClouderaDistributionPatterns() {
        if (clouderaDistributionPatterns == null || clouderaDistributionPatterns.isEmpty()) {
            clouderaDistributionPatterns = new ArrayList<>();
            Pattern clouderaDistributionPattern = Pattern.compile("(?:[Cc][Dd][Hh])([\\d]+\\.[\\d]+\\.[\\d]+)"); //$NON-NLS-1$
            clouderaDistributionPatterns.add(clouderaDistributionPattern);
        }
        return clouderaDistributionPatterns;
    }

}
