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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.DirectEditRequest;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.designer.gefabstractmap.figures.anchors.VarColumnAnchor;
import org.talend.designer.gefabstractmap.figures.var.VarEntityFigure;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.gefabstractmap.part.directedit.PigMapNodeCellEditorLocator;
import org.talend.designer.gefabstractmap.policy.RowSelectionEditPolicy;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.figures.tablenode.VarEntityManager;
import org.talend.designer.pigmap.figures.tablenode.VarNodeFigure;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.parts.directedit.PigMapNodeDirectEditManager;
import org.talend.designer.pigmap.policy.DragAndDropEditPolicy;
import org.talend.designer.pigmap.policy.PigDirectEditPolicy;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapVarNodeEditPart extends TableEntityPart implements NodeEditPart {

    private VarEntityFigure varNodeFigure;

    private PigMapNodeDirectEditManager directEditManager;

    private VarEntityManager manager;

    @Override
    protected IFigure createFigure() {
        varNodeFigure = new VarNodeFigure(manager);
        return varNodeFigure;
    }

    @Override
    public void setModel(Object model) {
        super.setModel(model);
        manager = new VarEntityManager((VarNode) model, this);
    }

    @Override
    public void createEditPolicies() {
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new RowSelectionEditPolicy());
        installEditPolicy("Drag and Drop", new DragAndDropEditPolicy());//$NON-NLS-1$
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new PigDirectEditPolicy());
    }

    @Override
    public void performRequest(Request req) {
        IFigure figure = null;
        Figure selectedFigure = null;
        if (RequestConstants.REQ_DIRECT_EDIT.equals(req.getType())) {
            DirectEditRequest drequest = (DirectEditRequest) req;
            Point figureLocation = drequest.getLocation();
            if (getFigure() instanceof VarEntityFigure) {
                figure = ((VarEntityFigure) getFigure()).findFigureAt(figureLocation);
            }
            // if (directEditManager == null) {
            // if (figure != null ) {
            if (figure != null) {
                // if (figure instanceof VariableContainerFigure) {
                // figure = ((VariableContainerFigure) figure).getVariableLabel();
                // }
                selectedFigure = (Figure) figure;
                directEditManager = new PigMapNodeDirectEditManager(this, new PigMapNodeCellEditorLocator(selectedFigure));
            }
            // }
            if (directEditManager != null) {
                directEditManager.show();
                ((PigMapGraphicViewer) getViewer()).getMapperManager().setCurrentDirectEditManager(directEditManager);
            }
        }
    }

    @Override
    protected List getModelSourceConnections() {
        List modelSourceConnection = new ArrayList();
        modelSourceConnection.addAll(((VarNode) getModel()).getOutgoingConnections());
        modelSourceConnection.addAll(((VarNode) getModel()).getFilterOutGoingConnections());
        return modelSourceConnection;
    }

    @Override
    protected List getModelTargetConnections() {
        return ((VarNode) getModel()).getIncomingConnections();
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new VarColumnAnchor(manager, getFigure(), true);
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new VarColumnAnchor(manager, getFigure(), false);
    }

    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new VarColumnAnchor(manager, getFigure(), true);
    }

    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new VarColumnAnchor(manager, getFigure(), false);
    }

    @Override
    public void notifyChanged(Notification notification) {
        int type = notification.getEventType();
        int featureId = notification.getFeatureID(PigmapPackage.class);
        switch (type) {
        case Notification.SET:
            switch (featureId) {
            case PigmapPackage.VAR_NODE__EXPRESSION:
                ((VarEntityFigure) getFigure()).getExpression().setText(((VarNode) getModel()).getExpression());
                break;
            case PigmapPackage.VAR_NODE__NULLABLE:
                boolean newBoolean = notification.getNewBooleanValue();
                ImageFigure checkImage = ((VarEntityFigure) getFigure()).getCheckImage();
                if (newBoolean) {
                    checkImage.setImage(ImageProvider.getImage(EImage.CHECKED_ICON));
                } else if (!newBoolean) {
                    checkImage.setImage(ImageProvider.getImage(EImage.UNCHECKED_ICON));
                }
                break;
            case PigmapPackage.VAR_NODE__NAME:
                ((VarEntityFigure) getFigure()).getVariableLabel().setText(((VarNode) getModel()).getName());
                break;
            case PigmapPackage.VAR_NODE__TYPE:
                ((VarEntityFigure) getFigure()).updateVarNodeType(((VarNode) getModel()).getType(),
                        ((VarNode) getModel()).isNullable());
                break;
            default:
            }
        case Notification.ADD:
        case Notification.ADD_MANY:
            switch (featureId) {
            case PigmapPackage.VAR_NODE__INCOMING_CONNECTIONS:
                refreshTargetConnections();
                break;
            case PigmapPackage.VAR_NODE__OUTGOING_CONNECTIONS:
            case PigmapPackage.VAR_NODE__FILTER_OUT_GOING_CONNECTIONS:
                refreshSourceConnections();
                break;
            }
        case Notification.REMOVE:
        case Notification.REMOVE_MANY:
            switch (featureId) {
            case PigmapPackage.VAR_NODE__INCOMING_CONNECTIONS:
                refreshTargetConnections();
                break;
            case PigmapPackage.VAR_NODE__OUTGOING_CONNECTIONS:
            case PigmapPackage.VAR_NODE__FILTER_OUT_GOING_CONNECTIONS:
                refreshSourceConnections();
                break;
            }
        }

    }

    public PigMapDataEditPart getMapDataEditPart() {
        List children2 = getViewer().getRootEditPart().getChildren();
        if (children2.size() == 1 && children2.get(0) instanceof PigMapDataEditPart) {
            return (PigMapDataEditPart) children2.get(0);
        }
        return null;
    }
}
