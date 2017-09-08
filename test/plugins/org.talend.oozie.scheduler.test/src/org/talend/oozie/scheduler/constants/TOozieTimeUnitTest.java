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
package org.talend.oozie.scheduler.constants;

import junit.framework.Assert;

import org.junit.Test;
import org.talend.oozie.scheduler.i18n.TOozieTestMessages;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext.Timeunit;
import org.talend.oozie.scheduler.test.TOozieTestCommonConstants;

/**
 * @author Marvin Wang
 * @version 1.0 jdk1.6
 * @date May 24, 2012
 */
public class TOozieTimeUnitTest {

    /**
     * Test method for {@link org.talend.oozie.scheduler.constants.TOozieTimeUnit#getDisplayName()}.
     */
    @Test
    public void testGetDisplayName() {
        Assert.assertEquals(message1("TOozieTimeUnit.MINUTE"), TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_MINUTE,
                TOozieTimeUnit.MINUTE.getDisplayName());

        Assert.assertEquals(message1("TOozieTimeUnit.HOUR"), TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_HOUR,
                TOozieTimeUnit.HOUR.getDisplayName());

        Assert.assertEquals(message1("TOozieTimeUnit.DAY"), TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_DAY,
                TOozieTimeUnit.DAY.getDisplayName());

        Assert.assertEquals(message1("TOozieTimeUnit.WEEK"), TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_WEEK,
                TOozieTimeUnit.WEEK.getDisplayName());

        Assert.assertEquals(message1("TOozieTimeUnit.MONTH"), TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_MONTH,
                TOozieTimeUnit.MONTH.getDisplayName());

        Assert.assertEquals(message1("TOozieTimeUnit.END_OF_DAY"),
                TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_DAY, TOozieTimeUnit.END_OF_DAY.getDisplayName());

        Assert.assertEquals(message1("TOozieTimeUnit.END_OF_MONTH"),
                TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_MONTH, TOozieTimeUnit.END_OF_MONTH.getDisplayName());

        Assert.assertEquals(message1("TOozieTimeUnit.NONE"), TOozieTestCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_NONE,
                TOozieTimeUnit.NONE.getDisplayName());

    }

    private String message1(String timeUnit) {
        return TOozieTestMessages.getString("TOozieTimeUnitTest.incorrect_display_name", new Object[] { timeUnit });
    }

    private String message2(TOozieTimeUnit timeUnit) {
        return TOozieTestMessages.getString("TOozieTimeUnitTest.incorrect_display_name", new Object[] { timeUnit });
    }

    /**
     * Test method for {@link org.talend.oozie.scheduler.constants.TOozieTimeUnit#getTimeUnit()}.
     */
    @Test
    public void testGetTimeUnit() {
        Assert.assertEquals(message2(TOozieTimeUnit.MINUTE), Timeunit.MINUTE, TOozieTimeUnit.MINUTE.getTimeUnit());

        Assert.assertEquals(message2(TOozieTimeUnit.HOUR), Timeunit.HOUR, TOozieTimeUnit.HOUR.getTimeUnit());

        Assert.assertEquals(message2(TOozieTimeUnit.DAY), Timeunit.DAY, TOozieTimeUnit.DAY.getTimeUnit());

        Assert.assertEquals(message2(TOozieTimeUnit.WEEK), Timeunit.WEEK, TOozieTimeUnit.WEEK.getTimeUnit());

        Assert.assertEquals(message2(TOozieTimeUnit.MONTH), Timeunit.MONTH, TOozieTimeUnit.MONTH.getTimeUnit());

        Assert.assertEquals(message2(TOozieTimeUnit.END_OF_DAY), Timeunit.END_OF_DAY, TOozieTimeUnit.END_OF_DAY.getTimeUnit());

        Assert.assertEquals(message2(TOozieTimeUnit.END_OF_MONTH), Timeunit.END_OF_MONTH,
                TOozieTimeUnit.END_OF_MONTH.getTimeUnit());

        Assert.assertEquals(message2(TOozieTimeUnit.NONE), Timeunit.NONE, TOozieTimeUnit.NONE.getTimeUnit());
    }

}
