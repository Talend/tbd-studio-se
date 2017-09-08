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
package org.talend.designer.hdfsbrowse.util;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.utils.TalendQuoteUtils;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHDFSFieldSeparator {

    SEMICOLON("Semicolon", "\";\""), //$NON-NLS-1$ //$NON-NLS-2$

    COMMA("Comma", "\",\""), //$NON-NLS-1$ //$NON-NLS-2$

    SPACE("Space", "\" \""), //$NON-NLS-1$ //$NON-NLS-2$

    TABULATION("Tabulation", "\"\\t\""), //$NON-NLS-1$ //$NON-NLS-2$

    ALT_65("''(Alt 65, #A4)", "\"''\""), //$NON-NLS-1$ //$NON-NLS-2$

    ;

    private String displayName;

    private String value;

    EHDFSFieldSeparator(String displayName, String value) {
        this.displayName = displayName;
        this.value = value;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getValue() {
        return this.value;
    }

    public static List<String> getAllFieldSeparators(boolean isDisplay) {
        List<String> separators = new ArrayList<String>();
        EHDFSFieldSeparator[] values = EHDFSFieldSeparator.values();
        for (EHDFSFieldSeparator type : values) {
            if (isDisplay) {
                separators.add(type.getDisplayName());
            } else {
                separators.add(type.getValue());
            }
        }

        return separators;
    }

    public static EHDFSFieldSeparator indexOf(String name, boolean display) {
        if (name != null) {
            if (!display) {
                name = TalendQuoteUtils.addQuotesIfNotExist(name);
            }
            for (EHDFSFieldSeparator separator : EHDFSFieldSeparator.values()) {
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

    public static EHDFSFieldSeparator getDefaultSeparator() {
        return SEMICOLON;
    }

}
