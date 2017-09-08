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
package org.talend.designer.hdfsbrowse.exceptions;

import java.lang.reflect.InvocationTargetException;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopReflectionException extends Exception {

    private static final long serialVersionUID = 7145405706088649721L;

    public HadoopReflectionException() {
        super();
    }

    public HadoopReflectionException(Throwable cause) {
        super(cause instanceof InvocationTargetException ? cause.getCause() : cause);
    }

    public HadoopReflectionException(String msg, Throwable cause) {
        super(msg, cause instanceof InvocationTargetException ? cause.getCause() : cause);
    }

}
