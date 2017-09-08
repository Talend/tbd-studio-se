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

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * created by ycbai on 2014-6-16 Detailled comment
 * 
 */
public class NoSQLGeneralException extends Exception {

    private static final long serialVersionUID = 6667621960220700921L;

    public NoSQLGeneralException(String message) {
        super(message);
    }

    public NoSQLGeneralException(Throwable cause) {
        super(cause instanceof InvocationTargetException ? cause.getCause() : cause);
    }

    public NoSQLGeneralException(String msg, Throwable cause) {
        super(msg, cause instanceof InvocationTargetException ? cause.getCause() : cause);
    }

}
