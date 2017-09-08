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
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.talend.oozie.scheduler.i18n.TOozieTestMessages;

/**
 * @author Marvin Wang
 * @version 1.0 jdk1.6
 * @date May 24, 2012
 */
public class TOozieStringUtilsTest {

    /**
     * Test method for {@link org.talend.oozie.scheduler.utils.TOozieStringUtils#formatDateLog(java.lang.String)}.
     */
    @Test
    public void testFormatDateLog() {
        Assert.assertEquals("[null]", TOozieStringUtils.formatDateLog(null));
        Assert.assertEquals("[]", TOozieStringUtils.formatDateLog(""));
        Assert.assertEquals("[2012-5-24 11:39:22]", TOozieStringUtils.formatDateLog("2012-5-24 11:39:22"));
    }

    /**
     * Test method for
     * {@link org.talend.oozie.scheduler.utils.TOozieStringUtils#outputLogWithPrefixDate(java.lang.String)}.
     */
    @Test
    public void testOutputLogWithPrefixDate() {
        String str1 = TOozieStringUtils.outputLogWithPrefixDate(null);
        Assert.assertNotNull(str1);
        String dateStr = str1.substring(str1.indexOf("[") + 1, str1.indexOf("]"));
        Assert.assertTrue(TOozieTestMessages.getString("TOozieStringUtilsTest.dateStr_not_null"),
                dateStr != null && !"".equals(dateStr));

        SimpleDateFormat sdf = new SimpleDateFormat(TOozieDateUtilsTest.TALEND_DATE_PATTERN);
        try {
            Date actualDate = sdf.parse(dateStr);
            Assert.assertNotNull(actualDate);
        } catch (ParseException e) {
            Assert.fail(TOozieTestMessages.getString("TOozieStringUtilsTest.dateStr_incorrect", new Object[] { dateStr,
                    TOozieDateUtilsTest.TALEND_DATE_PATTERN }));
        }

    }

    /**
     * Test method for {@link org.talend.oozie.scheduler.utils.TOozieStringUtils#isValidPath(java.lang.String)}.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testIsValidPath() {
        TOozieStringUtils.isValidPath(null);
    }

}
