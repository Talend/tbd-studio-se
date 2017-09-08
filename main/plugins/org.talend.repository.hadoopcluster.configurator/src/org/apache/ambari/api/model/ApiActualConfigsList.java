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

/**
 * created by bchen on Jun 10, 2015 Detailled comment
 *
 */
public class ApiActualConfigsList extends ApiListBase<ApiActualConfigs> {

    /**
     * DOC bchen ApiActualConfigsList constructor comment.
     */
    public ApiActualConfigsList() {
        // TODO Auto-generated constructor stub
    }

    public ApiActualConfigsList(List<ApiActualConfigs> actualConfigs) {
        super(actualConfigs);
    }

    @XmlElement
    private String href;

    @XmlElement
    public String getHref() {
        return href;
    }

    @XmlElement(name = "items")
    public List<ApiActualConfigs> getActualConfigs() {
        return values;
    }
}
