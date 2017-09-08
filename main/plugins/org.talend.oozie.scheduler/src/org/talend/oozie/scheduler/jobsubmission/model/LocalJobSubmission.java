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

import org.talend.core.runtime.process.TalendProcessArgumentConstant;

public class LocalJobSubmission implements JobSubmission {

    @Override
    public String submit(JobContext jobContext) throws JobSubmissionException {
        StringBuilder command = new StringBuilder(1024);
        command.append("java -cp lib/classpath.jar; ").append(jobContext.getJobFQClassName())
                .append(' ' + TalendProcessArgumentConstant.CMD_ARG_CONTEXT_NAME + "Default %*");

        try {
            Process process = Runtime.getRuntime().exec(command.toString());
            int returnValue = process.waitFor();
            return jobContext.getJobName() + (returnValue == 0 ? "-Completed" : "-Failed");
        } catch (Exception e) {
            throw new JobSubmissionException("Error executing the etl job.", e);
        }
    }

    @Override
    public String resubmit(String jobHandle, JobContext jobContext) throws JobSubmissionException {
        throw new UnsupportedOperationException("This operation does not make sense for local/sync execution!");
    }

    @Override
    public Status status(String jobHandle, String oozieEndPoint) throws JobSubmissionException {
        throw new UnsupportedOperationException("This operation does not make sense for local/sync execution!");
    }

    @Override
    public void kill(String jobHandle, String oozieEndPoint) throws JobSubmissionException {
        throw new UnsupportedOperationException("This operation does not make sense for local/sync execution!");
    }

    @Override
    public String getJobLog(String jobHandle, String oozieEndPoint) throws JobSubmissionException {
        throw new UnsupportedOperationException("This operation does not make sense for local/sync execution!");
    }
}
