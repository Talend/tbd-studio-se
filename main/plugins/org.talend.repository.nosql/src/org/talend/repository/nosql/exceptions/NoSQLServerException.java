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
 * 
 * created by ycbai on 2014-6-16 Detailled comment
 * 
 */
public class NoSQLServerException extends NoSQLGeneralException {

    private static final long serialVersionUID = 6647421983220700921L;

    public NoSQLServerException(String message) {
        super(message);
    }

    public NoSQLServerException(Throwable cause) {
        super(cause);
    }

    public NoSQLServerException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
