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
package org.talend.repository.nosql.model;

/**
 * 
 * created by ycbai on Jul 7, 2014 Detailled comment
 * 
 */
public enum ENoSQLSchemaType {

    DATABASE,

    TABLE,

    COLUMN,

    ;

    public String getName() {
        return this.name();
    }

}
