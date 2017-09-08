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
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.talend.designer.pigmap.figures.table.PigMapTableManager;
import org.talend.designer.pigmap.figures.tablesettings.InputTableSettingContainer;
import org.talend.designer.pigmap.figures.tablesettings.TableFilterContainer;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class InputTableFigure extends AbstractInOutTableFigure {

    public InputTableFigure(PigMapTableManager tableModelManager) {
        super(tableModelManager);
        createContents();
    }

    public InputTable getInputTable() {
        return (InputTable) getTableManager().getModel();
    }

    @Override
    public void setBounds(Rectangle rect) {
        super.setBounds(rect);
    }

    public void update(int type) {
        if (settingContainer != null) {
            settingContainer.update(type);
        }
        if (filterFigure != null) {
            filterFigure.update();
        }
        toolBarContainer.updateMinSizeImage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.pigmap.figures.PigMapInOutTableFigure#createTreeSettings(org.eclipse.draw2d.Figure)
     */
    @Override
    protected void createTreeSettings(Figure parent) {
        if (getInputTable().isLookup()) {
            settingContainer = new InputTableSettingContainer(getTableManager());
            parent.add(settingContainer);
        }
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
        return (getInputTable().isLookup() ? "lookup : " : "main :") + getInputTable().getName();
    }

}
