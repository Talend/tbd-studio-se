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
package org.talend.designer.pigmap.dnd;

public enum DropType {
    DROP_FILTER("Drop to update tree filter"), //$NON-NLS-1$
    DROP_EXPRESSION("Drop to update node filter"), //$NON-NLS-1$
    DROP_INSERT_INPUT("Drop to insert a new input column"), //$NON-NLS-1$
    DROP_INSERT_OUTPUT("Drop to insert a new output column");//$NON-NLS-1$

    DropType(String str) {

    }
}
