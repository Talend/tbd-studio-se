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
package org.talend.oozie.scheduler.jobsubmission;

import java.util.Date;

import org.talend.oozie.scheduler.jobsubmission.model.JobContext.Timeunit;
import org.talend.oozie.scheduler.jobsubmission.model.Utils;

public class DataSet { // aka Feed or Table

    private final String name;

    private final int frequency;

    private final Timeunit timeUnit;

    private final String timeZone;

    private final String uriTemplate;

    private final Date initialInstance;

    public DataSet(String name, int frequency, String uriTemplate, Date initialInstance) {
        this(name, frequency, Timeunit.MINUTE, "UTC", uriTemplate, initialInstance);
    }

    public DataSet(String name, int frequency, Timeunit timeUnit, String timeZone, String uriTemplate, Date initialInstance) {
        this.name = name;
        this.frequency = frequency;
        this.timeUnit = timeUnit;
        this.timeZone = timeZone;
        this.uriTemplate = uriTemplate;
        this.initialInstance = initialInstance;
    }

    public String getName() {
        return name;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Timeunit getTimeUnit() {
        return timeUnit;
    }

    public String getUriTemplate() {
        return uriTemplate;
    }

    public Date getInitialInstance() {
        return initialInstance;
    }

    @Override
    public String toString() {
        return "DataSet{" + "name='" + name + '\'' + ", frequency=" + frequency + ", timeUnit=" + timeUnit + ", timeZone='"
                + timeZone + '\'' + ", uriTemplate='" + uriTemplate + '\'' + ", initialInstance=" + initialInstance + '}';
    }

    public String toXMLString() {
        StringBuilder dataSetXML = new StringBuilder(8192);
        dataSetXML.append("  <datasets>");
        dataSetXML.append("    <dataset name=\"").append(name).append("\"");
        dataSetXML.append(" frequency=\"${coord:hours(").append(frequency).append(")}\"");
        dataSetXML.append(" timezone=\"").append(timeZone).append("\"");
        dataSetXML.append(" initial-instance=\"").append(Utils.formatDateUTC(initialInstance)).append("\">");
        dataSetXML.append("      <uri-template>").append(uriTemplate).append("</uri-template>");
        dataSetXML.append("    </dataset>");
        dataSetXML.append("  </datasets>");

        return dataSetXML.toString();
    }
}
