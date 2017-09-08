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

import org.talend.oozie.scheduler.jobsubmission.RemoteJobSubmission;
import org.talend.oozie.scheduler.jobsubmission.ScheduledJobSubmission;

/**
 * Factory class to dole out JobSubmission implementations. - If frequency and timeunit are specified, its a scheduled,
 * recurring job submission - If frequency and timeunit are not specified but a OozieEndPoint is, its a remote job
 * submission - Else Local Job
 */
public class JobSubmissionFactory {

    private JobSubmissionFactory() {
    }

    public static JobSubmission get(JobContext jobContext) {
        if (scheduled(jobContext)) {
            return new ScheduledJobSubmission();
        } else if (remote(jobContext)) {
            return new RemoteJobSubmission();
        } else {
            return new LocalJobSubmission();
        }
    }

    private static boolean scheduled(JobContext jobContext) {
        return jobContext.getFrequency() > 0 && jobContext.getTimeUnit() != JobContext.Timeunit.NONE;
    }

    private static boolean remote(JobContext jobContext) {
        return jobContext.getOozieEndPoint() != null;
    }
}
