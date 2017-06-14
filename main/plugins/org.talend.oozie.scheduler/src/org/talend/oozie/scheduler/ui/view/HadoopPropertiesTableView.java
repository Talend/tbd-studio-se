package org.talend.oozie.scheduler.ui.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.talend.commons.ui.swt.advanced.dataeditor.AbstractDataTableEditorView;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.button.PastePushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.PastePushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.commands.ExtendedTablePasteCommand;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.commons.utils.data.list.ListenableListEvent;
import org.talend.oozie.scheduler.ui.model.HadoopPropertiesFieldModel;

public class HadoopPropertiesTableView extends AbstractDataTableEditorView<HashMap<String, Object>> {

    private static final String VALUE = "VALUE";

    private static final String PROPERTY = "PROPERTY";

    public HadoopPropertiesTableView(HadoopPropertiesFieldModel model, Composite parent, int styleChild) {
        this(model, parent, styleChild, false);
    }

    public HadoopPropertiesTableView(HadoopPropertiesFieldModel model, Composite parent, int styleChild, boolean showDbTypeColumn) {
        super(parent, styleChild, model);
    }

    public HadoopPropertiesTableView(HadoopPropertiesFieldModel model, Composite parent) {
        this(model, parent, SWT.NONE, false);
    }

    @Override
    protected void handleBeforeListenableListOperationEvent(ListenableListEvent<HashMap<String, Object>> event) {
        super.handleBeforeListenableListOperationEvent(event);
    }

    @Override
    protected void handleAfterListenableListOperationEvent(ListenableListEvent<HashMap<String, Object>> event) {
        super.handleAfterListenableListOperationEvent(event);
    }

    @Override
    protected void setTableViewerCreatorOptions(TableViewerCreator<HashMap<String, Object>> newTableViewerCreator) {
        super.setTableViewerCreatorOptions(newTableViewerCreator);
    }

    @Override
    protected void createColumns(TableViewerCreator<HashMap<String, Object>> tableViewerCreator, Table table) {
        createKeyColumn(tableViewerCreator);
        createValueColumn(tableViewerCreator);
    }

    private TableViewerCreatorColumn createValueColumn(TableViewerCreator<HashMap<String, Object>> tableViewerCreator) {
        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Value"); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<HashMap<String, Object>, String>() {

            @Override
            public String get(HashMap<String, Object> bean) {
                return (String) bean.get(VALUE);
            }

            @Override
            public void set(HashMap<String, Object> bean, String value) {
                bean.put(VALUE, value);
            }

        });
        column.setCellEditor(new TextCellEditor(tableViewerCreator.getTable()));

        column.setModifiable(true);
        column.setWeight(30);
        column.setMinimumWidth(50);
        column.setDefaultInternalValue(""); //$NON-NLS-1$
        return column;
    }

    private TableViewerCreatorColumn createKeyColumn(TableViewerCreator<HashMap<String, Object>> tableViewerCreator) {
        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle("Property"); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<HashMap<String, Object>, String>() {

            @Override
            public String get(HashMap<String, Object> bean) {
                return (String) bean.get(PROPERTY);

            }

            @Override
            public void set(HashMap<String, Object> bean, String value) {
                bean.put(PROPERTY, value);
            }

        });
        column.setCellEditor(new TextCellEditor(tableViewerCreator.getTable()));

        column.setModifiable(true);
        column.setWeight(30);
        column.setMinimumWidth(50);
        column.setDefaultInternalValue(""); //$NON-NLS-1$
        return column;
    }

    public HadoopPropertiesFieldModel getModel() {
        return (HadoopPropertiesFieldModel) getExtendedTableModel();
    }

    @Override
    protected ExtendedToolbarView initToolBar() {
        return new ExtendedToolbarView(getMainComposite(), SWT.NONE, getExtendedTableViewer()) {

            @Override
            protected AddPushButtonForExtendedTable createAddPushButton() {
                return new AddPushButtonForExtendedTable(this.toolbar, getExtendedTableViewer()) {

                    @Override
                    protected Object getObjectToAdd() {
                        HashMap<String, Object> hpt = getModel().createHadoopPropertiesType();
                        hpt.put(PROPERTY, "new line");
                        hpt.put(VALUE, "");
                        return hpt;
                    }

                };
            }

            @Override
            protected PastePushButton createPastePushButton() {
                return new PastePushButtonForExtendedTable(toolbar, extendedTableViewer) {

                    @Override
                    protected Command getCommandToExecute(ExtendedTableModel extendedTableModel, Integer indexWhereInsert) {
                        return new ExtendedTablePasteCommand(extendedTableModel, indexWhereInsert) {

                            @Override
                            public List<HashMap<String, Object>> createPastableBeansList(ExtendedTableModel extendedTableModel,
                                    List copiedObjectsList) {
                                List list = new ArrayList();
                                HadoopPropertiesFieldModel fieldsModel = (HadoopPropertiesFieldModel) extendedTableModel;
                                for (Object current : copiedObjectsList) {
                                    if (current instanceof HashMap) {
                                        Map<String, Object> original = (HashMap<String, Object>) current;
                                        Map<String, Object> copy = fieldsModel.createHadoopPropertiesType();
                                        copy.putAll(original);
                                        list.add(copy);
                                    }
                                }
                                return list;
                            }
                        };
                    }

                };
            }
        };
    }
}
