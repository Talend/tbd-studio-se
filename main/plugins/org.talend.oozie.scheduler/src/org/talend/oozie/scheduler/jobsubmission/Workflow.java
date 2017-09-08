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

public class Workflow {

    private String name;

    private JavaAction action;

    public Workflow(String name, JavaAction action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public JavaAction getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "Workflow{" + "name='" + name + '\'' + ", action=" + action + '}';
    }

    public String toXMLString() {
        StringBuilder workflowXML = new StringBuilder(8192);
        workflowXML.append("<workflow-app xmlns=\"uri:oozie:workflow:0.1\" name=\"").append(name).append("\">");
        workflowXML.append("<start to=\"").append(action.getName()).append("\"/>");

        workflowXML.append(action.toXMLString());

        workflowXML
                .append("<kill name=\"fail\"><message>Job failed, error message[${wf:errorMessage(wf:lastErrorNode())}]</message></kill>");

        workflowXML.append("<end name=\"end\"/>");
        workflowXML.append("</workflow-app>");

        return workflowXML.toString();
    }
}
