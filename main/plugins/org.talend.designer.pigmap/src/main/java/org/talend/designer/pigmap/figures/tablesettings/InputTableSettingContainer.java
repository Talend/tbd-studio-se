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

import org.eclipse.draw2d.ToolbarLayout;
import org.talend.designer.gefabstractmap.figures.treesettings.AbstractTreeSettingContainer;
import org.talend.designer.pigmap.figures.table.InputSettingTable;
import org.talend.designer.pigmap.figures.table.PigMapTableManager;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class InputTableSettingContainer extends AbstractTreeSettingContainer {

    private PigMapTableManager tableModelManager;

    private InputSettingTable tableFigure;

    public InputTableSettingContainer(PigMapTableManager tableModelManager) {
        this.tableModelManager = tableModelManager;
        createContent();
    }

    public void createContent() {
        setLayoutManager(new ToolbarLayout());
        tableFigure = new InputSettingTable(tableModelManager);
        this.add(tableFigure);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.treesettings.AbstractTreeSettingContainer#update(int)
     */
    @Override
    public void update(int type) {
        tableFigure.update(type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.designer.gefabstractmap.figures.treesettings.AbstractTreeSettingContainer#deselectTreeSettingRows()
     */
    @Override
    public void deselectTreeSettingRows() {
        tableFigure.deselectTableSettingRows();

    }

}
