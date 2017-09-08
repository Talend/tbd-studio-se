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
package org.talend.designer.neo4j.ui.dialogs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.talend.core.CorePlugin;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IConnection;
import org.talend.designer.neo4j.Neo4jMain;
import org.talend.designer.neo4j.managers.UIManager;

public class Neo4jDialog extends Dialog {

    private String title;

    private Rectangle size;

    private Image icon;

    private boolean maximized;

    private Neo4jMain neo4jMain;

    private IMetadataTable metadataTable;

    private Set<String> preOutputColumnSet = new HashSet<String>();

    private Map<String, String> changedNameColumns = new HashMap<String, String>();

    public Neo4jDialog(Shell parentShell, Neo4jMain neo4jMain) {
        super(parentShell);
        this.neo4jMain = neo4jMain;
        metadataTable = neo4jMain.getNeo4jManager().getNeo4jComponent().getMetadataList().get(0);

        List<IMetadataColumn> listColumn = metadataTable.getListColumns();
        for (IMetadataColumn preColumn : listColumn) {
            preOutputColumnSet.add(preColumn.getLabel());
        }
        setShellStyle(getShellStyle() | SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX | SWT.APPLICATION_MODAL | SWT.RESIZE);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite panel = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        panel.setLayout(layout);
        panel.setLayoutData(new GridData(GridData.FILL_BOTH));
        applyDialogFont(panel);

        neo4jMain.createUI(panel, true);

        return panel;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);

        newShell.setText(title);
        newShell.setImage(icon);

        if (maximized) {
            newShell.setMaximized(true);
        } else {
            newShell.setBounds(size);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(Rectangle size) {
        this.size = size;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }

    @Override
    protected void okPressed() {
        UIManager uiManager = neo4jMain.getNeo4jManager().getUiManager();
        uiManager.closeNeo4j(SWT.OK, true);
        neo4jMain.buildExternalData();
        super.okPressed();

        String componentName = "";
        IComponent iComponent = neo4jMain.getNeo4jManager().getNeo4jComponent().getComponent();
        if (iComponent != null && iComponent instanceof IComponent) {
            componentName = iComponent.getName();
        }

        if (!"tNeo4jOutput".equals(componentName)) {
            List<? extends IConnection> connection = neo4jMain.getNeo4jManager().getNeo4jComponent().getOutgoingConnections();
            IConnection curConnection = null;
            for (IConnection conn : connection) {
                IMetadataTable metadataTable = conn.getMetadataTable();
                if (metadataTable != null) {
                    String tabName = metadataTable.getTableName();
                    if (tabName.equals(metadataTable.getTableName())) {
                        curConnection = conn;
                    }
                }
            }
            if (curConnection != null) {
                Set<String> addedColumns = new HashSet<String>();
                changedNameColumns = neo4jMain.getNeo4jUI().getChangedNameColumns();
                for (String changedColName : changedNameColumns.keySet()) {
                    String columnName = changedNameColumns.get(changedColName);
                    if (preOutputColumnSet.contains(columnName)) {
                        preOutputColumnSet.remove(columnName);
                        preOutputColumnSet.add(changedColName);
                    }
                }
                for (IMetadataColumn curColumn : metadataTable.getListColumns()) {
                    if (!preOutputColumnSet.contains(curColumn.getLabel())) {
                        addedColumns.add(curColumn.getLabel());
                    }
                }
                CorePlugin.getDefault().getDesignerCoreService()
                        .updateTraceColumnValues(curConnection, changedNameColumns, addedColumns);
            }
        }
    }

    @Override
    protected void cancelPressed() {
        UIManager uiManager = neo4jMain.getNeo4jManager().getUiManager();
        uiManager.closeNeo4j(SWT.CANCEL, true);
        neo4jMain.buildExternalData();
        super.cancelPressed();

    }
}
