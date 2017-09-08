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
package org.talend.designer.hdfsbrowse.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHadoopFileTypes {

    UNKNOWN("Unknown"),

    FOLDER("Folder"),

    FILE("File"),

    RESOURCE("Resource"), // Represent all the resources in HDFS.

    ;

    private String value;

    EHadoopFileTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name();
    }

    public static List<String> getAllFileTypeValues() {
        List<String> vals = new ArrayList<String>();
        EHadoopFileTypes[] values = values();
        for (EHadoopFileTypes type : values) {
            vals.add(type.getValue());
        }

        return vals;
    }

}
