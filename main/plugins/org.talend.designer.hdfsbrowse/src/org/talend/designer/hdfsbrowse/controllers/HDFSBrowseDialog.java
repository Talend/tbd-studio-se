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
package org.talend.designer.hdfsbrowse.controllers;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.talend.designer.hdfsbrowse.i18n.Messages;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.designer.hdfsbrowse.ui.provider.FileSelectorTreeViewerProvider;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSBrowseDialog extends Dialog {

    private EHadoopFileTypes type;

    private HDFSConnectionBean connectionBean;

    private IHDFSNode result;

    private TreeViewer viewer;

    private Tree tree;

    private Button okBtn;

    public HDFSBrowseDialog(Shell parentShell, HDFSConnectionBean connectionBean) {
        this(parentShell, EHadoopFileTypes.FILE, connectionBean);
    }

    public HDFSBrowseDialog(Shell parentShell, EHadoopFileTypes type, HDFSConnectionBean connectionBean) {
        super(parentShell);
        setShellStyle(getShellStyle() | SWT.MAX | SWT.MIN | SWT.RESIZE | SWT.APPLICATION_MODAL);
        this.type = type;
        this.connectionBean = connectionBean;
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayoutFactory.swtDefaults().numColumns(2).margins(15, 15).spacing(10, 10).applyTo(composite);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);

        createDescField(composite);

        createHDFSTreeField(composite);

        addListener();

        return composite;
    }

    private void createDescField(Composite composite) {
        Label label = new Label(composite, SWT.NONE);
        label.setText(Messages.getString("HDFSBrowseDialog.desc", type.getValue().toLowerCase())); //$NON-NLS-1$
        GridDataFactory.swtDefaults().span(2, 1).applyTo(label);
    }

    private void createHDFSTreeField(Composite composite) {
        viewer = new TreeViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
        viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        viewer.setUseHashlookup(true);
        tree = viewer.getTree();
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);

        TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
        column1.setText(Messages.getString("HDFSBrowseDialog.column.name")); //$NON-NLS-1$
        column1.setWidth(300);
        column1.setResizable(true);

        TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
        column2.setText(Messages.getString("HDFSBrowseDialog.column.type")); //$NON-NLS-1$
        column2.setWidth(120);
        column2.setResizable(true);

        TreeColumn column3 = new TreeColumn(tree, SWT.LEFT);
        column3.setText(Messages.getString("HDFSBrowseDialog.column.size")); //$NON-NLS-1$
        column3.setWidth(150);
        column3.setResizable(true);

        FileSelectorTreeViewerProvider provider = new FileSelectorTreeViewerProvider();
        viewer.setContentProvider(provider);
        viewer.setLabelProvider(provider);
        viewer.setInput(connectionBean);
        viewer.expandToLevel(2);
    }

    private void addListener() {
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(SelectionChangedEvent event) {
                okBtn.setEnabled(isSelectionValid(event));
            }

        });
        viewer.addDoubleClickListener(new IDoubleClickListener() {

            public void doubleClick(DoubleClickEvent event) {
                if (okBtn.isEnabled()) {
                    okPressed();
                }
            }
        });
    }

    protected boolean isSelectionValid(SelectionChangedEvent event) {
        boolean isValid = true;
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        if (selection == null || selection.size() != 1) {
            isValid = false;
        } else {
            IHDFSNode node = (IHDFSNode) selection.getFirstElement();
            if (type == EHadoopFileTypes.RESOURCE) {
                isValid = true;
            } else if (node.getType() != type) {
                isValid = false;
            }
        }
        return isValid;
    }

    protected void okPressed() {
        IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
        result = (IHDFSNode) selection.getFirstElement();
        super.okPressed();
    }

    public IHDFSNode getResult() {
        return result;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(Messages.getString("HDFSBrowseDialog.title")); //$NON-NLS-1$ 
        newShell.setSize(650, 500);
    }

    protected void initializeBounds() {
        super.initializeBounds();

        Point size = getShell().getSize();
        Point location = getInitialLocation(size);
        getShell().setBounds(getConstrainedShellBounds(new Rectangle(location.x, location.y, size.x, size.y)));
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        okBtn = getButton(IDialogConstants.OK_ID);
        okBtn.setEnabled(false);
    }

}
