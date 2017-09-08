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
package org.talend.oozie.scheduler.exceptions;

/**
 */
public class OozieJobException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1890985084024816156L;

    public OozieJobException() {
        super();
    }

    public OozieJobException(String msg, Exception e) {
        super(msg, e);
    }
}
