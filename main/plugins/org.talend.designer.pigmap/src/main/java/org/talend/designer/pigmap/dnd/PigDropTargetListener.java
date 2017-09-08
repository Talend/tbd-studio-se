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
package org.talend.designer.pigmap.dnd;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.talend.designer.gefabstractmap.dnd.MapperDropTargetListener;
import org.talend.designer.gefabstractmap.dnd.TransferedObject;
import org.talend.designer.gefabstractmap.figures.sash.ISash;
import org.talend.designer.pigmap.model.emf.pigmap.NodeType;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigDropTargetListener extends MapperDropTargetListener {

    private IFigure targetFigure;

    public PigDropTargetListener(EditPartViewer viewer) {
        super(viewer);
    }

    @Override
    protected Request createTargetRequest() {
        CreateNodeConnectionRequest request = new CreateNodeConnectionRequest(getTargetEditPart());
        return request;
    }

    @Override
    protected void updateTargetEditPart() {
        super.updateTargetEditPart();
        Point dropLocation = getDropLocation();
        if (getTargetEditPart() != null) {
            targetFigure = ((AbstractGraphicalEditPart) getTargetEditPart()).getFigure().findFigureAt(dropLocation.x,
                    dropLocation.y);
        }
    }

    @Override
    public void dragEnter(DropTargetEvent event) {
    }

    @Override
    public void dragLeave(DropTargetEvent event) {

    }

    @Override
    public void dragOperationChanged(DropTargetEvent event) {

    }

    @Override
    public void handleDragOver() {
        getCurrentEvent().detail = DND.DROP_NONE;
        getCurrentEvent().feedback = DND.FEEDBACK_NONE;
        updateTargetRequest();
        updateTargetEditPart();

        DropTargetEvent event = getCurrentEvent();

        Object object = TemplateTransfer.getInstance().getObject();
        if (object == null) {
            event.detail = DND.DROP_NONE;
            return;
        }

        // dnd the sash
        if (object instanceof ISash) {
            handleSashDrag(event, (ISash) object);
            return;
        }

        // dnd the table node
        if (!(object instanceof TransferedObject)) {
            event.detail = DND.DROP_NONE;
            return;
        }

        DropContextAnalyzer analyzer = new DropContextAnalyzer((TransferedObject) object,
                (AbstractGraphicalEditPart) getTargetEditPart(), getDropLocation());
        if (analyzer.isDropValid()) {
            event.detail = analyzer.getDetail();
            NewNodeCreationFactory factory = new NewNodeCreationFactory(analyzer.getDropType(), null);
            getCreateRequest().setFactory(factory);
        }

        showTargetFeedback();
    }

    @Override
    public void drop(DropTargetEvent event) {
        setCurrentEvent(event);
        eraseTargetFeedback();
        handleDrop();
        unload();
    }

    @Override
    protected void handleDrop() {
        final Object object = TemplateTransfer.getInstance().getObject();
        if (object == null || !(object instanceof TransferedObject)) {
            return;
        }
        updateTargetRequest();
        updateTargetEditPart();

        DropContextAnalyzer analyzer = new DropContextAnalyzer((TransferedObject) object,
                (AbstractGraphicalEditPart) getTargetEditPart(), getDropLocation());
        if (analyzer.isDropValid()) {
            NodeType selectedNodeType = NodeType.ELEMENT;
            DropType dropType = analyzer.getDropType();
            NewNodeCreationFactory factory = new NewNodeCreationFactory(dropType, selectedNodeType);
            getCreateRequest().setFactory(factory);
        }

        if (getTargetEditPart() != null) {
            Command command = getCommand();
            if (command != null && command.canExecute()) {
                getViewer().getEditDomain().getCommandStack().execute(command);
            } else {
                getCurrentEvent().detail = DND.DROP_NONE;
            }
        } else {
            getCurrentEvent().detail = DND.DROP_NONE;
        }

        selectAddedObject();
    }

    private void selectAddedObject() {
        List newObjects = getCreateRequest().getNewObjects();
        for (int i = 0; i < newObjects.size(); i++) {
            Object model = newObjects.get(i);
            EditPartViewer viewer = getViewer();
            viewer.getControl().forceFocus();
            Object editpart = viewer.getEditPartRegistry().get(model);
            boolean added = false;
            if (editpart instanceof EditPart) {
                if (!added) {
                    // Force a layout first.
                    getViewer().flush();
                    viewer.select((EditPart) editpart);
                    added = true;
                } else {
                    viewer.appendSelection((EditPart) editpart);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#isEnabled(org.eclipse.swt.dnd.DropTargetEvent)
     */
    @Override
    public boolean isEnabled(DropTargetEvent event) {
        final Object object = TemplateTransfer.getInstance().getObject();
        if (object instanceof ISash) {
            return true;
        }
        return super.isEnabled(event);
    }

    @Override
    protected void updateTargetRequest() {
        CreateNodeConnectionRequest request = getCreateRequest();
        request.setLocation(getDropLocation());
    }

    protected CreateNodeConnectionRequest getCreateRequest() {
        return ((CreateNodeConnectionRequest) getTargetRequest());
    }

    @Override
    protected void handleExitingEditPart() {
        eraseTargetFeedback();
    }

}
