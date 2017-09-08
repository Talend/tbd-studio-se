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
package org.talend.repository.nosql.exceptions;

/**
 * DOC PLV class global comment. Detailled comment
 */
public class NoSQLReflectionException extends NoSQLGeneralException {

    private static final long serialVersionUID = 7048888294801727325L;

    /**
     * DOC PLV NoSQLReflectionException constructor comment.
     */
    public NoSQLReflectionException(String message) {
        super(message);
    }

    public NoSQLReflectionException(Throwable cause) {
        super(cause);
    }

    public NoSQLReflectionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
