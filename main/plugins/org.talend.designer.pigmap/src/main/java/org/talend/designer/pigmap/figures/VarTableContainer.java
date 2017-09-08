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
package org.talend.designer.pigmap.figures;

import org.eclipse.draw2d.Figure;
import org.talend.designer.gefabstractmap.figures.table.AbstractTable;
import org.talend.designer.gefabstractmap.figures.table.AbstractTableContainer;
import org.talend.designer.gefabstractmap.figures.treetools.ToolBarContainer;
import org.talend.designer.pigmap.figures.table.PigMapVarTable;
import org.talend.designer.pigmap.figures.table.PigMapVarTableManager;
import org.talend.designer.pigmap.figures.tablesettings.VarToolBarFigure;
import org.talend.designer.pigmap.i18n.Messages;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class VarTableContainer extends AbstractTableContainer {

    private VarToolBarFigure toolBarConainer;

    public VarTableContainer(PigMapVarTableManager tableManager) {
        super(tableManager, false);
        createContents();
    }

    @Override
    public PigMapVarTableManager getTableManager() {
        return (PigMapVarTableManager) super.getTableManager();
    }

    @Override
    protected ToolBarContainer createToolBarContainer() {
        if (toolBarConainer == null) {
            toolBarConainer = new VarToolBarFigure(getTableManager());
        }
        return toolBarConainer;
    }

    @Override
    protected AbstractTable createTable() {
        if (table == null) {
            table = new PigMapVarTable(getTableManager());
        }
        return table;
    }

    @Override
    protected String getTreeDisplayName() {
        return Messages.getString("VarTableContainer.varTable.title");//$NON-NLS-1$
    }

    @Override
    protected void createTreeSettings(Figure parent) {

    }

    public Figure getHeader() {
        return this.header;
    }

    public Figure getTableItemContainer() {
        return table.getTableItemContainer();
    }

    public VarToolBarFigure getToolBarFigure() {
        return this.toolBarConainer;
    }

}
