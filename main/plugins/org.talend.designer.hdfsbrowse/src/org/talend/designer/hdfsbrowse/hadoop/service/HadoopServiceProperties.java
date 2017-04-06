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
package org.talend.designer.hdfsbrowse.hadoop.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;

/**
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public class HadoopServiceProperties {

    private ContextType contextType;

    private String relativeHadoopClusterId;

    private String relativeHadoopClusterLabel;

    private String distribution;

    private String version;

    private boolean isCustom;

    private boolean useCustomConfs;

    private String nameNode;

    private String jobTracker;

    private String resourceManager;

    private String userName;

    private String group;

    private boolean useKrb;

    private String principal;

    private String jtOrRmPrincipal;

    private String jobHistoryPrincipal;

    private boolean useKeytab;

    private String keytabPrincipal;

    private String keytab;

    private String uid;

    private Set<String> customJars;

    private List<Map<String, Object>> hadoopProperties;

    private boolean maprT;

    private String maprTPassword;

    private String maprTCluster;

    private String maprTDuration;

    private boolean setMaprTHomeDir;

    private boolean setHadoopLogin;

    private boolean preloadAuthentification;

    private String maprTHomeDir;

    private String maprTHadoopLogin;

    private String getRealValue(String value) {
        if (contextType == null) {
            return value;
        } else {
            return ContextParameterUtils.getOriginalValue(contextType, value);
        }
    }

    public void setContextType(ContextType contextType) {
        this.contextType = contextType;
    }

    public String getDistribution() {
        return getRealValue(this.distribution);
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getVersion() {
        return getRealValue(this.version);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isCustom() {
        return this.isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public boolean isUseCustomConfs() {
        return this.useCustomConfs;
    }

    public void setUseCustomConfs(boolean useCustomConfs) {
        this.useCustomConfs = useCustomConfs;
    }

    public String getNameNode() {
        return getRealValue(this.nameNode);
    }

    public void setNameNode(String nameNode) {
        this.nameNode = nameNode;
    }

    public String getJobTracker() {
        return getRealValue(this.jobTracker);
    }

    public void setJobTracker(String jobTracker) {
        this.jobTracker = jobTracker;
    }

    public String getResourceManager() {
        return getRealValue(this.resourceManager);
    }

    public void setResourceManager(String resourceManager) {
        this.resourceManager = resourceManager;
    }

    public String getUserName() {
        return getRealValue(this.userName);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroup() {
        return getRealValue(this.group);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isUseKrb() {
        return this.useKrb;
    }

    public void setUseKrb(boolean useKrb) {
        this.useKrb = useKrb;
    }

    public Set<String> getCustomJars() {
        return this.customJars;
    }

    public void setCustomJars(Set<String> customJars) {
        this.customJars = customJars;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPrincipal() {
        return getRealValue(this.principal);
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getJtOrRmPrincipal() {
        return getRealValue(this.jtOrRmPrincipal);
    }

    public void setJtOrRmPrincipal(String jtOrRmPrincipal) {
        this.jtOrRmPrincipal = jtOrRmPrincipal;
    }

    public String getJobHistoryPrincipal() {
        return getRealValue(this.jobHistoryPrincipal);
    }

    public void setJobHistoryPrincipal(String jobHistoryPrincipal) {
        this.jobHistoryPrincipal = jobHistoryPrincipal;
    }

    public boolean isUseKeytab() {
        return this.useKeytab;
    }

    public void setUseKeytab(boolean useKeytab) {
        this.useKeytab = useKeytab;
    }

    public String getKeytabPrincipal() {
        return getRealValue(this.keytabPrincipal);
    }

    public void setKeytabPrincipal(String keytabPrincipal) {
        this.keytabPrincipal = keytabPrincipal;
    }

    public String getKeytab() {
        return getRealValue(this.keytab);
    }

    public void setKeytab(String keytab) {
        this.keytab = keytab;
    }

    public List<Map<String, Object>> getHadoopProperties() {
        return this.hadoopProperties;
    }

    public void setHadoopProperties(List<Map<String, Object>> hadoopProperties) {
        this.hadoopProperties = hadoopProperties;
    }

    public String getRelativeHadoopClusterId() {
        return this.relativeHadoopClusterId;
    }

    public void setRelativeHadoopClusterId(String relativeHadoopClusterId) {
        this.relativeHadoopClusterId = relativeHadoopClusterId;
    }

    public String getRelativeHadoopClusterLabel() {
        return this.relativeHadoopClusterLabel;
    }

    public void setRelativeHadoopClusterLabel(String relativeHadoopClusterLabel) {
        this.relativeHadoopClusterLabel = relativeHadoopClusterLabel;
    }

    public boolean isMaprT() {
        return this.maprT;
    }

    public void setMaprT(boolean maprT) {
        this.maprT = maprT;
    }

    public String getMaprTPassword() {
        return getRealValue(this.maprTPassword);
    }

    public void setMaprTPassword(String maprTPassword) {
        this.maprTPassword = maprTPassword;
    }

    public String getMaprTCluster() {
        return getRealValue(this.maprTCluster);
    }

    public void setMaprTCluster(String maprTCluster) {
        this.maprTCluster = maprTCluster;
    }

    public String getMaprTDuration() {
        return getRealValue(this.maprTDuration);
    }

    public void setMaprTDuration(String maprTDuration) {
        this.maprTDuration = maprTDuration;
    }

    public boolean isSetMaprTHomeDir() {
        return this.setMaprTHomeDir;
    }

    public void setSetMaprTHomeDir(boolean setMaprTHomeDir) {
        this.setMaprTHomeDir = setMaprTHomeDir;
    }

    public boolean isSetHadoopLogin() {
        return this.setHadoopLogin;
    }

    public void setSetHadoopLogin(boolean setHadoopLogin) {
        this.setHadoopLogin = setHadoopLogin;
    }

    public boolean isPreloadAuthentification() {
        return this.preloadAuthentification;
    }

    public void setPreloadAuthentification(boolean preloadAuthentification) {
        this.preloadAuthentification = preloadAuthentification;
    }

    public String getMaprTHomeDir() {
        return getRealValue(this.maprTHomeDir);
    }

    public void setMaprTHomeDir(String maprTHomeDir) {
        this.maprTHomeDir = maprTHomeDir;
    }

    public String getMaprTHadoopLogin() {
        return getRealValue(this.maprTHadoopLogin);
    }

    public void setMaprTHadoopLogin(String maprTHadoopLogin) {
        this.maprTHadoopLogin = maprTHadoopLogin;
    }

}
