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

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.talend.core.model.process.INode;
import org.talend.designer.gefabstractmap.part.VarTablePart;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.figures.VarTableContainer;
import org.talend.designer.pigmap.figures.table.PigMapVarTableManager;
import org.talend.designer.pigmap.figures.tablesettings.VarToolBarFigure;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.util.MapDataHelper;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapVarTablePart extends VarTablePart {

    private VarTableContainer centerVarFigure;

    private ISelectionChangedListener selectionListener;

    @Override
    protected IFigure createFigure() {

        /* Center var figure */
        VarTable model = (VarTable) getModel();
        PigMapVarTableManager manager = new PigMapVarTableManager(model, this);
        centerVarFigure = new VarTableContainer(manager);

        // Need list all the tPigLoad node if connect the tPigMap,then convert define dunctions to var nodes.
        if (getViewer() != null && getViewer() instanceof PigMapGraphicViewer) {
            List<INode> iNodes = new ArrayList<INode>();
            PigMapGraphicViewer viewer = (PigMapGraphicViewer) getViewer();
            if (viewer != null) {
                List<VarNode> varNodes = MapDataHelper.convertDefineFunctionsToVarNodes(viewer.getMapperManager(), iNodes);
                ((VarTable) getModel()).getNodes().clear();
                ((VarTable) getModel()).getNodes().addAll(varNodes);
            }
        }
        centerVarFigure.getTableManager().setMinimized(false);
        centerVarFigure.getToolBarFigure().updateMinSizeImage();

        return centerVarFigure;
    }

    @Override
    public IFigure getContentPane() {
        return centerVarFigure.getTableItemContainer();
    }

    @Override
    protected List getModelChildren() {
        return ((VarTable) getModel()).getNodes();
    }

    @Override
    public void notifyChanged(Notification notification) {
        int type = notification.getEventType();
        int featureId = notification.getFeatureID(PigmapPackage.class);
        switch (type) {
        case Notification.ADD:
        case Notification.REMOVE:
            switch (featureId) {
            case PigmapPackage.VAR_NODE:
                refreshSourceConnections();
                refreshTargetConnections();
            case PigmapPackage.VAR_TABLE__NODES:
                refreshChildren();
                refreshSourceConnections();
                refreshTargetConnections();
                break;
            }

        case Notification.SET:
            switch (featureId) {
            case PigmapPackage.VAR_TABLE__NODES:
                refreshChildren();
                break;
            case PigmapPackage.VAR_TABLE__MINIMIZED:
                ((VarTableContainer) getFigure()).getToolBarFigure().updateMinSizeImage();
                refreshChildren();
            }
        }
    }

    @Override
    public void activate() {
        super.activate();

        selectionListener = new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                List selectedEditParts = getViewer().getSelectedEditParts();

                boolean remove = false;
                boolean moveUp = false;
                boolean moveDown = false;

                boolean disableMoveUp = false;
                boolean disableMoveDown = false;

                for (Object obj : selectedEditParts) {
                    if (obj instanceof PigMapVarNodeEditPart) {
                        remove = true;
                        VarNode selectedNode = (VarNode) ((PigMapVarNodeEditPart) obj).getModel();
                        EList<VarNode> nodes = ((VarTable) getModel()).getNodes();

                        int indexOf = nodes.indexOf(selectedNode);

                        if (!disableMoveUp) {
                            if (indexOf != -1 && indexOf > 0) {
                                moveUp = true;
                            } else {
                                moveUp = false;
                                disableMoveUp = true;
                            }
                        }

                        if (!disableMoveDown) {
                            if (indexOf != -1 && indexOf < nodes.size() - 1) {
                                moveDown = true;
                            } else {
                                moveDown = false;
                                disableMoveDown = true;
                            }
                        }

                    }
                }
                VarToolBarFigure toolBarFigure = ((VarTableContainer) getFigure()).getToolBarFigure();
                toolBarFigure.setRemoveEnable(remove);
                toolBarFigure.setMoveUpEnable(moveUp);
                toolBarFigure.setMoveDownEnable(moveDown);

            }

        };

        getViewer().addSelectionChangedListener(selectionListener);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        getViewer().removeSelectionChangedListener(selectionListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.part.MapperTablePart#highLightHeader()
     */
    @Override
    public void highLightHeader(boolean highLight) {
        // TODO Auto-generated method stub

    }

}
