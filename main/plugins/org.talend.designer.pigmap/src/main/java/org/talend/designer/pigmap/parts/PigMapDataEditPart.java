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
package org.talend.designer.pigmap.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.DirectEditRequest;
import org.talend.designer.gefabstractmap.figures.layout.ZoneContentLayout;
import org.talend.designer.gefabstractmap.figures.treetools.zone.InputZoneToolBar;
import org.talend.designer.gefabstractmap.figures.treetools.zone.OutputZoneToolBar;
import org.talend.designer.gefabstractmap.figures.treetools.zone.SearchZoneToolBar;
import org.talend.designer.gefabstractmap.part.MapperRootEditPart;
import org.talend.designer.gefabstractmap.part.directedit.PigMapNodeCellEditorLocator;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.figures.manager.PigMapContainerLayout;
import org.talend.designer.pigmap.figures.manager.PigMapInputZoneToolBar;
import org.talend.designer.pigmap.figures.manager.PigMapOutputZoneToolBar;
import org.talend.designer.pigmap.figures.manager.PigMapSearchZoneToolBar;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.parts.directedit.PigMapNodeDirectEditManager;
import org.talend.designer.pigmap.policy.PigDirectEditPolicy;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapDataEditPart extends MapperRootEditPart {

    private PigMapNodeDirectEditManager directEditManager;

    @Override
    public List getModelChildren() {
        List children = new ArrayList();
        children.addAll(((PigMapData) getModel()).getInputTables());
        children.addAll(((PigMapData) getModel()).getOutputTables());
        children.addAll(((PigMapData) getModel()).getVarTables());
        return children;
    }

    @Override
    protected PigMapDataManager createRootModelManager() {
        return new PigMapDataManager((PigMapData) getModel(), this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperRootEditPart#createOutputZoneToolBar()
     */
    @Override
    protected OutputZoneToolBar createOutputZoneToolBar() {
        return new PigMapOutputZoneToolBar((PigMapDataManager) getRootModelManager());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperRootEditPart#createInputZoneToolBar()
     */
    @Override
    protected InputZoneToolBar createInputZoneToolBar() {
        return new PigMapInputZoneToolBar((PigMapDataManager) getRootModelManager());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperRootEditPart#createZoneContentLayout()
     */
    @Override
    protected ZoneContentLayout createZoneContentLayout() {
        return new PigMapContainerLayout();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.MapperRootEditPart#createSearchZoneToolBar()
     */
    @Override
    protected SearchZoneToolBar createSearchZoneToolBar() {
        return new PigMapSearchZoneToolBar((PigMapDataManager) getRootModelManager());
    }

    @Override
    public void performRequest(Request req) {
        if (RequestConstants.REQ_DIRECT_EDIT.equals(req.getType())) {
            DirectEditRequest drequest = (DirectEditRequest) req;
            Point figureLocation = drequest.getLocation();
            IFigure findFigureAt = getFigure().findFigureAt(figureLocation.x, figureLocation.y);
            if (findFigureAt != null && findFigureAt instanceof Label) {
                directEditManager = new PigMapNodeDirectEditManager(this, new PigMapNodeCellEditorLocator((Figure) findFigureAt));
                directEditManager.show();
            }
            if (directEditManager != null) {
                if (findFigureAt != null && findFigureAt instanceof Label) {
                    if (figure.containsPoint(figureLocation)) {
                        directEditManager.show();
                        ((PigMapGraphicViewer) getViewer()).getMapperManager().setCurrentDirectEditManager(directEditManager);
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.part.BaseEditPart#createEditPolicies()
     */
    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new PigDirectEditPolicy());
    }
}
