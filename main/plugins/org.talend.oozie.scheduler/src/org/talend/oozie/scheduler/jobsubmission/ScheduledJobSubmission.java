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
package org.talend.oozie.scheduler.jobsubmission;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext;
import org.talend.oozie.scheduler.jobsubmission.model.JobSubmissionException;
import org.talend.oozie.scheduler.utils.TOozieParamUtils;

/**
 * JobSubmission implementation that schedules the recurring execution of the ETL job on the Hadoop Cluster using Oozie
 * Coordinator.
 */
public class ScheduledJobSubmission extends AbstractOozieJobSubmission {

    @Override
    public String submit(JobContext jobContext) throws JobSubmissionException, InterruptedException, URISyntaxException {
        try {
            createWorkflowTemplate(jobContext);
            createCoordinatorTemplate(jobContext);

            return doSubmit(jobContext);
        } catch (IOException e) {
            ExceptionHandler.process(e);
            throw new JobSubmissionException("Error serializing coordinator xml to HDFS.", e);
        } catch (OozieClientException e) {
            ExceptionHandler.process(e);
            throw new JobSubmissionException("Error submitting coordinator job to Oozie.", e);
        }
    }

    protected String doSubmit(JobContext jobContext) throws OozieClientException {
        OozieClient oozieClient = createOozieClient(jobContext.getOozieEndPoint(), jobContext.getDebug());
        String userName = jobContext.get(OozieClient.USER_NAME);
        // create a coordinator job configuration and set the coordinator application path
        Properties configuration = oozieClient.createConfiguration();
        if (userName != null && !"".equals(userName)) {
            configuration.setProperty(OozieClient.USER_NAME, userName);
            // configuration.setProperty(OozieClient.APP_PATH, jobContext.getNameNodeEndPoint() +
            // jobContext.getJobPathOnHDFS());
        }

        String appPath = TOozieParamUtils.getAppPath(jobContext.getNameNodeEndPoint(), jobContext.getJobPathOnHDFS());
        configuration.setProperty(OozieClient.COORDINATOR_APP_PATH, appPath);

        // start the coordinator job
        return oozieClient.run(configuration);
    }

    @Override
    public Status status(String jobHandle, String oozieEndPoint) throws JobSubmissionException {
        try {
            OozieClient oozieClient = createOozieClient(oozieEndPoint, 0);
            String status = oozieClient.getCoordJobInfo(jobHandle).getStatus().name();
            return Status.valueOf(status);
        } catch (OozieClientException e) {
            throw new JobSubmissionException("Error getting status for job: " + jobHandle, e);
        }
    }
}
