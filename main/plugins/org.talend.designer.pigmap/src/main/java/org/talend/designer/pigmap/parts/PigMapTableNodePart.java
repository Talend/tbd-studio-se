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
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.DirectEditRequest;
import org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE;
import org.talend.designer.gefabstractmap.figures.ExpressionFigure;
import org.talend.designer.gefabstractmap.figures.anchors.ConnectionColumnAnchor;
import org.talend.designer.gefabstractmap.figures.anchors.FilterColumnAnchor;
import org.talend.designer.gefabstractmap.figures.anchors.LookupColumnAnchor;
import org.talend.designer.gefabstractmap.figures.cells.IWidgetCell;
import org.talend.designer.gefabstractmap.figures.table.entity.TableTreeEntityFigure;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.gefabstractmap.part.directedit.PigMapNodeCellEditorLocator;
import org.talend.designer.gefabstractmap.policy.RowSelectionEditPolicy;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.figures.tablenode.PigMapTableNodeFigure;
import org.talend.designer.pigmap.figures.tablenode.TableNodeEntityManager;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.directedit.PigMapNodeDirectEditManager;
import org.talend.designer.pigmap.policy.DragAndDropEditPolicy;
import org.talend.designer.pigmap.policy.PigDirectEditPolicy;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapTableNodePart extends TableEntityPart implements NodeEditPart {

    private TableNodeEntityManager entityManger;

    protected PigMapNodeDirectEditManager directEditManager;

    @Override
    protected IFigure createFigure() {
        TableNode model = (TableNode) getModel();
        boolean isRoot = false;
        if (model.eContainer() instanceof AbstractInOutTable) {
            isRoot = true;
        }
        PigMapTableNodeFigure tableNodeFigure = new PigMapTableNodeFigure(entityManger, isRoot);
        return tableNodeFigure;
    }

    @Override
    public void setModel(Object model) {
        super.setModel(model);
        entityManger = new TableNodeEntityManager((TableNode) model, this);
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new RowSelectionEditPolicy());
        installEditPolicy("Drag and Drop", new DragAndDropEditPolicy());
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new PigDirectEditPolicy());
    }

    @Override
    protected List getModelSourceConnections() {
        TableNode model = (TableNode) getModel();
        List list = new ArrayList();
        list.addAll(model.getOutgoingConnections());
        list.addAll(model.getLookupOutgoingConnections());
        list.addAll(model.getFilterOutGoingConnections());
        return list;
    }

    @Override
    protected List getModelTargetConnections() {
        TableNode model = (TableNode) getModel();
        List list = new ArrayList();
        list.addAll(model.getIncomingConnections());
        list.addAll(model.getLookupIncomingConnections());
        return list;
    }

    @Override
    public IFigure getContentPane() {
        return ((PigMapTableNodeFigure) getFigure()).getContents();
    }

    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        super.addChildVisual(childEditPart, index);
    }

    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        super.removeChildVisual(childEditPart);
    }

    @Override
    public void notifyChanged(Notification notification) {
        int type = notification.getEventType();
        int featureId = notification.getFeatureID(PigmapPackage.class);
        PigMapTableNodeFigure tableNodeFigure = (PigMapTableNodeFigure) getFigure();

        switch (type) {
        case Notification.SET:
            switch (featureId) {
            case PigmapPackage.TABLE_NODE__NAME:
                tableNodeFigure.getBranchContent().updataNameFigure();
                break;
            case PigmapPackage.TABLE_NODE__TYPE:
                refreshChildren();
                break;
            case PigmapPackage.TABLE_NODE__EXPRESSION:
                if (getModel() instanceof TableNode) {
                    TableNode tableNode = (TableNode) getModel();
                    if (tableNode != null && tableNode.eContainer() instanceof InputTable) {
                        InputTable inputTable = (InputTable) tableNode.eContainer();
                        if (inputTable.isLookup()) {
                            if (((TableNode) getModel()).getExpression() != null
                                    && !((TableNode) getModel()).getExpression().trim().equals("")) {
                                //
                            }
                        }
                    }
                }
                if (getFigure() instanceof TableTreeEntityFigure) {
                    tableNodeFigure.updateExpression();
                }
                break;
            case PigmapPackage.INPUT_TABLE__MINIMIZED:
                refreshSourceConnections();
                refreshTargetConnections();
                break;
            }
            break;

        case Notification.ADD:
        case Notification.ADD_MANY:
            switch (featureId) {
            case PigmapPackage.TABLE_NODE__OUTGOING_CONNECTIONS:
            case PigmapPackage.TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS:
            case PigmapPackage.TABLE_NODE__FILTER_OUT_GOING_CONNECTIONS:
                refreshSourceConnections();
                break;
            case PigmapPackage.TABLE_NODE__INCOMING_CONNECTIONS:
            case PigmapPackage.TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS:
                refreshTargetConnections();
                break;
            }
            break;
        case Notification.REMOVE:
        case Notification.REMOVE_MANY:
            switch (featureId) {
            case PigmapPackage.TABLE_NODE__OUTGOING_CONNECTIONS:
            case PigmapPackage.TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS:
            case PigmapPackage.TABLE_NODE__FILTER_OUT_GOING_CONNECTIONS:
                refreshSourceConnections();
                break;
            case PigmapPackage.TABLE_NODE__INCOMING_CONNECTIONS:
            case PigmapPackage.TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS:
                refreshTargetConnections();

            }
            break;
        }

    }

    @Override
    public void performRequest(Request req) {
        if (RequestConstants.REQ_DIRECT_EDIT.equals(req.getType())) {
            Figure figure = null;
            DirectEditRequest drequest = (DirectEditRequest) req;
            Point figureLocation = drequest.getLocation();
            if (getFigure() instanceof PigMapTableNodeFigure) {
                PigMapTableNodeFigure tableNodeFigure = (PigMapTableNodeFigure) getFigure();
                ArrayList collection = new ArrayList();
                collection.add(tableNodeFigure.getExpressionFigure());
                collection.add(tableNodeFigure.getBranchContent());
                figure = (Figure) tableNodeFigure.findFigureAt(figureLocation.x, figureLocation.y, new FigureSearch(collection));
            }
            if (figure instanceof IWidgetCell) {
                directEditManager = new PigMapNodeDirectEditManager(this, new PigMapNodeCellEditorLocator(figure, this));
            }
            if (directEditManager != null) {
                TableNode tableNode = (TableNode) getModel();
                if (figure instanceof ExpressionFigure) {
                    if (PigMapUtil.isExpressionEditable(tableNode)) {
                        Point location = drequest.getLocation();
                        if (figure.containsPoint(location)) {
                            directEditManager.show();
                            ((PigMapGraphicViewer) getViewer()).getMapperManager().setCurrentDirectEditManager(directEditManager);
                        }
                    }
                } else if (!(((TableNode) getModel()).eContainer() instanceof AbstractInOutTable)) {
                }
            }
        }
        super.performRequest(req);
    }

    public PigMapDataEditPart getMapDataEditPart() {
        List children2 = getViewer().getRootEditPart().getChildren();
        if (children2.size() == 1 && children2.get(0) instanceof PigMapDataEditPart) {
            return (PigMapDataEditPart) children2.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        IFigure figure = null;
        boolean forceDarshDot = false;
        if (getRootAnchor() != null) {
            figure = getRootAnchor();
            forceDarshDot = true;
        } else {
            figure = getFigure();
        }
        if (connection instanceof PigMapLookupConnectionPart) {
            return new LookupColumnAnchor(figure, connection, entityManger);
        }
        if (connection instanceof PigMapFilterConnectionPart) {
            return new FilterColumnAnchor(figure, connection, entityManger);
        }
        return new ConnectionColumnAnchor(figure, connection, entityManger, forceDarshDot);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public ConnectionAnchor getSourceConnectionAnchor(Request arg0) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
     */
    @Override
    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        IFigure figure = null;
        boolean forceDarshDot = false;
        if (getRootAnchor() != null) {
            figure = getRootAnchor();
            forceDarshDot = true;
        } else {
            figure = getFigure();
        }
        if (connection instanceof PigMapLookupConnectionPart) {
            return new LookupColumnAnchor(figure, connection, entityManger);
        }
        return new ConnectionColumnAnchor(figure, connection, entityManger, forceDarshDot);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
     */
    @Override
    public ConnectionAnchor getTargetConnectionAnchor(Request arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * DOC hcyi TableNodeEditPart class global comment. Detailled comment
     */
    class FigureSearch implements TreeSearch {

        final Collection collection;

        public FigureSearch(Collection collection) {
            this.collection = collection;
        }

        @Override
        public boolean accept(IFigure figure) {
            return collection.contains(figure);
        }

        @Override
        public boolean prune(IFigure figure) {
            return false;
        }
    }
}
