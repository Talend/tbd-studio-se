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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.talend.oozie.scheduler.i18n.TOozieTestMessages;
import org.talend.oozie.scheduler.test.TOozieTestUtils;

/**
 * Created by Marvin Wang on May 22, 2012.
 */
public class TOozieDateUtilsTest {

    public static final String TALEND_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static String currDateStr;

    private static Date currDate;

    private static int currYear = -1;

    private static int currMonth = -1;

    private static int currDayofMonth = -1;

    private static int currHourofDay = -1;

    private static int currMinute = -1;

    private static int currSecond = -1;

    @BeforeClass
    public static void beforeClass() {
        Calendar time = Calendar.getInstance();
        currDate = time.getTime();
        currYear = time.get(Calendar.YEAR);
        currMonth = time.get(Calendar.MONTH) + 1; // The number of MONTH counts from zero.
        currDayofMonth = time.get(Calendar.DAY_OF_MONTH);
        currHourofDay = time.get(Calendar.HOUR_OF_DAY);
        currMinute = time.get(Calendar.MINUTE);
        currSecond = time.get(Calendar.SECOND);

        currDateStr = TOozieTestUtils
                .makeupDateString(currYear, currMonth, currDayofMonth, currHourofDay, currMinute, currSecond);
    }

    /**
     * Test method for {@link org.talend.oozie.scheduler.utils.TOozieDateUtils#convertDateToString(java.util.Date)}.
     */
    @Test
    public void testConvertDateToStringDate() {

        String dateStr = TOozieDateUtils.convertDateToString(currDate);
        Assert.assertNotNull(dateStr);

        Assert.assertEquals(
                TOozieTestMessages.getString("TOozieDateUtilsTest.not_excepted_date_string", new Object[] { TALEND_DATE_PATTERN,
                        dateStr }), currDateStr, dateStr);
    }

    /**
     * Test method for {@link org.talend.oozie.scheduler.utils.TOozieDateUtils#converStringToDate(java.lang.String)}.
     */
    @Test
    public void testConvertStringToDateOneArguments() {
        Date actualDate;
        try {
            actualDate = TOozieDateUtils.converStringToDate(currDateStr);
            checkCalendaField(actualDate);
        } catch (ParseException e) {
            Assert.fail(TOozieTestMessages.getString("TOozieDateUtilsTest.not_convertTo_date", new Object[] { currDateStr }));
        }
    }

    /**
     * Test method for
     * {@link org.talend.oozie.scheduler.utils.TOozieDateUtils#ConvertStringToDate(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testConvertStringToDateTwoArguments() {
        try {
            Date actualDate = TOozieDateUtils.ConvertStringToDate(currDateStr, TALEND_DATE_PATTERN);
            checkCalendaField(actualDate);
        } catch (ParseException e) {
            Assert.fail(TOozieTestMessages.getString("TOozieDateUtilsTest.not_convertTo_date", new Object[] { currDateStr }));
        }
    }

    private void checkCalendaField(Date actualDate) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.setTime(actualDate);

        int actualYear = time.get(Calendar.YEAR);
        Assert.assertEquals(TOozieTestMessages.getString("TOozieDateUtilsTest.convert_calendar_field", new Object[] { "Year" }),
                currYear, actualYear);

        int actualMonth = time.get(Calendar.MONTH);
        Assert.assertEquals(TOozieTestMessages.getString("TOozieDateUtilsTest.convert_calendar_field", new Object[] { "Month" }),
                currMonth, actualMonth + 1);

        int actualDayofMonth = time.get(Calendar.DAY_OF_MONTH);
        Assert.assertEquals(
                TOozieTestMessages.getString("TOozieDateUtilsTest.convert_calendar_field", new Object[] { "Day of month" }),
                currDayofMonth, actualDayofMonth);

        int actualHourofDay = time.get(Calendar.HOUR_OF_DAY);
        Assert.assertEquals(
                TOozieTestMessages.getString("TOozieDateUtilsTest.convert_calendar_field", new Object[] { "Hour of day" }),
                currHourofDay, actualHourofDay);

        int actualMinute = time.get(Calendar.MINUTE);
        Assert.assertEquals(
                TOozieTestMessages.getString("TOozieDateUtilsTest.convert_calendar_field", new Object[] { "Minute" }),
                currMinute, actualMinute);

        int actualSecond = time.get(Calendar.SECOND);
        Assert.assertEquals(
                TOozieTestMessages.getString("TOozieDateUtilsTest.convert_calendar_field", new Object[] { "Second" }),
                currSecond, actualSecond);
    }

    /**
     * Test method for
     * {@link org.talend.oozie.scheduler.utils.TOozieDateUtils#convertDateToString(java.util.Date, java.lang.String)}.
     */
    @Test
    public void testConvertDateToString() {
        String actualDateStr = TOozieDateUtils.convertDateToString(currDate, TALEND_DATE_PATTERN);
        Assert.assertNotNull(actualDateStr);

        Assert.assertEquals(
                TOozieTestMessages.getString("TOozieDateUtilsTest.not_excepted_date_string", new Object[] { TALEND_DATE_PATTERN,
                        actualDateStr }), currDateStr, actualDateStr);
    }

    /**
     * Test method for {@link org.talend.oozie.scheduler.utils.TOozieDateUtils#fetchCurrentDate()}.
     */
    @Test
    public void testFetchCurrentDate() {
        String actualDateStr = TOozieDateUtils.fetchCurrentDate();
        Date tempDate = Calendar.getInstance().getTime();

        Assert.assertNotNull(actualDateStr);

        SimpleDateFormat sdf = new SimpleDateFormat(TALEND_DATE_PATTERN);
        try {
            Date actualDate = sdf.parse(actualDateStr);
            Assert.assertTrue(actualDate.getTime() <= tempDate.getTime());
        } catch (ParseException e) {
            Assert.fail(TOozieTestMessages.getString("TOozieDateUtilsTest.not_convertTo_date", new Object[] { actualDateStr }));
        }

    }

}
