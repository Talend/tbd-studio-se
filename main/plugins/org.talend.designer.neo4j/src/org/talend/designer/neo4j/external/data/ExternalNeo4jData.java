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
package org.talend.designer.neo4j.external.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExternalNeo4jData implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ExternalNeo4jTable> outputTables = new ArrayList<ExternalNeo4jTable>();

    private ExternalNeo4jUIProperties uiProperties = new ExternalNeo4jUIProperties();

    public List<ExternalNeo4jTable> getOutputTables() {
        return outputTables;
    }

    public void setOutputTables(List<ExternalNeo4jTable> outputTables) {
        this.outputTables = outputTables;
    }

    public ExternalNeo4jUIProperties getUiProperties() {
        return uiProperties;
    }

    public void setUiProperties(ExternalNeo4jUIProperties uiProperties) {
        this.uiProperties = uiProperties;
    }

}
