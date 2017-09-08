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

import java.net.URISyntaxException;

/**
 * JobSubmission interface to launch the execution of an ETL job
 */
public interface JobSubmission {

    public static enum Status {
        PREP,
        RUNNING,
        SUCCEEDED,
        KILLED,
        FAILED,
        SUSPENDED
    }

    /**
     * Submit an ETL job for execution
     * 
     * @param jobContext - job context parameters
     * @return external job id or handle
     * @throws JobSubmissionException wrapper for exceptions from HDFS and Oozie
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    String submit(JobContext jobContext) throws JobSubmissionException, InterruptedException, URISyntaxException;

    /**
     * Redeploy an ETL job that is already deployed on Oozie
     * 
     * @param jobHandle external job handle that was returned as part of the initial job submission
     * @param jobContext job context parameters
     * @return external job id or handle
     * @throws JobSubmissionException wrapper for exceptions from HDFS and Oozie
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    String resubmit(String jobHandle, JobContext jobContext) throws JobSubmissionException, InterruptedException,
            URISyntaxException;

    /**
     * Determine the current status of a previously submitted job
     * 
     * @param jobHandle external job handle that was returned as part of the initial job submission
     * @param oozieEndPoint Web Service End Point of Oozie Scheduler Service
     * @return Status Current status of the job submitted
     * @throws JobSubmissionException wrapper for exceptions from HDFS and Oozie
     */
    Status status(String jobHandle, String oozieEndPoint) throws JobSubmissionException;

    /**
     * Kill a previously submitted job
     * 
     * @param jobHandle external job handle that was returned as part of the initial job submission
     * @param oozieEndPoint Web Service End Point of Oozie Scheduler Service
     * @throws JobSubmissionException wrapper for exceptions from HDFS and Oozie
     */
    void kill(String jobHandle, String oozieEndPoint) throws JobSubmissionException;

    // JobInfo info(String jobHandle) throws JobSubmissionException;

    /**
     * Retrieve the logs of a previously submitted job
     * 
     * @param jobHandle external job handle that was returned as part of the initial job submission
     * @param oozieEndPoint Web Service End Point of Oozie Scheduler Service
     * @return Log contents as a String
     * @throws JobSubmissionException wrapper for exceptions from HDFS and Oozie
     */
    String getJobLog(String jobHandle, String oozieEndPoint) throws JobSubmissionException;
}
