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

import org.talend.oozie.scheduler.constants.TOozieOutputMessages;

/**
 */
public class TOozieStringUtils {

    public static final String REGEX_PATH = "";

    /**
     * Formats the date string to the string that begins with "<code>[</code>" and ends with "<code>]</code>". The
     * string of returning is like <code>[2012-5-24 11:39:22]</code>. If <code>dateStr</code> is <code>null</code>, then
     * the returned string is <code>[null]</code>.
     * 
     * @param dateStr
     * @return a string.
     */
    public static String formatDateLog(String dateStr) {
        StringBuffer sb = new StringBuffer("");
        sb.append("[");
        sb.append(dateStr);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Outputs log as a line beginning with date like "[yyyy-MM-dd HH:mm:ss]Starting job remote running...". If
     * <code>log</code> is <code>null</code>, it will be converted to empty string.
     * 
     * @param log
     * @return a string
     */
    public static String outputLogWithPrefixDate(String log) {
        StringBuffer sb = new StringBuffer("");
        String currentDate = TOozieDateUtils.fetchCurrentDate();
        sb.append(formatDateLog(currentDate));
        sb.append(log == null ? "" : log);
        sb.append(TOozieOutputMessages.MSG_OUTPUT_LINE_BREAK_CHAR);
        return sb.toString();
    }

    /**
     * 
     * @param path
     * @return
     */
    @Deprecated
    public static boolean isValidPath(String path) {
        boolean isValid = false;

        return isValid;
    }

}
