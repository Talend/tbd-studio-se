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
package org.talend.designer.pigmap.figures.table;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PositionConstants;
import org.talend.designer.gefabstractmap.figures.ComboCellLabel;
import org.talend.designer.gefabstractmap.figures.borders.ColumnBorder;
import org.talend.designer.gefabstractmap.figures.borders.RowBorder;
import org.talend.designer.gefabstractmap.figures.cells.ITextCell;
import org.talend.designer.gefabstractmap.figures.layout.RowLayout;
import org.talend.designer.gefabstractmap.figures.table.AbstractTable;
import org.talend.designer.gefabstractmap.figures.table.ColumnKeyConstant;
import org.talend.designer.gefabstractmap.figures.table.ColumnSash;
import org.talend.designer.gefabstractmap.figures.table.TableColumn;
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.gefabstractmap.resource.ColorInfo;
import org.talend.designer.gefabstractmap.resource.ColorProviderMapper;
import org.talend.designer.pigmap.figures.tablesettings.IUIJoinOptimization;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_JOIN_OPTIMIZATION;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class InputSettingTable extends AbstractTable {

    private InputTable inputTable;

    private Figure joinModelRow;

    private ComboCellLabel joinModel;

    private Figure joinOptimizationRow;

    private ComboCellLabel joinOptimization;

    private Figure customPartitionerRow;

    private TextCellLabel customPartitioner;

    private Figure increaseParallelismRow;

    private TextCellLabel increaseParallelism;

    public InputSettingTable(PigMapTableManager tableModelManager) {
        super(tableModelManager);
        inputTable = (InputTable) tableModelManager.getModel();
        createColumns();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.table.AbstractTable#createColumns()
     */
    @Override
    protected void createColumns() {
        TableColumn column = new TableColumn(ColumnKeyConstant.TREE_SETTING_PROPERTY);
        column.setText("Property");
        addColumn(column);

        ColumnSash sash = new ColumnSash(this);
        sash.setLeftColumn(column);
        addSeparator(sash);

        column = new TableColumn(ColumnKeyConstant.TREE_SETTING_VALUE);
        column.setText("Value");
        sash.setRightColumn(column);
        addColumn(column);

        Figure container = getTableItemContainer();

        joinModelRow = new Figure();
        joinModelRow.setLayoutManager(new RowLayout());
        Label label = new Label();
        label.setText("Join Model");
        label.setLabelAlignment(PositionConstants.LEFT);
        CompoundBorder compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        joinModelRow.add(label);
        joinModel = new ComboCellLabel();
        joinModel.setDirectEditType(DirectEditType.JOIN_MODEL);
        joinModel.setText(inputTable.getJoinModel());
        joinModel.setLabelAlignment(PositionConstants.LEFT);
        joinModel.setBorder(new RowBorder(2, 5, 2, -1));
        joinModelRow.add(joinModel);
        container.add(joinModelRow);

        //
        joinOptimizationRow = new Figure();
        joinOptimizationRow.setLayoutManager(new RowLayout());
        label = new Label();
        label.setText("Join Optimization");
        label.setLabelAlignment(PositionConstants.LEFT);
        compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        joinOptimizationRow.add(label);
        joinOptimization = new ComboCellLabel();
        joinOptimization.setDirectEditType(DirectEditType.JOIN_OPTIMIZATION);
        joinOptimization.setText(getJoinOptimizationDisplayName(inputTable.getJoinOptimization()));
        joinOptimization.setLabelAlignment(PositionConstants.LEFT);
        joinOptimization.setBorder(new RowBorder(2, 5, 2, -1));
        joinOptimizationRow.add(joinOptimization);
        container.add(joinOptimizationRow);

        //
        customPartitionerRow = new Figure();
        customPartitionerRow.setLayoutManager(new RowLayout());
        label = new Label();
        label.setText("Custom Partitioner");
        label.setLabelAlignment(PositionConstants.LEFT);
        compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        customPartitionerRow.add(label);
        customPartitioner = new TextCellLabel();
        customPartitioner.setDirectEditType(DirectEditType.CUSTOM_PARTITIONER);
        customPartitioner.setText(inputTable.getCustomPartitioner());
        customPartitioner.setLabelAlignment(PositionConstants.LEFT);
        customPartitioner.setBorder(new RowBorder(2, 5, 2, -1));
        customPartitionerRow.add(customPartitioner);
        container.add(customPartitionerRow);

        //
        increaseParallelismRow = new Figure();
        increaseParallelismRow.setLayoutManager(new RowLayout());
        label = new Label();
        label.setText("Increase Parallelism");
        label.setLabelAlignment(PositionConstants.LEFT);
        compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        increaseParallelismRow.add(label);
        increaseParallelism = new TextCellLabel();
        increaseParallelism.setDirectEditType(DirectEditType.INCREASE_PARALLELISM);
        increaseParallelism.setText(inputTable.getIncreaseParallelism());
        increaseParallelism.setLabelAlignment(PositionConstants.LEFT);
        increaseParallelism.setBorder(new RowBorder(2, 5, 2, -1));
        increaseParallelismRow.add(increaseParallelism);
        container.add(increaseParallelismRow);
        container.setOpaque(true);
        container.setBackgroundColor(ColorConstants.white);

        container.addMouseListener(new MouseListener() {

            Figure selectedFigure = null;

            @Override
            public void mousePressed(MouseEvent me) {
                boolean joinOptimization = joinOptimizationRow.containsPoint(me.x, me.y);
                if (joinOptimization) {
                    if (selectedFigure != joinOptimizationRow) {
                        joinOptimizationRow.setOpaque(true);
                        joinOptimizationRow.setBackgroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        customPartitionerRow.setOpaque(false);
                        joinModelRow.setOpaque(false);
                        increaseParallelismRow.setOpaque(false);
                    }
                    return;
                }
                boolean customPartitioner = customPartitionerRow.containsPoint(me.x, me.y);
                if (customPartitioner) {
                    if (selectedFigure != customPartitionerRow) {
                        customPartitionerRow.setOpaque(true);
                        customPartitionerRow.setBackgroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        joinOptimizationRow.setOpaque(false);
                        joinModelRow.setOpaque(false);
                        increaseParallelismRow.setOpaque(false);
                    }
                    return;
                }
                boolean joinModel = joinModelRow.containsPoint(me.x, me.y);
                if (joinModel) {
                    if (selectedFigure != joinModelRow) {
                        joinModelRow.setOpaque(true);
                        joinModelRow.setBackgroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        joinOptimizationRow.setOpaque(false);
                        customPartitionerRow.setOpaque(false);
                        increaseParallelismRow.setOpaque(false);
                    }
                    return;
                }
                boolean increaseParallelism = increaseParallelismRow.containsPoint(me.x, me.y);
                if (increaseParallelism) {
                    if (selectedFigure != increaseParallelismRow) {
                        increaseParallelismRow.setOpaque(true);
                        increaseParallelismRow.setBackgroundColor(ColorProviderMapper
                                .getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        joinOptimizationRow.setOpaque(false);
                        customPartitionerRow.setOpaque(false);
                        joinModelRow.setOpaque(false);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }

        });
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

    public void update(int type) {
        switch (type) {
        case PigmapPackage.INPUT_TABLE__JOIN_MODEL:
            joinModel.setText(inputTable.getJoinModel());
            break;
        case PigmapPackage.INPUT_TABLE__JOIN_OPTIMIZATION:
            joinOptimization.setText(getJoinOptimizationDisplayName(inputTable.getJoinOptimization()));
            break;
        case PigmapPackage.INPUT_TABLE__CUSTOM_PARTITIONER:
            customPartitioner.setText(inputTable.getCustomPartitioner());
            break;
        case PigmapPackage.INPUT_TABLE__INCREASE_PARALLELISM:
            increaseParallelism.setText(inputTable.getIncreaseParallelism());
        default:
            break;
        }
    }

    public void deselectTableSettingRows() {
        if (joinModelRow.isOpaque()) {
            joinModelRow.setOpaque(false);
        }
        if (joinOptimizationRow.isOpaque()) {
            joinOptimizationRow.setOpaque(false);
        }
        if (customPartitionerRow.isOpaque()) {
            customPartitionerRow.setOpaque(false);
        }
        if (increaseParallelismRow.isOpaque()) {
            increaseParallelismRow.setOpaque(false);
        }
    }

    /**
     * 
     * DOC hcyi InputSettingTable class global comment. Detailled comment
     */
    class TextCellLabel extends Label implements ITextCell {

        private DirectEditType type;

        public void setDirectEditType(DirectEditType type) {
            this.type = type;
        }

        public DirectEditType getDirectEditType() {
            return this.type;
        }
    }
}
