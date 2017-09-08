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

/**
 * created by ycbai on 2014-6-3 Detailled comment
 * 
 */
public enum EHDFSFileTypes {

    TEXT("Text File"),

    SEQUENCE("Sequence File"),

    // AVRO formatted file, can be avsc or avro formatted file.
    AVRO("AVRO Kind File"),

    ;

    private String label;

    EHDFSFileTypes(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name();
    }

    /**
     * Getter for label.
     * 
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

}
