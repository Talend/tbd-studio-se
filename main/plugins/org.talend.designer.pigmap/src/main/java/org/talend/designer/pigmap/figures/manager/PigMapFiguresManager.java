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

import java.util.ArrayList;
import java.util.List;

import org.talend.designer.gefabstractmap.figures.manager.FiguresManager;
import org.talend.designer.gefabstractmap.model.abstractmap.MapperTable;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.parts.PigMapDataEditPart;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapFiguresManager extends FiguresManager {

    public PigMapFiguresManager(PigMapGraphicViewer graphicViewer) {
        super(graphicViewer);

    }

    @Override
    public PigMapDataEditPart getRootEditPart() {
        return (PigMapDataEditPart) super.getRootEditPart();
    }

    @Override
    public PigMapInputTablePart getCurrentSelectedInputTable() {
        return (PigMapInputTablePart) super.getCurrentSelectedInputTable();
    }

    @Override
    public PigMapOutputTablePart getCurrentSelectedOutputTable() {
        return (PigMapOutputTablePart) super.getCurrentSelectedOutputTable();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.FiguresManager#getInputTables()
     */
    @Override
    protected List<MapperTable> getInputTables() {
        List<MapperTable> inputTables = new ArrayList<MapperTable>();
        PigMapData mapData = (PigMapData) getRootEditPart().getModel();
        inputTables.addAll(mapData.getInputTables());
        return inputTables;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.FiguresManager#getOutputTables()
     */
    @Override
    protected List<MapperTable> getOutputTables() {
        List<MapperTable> outputTables = new ArrayList<MapperTable>();
        PigMapData mapData = (PigMapData) getRootEditPart().getModel();
        outputTables.addAll(mapData.getOutputTables());
        return outputTables;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.FiguresManager#isErrorRejectTable(org.talend.designer.
     * gefabstractmap.model.abstractmap.MapperTable)
     */
    @Override
    protected boolean isErrorRejectTable(MapperTable table) {
        if (table instanceof OutputTable) {
            return ((OutputTable) table).isErrorReject();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.designer.gefabstractmap.figures.manager.FiguresManager#isLookup(org.talend.designer.gefabstractmap
     * .model.abstractmap.MapperTable)
     */
    @Override
    protected boolean isLookup(MapperTable table) {
        if (table instanceof InputTable) {
            return ((InputTable) table).isLookup();
        }
        return false;
    }
}
