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
package org.talend.repository.hadoopcluster.ui.conf;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.core.model.context.ContextUtils;
import org.talend.core.model.properties.ContextItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 *
 * created by ycbai on 2017年2月9日 Detailled comment
 *
 */
public class HadoopContextConfConfigDialog extends TitleAreaDialog {

    private Map<String, String> context2Jar;

    private AbstractHadoopForm parentForm;

    private HadoopClusterConnectionItem connectionItem;

    private TableViewer viewer;

    private Table table;

    public HadoopContextConfConfigDialog(Shell parentShell, AbstractHadoopForm parentForm,
            HadoopClusterConnectionItem connectionItem) {
        super(parentShell);
        context2Jar = new HashMap<>();
        this.parentForm = parentForm;
        this.connectionItem = connectionItem;
    }

    @Override
    public void create() {
        super.create();
        setTitle(Messages.getString("HadoopContextConfConfigDialog.title")); //$NON-NLS-1$
        setMessage(Messages.getString("HadoopContextConfConfigDialog.msg")); //$NON-NLS-1$
        setTitleImage(ImageProvider.getImage(EImage.HADOOP_WIZ_ICON));
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(Messages.getString("HadoopContextConfConfigDialog.topTitle")); //$NON-NLS-1$
        newShell.setSize(650, 600);
        setHelpAvailable(false);
    }

    @Override
    protected void initializeBounds() {
        super.initializeBounds();
        Point size = getShell().getSize();
        Point location = getInitialLocation(size);
        getShell().setBounds(getConstrainedShellBounds(new Rectangle(location.x, location.y, size.x, size.y)));
    }

    @Override
    public int open() {
        HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
        if (connection.isContextMode()) {
            EMap<String, byte[]> confFiles = connection.getConfFiles();
            ContextItem contextItem = ContextUtils.getContextItemById2(connection.getContextId());
            if (contextItem != null) {
                EList<ContextType> contexts = contextItem.getContext();
                for (ContextType contextType : contexts) {
                    String contextName = contextType.getName();
                    String jarName = HadoopConfsUtils.getConfsJarDefaultName(connectionItem, false, contextName);
                    byte[] bs = confFiles.get(contextName);
                    if (bs == null) {
                        jarName = Messages.getString("HadoopContextConfConfigDialog.prompt.importJar"); //$NON-NLS-1$
                    }
                    context2Jar.put(contextName, jarName);
                }
                return super.open();
            }
        } else {
            HadoopConfsUtils.openHadoopConfsWizard(parentForm, connectionItem, false);
        }
        return IDialogConstants.OK_ID;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        Composite comp = new Composite(composite, SWT.NONE);
        comp.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();
        layout.marginHeight = 10;
        layout.marginWidth = 10;
        comp.setLayout(layout);

        createTable(comp);

        return parent;
    }

    private Composite createTable(Composite parent) {
        Composite tableComposite = new Composite(parent, SWT.NONE);
        tableComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        TableColumnLayout tableColumnLayout = new TableColumnLayout();
        tableComposite.setLayout(tableColumnLayout);

        viewer = new TableViewer(tableComposite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
        viewer.setContentProvider(new ViewerContentProvider());
        table = viewer.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableViewerColumn contextColumn = createTableViewerColumn(
                Messages.getString("HadoopContextConfConfigDialog.table.contextColumn"), 0); //$NON-NLS-1$
        contextColumn.setLabelProvider(new ColumnLabelProvider() {

            @Override
            public String getText(Object element) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) element;
                return entry.getKey();
            }
        });
        tableColumnLayout.setColumnData(contextColumn.getColumn(), new ColumnWeightData(35, 80, true));
        TableViewerColumn jarNameColumn = createTableViewerColumn(
                Messages.getString("HadoopContextConfConfigDialog.table.jarNameColumn"), 1); //$NON-NLS-1$
        jarNameColumn.setEditingSupport(new JarNameEditingSupport(viewer));
        jarNameColumn.setLabelProvider(new ColumnLabelProvider() {

            @Override
            public String getText(Object element) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) element;
                return entry.getValue();
            }
        });
        tableColumnLayout.setColumnData(jarNameColumn.getColumn(), new ColumnWeightData(65, 120, true));

        viewer.setInput(context2Jar);

        return tableComposite;
    }

    private TableViewerColumn createTableViewerColumn(String title, final int colNumber) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setResizable(true);
        column.setMoveable(true);
        return viewerColumn;
    }

    @Override
    protected boolean isResizable() {
        return true;
    }

    class ViewerContentProvider extends ArrayContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Map) {
                return ((Map) inputElement).entrySet().toArray();
            }
            return super.getElements(inputElement);
        }

    }

    class JarNameDialogCellEditor extends DialogCellEditor {

        public JarNameDialogCellEditor(Composite composite) {
            super(composite);
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            String contextGroup = null;
            IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
            Object obj = selection.getFirstElement();
            if (obj instanceof Map.Entry) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) obj;
                contextGroup = entry.getKey();
            }
            String confJarName = HadoopConfsUtils.openHadoopConfsWizard(parentForm, connectionItem, contextGroup, false);
            return confJarName;
        }

    }

    class JarNameEditingSupport extends EditingSupport {

        private TableViewer tv;

        public JarNameEditingSupport(TableViewer viewer) {
            super(viewer);
            tv = viewer;
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return new JarNameDialogCellEditor(tv.getTable());
        }

        @Override
        protected Object getValue(Object element) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) element;
            return entry.getValue();
        }

        @Override
        protected void setValue(Object element, Object value) {
            String newJarName = value.toString();
            Map.Entry<String, String> entry = (Map.Entry<String, String>) element;
            if (newJarName != null && !newJarName.equals(entry.getValue())) {
                entry.setValue(newJarName);
                getViewer().update(element, null);
            }
        }

    }

}
