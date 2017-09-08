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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private Utils() {
    }

    public static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertNotEmpty(String value, String message) {
        if (value == null || "".equals(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static String formatDateUTC(Date d) {
        return (d != null) ? getISO8601DateFormat().format(d) : "NULL";
    }

    public static DateFormat getISO8601DateFormat() {
        DateFormat dateFormat = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        dateFormat.setTimeZone(UTC);
        return dateFormat;
    }
}
