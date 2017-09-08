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

import com.google.common.base.Objects;

/**
 * created by bchen on Jun 2, 2015 Detailled comment
 *
 */
/**
 * A cluster represents a set of interdependent services running on a set of hosts. All services on a given cluster are
 * of the same software version.
 */
public class ApiCluster {

    private String href;

    @XmlElement(name = "Clusters")
    private ApiClusterInfo info;

    public ApiCluster() {
        // For JAX-B
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("href", href).add("cluster", info).toString();
    }

    /**
     * Getter for href.
     * 
     * @return the href
     */
    @XmlElement
    public String getHref() {
        return this.href;
    }

    /**
     * Getter for info.
     * 
     * @return the info
     */
    @XmlElement(name = "Clusters")
    public ApiClusterInfo getInfo() {
        return this.info;
    }

}