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
package org.talend.designer.pigmap.policy;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.swt.graphics.Color;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.designer.gefabstractmap.dnd.TransferedObject;
import org.talend.designer.gefabstractmap.part.MapperTablePart;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.gefabstractmap.resource.ColorInfo;
import org.talend.designer.gefabstractmap.resource.ColorProviderMapper;
import org.talend.designer.pigmap.commands.InsertNewTableNodeCommand;
import org.talend.designer.pigmap.commands.UpdateExpressionCommand;
import org.talend.designer.pigmap.commands.UpdateFilterExpressionCommand;
import org.talend.designer.pigmap.dnd.CreateNodeConnectionRequest;
import org.talend.designer.pigmap.dnd.DropType;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class DragAndDropEditPolicy extends GraphicalEditPolicy {

    private InsertionIndicator indicator;

    @Override
    public EditPart getTargetEditPart(Request request) {
        if (request instanceof CreateNodeConnectionRequest) {
            return getHost();
        }
        return null;
    }

    @Override
    public Command getCommand(Request request) {
        if (request instanceof CreateNodeConnectionRequest) {
            CreateNodeConnectionRequest rq = (CreateNodeConnectionRequest) request;
            EditPart targetEditPart = rq.getTargetEditPart();
            Command command = null;
            if (targetEditPart != null && TemplateTransfer.getInstance().getObject() instanceof TransferedObject) {
                TransferedObject toDrop = (TransferedObject) TemplateTransfer.getInstance().getObject();
                MapperManager manager = ((PigMapGraphicViewer) targetEditPart.getViewer()).getMapperManager();
                if (manager != null && manager.getExternalData() != null) {
                    DropType dropType = rq.getNewObjectType();
                    if (dropType != null) {
                        switch (dropType) {
                        case DROP_FILTER:
                            if (targetEditPart instanceof MapperTablePart) {
                                command = new UpdateFilterExpressionCommand(toDrop, (MapperTablePart) targetEditPart,
                                        manager.getExternalData());
                            }
                            break;
                        case DROP_EXPRESSION:
                            if (targetEditPart instanceof TableEntityPart) {
                                command = new UpdateExpressionCommand(toDrop, (TableEntityPart) targetEditPart, manager);
                            }
                            break;
                        case DROP_INSERT_OUTPUT:
                        case DROP_INSERT_INPUT:
                            command = new InsertNewTableNodeCommand(toDrop, targetEditPart, rq, manager, dropType);
                        default:
                            break;
                        }
                    }
                }
            }

            if (command != null) {
                return command;
            }
        }
        return super.getCommand(request);
    }

    @Override
    public void showTargetFeedback(Request request) {
        if (request instanceof CreateNodeConnectionRequest) {
            CreateNodeConnectionRequest rq = (CreateNodeConnectionRequest) request;
            AbstractGraphicalEditPart targetEditPart = (AbstractGraphicalEditPart) rq.getTargetEditPart();
            // show feedback when insert new column
            if (rq.getNewObjectType() != null && targetEditPart != null) {

                Object model = targetEditPart.getModel();
                switch (rq.getNewObjectType()) {
                case DROP_INSERT_OUTPUT:
                    //
                    if (model instanceof TableNode) {
                        IFigure targetFigure = targetEditPart.getFigure();
                        if (targetFigure != null) {
                            if (indicator == null) {
                                indicator = new InsertionIndicator();
                            }

                            Rectangle copy = targetFigure.getBounds().getCopy();
                            Rectangle bounds = new Rectangle();
                            bounds.x = copy.x - 5;
                            bounds.y = copy.y - 5;
                            bounds.width = copy.width + 10;
                            bounds.height = 10;
                            indicator.setBounds(bounds);
                            getFeedbackLayer().add(indicator);

                        }
                    } else {
                        if (indicator != null && getFeedbackLayer() != null && indicator.getParent() == getFeedbackLayer()) {
                            getFeedbackLayer().remove(indicator);
                        }
                    }
                    break;
                }
            }

        }
    }

    @Override
    public void eraseTargetFeedback(Request request) {
        if (indicator != null) {
            if (request instanceof CreateNodeConnectionRequest) {
                CreateNodeConnectionRequest rq = (CreateNodeConnectionRequest) request;
                AbstractGraphicalEditPart targetEditPart = (AbstractGraphicalEditPart) rq.getTargetEditPart();
                if (targetEditPart != null && getFeedbackLayer() != null && indicator.getParent() == getFeedbackLayer()) {
                    try {
                        getFeedbackLayer().remove(indicator);
                    } catch (Exception e) {
                        ExceptionHandler.process(e);
                    }
                }
            }
        }
    }

    class InsertionIndicator extends Shape {

        @Override
        protected void fillShape(Graphics graphics) {

        }

        @Override
        protected void outlineShape(Graphics graphics) {
            Color color = ColorProviderMapper.getColor(ColorInfo.COLOR_DRAGGING_INSERTION_INDICATOR);
            // Color color = ColorConstants.red;

            Point start = new Point(getBounds().x + 5, getBounds().y + 5);
            Point end = new Point(getBounds().x + getBounds().width - 5, getBounds().y + 5);

            graphics.setBackgroundColor(color);
            graphics.setForegroundColor(color);

            graphics.drawLine(start, end);
            graphics.fillPolygon(new int[] { start.x, start.y, start.x - 5, start.y + 5, start.x - 5, start.y - 5 });
            graphics.fillPolygon(new int[] { end.x, end.y, end.x + 5, end.y - 5, end.x + 5, end.y + 5 });
        }
    }
}
