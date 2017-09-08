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
package org.apache.ambari.api.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * created by bchen on Jun 10, 2015 Detailled comment
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiActualConfigs extends ApiListBase<ApiComponents> {

    /**
     * DOC bchen ApiActualConfigs constructor comment.
     */
    public ApiActualConfigs() {
        // TODO Auto-generated constructor stub
    }

    public ApiActualConfigs(List<ApiComponents> components) {
        super(components);
    }

    @XmlElement(name = "ServiceInfo")
    private ApiServiceInfo info;

    /**
     * Getter for info.
     * 
     * @return the info
     */
    @XmlElement(name = "ServiceInfo")
    public ApiServiceInfo getServiceInfo() {
        return this.info;
    }

    @XmlElement(name = "components")
    public List<ApiComponents> getComponents() {
        return values;
    }
}
