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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

/**
 * created by bchen on Jun 2, 2015 Detailled comment
 *
 */
/**
 * A list of clusters.
 */
@XmlRootElement(name = "clusterList")
public class ApiClusterList extends ApiListBase<ApiCluster> {

    private String href;

    /**
     * Getter for href.
     * 
     * @return the href
     */
    @XmlElement
    public String getHref() {
        return this.href;
    }

    public ApiClusterList() {

    }

    public ApiClusterList(List<ApiCluster> clusters) {
        super(clusters);
    }

    @XmlElementWrapper(name = "items")
    public List<ApiCluster> getClusters() {
        return values;
    }

    public void setClusters(List<ApiCluster> clusters) {
        this.values = clusters;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("href", href).add("items", getClusters()).toString();
    }

}