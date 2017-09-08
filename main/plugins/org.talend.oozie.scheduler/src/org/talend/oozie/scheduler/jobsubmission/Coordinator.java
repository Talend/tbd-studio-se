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

public class Coordinator {

    private final String name;

    private final DataSet dataSet;

    private final String path;

    private final Date startTime;

    private final Date endTime;

    private final int frequency;

    private Timeunit timeUnit = Timeunit.MINUTE;

    private final String timeZone;

    private final int concurrency;

    public Coordinator(String name, String path, Date startTime, Date endTime, int frequency, Timeunit timeUnit) {
        this(name, null, path, startTime, endTime, frequency, timeUnit, "UTC", 1);
    }

    public Coordinator(String name, DataSet dataSet, String path, Date startTime, Date endTime, int frequency, Timeunit timeUnit,
            String timeZone, int concurrency) {
        this.name = name;
        this.dataSet = dataSet;
        this.path = path;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
        this.timeUnit = timeUnit;
        this.timeZone = timeZone;
        this.concurrency = concurrency;
    }

    public String getName() {
        return name;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public String getPath() {
        return path;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getFrequency() {
        return frequency;
    }

    public Timeunit getTimeUnit() {
        return timeUnit;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public int getConcurrency() {
        return concurrency;
    }

    @Override
    public String toString() {
        return "Coordinator{" + "name='" + name + '\'' + ", dataSet=" + dataSet + ", path='" + path + '\'' + ", startTime="
                + startTime + ", endTime=" + endTime + ", frequency=" + frequency + ", timeUnit=" + timeUnit + ", timeZone='"
                + timeZone + '\'' + ", concurrency=" + concurrency + '}';
    }

    public String toXMLString() {
        StringBuilder coordXML = new StringBuilder(8192);
        coordXML.append("<coordinator-app xmlns=\"uri:oozie:coordinator:0.2\" name=\"").append(name)
                .append("\" ")
                // .append(" frequency=\"${coord:hours(").append(frequency).append(")}\"")
                .append(getCoordinatorFrequency(frequency, timeUnit)).append(" start=\"").append(Utils.formatDateUTC(startTime))
                .append("\"")
                // yyMMddHHmmssZ = "yyyy-MM-dd'T'HH:mm'Z'"
                .append(" end=\"").append(Utils.formatDateUTC(endTime)).append("\"").append(" timezone=\"").append(timeZone)
                .append("\">");

        coordXML.append("<controls><concurrency>").append(concurrency).append("</concurrency></controls>");

        if (dataSet != null) {
            coordXML.append(dataSet.toXMLString());
            coordXML.append("<output-events>");
            coordXML.append("<data-out name=\"output\" dataset=\"").append(dataSet.getName()).append("\">");
            coordXML.append("<instance>${coord:current(0)}</instance>");
            coordXML.append("</data-out>");
            coordXML.append("</output-events>");
        }

        coordXML.append("<action>");
        coordXML.append("<workflow>");
        coordXML.append("<app-path>").append(path).append("</app-path>");

        // configure the workflow output to be the dataset output
        if (dataSet != null) {
            coordXML.append("<configuration>");
            coordXML.append(" <property>");
            coordXML.append("  <name>wfOutput</name>");
            coordXML.append("  <value>${coord:dataOut('output')}</value>");
            coordXML.append(" </property>");
            coordXML.append("</configuration>");
        }
        coordXML.append("</workflow>");
        coordXML.append("</action>");
        coordXML.append("</coordinator-app>");

        return coordXML.toString();
    }

    protected String getCoordinatorFrequency(int frequency, Timeunit timeunit) {
        StringBuilder coordinatorFrequency = new StringBuilder(1024);
        coordinatorFrequency.append(" frequency=\"${");
        switch (timeunit.ordinal()) {
        case 0: // Timeunit.Minute
            coordinatorFrequency.append("coord:minutes");
            break;

        case 1: // Timeunit.HOUR
            coordinatorFrequency.append("coord:hours");
            break;

        case 2: // Timeunit.DAY
            coordinatorFrequency.append("coord:days");
            break;

        case 3: // Timeunit.WEEK
            coordinatorFrequency.append("coord:days");
            frequency = frequency * 7;
            break;

        case 4: // Timeunit.MONTH
            coordinatorFrequency.append("coord:months");
            break;

        case 5: // Timeunit.END_OF_DAY
            coordinatorFrequency.append("coord:endOfDays");
            break;

        case 6: // Timeunit.END_OF_MONTH
            coordinatorFrequency.append("coord:endOfMonths");
            break;

        default: // case 7: Timeunit.NONE
            coordinatorFrequency.append("coord:none");
            break;
        }
        coordinatorFrequency.append("(").append(frequency).append(")}\"");

        return coordinatorFrequency.toString();
    }
}
