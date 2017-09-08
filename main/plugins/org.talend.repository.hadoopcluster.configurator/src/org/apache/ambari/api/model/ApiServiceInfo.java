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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
@XmlRootElement(name = "ServiceInfo")
public class ApiServiceInfo {

    @XmlElement(name = "cluster_name")
    private String clusterName;

    @XmlElement(name = "service_name")
    private String serviceName;

    /**
     * Getter for name.
     * 
     * @return the name
     */
    @XmlElement(name = "cluster_name")
    public String getClusterName() {
        return this.clusterName;
    }

    /**
     * Getter for version.
     * 
     * @return the version
     */
    @XmlElement(name = "service_name")
    public String getServiceName() {
        return this.serviceName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("clusterName", clusterName).add("serviceName", serviceName).toString();
    }
}
