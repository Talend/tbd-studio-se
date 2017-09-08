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
package org.talend.designer.neo4j.ui.footer;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.talend.designer.neo4j.managers.Neo4jManager;
import org.talend.designer.neo4j.managers.UIManager;

public class FooterComposite extends Composite {

    private final Neo4jManager neo4jManager;

    public FooterComposite(Composite parent, int style, Neo4jManager neo4jManager) {
        super(parent, style);
        this.neo4jManager = neo4jManager;
        createComponents();
    }

    private void createComponents() {
        final UIManager uiManager = neo4jManager.getUiManager();

        GridData footerCompositeGridData = new GridData(GridData.FILL_HORIZONTAL);
        setLayoutData(footerCompositeGridData);

        FormLayout formLayout = new FormLayout();
        setLayout(formLayout);

        Button okButton = new Button(this, SWT.NONE);
        okButton.setText("Ok"); // Internationalize this
        FormData okFormData = new FormData();
        Point minSize = okButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        okFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);
        okButton.setLayoutData(okFormData);
        okButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
                uiManager.closeNeo4j(SWT.OK, false);
            }
        });

        Button cancelButton = new Button(this, SWT.NONE);
        cancelButton.setText("Cancel"); // Internationalize this
        FormData cancelFormData = new FormData();
        cancelFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);
        cancelButton.setLayoutData(cancelFormData);
        cancelButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
                uiManager.closeNeo4j(SWT.CANCEL, false);
            }
        });

        cancelFormData.right = new FormAttachment(100, -5);
        okFormData.right = new FormAttachment(cancelButton, -5);
    }
}
