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
package org.talend.designer.pigmap.figures.manager;

import org.talend.designer.gefabstractmap.figures.layout.ZoneContentLayout;
import org.talend.designer.gefabstractmap.figures.table.AbstractTableContainer;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapContainerLayout extends ZoneContentLayout {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.layout.TreeContainerLayout#getLookupConnectionSize()
     */
    @Override
    protected int getLookupConnectionSize(AbstractTableContainer tableContainer) {
        return 0;
    }

}
