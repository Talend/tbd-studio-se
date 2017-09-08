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
package org.talend.oozie.scheduler.jobsubmission.model;

/**
 * A Wrapper for Exceptions thrown from external services like Hadoop, Oozie, etc.
 */
public class JobSubmissionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7035183424095154976L;

    public JobSubmissionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JobSubmissionException(Throwable throwable) {
        super(throwable);
    }
}
