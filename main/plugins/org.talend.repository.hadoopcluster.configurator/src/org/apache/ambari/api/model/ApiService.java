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
@XmlRootElement(name = "service")
public class ApiService {

    private String href;

    @XmlElement(name = "ServiceInfo")
    private ApiServiceInfo info;

    public ApiService() {
        // For JAX-B
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("href", href).add("service", info).toString();
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
    @XmlElement(name = "ServiceInfo")
    public ApiServiceInfo getInfo() {
        return this.info;
    }
}
