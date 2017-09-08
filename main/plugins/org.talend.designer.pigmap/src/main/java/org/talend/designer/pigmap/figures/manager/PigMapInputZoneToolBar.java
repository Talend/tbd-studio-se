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
package org.talend.designer.pigmap.figures.manager;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.talend.designer.gefabstractmap.figures.treetools.zone.InputZoneToolBar;
import org.talend.designer.pigmap.PigMapComponent;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.parts.PigMapDataEditPart;
import org.talend.designer.pigmap.parts.PigMapDataManager;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapInputZoneToolBar extends InputZoneToolBar {

    private PigMapData externalData;

    private PigMapDataEditPart externalPart;

    private PigMapComponent mapperComponent;

    private MapperManager mapperManger;

    private PigMapGraphicViewer graphicViewer;

    public PigMapInputZoneToolBar(PigMapDataManager rootModelManager) {
        super(rootModelManager);
        externalData = rootModelManager.getModel();
        externalPart = rootModelManager.getEditPart();
        graphicViewer = (PigMapGraphicViewer) rootModelManager.getGraphicalViewer();
        mapperManger = (MapperManager) rootModelManager.getMapperManger();
        mapperComponent = mapperManger.getMapperComponent();
        createZoneContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.treetools.zone.InputZoneTooBar#changProperty()
     */
    @Override
    public void changProperty() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.pigmap.figures.treetools.zone.ZoneToolBar#moveUp()
     */
    @Override
    public void moveUp() {
        List<InputTable> inputTables = externalData.getInputTables();
        PigMapInputTablePart currentSelectedInputTable = graphicViewer.getFiguresManager().getCurrentSelectedInputTable();
        if (currentSelectedInputTable != null) {
            InputTable selectedTable = (InputTable) currentSelectedInputTable.getModel();
            int index = inputTables.indexOf(selectedTable);
            if (index != -1 && index - 1 >= 0) {
                inputTables.remove(selectedTable);
                inputTables.add(index - 1, selectedTable);

                // index of modelchildren is different from index of table
                int indexOf = externalPart.getModelChildren().indexOf(selectedTable);
                if (indexOf != -1) {
                    externalPart.getViewer().appendSelection((EditPart) externalPart.getChildren().get(indexOf));
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.pigmap.figures.treetools.zone.ZoneToolBar#moveDown()
     */
    @Override
    public void moveDown() {
        List<InputTable> inputTables = externalData.getInputTables();
        PigMapInputTablePart currentSelectedInputTable = graphicViewer.getFiguresManager().getCurrentSelectedInputTable();
        if (currentSelectedInputTable != null) {
            InputTable selectedTable = (InputTable) currentSelectedInputTable.getModel();
            int index = inputTables.indexOf(selectedTable);
            if (index != -1 && index + 1 < inputTables.size()) {
                inputTables.remove(selectedTable);
                inputTables.add(index + 1, selectedTable);

                // index of modelchildren is different from index of table
                int indexOf = externalPart.getModelChildren().indexOf(selectedTable);
                if (indexOf != -1) {
                    graphicViewer.appendSelection((EditPart) externalPart.getChildren().get(indexOf));
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.pigmap.figures.treetools.zone.ZoneToolBar#minSize()
     */
    @Override
    public void minSize() {
        minimized = !minimized;
        EList<InputTable> inputTrees = externalData.getInputTables();
        for (InputTable inputTree : inputTrees) {
            if (minimized != inputTree.isMinimized()) {
                inputTree.setMinimized(minimized);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.treetools.zone.ZoneToolBar#isMinSizeEnable()
     */
    @Override
    protected boolean isMinSizeEnable() {
        return externalData.getInputTables().isEmpty();
    }

    @Override
    public boolean getMinSizeStatus() {
        boolean min = true;
        for (InputTable table : externalData.getInputTables()) {
            if (!table.isMinimized()) {
                min = false;
            }
        }
        return min;
    }
}
