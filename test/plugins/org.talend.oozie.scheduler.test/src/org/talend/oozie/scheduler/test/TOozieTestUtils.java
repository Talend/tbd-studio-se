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
package org.talend.oozie.scheduler.test;

/**
 * Created by Marvin Wang on May 23, 2012.
 */
public class TOozieTestUtils {

    public static final String DASH_STR = "-";

    public static final String BLANK_STR = " ";

    public static final String COLON_STR = ":";

    public static String makeupDateString(int year, int month, int dayofMonth, int hourofDay, int minute, int second) {
        StringBuffer dateStr = new StringBuffer();
        dateStr.append(year);
        dateStr.append(DASH_STR);
        if (month < 10 && month > 0)
            dateStr.append("0");
        dateStr.append(month);
        dateStr.append(DASH_STR);
        if (dayofMonth < 10 && dayofMonth > 0)
            dateStr.append("0");
        dateStr.append(dayofMonth);

        dateStr.append(BLANK_STR);
        if (hourofDay < 10 && hourofDay >= 0)
            dateStr.append("0");
        dateStr.append(hourofDay);
        dateStr.append(COLON_STR);
        if (minute < 10 && minute >= 0)
            dateStr.append("0");
        dateStr.append(minute);
        dateStr.append(COLON_STR);
        if (second < 10 && second >= 0)
            dateStr.append("0");
        dateStr.append(second);

        return dateStr.toString();
    }
}
