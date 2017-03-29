// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.hdfsbrowse.util;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.utils.TalendQuoteUtils;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHDFSRowSeparator {

    STANDARD_EOL("Standard EOL", "\"\\n\""), //$NON-NLS-1$ //$NON-NLS-2$

    ;

    private String displayName;

    private String value;

    EHDFSRowSeparator(String displayName, String value) {
        this.displayName = displayName;
        this.value = value;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getValue() {
        return this.value;
    }

    public static List<String> getAllRowSeparators(boolean isDisplay) {
        List<String> separators = new ArrayList<String>();
        EHDFSRowSeparator[] values = EHDFSRowSeparator.values();
        for (EHDFSRowSeparator type : values) {
            if (isDisplay) {
                separators.add(type.getDisplayName());
            } else {
                separators.add(type.getValue());
            }
        }

        return separators;
    }

    public static EHDFSRowSeparator indexOf(String name, boolean display) {
        if (name != null) {
            if (!display) {
                name = TalendQuoteUtils.addQuotesIfNotExist(name);
            }
            for (EHDFSRowSeparator separator : EHDFSRowSeparator.values()) {
                if (display) {
                    if (name.equalsIgnoreCase(separator.getDisplayName())) {
                        return separator;
                    }
                } else {
                    if (name.equalsIgnoreCase(separator.getValue())) {
                        return separator;
                    }
                }
            }
        }

        return null;
    }

    public static EHDFSRowSeparator getDefaultSeparator() {
        return STANDARD_EOL;
    }

}
