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
package org.talend.repository.nosql.util;

import org.talend.repository.nosql.model.ENoSQLSchemaType;
import org.talend.repository.nosql.model.INoSQLSchemaNode;

/**
 * created by ycbai on Jul 9, 2014 Detailled comment
 * 
 */
public class NoSQLRepositoryUtil {

    public static String getDBName(INoSQLSchemaNode node) {
        INoSQLSchemaNode dbNode = getDBNode(node);
        if (dbNode != null) {
            return dbNode.getName();
        } else {
            return null;
        }
    }

    public static INoSQLSchemaNode getDBNode(INoSQLSchemaNode node) {
        if (node == null) {
            return null;
        }

        ENoSQLSchemaType schemaType = node.getSchemaType();
        if (ENoSQLSchemaType.DATABASE.equals(schemaType)) {
            return node;
        } else {
            return getDBNode(node.getParent());
        }
    }

}
