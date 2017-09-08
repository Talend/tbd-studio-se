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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiConfigFile {

    public ApiConfigFile() {

    }

    private String type;

    public String getType() {
        return type;
    }

    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

}
