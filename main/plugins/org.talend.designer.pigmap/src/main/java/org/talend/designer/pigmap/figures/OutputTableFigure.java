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
import org.eclipse.swt.widgets.Composite;
import org.talend.designer.pigmap.figures.table.PigMapTableManager;
import org.talend.designer.pigmap.figures.tablesettings.OutputTableSettingContainer;
import org.talend.designer.pigmap.figures.tablesettings.TableFilterContainer;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class OutputTableFigure extends AbstractInOutTableFigure {

    public OutputTableFigure(PigMapTableManager tableModelManager) {
        super(tableModelManager);
        createContents();
    }

    public void update(int type) {
        settingContainer.update(type);
        filterFigure.update();
        toolBarContainer.updateMinSizeImage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.pigmap.figures.PigMapInOutTableFigure#createTreeSettings(org.eclipse.draw2d.Figure)
     */
    @Override
    protected void createTreeSettings(Figure parent) {
        settingContainer = new OutputTableSettingContainer(getTableManager());
        parent.add(settingContainer);
        filterFigure = new TableFilterContainer(getTableManager(), (Composite) getTableManager().getEditPart().getViewer()
                .getControl());
        parent.add(filterFigure);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.pigmap.figures.PigMapInOutTableFigure#getTreeDisplayName()
     */
    @Override
    protected String getTreeDisplayName() {
        return getTableManager().getModel().getName();
    }
}
