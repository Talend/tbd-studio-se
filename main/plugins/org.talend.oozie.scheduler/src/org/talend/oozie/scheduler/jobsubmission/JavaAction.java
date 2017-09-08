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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaAction {

    private final String name;

    private final String jobTrackerURI;

    private final String nameNodeURI;

    private final String clazzFQN;

    private boolean captureOutput;

    private List<String> arguments;

    public JavaAction(String name, String jobTrackerURI, String nameNodeURI, String clazzFQN) {
        this.name = name;
        this.jobTrackerURI = jobTrackerURI;
        this.nameNodeURI = nameNodeURI;
        this.clazzFQN = clazzFQN;
        arguments = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public String getJobTrackerURI() {
        return jobTrackerURI;
    }

    public String getNameNodeURI() {
        return nameNodeURI;
    }

    public String getClazzFQN() {
        return clazzFQN;
    }

    public boolean isCaptureOutput() {
        return captureOutput;
    }

    public void setCaptureOutput(boolean captureOutput) {
        this.captureOutput = captureOutput;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void addArgument(String argument) {
        this.arguments.add(argument);
    }

    @Override
    public String toString() {
        return "JavaAction{" + "name='" + name + '\'' + ", jobTrackerURI='" + jobTrackerURI + '\'' + ", nameNodeURI='"
                + nameNodeURI + '\'' + ", clazzFQN='" + clazzFQN + '\'' + ", captureOutput=" + captureOutput + ", arguments="
                + (arguments == null ? null : Arrays.asList(arguments)) + '}';
    }

    public String toXMLString() {
        StringBuilder actionXML = new StringBuilder(8192);
        actionXML.append("<action name=\"").append(name).append("\">");

        actionXML.append("<java>");
        actionXML.append("<job-tracker>").append(jobTrackerURI).append("</job-tracker>");
        actionXML.append("<name-node>").append(nameNodeURI).append("</name-node>");

        actionXML.append("<main-class>").append(clazzFQN).append("</main-class>");
        for (String argument : arguments) {
            actionXML.append("<arg>").append(argument).append("</arg>");
        }

        if (captureOutput)
            actionXML.append("<capture-output/>");
        actionXML.append("</java>");

        actionXML.append("<ok to=\"end\"/>");
        actionXML.append("<error to=\"fail\"/>");

        actionXML.append("</action>");
        return actionXML.toString();
    }
}
