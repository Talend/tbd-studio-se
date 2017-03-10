// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.configurator;

import org.talend.core.model.process.AbstractNode;
import org.talend.core.model.process.EComponentCategory;
import org.talend.core.model.process.EParameterFieldType;
import org.talend.core.model.process.IContextManager;
import org.talend.core.model.properties.Property;
import org.talend.designer.core.model.components.EParameterName;
import org.talend.designer.core.model.components.ElementParameter;

public class RetrieveConfigurationProcess extends org.talend.designer.core.ui.editor.process.Process {

    protected IContextManager contextManager;


    protected AbstractNode node;
    
    public RetrieveConfigurationProcess(Property property, AbstractNode node, IContextManager manager) {
        super(property);
        initNodesAndConnections(node, manager);
        createProcessParameters();
    }

    protected void initNodesAndConnections(AbstractNode inputNode, IContextManager manager) {
        this.node = inputNode;
        if (manager == null) {
            contextManager = new RetrieveConfigurationContextManager();
        } else {
            contextManager = manager;
        }

        nodes.add(inputNode);

        inputNode.setProcess(this);
    }

    /**
     * Add all parameters for a process.
     */
    protected void createProcessParameters() {
        // for the log4j,no need to do the data viewer,so disable it.
        ElementParameter param = new ElementParameter(this);
        param.setName(EParameterName.LOG4J_ACTIVATE.getName());
        param.setCategory(EComponentCategory.TECHNICAL);
        param.setFieldType(EParameterFieldType.CHECK);
        param.setDisplayName(EParameterName.LOG4J_ACTIVATE.getDisplayName());
        param.setNumRow(99);
        param.setShow(false);
        param.setValue("false");
        param.setReadOnly(true);
        addElementParameter(param);
    } 
}
