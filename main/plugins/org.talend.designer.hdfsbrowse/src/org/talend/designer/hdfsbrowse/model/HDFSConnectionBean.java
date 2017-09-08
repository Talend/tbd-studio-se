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
package org.talend.designer.hdfsbrowse.model;

import java.util.HashMap;
import java.util.Map;

import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSConnectionBean {

    private ContextType parentContextType;

    private ContextType contextType;

    private String distribution;

    private String dfVersion;

    private String nameNodeURI;

    private String jobtrackerURI;

    private boolean enableKerberos;

    private String principal;

    private String userName;

    private String group;

    private String rowSeparator;

    private String fieldSeparator;

    private String relativeHadoopClusterId;

    private boolean useCustomVersion;

    private boolean useKeytab;

    private String keytabPrincipal;

    private String keytab;

    private boolean useCustomConfs;

    private Map<String, Object> configurations = new HashMap<String, Object>();

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    private String getRealValue(String value) {
        return getRealValue(value, false);
    }

    private String getRealValue(String value, boolean isParentParam) {
        ContextType ct = null;
        if (isParentParam) {
            ct = parentContextType;
        } else {
            ct = contextType;
        }
        if (ct == null) {
            return value;
        } else {
            return ContextParameterUtils.getOriginalValue(ct, value);
        }
    }

    public ContextType getParentContextType() {
        return this.parentContextType;
    }

    public void setParentContextType(ContextType parentContextType) {
        this.parentContextType = parentContextType;
    }

    public ContextType getContextType() {
        return this.contextType;
    }

    public void setContextType(ContextType contextType) {
        this.contextType = contextType;
    }

    public String getDistribution() {
        return getRealValue(this.distribution, true);
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getDfVersion() {
        return getRealValue(this.dfVersion, true);
    }

    public void setDfVersion(String dfVersion) {
        this.dfVersion = dfVersion;
    }

    public String getNameNodeURI() {
        return getRealValue(this.nameNodeURI, true);
    }

    public void setNameNodeURI(String nameNodeURI) {
        this.nameNodeURI = nameNodeURI;
    }

    public String getJobtrackerURI() {
        return getRealValue(this.jobtrackerURI, true);
    }

    public void setJobtrackerURI(String jobtrackerURI) {
        this.jobtrackerURI = jobtrackerURI;
    }

    public boolean isEnableKerberos() {
        return this.enableKerberos;
    }

    public void setEnableKerberos(boolean enableKerberos) {
        this.enableKerberos = enableKerberos;
    }

    public String getPrincipal() {
        return getRealValue(this.principal, true);
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getUserName() {
        return getRealValue(this.userName);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroup() {
        return getRealValue(this.group, true);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRowSeparator() {
        return getRealValue(this.rowSeparator);
    }

    public void setRowSeparator(String rowSeparator) {
        this.rowSeparator = rowSeparator;
    }

    public String getFieldSeparator() {
        return getRealValue(this.fieldSeparator);
    }

    public void setFieldSeparator(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    public String getRelativeHadoopClusterId() {
        return this.relativeHadoopClusterId;
    }

    public void setRelativeHadoopClusterId(String relativeHadoopClusterId) {
        this.relativeHadoopClusterId = relativeHadoopClusterId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public boolean isUseCustomVersion() {
        return this.useCustomVersion;
    }

    public void setUseCustomVersion(boolean useCustomVersion) {
        this.useCustomVersion = useCustomVersion;
    }

    public boolean isUseCustomConfs() {
        return this.useCustomConfs;
    }

    public void setUseCustomConfs(boolean useCustomConfs) {
        this.useCustomConfs = useCustomConfs;
    }

    public boolean isUseKeytab() {
        return this.useKeytab;
    }

    public void setUseKeytab(boolean useKeytab) {
        this.useKeytab = useKeytab;
    }

    public String getKeytabPrincipal() {
        return getRealValue(this.keytabPrincipal, true);
    }

    public void setKeytabPrincipal(String keytabPrincipal) {
        this.keytabPrincipal = keytabPrincipal;
    }

    public String getKeytab() {
        return getRealValue(this.keytab, true);
    }

    public void setKeytab(String keytab) {
        this.keytab = keytab;
    }

    public Map<String, Object> getConfigurations() {
        return this.configurations;
    }

}
