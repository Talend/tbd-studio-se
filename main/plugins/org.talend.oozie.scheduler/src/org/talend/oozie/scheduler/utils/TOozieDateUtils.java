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

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 */
public class TOozieDateUtils {

    public static final String TALEND_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Converts the given date to the specified string pattern "yyyy-MM-dd HH:mm:ss";
     * 
     * @param date
     * @return
     */
    public static String convertDateToString(Date date) {
        String result = convertDateToString(date, TALEND_DATE_PATTERN);
        return result;
    }

    public static Date converStringToDate(String dateStr) throws ParseException {
        return ConvertStringToDate(dateStr, TALEND_DATE_PATTERN);
    }

    public static Date ConvertStringToDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateStr);
    }

    public static String convertDateToString(Date date, String pattern) {
        StringBuffer result = new StringBuffer();

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.format(date, result, new FieldPosition(0));
        return result.toString();
    }

    /**
     * Fetches the current date as format "yyyy-MM-dd HH:mm:ss".
     * 
     * @return
     */
    public static String fetchCurrentDate() {
        Date currDate = Calendar.getInstance().getTime();

        return convertDateToString(currDate);
    }
}
