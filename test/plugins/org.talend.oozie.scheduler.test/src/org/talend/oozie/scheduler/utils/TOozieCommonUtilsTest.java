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
package org.talend.oozie.scheduler.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talend.oozie.scheduler.constants.OozieJobProcessStatus;
import org.talend.oozie.scheduler.constants.TOozieCommonConstants;
import org.talend.oozie.scheduler.constants.TOozieTimeUnit;
import org.talend.oozie.scheduler.i18n.TOozieTestMessages;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext.Timeunit;
import org.talend.oozie.scheduler.jobsubmission.model.JobSubmission;

/**
 * Created by Marvin Wang on May 22, 2012 for JUnit test.
 */
public class TOozieCommonUtilsTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsWindowsOS() {
        String osName = System.getProperty("os.name");
        if (osName.trim().startsWith(TOozieCommonConstants.OS_WINDOWS_PREFIX))
            Assert.assertTrue(TOozieTestMessages.getString("TOozieCommonUtilsTest.currentOS_is_windows"),
                    TOozieCommonUtils.isWindowsOS());
        else
            Assert.assertFalse(TOozieTestMessages.getString("TOozieCommonUtilsTest.currOS_isnot_windows"),
                    TOozieCommonUtils.isWindowsOS());
    }

    @Test
    public void testConvertToOozieJobProcessStatus() {
        OozieJobProcessStatus prepStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.PREP);
        OozieJobProcessStatus runningStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.RUNNING);
        OozieJobProcessStatus succeededStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.SUCCEEDED);
        OozieJobProcessStatus killedStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.KILLED);
        OozieJobProcessStatus failedStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.FAILED);
        OozieJobProcessStatus suspendedStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.SUSPENDED);

        Assert.assertTrue(
                TOozieTestMessages.getString("TOozieCommonUtilsTest.status_jobsub_to_oozie", new Object[] {
                        JobSubmission.Status.PREP, OozieJobProcessStatus.PREP }), OozieJobProcessStatus.PREP == prepStatus);
        Assert.assertTrue(
                TOozieTestMessages.getString("TOozieCommonUtilsTest.status_jobsub_to_oozie", new Object[] {
                        JobSubmission.Status.RUNNING, OozieJobProcessStatus.RUNNING }),
                OozieJobProcessStatus.RUNNING == runningStatus);
        Assert.assertTrue(
                TOozieTestMessages.getString("TOozieCommonUtilsTest.status_jobsub_to_oozie", new Object[] {
                        JobSubmission.Status.SUCCEEDED, OozieJobProcessStatus.SUCCEEDED }),
                OozieJobProcessStatus.SUCCEEDED == succeededStatus);
        Assert.assertTrue(
                TOozieTestMessages.getString("TOozieCommonUtilsTest.status_jobsub_to_oozie", new Object[] {
                        JobSubmission.Status.KILLED, OozieJobProcessStatus.KILLED }),
                OozieJobProcessStatus.KILLED == killedStatus);
        Assert.assertTrue(
                TOozieTestMessages.getString("TOozieCommonUtilsTest.status_jobsub_to_oozie", new Object[] {
                        JobSubmission.Status.FAILED, OozieJobProcessStatus.FAILED }),
                OozieJobProcessStatus.FAILED == failedStatus);
        Assert.assertTrue(
                TOozieTestMessages.getString("TOozieCommonUtilsTest.status_jobsub_to_oozie", new Object[] {
                        JobSubmission.Status.SUSPENDED, OozieJobProcessStatus.SUSPENDED }),
                OozieJobProcessStatus.SUSPENDED == suspendedStatus);
    }

    @Test
    public void testGetTimeUnitItems() {
        String[] timeUnitNames = TOozieCommonUtils.getTimeUnitItems();
        String[] expectedTimeUnitNames = new String[] {
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_MINUTE"),// "minute".
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_HOUR"),// "hour".
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_DAY"),// "day".
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_WEEK"),// "week".
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_MONTH"),// "month".
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_DAY"), // "end of day".
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_MONTH"), // "end of month".
                TOozieTestMessages.getString("TOozieCommonUtilsTest.OOZIE_TIMEUNIT_DISPLAY_NAME_NONE") // "none".
        };
        Assert.assertNotNull(TOozieTestMessages.getString("TOozieCommonUtilsTest.items_not_null"), timeUnitNames);
        Assert.assertEquals(TOozieTestMessages.getString("TOozieCommonUtilsTest.number_of_timeUnit", new Object[] { 8 }), 8,
                timeUnitNames.length);

        Assert.assertArrayEquals(TOozieTestMessages.getString("TOozieCommonUtilsTest.is_not_timeUnit_item"),
                expectedTimeUnitNames, timeUnitNames);
    }

    @Test
    public void testLookupTimeUnit() {
        List<Timeunit> expecteds = new ArrayList<Timeunit>();
        expecteds.add(Timeunit.MINUTE);
        expecteds.add(Timeunit.HOUR);
        expecteds.add(Timeunit.DAY);
        expecteds.add(Timeunit.WEEK);
        expecteds.add(Timeunit.MONTH);
        expecteds.add(Timeunit.END_OF_DAY);
        expecteds.add(Timeunit.END_OF_MONTH);
        expecteds.add(Timeunit.NONE);
        int lookupIndex = TOozieTimeUnit.values().length + 1;
        int expectedsSize = expecteds.size();
        for (int i = -1; i < lookupIndex; i++) {
            Timeunit acturalTU = TOozieCommonUtils.lookupTimeUnit(i);
            if (i < expectedsSize - 1 && i >= 0)
                Assert.assertEquals(TOozieTestMessages.getString("TOozieCommonUtilsTest.lookup_timeUnit"), expecteds.get(i),
                        acturalTU);
            else
                Assert.assertEquals(TOozieTestMessages.getString("TOozieCommonUtilsTest.lookup_timeUnit"), Timeunit.NONE,
                        acturalTU);

        }
    }
}
