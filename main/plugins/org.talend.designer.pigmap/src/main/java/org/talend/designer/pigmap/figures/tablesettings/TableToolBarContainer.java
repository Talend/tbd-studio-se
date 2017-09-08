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
package org.talend.designer.pigmap.figures.tablesettings;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.Clickable;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.talend.designer.gefabstractmap.figures.treetools.ToolBarButtonImageFigure;
import org.talend.designer.gefabstractmap.figures.treetools.ToolBarContainer;
import org.talend.designer.gefabstractmap.resource.ImageInfo;
import org.talend.designer.gefabstractmap.resource.ImageProviderMapper;
import org.talend.designer.pigmap.figures.table.PigMapTableManager;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class TableToolBarContainer extends ToolBarContainer {

    private Map<String, Object> defaultSettingMap = new HashMap<String, Object>();

    protected ToolBarButtonImageFigure condensedButton;

    protected ToolBarButtonImageFigure expressionFilterButton;

    private AbstractInOutTable abstractTable;

    public TableToolBarContainer(PigMapTableManager tableManager) {
        super(tableManager);
        createToolbar();
    }

    @Override
    public PigMapTableManager getTableManager() {
        return (PigMapTableManager) super.getTableManager();
    }

    @Override
    protected void createToolbar() {
        super.createToolbar();
        condensedButton = new CondensedButton(ImageProviderMapper.getImage(ImageInfo.CONDENSED_TOOL_ICON));
        condensedButton.setSelected(getTableManager().isActivateCondensedTool());
        setTooltips(condensedButton, "tPigMap settings");
        expressionFilterButton = new ExpressionFilterButton(ImageProviderMapper.getImage(ImageInfo.ACTIVATE_FILTER_ICON));
        expressionFilterButton.setSelected(getTableManager().isActivateExpressionFilter());
        setTooltips(expressionFilterButton, "Enable/disable expression filter");

        // main table no show the condensedButton
        abstractTable = getTableManager().getModel();
        if (abstractTable instanceof OutputTable
                || (abstractTable instanceof InputTable && ((InputTable) abstractTable).isLookup())) {
            this.add(condensedButton);
        }
        this.add(expressionFilterButton);
        boolean isErrorReject = false;
        if (isErrorReject) {
            condensedButton.setEnabled(false);
            expressionFilterButton.setEnabled(false);
        }
        this.add(min_size);
    }

    @Override
    public void updateButtonsColor(Color color) {
        super.updateButtonsColor(color);
        if (condensedButton != null) {
            if (!condensedButton.isSelected()) {
                condensedButton.setBackgroundColor(color);
            }
        }
        if (expressionFilterButton != null) {
            if (!expressionFilterButton.isSelected()) {
                expressionFilterButton.setBackgroundColor(color);
            }
        }
    }

    public Map<String, Object> getDefaultSetting() {
        if (defaultSettingMap.isEmpty()) {
            defaultSettingMap.put(TableSettingsConstant.OUTPUT_REJECT, false);
            defaultSettingMap.put(TableSettingsConstant.LOOK_UP_INNER_JOIN_REJECT, false);
        }
        return defaultSettingMap;
    }

    class CondensedButton extends ToolBarButtonImageFigure {

        public CondensedButton(Image image) {
            super(image);
            setStyle(Clickable.STYLE_TOGGLE);
        }

        @Override
        public void toolBarButtonPressed(MouseEvent me) {
            super.toolBarButtonPressed(me);
            getTableManager().setActivateCondensedTool(this.isSelected());
            revalidate();
        }
    }

    class ExpressionFilterButton extends ToolBarButtonImageFigure {

        public ExpressionFilterButton(Image image) {
            super(image);
            setStyle(Clickable.STYLE_TOGGLE);
        }

        @Override
        public void toolBarButtonPressed(MouseEvent me) {
            super.toolBarButtonPressed(me);
            getTableManager().setActivateExpressionFilter(this.isSelected());
            revalidate();
        }
    }
}
