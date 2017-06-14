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
package org.talend.designer.neo4j;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.ui.runtime.image.ImageUtils.ICON_SIZE;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.components.IODataComponentContainer;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.core.ui.branding.IBrandingService;
import org.talend.core.ui.images.CoreImageProvider;
import org.talend.designer.neo4j.external.data.ExternalNeo4jData;
import org.talend.designer.neo4j.external.data.ExternalNeo4jTable;
import org.talend.designer.neo4j.external.data.ExternalNeo4jUIProperties;
import org.talend.designer.neo4j.external.data.IOConnection;
import org.talend.designer.neo4j.managers.Neo4jManager;
import org.talend.designer.neo4j.ui.Neo4jUI;
import org.talend.designer.neo4j.ui.dialogs.Neo4jDialog;

public class Neo4jMain {

    private Neo4jComponent connector;

    private Neo4jManager neo4jManager;

    private Neo4jUI neo4jUI;

    private List<ExternalNeo4jTable> outputTables;

    public Neo4jMain(Neo4jComponent connector) {
        this.connector = connector;
        this.neo4jManager = new Neo4jManager(connector);
    }

    public void createUI(Composite parent, boolean fromDialog) {
        neo4jUI = new Neo4jUI(parent, neo4jManager);
        neo4jUI.init(fromDialog);
    }

    public int getMapperDialogResponse() {
        return neo4jManager.getUiManager().getNeo4jResponse();
    }

    public Shell createUI(Display display) {
        Shell shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.BORDER | SWT.RESIZE | SWT.CLOSE | SWT.MIN | SWT.MAX
                | SWT.TITLE);
        IComponent component = connector.getComponent();
        shell.setImage(CoreImageProvider.getComponentIcon(component, ICON_SIZE.ICON_32));

        IBrandingService brandingService = (IBrandingService) GlobalServiceRegister.getDefault().getService(
                IBrandingService.class);
        String productName = brandingService.getFullProductName();

        shell.setText("Neo4j"); //$NON-NLS-1$

        Rectangle boundsRG = ExternalNeo4jUIProperties.getBoundsNeo4j();
        if (ExternalNeo4jUIProperties.isShellMaximized()) {
            shell.setMaximized(ExternalNeo4jUIProperties.isShellMaximized());
        } else {
            boundsRG = ExternalNeo4jUIProperties.getBoundsNeo4j();
            if (boundsRG.x < 0) {
                boundsRG.x = 0;
            }
            if (boundsRG.y < 0) {
                boundsRG.y = 0;
            }
            shell.setBounds(boundsRG);
        }

        createUI(shell, false);
        shell.open();
        return shell;
    }

    public Dialog createNeo4jDialog(Shell parentShell) {
        Neo4jDialog dialog = new Neo4jDialog(parentShell, this);
        IComponent component = connector.getComponent();

        IBrandingService brandingService = (IBrandingService) GlobalServiceRegister.getDefault().getService(
                IBrandingService.class);
        String productName = brandingService.getFullProductName();

        Rectangle boundsRG = ExternalNeo4jUIProperties.getBoundsNeo4j();
        if (ExternalNeo4jUIProperties.isShellMaximized()) {
            dialog.setMaximized(ExternalNeo4jUIProperties.isShellMaximized());
        } else {
            boundsRG = ExternalNeo4jUIProperties.getBoundsNeo4j();
            if (boundsRG.x < 0) {
                boundsRG.x = 0;
            }
            if (boundsRG.y < 0) {
                boundsRG.y = 0;
            }
            dialog.setSize(boundsRG);
        }
        dialog.setIcon(CoreImageProvider.getComponentIcon(component, ICON_SIZE.ICON_32));

        dialog.setTitle("Neo4J Mapping"); //$NON-NLS-1$

        return dialog;
    }

    public static boolean isStandAloneMode() {
        return false;
    }

    public ExternalNeo4jData buildExternalData() {
        ExternalNeo4jData externalData = new ExternalNeo4jData();
        outputTables = new ArrayList<ExternalNeo4jTable>();
        externalData.setOutputTables(outputTables);
        externalData.setUiProperties(neo4jManager.getUiManager().getUIProperties());
        return externalData;
    }

    public void createModelFromExternalData(List<? extends IConnection> incomingConnections,
            List<? extends IConnection> outgoingConnections, ExternalNeo4jData externalData,
            List<IMetadataTable> outputMetadataTables, boolean checkProblems) {
        List<IOConnection> inputs = createIOConnections(incomingConnections);
        List<IOConnection> outputs = createIOConnections(outgoingConnections);
        createModelFromExternalData(inputs, outputs, outputMetadataTables, externalData, checkProblems);
    }

    private List<IOConnection> createIOConnections(List<? extends IConnection> connections) {
        List<IOConnection> ioConnections = new ArrayList<IOConnection>(connections.size());
        for (IConnection connection : connections) {
            ioConnections.add(new IOConnection(connection));
        }
        return ioConnections;
    }

    public void createModelFromExternalData(IODataComponentContainer dataComponents, List<IMetadataTable> metadataList,
            ExternalNeo4jData externalData, boolean checkProblems) {
        List<IODataComponent> inputsData = dataComponents.getInputs();
        List<IODataComponent> outputsData = dataComponents.getOuputs();

        List<IOConnection> inputs = new ArrayList<IOConnection>(inputsData.size());
        for (IODataComponent iData : inputsData) {
            inputs.add(new IOConnection(iData));
        }
        List<IOConnection> outputs = new ArrayList<IOConnection>(outputsData.size());
        for (IODataComponent oData : outputsData) {
            outputs.add(new IOConnection(oData));
        }
        createModelFromExternalData(inputs, outputs, metadataList, externalData, false);
    }

    private void createModelFromExternalData(List<IOConnection> inputs, List<IOConnection> outputs,
            List<IMetadataTable> outputMetadataTables, ExternalNeo4jData externalData, boolean checkProblems) {
        if (externalData == null) {
            externalData = new ExternalNeo4jData();
        }
        neo4jManager.getUiManager().setUIProperties(externalData.getUiProperties());
    }

    public Neo4jManager getNeo4jManager() {
        return neo4jManager;
    }

    public Neo4jUI getNeo4jUI() {
        return neo4jUI;
    }

}
