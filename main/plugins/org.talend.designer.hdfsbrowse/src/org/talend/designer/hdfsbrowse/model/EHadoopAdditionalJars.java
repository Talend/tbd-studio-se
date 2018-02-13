// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
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
 * 
 * created by hcyi on May 18, 2017 Detailled comment
 *
 */
public enum EHadoopAdditionalJars {

    LZO_ID("io.compression.codec.lzo.class")//$NON-NLS-1$ 

    ;

    private String value;

    EHadoopAdditionalJars(String value) {
        this.value = value;
    }

    public static List<String> getBaseLoaderClassIds() {
        List<String> vals = new ArrayList<String>();
        EHadoopAdditionalJars[] values = values();
        for (EHadoopAdditionalJars value : values) {
            vals.add(value.getValue());
        }
        return vals;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name();
    }
}
