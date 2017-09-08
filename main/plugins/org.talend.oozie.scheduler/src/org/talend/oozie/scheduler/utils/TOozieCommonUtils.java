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
import java.util.regex.Pattern;

import org.talend.oozie.scheduler.constants.OozieJobProcessStatus;
import org.talend.oozie.scheduler.constants.TOozieCommonConstants;
import org.talend.oozie.scheduler.constants.TOozieTimeUnit;
import org.talend.oozie.scheduler.jobsubmission.model.JobContext.Timeunit;
import org.talend.oozie.scheduler.jobsubmission.model.JobSubmission;

/**
 * Created by Marvin Wang on Apr. 24, 2012
 */
public class TOozieCommonUtils {

    /**
     * If the current OS is Windows, return <code>true</code>, otherwise return <code>false</code>
     * 
     * @return
     */
    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.trim().startsWith(TOozieCommonConstants.OS_WINDOWS_PREFIX))
            isWindowsOS = true;
        return isWindowsOS;
    }

    public static boolean isValidURL(String URL) {
        boolean isValid = false;
        Pattern.matches("^(http|ftp|file)://.* ", URL);

        return isValid;
    }

    public static OozieJobProcessStatus convertToOozieJobProcessStatus(JobSubmission.Status status) {
        switch (status) {
        case PREP:
            return OozieJobProcessStatus.PREP;
        case RUNNING:
            return OozieJobProcessStatus.RUNNING;
        case SUCCEEDED:
            return OozieJobProcessStatus.SUCCEEDED;
        case KILLED:
            return OozieJobProcessStatus.KILLED;
        case FAILED:
            return OozieJobProcessStatus.FAILED;
        case SUSPENDED:
            return OozieJobProcessStatus.SUSPENDED;
        default:
            return OozieJobProcessStatus.INIT;
        }
    }

    /**
     * Gets the all time units to display. Refer to the enum {@link TOozieTimeUnit}.
     * 
     * @return
     */
    public static String[] getTimeUnitItems() {
        TOozieTimeUnit[] timeUnits = TOozieTimeUnit.values();

        List<String> displayNames = new ArrayList<String>();
        for (int i = 0; i < timeUnits.length; i++) {
            displayNames.add(timeUnits[i].getDisplayName());
        }

        return displayNames.toArray(new String[displayNames.size()]);
    }

    /**
     * Looks up the Time Unit by index. Refer to the {@link TOozieTimeUnit}.
     * 
     * @param index
     * @return
     */
    public static Timeunit lookupTimeUnit(int index) {
        switch (index) {
        case 0:
            return TOozieTimeUnit.MINUTE.getTimeUnit();
        case 1:
            return TOozieTimeUnit.HOUR.getTimeUnit();
        case 2:
            return TOozieTimeUnit.DAY.getTimeUnit();
        case 3:
            return TOozieTimeUnit.WEEK.getTimeUnit();
        case 4:
            return TOozieTimeUnit.MONTH.getTimeUnit();
        case 5:
            return TOozieTimeUnit.END_OF_DAY.getTimeUnit();
        case 6:
            return TOozieTimeUnit.END_OF_MONTH.getTimeUnit();
        case 7:
            return TOozieTimeUnit.NONE.getTimeUnit();
        default:
            return TOozieTimeUnit.NONE.getTimeUnit();
        }
    }

    public static void main(String[] args) {
        String[] testNames = getTimeUnitItems();
        for (int i = 0; i < testNames.length; i++) {
            System.out.println(testNames[i]);
        }
    }
}
