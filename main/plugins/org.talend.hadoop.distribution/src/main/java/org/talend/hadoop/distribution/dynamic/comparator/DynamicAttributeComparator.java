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
package org.talend.hadoop.distribution.dynamic.comparator;

import java.util.Comparator;

import org.talend.core.runtime.dynamic.IDynamicAttribute;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicAttributeComparator implements Comparator<IDynamicAttribute> {

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    private String compareAttr = ATTR_ID;

    public DynamicAttributeComparator() {
        this.compareAttr = ATTR_ID;
    }

    public DynamicAttributeComparator(String compareAttr) {
        this.compareAttr = compareAttr;
    }

    @Override
    public int compare(IDynamicAttribute arg0, IDynamicAttribute arg1) {
        String id0 = (String) arg0.getAttribute(compareAttr);
        String id1 = (String) arg1.getAttribute(compareAttr);

        if (id0 == null && id1 == null) {
            return 0;
        }
        if (id0 == null) {
            return -1;
        }
        if (id1 == null) {
            return 1;
        }
        return id0.compareTo(id1);
    }

    public String getCompareAttr() {
        return this.compareAttr;
    }

    public void setCompareAttr(String compareAttr) {
        this.compareAttr = compareAttr;
    }

}
