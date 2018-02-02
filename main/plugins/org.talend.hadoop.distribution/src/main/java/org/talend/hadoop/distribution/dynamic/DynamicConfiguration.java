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
package org.talend.hadoop.distribution.dynamic;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.repository.ProjectManager;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicConfiguration {

    private String distribution;

    private String id;

    private String version;

    private String name;

    private String description;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistribution() {
        return this.distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemoteRepositoryUrl() {
        return getPreference().getRepository();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IDynamicDistributionPreference getPreference() {
        try {
            return DynamicDistributionManager.getInstance().getDynamicDistributionGroup(getDistribution())
                    .getDynamicDistributionPreference(ProjectManager.getInstance().getCurrentProject());
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return null;
    }

}
