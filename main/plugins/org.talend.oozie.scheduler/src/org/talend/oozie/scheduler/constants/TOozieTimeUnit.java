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

import org.talend.oozie.scheduler.jobsubmission.model.JobContext.Timeunit;

/**
 * Created by Marvin Wang on May 6, 2012. The enum provides the time unit for Oozie Coordinator. All the time units are
 * mapping to {@link Timeunit} as follows:
 * 
 * <pre>
 * <li>{@link TOozieTimeUnit#MINUTE}
 * <li>{@link TOozieTimeUnit#HOUR}
 * <li>{@link TOozieTimeUnit#DAY}
 * <li>{@link TOozieTimeUnit#WEEK}
 * <li>{@link TOozieTimeUnit#MONTH}
 * <li>{@link TOozieTimeUnit#END_OF_DAY}
 * <li>{@link TOozieTimeUnit#END_OF_MONTH}
 * <li>{@link TOozieTimeUnit#NONE}
 */
public enum TOozieTimeUnit {

    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_MINUTE} and time unit is
     * <code>Timeunit.MINUTE</code>.
     */
    MINUTE(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_MINUTE, Timeunit.MINUTE),
    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_HOUR} and time unit is
     * <code>Timeunit.HOUR</code>.
     */
    HOUR(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_HOUR, Timeunit.HOUR),
    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_DAY} and time unit is
     * <code>Timeunit.DAY</code>.
     */
    DAY(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_DAY, Timeunit.DAY),
    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_WEEK} and time unit is
     * <code>Timeunit.WEEK</code>.
     */
    WEEK(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_WEEK, Timeunit.WEEK),
    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_MONTH} and time unit is
     * <code>Timeunit.MONTH</code>.
     */
    MONTH(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_MONTH, Timeunit.MONTH),
    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_DAY} and time unit is
     * <code>Timeunit.END_OF_DAY</code>.
     */
    END_OF_DAY(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_DAY, Timeunit.END_OF_DAY),
    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_MONTH} and time unit is
     * <code>Timeunit.END_OF_MONTH</code>.
     */
    END_OF_MONTH(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_MONTH, Timeunit.END_OF_MONTH),
    /**
     * Display name refers to {@link TOozieCommonConstants#OOZIE_TIMEUNIT_DISPLAY_NAME_NONE} and time unit is
     * <code>Timeunit.NONE</code>.
     */
    NONE(TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_NONE, Timeunit.NONE);

    private String displayName;

    private Timeunit timeUnit;

    private TOozieTimeUnit(String displayName, Timeunit timeUnit) {
        this.displayName = displayName;
        this.timeUnit = timeUnit;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Timeunit getTimeUnit() {
        return this.timeUnit;
    }

}
