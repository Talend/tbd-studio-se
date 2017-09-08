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
 * created by bchen on Jun 2, 2015 Detailled comment
 *
 */
@XmlRootElement(name = "Clusters")
public class ApiClusterInfo {

    @XmlElement(name = "cluster_name")
    private String name;

    private String version;

    /**
     * Getter for name.
     * 
     * @return the name
     */
    @XmlElement(name = "cluster_name")
    public String getName() {
        return this.name;
    }

    /**
     * Getter for version.
     * 
     * @return the version
     */
    @XmlElement
    public String getVersion() {
        return this.version;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", name).add("version", version).toString();
    }
}
