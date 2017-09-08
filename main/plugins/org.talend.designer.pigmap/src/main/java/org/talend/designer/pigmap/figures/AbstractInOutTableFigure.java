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
import org.talend.designer.gefabstractmap.figures.treesettings.FilterContainer;
import org.talend.designer.gefabstractmap.figures.treetools.ToolBarContainer;
import org.talend.designer.pigmap.figures.table.PigMapTable;
import org.talend.designer.pigmap.figures.table.PigMapTableManager;
import org.talend.designer.pigmap.figures.tablesettings.TableToolBarContainer;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public abstract class AbstractInOutTableFigure extends AbstractTableContainer {

    public AbstractInOutTableFigure(PigMapTableManager tableModelManager) {
        super(tableModelManager);
    }

    @Override
    protected AbstractTable createTable() {
        if (table == null) {
            table = new PigMapTable(getTableManager());
        }
        return table;
    }

    public PigMapTableManager getTableManager() {
        return (PigMapTableManager) super.getTableManager();
    }

    @Override
    public AbstractTable getTableTree() {
        return this.table;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.table.AbstractTableContainer#createToolBarContainer()
     */
    @Override
    protected ToolBarContainer createToolBarContainer() {
        return new TableToolBarContainer(getTableManager());
    }

    @Override
    public FilterContainer getFilterContainer() {
        return this.filterFigure;
    }

    @Override
    protected abstract String getTreeDisplayName();

    @Override
    protected abstract void createTreeSettings(Figure parent);
}
