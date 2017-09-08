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
package org.talend.designer.pigmap.parts.directedit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.runtime.model.expressionbuilder.Variable;
import org.talend.commons.ui.runtime.expressionbuilder.IExpressionBuilderDialogController;
import org.talend.commons.ui.runtime.swt.tableviewer.celleditor.CellEditorDialogBehavior;
import org.talend.commons.ui.runtime.swt.tableviewer.celleditor.ExtendedTextCellEditor;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.IService;
import org.talend.core.runtime.services.IExpressionBuilderDialogService;
import org.talend.designer.gefabstractmap.figures.VarNodeTextLabel;
import org.talend.designer.gefabstractmap.figures.cells.IComboCell;
import org.talend.designer.gefabstractmap.figures.cells.IExpressionBuilderCell;
import org.talend.designer.gefabstractmap.figures.cells.ITextAreaCell;
import org.talend.designer.gefabstractmap.figures.cells.ITextCell;
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.gefabstractmap.part.directedit.PigMapNodeCellEditorLocator;
import org.talend.designer.pigmap.figures.manager.PigMapSearchZoneToolBar;
import org.talend.designer.pigmap.figures.tablesettings.IUIJoinOptimization;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_JOIN_OPTIMIZATION;
import org.talend.designer.pigmap.figures.tablesettings.TableSettingsConstant;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.util.MapDataHelper;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapNodeDirectEditManager extends DirectEditManager {

    private GraphicalEditPart source;

    private CellEditorLocator locator;

    private Object model;

    private Map<CellEditor, DirectEditType> cellAndType = new HashMap<CellEditor, DirectEditType>();

    private final String[] joinModel = new String[] { TableSettingsConstant.INNER_JOIN, TableSettingsConstant.LEFT_OUTER_JOIN,
            TableSettingsConstant.RIGHT_OUTER_JOIN, TableSettingsConstant.FULL_OUTER_JOIN };

    public PigMapNodeDirectEditManager(GraphicalEditPart source, CellEditorLocator locator) {
        super(source, null, locator);
        this.source = source;
        this.locator = locator;
        model = source.getModel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
     */
    @Override
    protected void initCellEditor() {
        DirectEditType directEditType = cellAndType.get(getCellEditor());
        if (model instanceof AbstractNode) {
            final AbstractNode abstractNode = (AbstractNode) model;
            if (directEditType != null) {
                switch (directEditType) {
                case EXPRESSION:
                    String expression = abstractNode.getExpression() == null ? "" : abstractNode.getExpression();
                    getCellEditor().setValue(expression);
                    Text text = ((ExtendedTextCellEditor) getCellEditor()).getTextControl();
                    text.selectAll();
                    // Update the configure of var define functions
                    PigMapData pigMapData = PigMapUtil.getPigMapData(abstractNode);
                    if (pigMapData != null && pigMapData.getVarTables() != null) {
                        VarTable varTable = pigMapData.getVarTables().get(0);
                        MapDataHelper.convertVarNodesToDefineFunctions(varTable);
                    }
                    break;
                case NODE_NAME:
                    String variable = abstractNode.getName();
                    if (variable == null) {
                        variable = "";
                    }
                    getCellEditor().setValue(variable);
                    final Text nametext = (Text) ((TextCellEditor) getCellEditor()).getControl();
                    nametext.selectAll();
                    break;
                case VAR_NODE_TYPE:
                    if (getCellEditor() instanceof ComboBoxCellEditor) {
                        CCombo combo = (CCombo) getCellEditor().getControl();
                        combo.setText(abstractNode.getType());
                    }
                    break;
                }
            }
        } else if (model instanceof InputTable) {
            InputTable inputTable = (InputTable) model;
            if (directEditType != null) {
                switch (directEditType) {
                case EXPRESSION_FILTER:
                    String expressionFilter = inputTable.getExpressionFilter();
                    if (expressionFilter == null) {
                        expressionFilter = "";
                    }
                    getCellEditor().setValue(expressionFilter);
                    Text textarea = ((ExtendedTextCellEditor) getCellEditor()).getTextControl();
                    textarea.selectAll();
                    // Update the configure of var define functions
                    if (inputTable.eContainer() != null && inputTable.eContainer() instanceof PigMapData) {
                        PigMapData pigMapData = (PigMapData) inputTable.eContainer();
                        if (pigMapData != null && pigMapData.getVarTables() != null) {
                            VarTable varTable = pigMapData.getVarTables().get(0);
                            MapDataHelper.convertVarNodesToDefineFunctions(varTable);
                        }
                    }
                    break;
                case JOIN_MODEL:
                    if (getCellEditor() instanceof ComboBoxCellEditor) {
                        CCombo combo = (CCombo) getCellEditor().getControl();
                        combo.setText(inputTable.getJoinModel() != null ? inputTable.getJoinModel() : "");
                    }
                    break;
                case JOIN_OPTIMIZATION:
                    if (getCellEditor() instanceof ComboBoxCellEditor) {
                        CCombo combo = (CCombo) getCellEditor().getControl();
                        combo.setText(getJoinOptimizationDisplayName(inputTable.getJoinOptimization()));
                    }
                    break;
                case CUSTOM_PARTITIONER:
                    if (getCellEditor() instanceof TextCellEditor) {
                        Text text = (Text) getCellEditor().getControl();
                        text.setText(inputTable.getCustomPartitioner() != null ? inputTable.getCustomPartitioner() : "");
                    }
                    break;
                case INCREASE_PARALLELISM:
                    if (getCellEditor() instanceof TextCellEditor) {
                        Text text = (Text) getCellEditor().getControl();
                        text.setText(inputTable.getIncreaseParallelism() != null ? inputTable.getIncreaseParallelism() : "");
                    }
                    break;
                }
            }

        } else if (model instanceof OutputTable) {
            OutputTable outputTable = (OutputTable) model;
            if (directEditType != null) {
                switch (directEditType) {
                case EXPRESSION_FILTER:
                    String expressionFilter = outputTable.getExpressionFilter();
                    if (expressionFilter == null) {
                        expressionFilter = "";
                    }
                    getCellEditor().setValue(expressionFilter);
                    Text textarea = ((ExtendedTextCellEditor) getCellEditor()).getTextControl();
                    textarea.selectAll();
                    // Update the configure of var define functions
                    if (outputTable.eContainer() != null && outputTable.eContainer() instanceof PigMapData) {
                        PigMapData pigMapData = (PigMapData) outputTable.eContainer();
                        if (pigMapData != null && pigMapData.getVarTables() != null) {
                            VarTable varTable = pigMapData.getVarTables().get(0);
                            MapDataHelper.convertVarNodesToDefineFunctions(varTable);
                        }
                    }
                    break;
                case OUTPUT_REJECT:
                    if (getCellEditor() instanceof ComboBoxCellEditor) {
                        CCombo combo = (CCombo) getCellEditor().getControl();
                        combo.setText(String.valueOf(outputTable.isReject()));
                    }
                    break;
                case LOOK_UP_INNER_JOIN_REJECT:
                    if (getCellEditor() instanceof ComboBoxCellEditor) {
                        CCombo combo = (CCombo) getCellEditor().getControl();
                        combo.setText(String.valueOf(outputTable.isRejectInnerJoin()));
                    }
                    break;
                case ALL_IN_ONE:
                    if (getCellEditor() instanceof ComboBoxCellEditor) {
                        CCombo combo = (CCombo) getCellEditor().getControl();
                        combo.setText(String.valueOf(outputTable.isAllInOne()));
                    }
                    break;
                case ENABLE_EMPTY_ELEMENT:
                    if (getCellEditor() instanceof ComboBoxCellEditor) {
                        CCombo combo = (CCombo) getCellEditor().getControl();
                        combo.setText(String.valueOf(outputTable.isEnableEmptyElement()));
                    }
                }
            }

        } else if (model instanceof PigMapData) {
            PigMapData pigMapData = (PigMapData) model;
            if (directEditType != null) {
                switch (directEditType) {
                case SERACH:
                    Text text = (Text) getCellEditor().getControl();
                    text.selectAll();
                    break;
                }
            }
        }
    }

    private String getJoinOptimizationDisplayName(String joinOptimization) {
        IUIJoinOptimization[] availableJoins = { PIG_MAP_JOIN_OPTIMIZATION.NONE, PIG_MAP_JOIN_OPTIMIZATION.REPLICATED,
                PIG_MAP_JOIN_OPTIMIZATION.SKEWED, PIG_MAP_JOIN_OPTIMIZATION.MERGE };
        for (IUIJoinOptimization model : availableJoins) {
            if (model.toString().equals(joinOptimization)) {
                return model.getLabel();
            }
        }
        return joinOptimization;
    }

    @Override
    protected CellEditor createCellEditorOn(Composite composite) {
        Composite parent = (Composite) source.getViewer().getControl();
        CellEditor cellEditor = null;
        Figure figure = null;
        if (this.locator instanceof PigMapNodeCellEditorLocator) {
            PigMapNodeCellEditorLocator lo = (PigMapNodeCellEditorLocator) locator;
            figure = lo.getFigure();
        }
        if (figure instanceof IComboCell) {
            try {
                // table setting can be edit or not
                if (source instanceof PigMapInputTablePart) {
                    InputTable inputTable = (InputTable) ((PigMapInputTablePart) source).getModel();
                    if (DirectEditType.JOIN_MODEL.equals(((IComboCell) figure).getDirectEditType())) {
                    }
                    if (DirectEditType.JOIN_OPTIMIZATION.equals(((IComboCell) figure).getDirectEditType())) {
                    }
                }
                if (source instanceof PigMapOutputTablePart) {
                    OutputTable outputTable = (OutputTable) ((PigMapOutputTablePart) source).getModel();
                    if (DirectEditType.ALL_IN_ONE.equals(((IComboCell) figure).getDirectEditType())) {
                    }
                }
                cellEditor = new ComboCellEditor();
                cellEditor.create(composite);
                ((ComboCellEditor) cellEditor).setItems(getComboItemsByType(((IComboCell) figure).getDirectEditType()));
                cellAndType.put(cellEditor, ((IComboCell) figure).getDirectEditType());
            } catch (Exception e) {
                return null;
            }
        } else if (figure instanceof ITextCell) {
            // this one is created for direct doc child name , no use anymore...
            cellEditor = new TextCellEditor(composite);
            cellAndType.put(cellEditor, ((ITextCell) figure).getDirectEditType());
            // for the search
            PigMapNodeCellEditorLocator lo = (PigMapNodeCellEditorLocator) locator;
            if (lo.getFigure() != null && lo.getFigure() instanceof VarNodeTextLabel) {
                figure = lo.getFigure();
                if (figure.getParent() != null && figure.getParent() instanceof PigMapSearchZoneToolBar) {
                    PigMapSearchZoneToolBar searchZone = (PigMapSearchZoneToolBar) figure.getParent();
                    if (searchZone.getSearchMaps().size() > 0) {
                        searchZone.getSearchZoneMapper().hightlightAll(searchZone.getSearchMaps(), false);
                        searchZone.getSearchZoneMapper().setHightlightAll(false);
                        searchZone.getSearchMaps().clear();
                        searchZone.hightLightAll.setSelected(false);
                    }
                }
            }
        } else if (figure instanceof IExpressionBuilderCell && model instanceof AbstractNode) {
            IService expressionBuilderDialogService = GlobalServiceRegister.getDefault().getService(
                    IExpressionBuilderDialogService.class);
            CellEditorDialogBehavior behavior = new CellEditorDialogBehavior();
            cellEditor = new ExpressionCellEditor(composite, behavior, source, DirectEditType.EXPRESSION);
            ((ExpressionCellEditor) cellEditor).setOwnerId(((AbstractNode) model).getExpression());
            //
            List<Variable> vars = new ArrayList<Variable>();
            PigMapData pigMapData = PigMapUtil.getPigMapData(((AbstractNode) model));
            List<InputTable> inputTables = pigMapData.getInputTables();
            for (InputTable table : inputTables) {
                List<TableNode> nodes = table.getNodes();
                for (TableNode node : nodes) {
                    Variable variable = new Variable();
                    variable.setName(table.getName());
                    variable.setValue(node.getName());
                    vars.add(variable);
                }
            }
            // only var table has the DataFu category
            boolean hasPigDataFuCategory = false;
            if (model instanceof VarNode) {
                hasPigDataFuCategory = true;
            }
            IExpressionBuilderDialogController dialog = ((IExpressionBuilderDialogService) expressionBuilderDialogService)
                    .getExpressionBuilderInstance(parent, (ExpressionCellEditor) cellEditor, null, vars, hasPigDataFuCategory);
            cellAndType.put(cellEditor, DirectEditType.EXPRESSION);
            behavior.setCellEditorDialog(dialog);
        } else if (figure instanceof ITextAreaCell) {
            TextAreaBehavior behavior = new TextAreaBehavior();
            cellEditor = new ExpressionCellEditor(composite, behavior, source, DirectEditType.EXPRESSION_FILTER);
            cellAndType.put(cellEditor, DirectEditType.EXPRESSION_FILTER);
        }

        // }
        return cellEditor;
    }

    @Override
    protected boolean isDirty() {
        return super.isDirty();
    }

    @Override
    protected Object getDirectEditFeature() {
        return cellAndType.get(getCellEditor());
    }

    private String[] getComboItemsByType(DirectEditType type) {
        if (type == null) {
            return new String[0];
        }

        switch (type) {
        case VAR_NODE_TYPE:
            String[] items = new String[MapDataHelper.iNodesDefineFunctions.size()];
            for (int i = 0; i < MapDataHelper.iNodesDefineFunctions.size(); i++) {
                items[i] = MapDataHelper.iNodesDefineFunctions.get(i).getUniqueName();
            }
            return items;
        case JOIN_MODEL:
            return joinModel;
        case JOIN_OPTIMIZATION:
            IUIJoinOptimization[] availableJoins = { PIG_MAP_JOIN_OPTIMIZATION.NONE, PIG_MAP_JOIN_OPTIMIZATION.REPLICATED,
                    PIG_MAP_JOIN_OPTIMIZATION.SKEWED, PIG_MAP_JOIN_OPTIMIZATION.MERGE };
            String names[] = new String[availableJoins.length];
            for (int i = 0; i < availableJoins.length; i++) {
                names[i] = availableJoins[i].getLabel();
            }
            return names;
        case OUTPUT_REJECT:
        case LOOK_UP_INNER_JOIN_REJECT:
        case ALL_IN_ONE:
        case ENABLE_EMPTY_ELEMENT:
            return new String[] { String.valueOf(Boolean.TRUE), String.valueOf(Boolean.FALSE) };
        default:
            return new String[0];
        }

    }

    @Override
    public void commit() {
        DirectEditType directEditType = cellAndType.get(getCellEditor());
        if (directEditType != null) {
            switch (directEditType) {
            case SERACH:
                VarNodeTextLabel figure = null;
                if (this.locator instanceof PigMapNodeCellEditorLocator) {
                    PigMapNodeCellEditorLocator lo = (PigMapNodeCellEditorLocator) locator;
                    if (lo.getFigure() != null && lo.getFigure() instanceof VarNodeTextLabel) {
                        figure = (VarNodeTextLabel) lo.getFigure();
                        Object searchTextObject = getDirectEditRequest().getCellEditor().getValue();
                        if (searchTextObject != null) {
                            if (figure.getParent() != null && figure.getParent() instanceof PigMapSearchZoneToolBar) {
                                PigMapSearchZoneToolBar searchZone = (PigMapSearchZoneToolBar) figure.getParent();
                                searchZone.search(searchTextObject.toString());
                                figure.setText(searchTextObject.toString());
                            }
                        }
                    }
                }
            }
        }
        super.commit();
    }

    @Override
    public void showFeedback() {
        getEditPart().showSourceFeedback(getDirectEditRequest());
    }

    @Override
    public CellEditor getCellEditor() {
        return super.getCellEditor();
    }

    /**
     * 
     * DOC hcyi PigMapNodeDirectEditManager class global comment. Detailled comment
     */
    class ComboCellEditor extends ComboBoxCellEditor {

        @Override
        protected Control createControl(Composite parent) {
            Control control = super.createControl(parent);
            CCombo combo = (CCombo) control;
            combo.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetDefaultSelected(SelectionEvent event) {
                    // applyEditorValueAndDeactivate();
                }

                @Override
                public void widgetSelected(SelectionEvent event) {
                    valueChanged(true, true);
                }
            });
            return control;
        }
    }

    class TextAreaBehavior extends CellEditorDialogBehavior {

        /**
         * 
         * DOC hcyi TextAreaBehavior constructor comment.
         */
        public TextAreaBehavior() {
            super();
        }

        private Composite panel;

        @Override
        public Control createBehaviorControls(Composite parent) {
            panel = new Composite(parent, SWT.NONE);
            GridLayout gridLayout = new GridLayout(2, false);
            gridLayout.marginBottom = 0;
            gridLayout.marginHeight = 0;
            gridLayout.marginLeft = 0;
            gridLayout.marginRight = 2;
            gridLayout.marginTop = 0;
            gridLayout.marginWidth = 0;
            panel.setLayout(gridLayout);
            GridData gd = new GridData(GridData.FILL_BOTH | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
            panel.setLayoutData(gd);
            GridData controlGD = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_BEGINNING);
            Control text = getExtendedTextCellEditor().createText(panel);
            text.setLayoutData(controlGD);
            text.setBackground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
            return panel;
        }
    }
}
