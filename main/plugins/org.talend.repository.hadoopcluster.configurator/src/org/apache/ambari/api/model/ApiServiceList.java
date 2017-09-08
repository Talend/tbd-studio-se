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
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
/**
 * A list of services.
 */
@XmlRootElement(name = "serviceList")
public class ApiServiceList extends ApiListBase<ApiService> {

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

    public ApiServiceList() {
        // For JAX-B
    }

    public ApiServiceList(List<ApiService> services) {
        super(services);
    }

    @XmlElementWrapper(name = "items")
    public List<ApiService> getServices() {
        return values;
    }

    public void setServices(List<ApiService> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("href", href).add("items", getServices()).toString();
    }
}
