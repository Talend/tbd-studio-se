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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.util.DynamicDistributionAetherUtils;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class ClouderaDependencyResolver extends AbstractDependencyResolver implements IClouderaDependencyResolver {

    private Pattern clouderaDistributionPattern;

    @Override
    public String getDependencyVersionByHadoopVersion(String groupId, String artifactId, IDynamicMonitor monitor)
            throws Exception {
        DynamicConfiguration configuration = getConfiguration();
        String distributionVersion = configuration.getVersion();
        String remoteRepositoryUrl = configuration.getRemoteRepositoryUrl();
        String localRepositoryPath = getLocalRepositoryPath();

        List<String> versionRange = DynamicDistributionAetherUtils.versionRange(remoteRepositoryUrl, localRepositoryPath, groupId,
                artifactId, null, null, monitor);

        String dependencyVersion = getVersionByHadoopVersion(versionRange, distributionVersion);

        return dependencyVersion;
    }

    @Override
    public List<String> listHadoopVersions(String baseVersion, String topVersion, IDynamicMonitor monitor) throws Exception {
        String groupId = "org.apache.hadoop"; //$NON-NLS-1$
        String artifactId = "hadoop-client"; //$NON-NLS-1$
        DynamicConfiguration configuration = getConfiguration();
        String remoteRepositoryUrl = configuration.getRemoteRepositoryUrl();
        String localRepositoryPath = getLocalRepositoryPath();
        List<String> versionRange = DynamicDistributionAetherUtils.versionRange(remoteRepositoryUrl, localRepositoryPath, groupId,
                artifactId, baseVersion, topVersion, monitor);
        return getCleanHadoopVersion(versionRange);
    }

    public List<String> getCleanHadoopVersion(List<String> versionRange) throws Exception {
        Pattern pattern = getClouderaDistributionPattern();
        List<String> cleanVersions = new ArrayList<>();
        for (String version : versionRange) {
            Matcher matcher = pattern.matcher(version);
            if (!matcher.find()) {
                ExceptionHandler.process(new Exception("Unknown hadoop version: " + version));
                continue;
            }
            String cleanVersion = matcher.group();
            cleanVersions.add(cleanVersion);
        }
        return cleanVersions;
    }

    public String getVersionByHadoopVersion(List<String> versionRange, String hadoopVersion) throws Exception {
        Pattern pattern = getClouderaDistributionPattern();
        for (String version : versionRange) {
            Matcher matcher = pattern.matcher(version);
            if (matcher.find()) {
                String group = matcher.group();
                if (group.equals(hadoopVersion)) {
                    return version;
                }
            }
        }
        return null;
    }

    private Pattern getClouderaDistributionPattern() {
        if (clouderaDistributionPattern == null) {
            clouderaDistributionPattern = Pattern.compile("[Cc][Dd][Hh][\\d]+\\.[\\d]+\\.[\\d]+"); //$NON-NLS-1$
        }
        return clouderaDistributionPattern;
    }

}
