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
package org.talend.designer.pigmap.commands;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.window.Window;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.designer.gefabstractmap.dnd.TransferdType;
import org.talend.designer.gefabstractmap.dnd.TransferedObject;
import org.talend.designer.pigmap.dnd.CreateNodeConnectionRequest;
import org.talend.designer.pigmap.dnd.DropType;
import org.talend.designer.pigmap.i18n.Messages;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.ui.tabs.MapperManager;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class InsertNewTableNodeCommand extends Command {

    private EditPart targetEditPart;

    private PigMapData pigMapData;

    private TransferedObject objects;

    private DropType dropType;

    private MapperManager manager;

    private CreateNodeConnectionRequest rq;

    public InsertNewTableNodeCommand(TransferedObject objects, EditPart targetEditPart, CreateNodeConnectionRequest rq,
            MapperManager manager, DropType dropType) {
        this.objects = objects;
        this.targetEditPart = targetEditPart;
        this.rq = rq;
        this.manager = manager;
        this.pigMapData = manager.getExternalData();
        this.dropType = dropType;
    }

    @Override
    public void execute() {
        if (rq == null || objects.getToTransfer() == null || targetEditPart == null || dropType == null) {
            return;
        }
        Object targetModel = targetEditPart.getModel();
        for (Object obj : objects.getToTransfer()) {
            AbstractNode createdNode = rq.getNewObject();
            // target InputTable
            boolean isLookup = false;
            InputTable targetInputTable = null;
            if (targetModel != null && targetModel instanceof InputTable) {
                targetInputTable = (InputTable) targetModel;
                isLookup = ((InputTable) targetModel).isLookup();
            }
            // INPUT => OUTPUT INSERT
            if (objects.getType() == TransferdType.INPUT) {
                PigMapTableNodePart part = (PigMapTableNodePart) obj;
                InputTable sourceInputTable = null;
                if (part.getParent() != null && part.getParent() instanceof PigMapInputTablePart) {
                    sourceInputTable = (InputTable) part.getParent().getModel();
                } else if (part.getParent() != null && part.getParent() instanceof PigMapOutputTablePart) {
                    // now not allow dnd the output table node
                    return;
                }
                TableNode sourceNode = (TableNode) part.getModel();
                // expression
                String expression = null;
                if (isLookup || targetModel instanceof OutputTable) {
                    expression = sourceInputTable.getName() + "." + sourceNode.getName();//$NON-NLS-1$
                }
                switch (dropType) {
                case DROP_INSERT_OUTPUT:
                    int index = -1;
                    OutputTable outputTable = null;
                    if (targetModel instanceof TableNode) {
                        TableNode targetNode = (TableNode) targetModel;
                        if (targetNode.eContainer() instanceof OutputTable) {
                            outputTable = (OutputTable) targetNode.eContainer();
                            index = outputTable.getNodes().indexOf(targetNode);
                        }
                    } else if (targetModel instanceof OutputTable) {
                        outputTable = (OutputTable) targetModel;
                    }
                    if (outputTable != null) {
                        boolean fillNode = fillTableNode(outputTable.getNodes(), sourceNode, outputTable.getName(), expression,
                                (TableNode) createdNode);
                        if (!fillNode) {
                            return;
                        }
                        if (index != -1) {
                            outputTable.getNodes().add(index, (TableNode) createdNode);
                        } else {
                            outputTable.getNodes().add((TableNode) createdNode);
                        }
                        PigMapUtil.createConnection(sourceNode, createdNode, pigMapData);
                        AbstractInOutTable abstractTable = PigMapUtil.getAbstractInOutTable(sourceNode);
                        createOutputMetadataColumn(abstractTable.getName(), outputTable.getName(), sourceNode,
                                createdNode.getName(), index);
                    }
                    break;
                case DROP_INSERT_INPUT:
                    boolean fillNode = fillTableNode(targetInputTable.getNodes(), sourceNode, targetInputTable.getName(),
                            expression, (TableNode) createdNode);
                    if (!fillNode) {
                        return;
                    }
                    targetInputTable.getNodes().add((TableNode) createdNode);
                    PigMapUtil.createLookupConnection(sourceNode, (TableNode) createdNode, pigMapData);
                    AbstractInOutTable abstractTable = PigMapUtil.getAbstractInOutTable(sourceNode);
                    createInputMetadataColumn(abstractTable.getName(), targetInputTable.getName(), sourceNode,
                            createdNode.getName(), -1);
                default:
                    break;
                }
            }
        }

        switch (dropType) {
        case DROP_INSERT_OUTPUT:
            manager.getMapperUI().getTabFolderEditors().getOutputMetaEditorView().getTableViewerCreator().refresh();
        case DROP_INSERT_INPUT:
            manager.getMapperUI().getTabFolderEditors().getOutputMetaEditorView().getTableViewerCreator().refresh();
        }

    }

    private void createInputMetadataColumn(String sourceTreeName, String targetTreeName, TableNode sourceNode,
            String targetNodeName, int index) {
        String sourceNodeName = sourceNode.getName();
        IMetadataTable metadataTarget = null;
        List<IODataComponent> inputs = manager.getMapperComponent().getIODataComponents().getInputs();
        for (IODataComponent incoming : inputs) {
            if (targetTreeName != null && targetTreeName.equals(incoming.getConnection().getName())) {
                metadataTarget = incoming.getTable();
            }
        }

        if (metadataTarget != null) {
            IMetadataColumn columnSource = null;
            if (sourceTreeName != null) {
                columnSource = getSourceColumn(sourceTreeName, sourceNodeName);
            }
            creatMeatadataColumn(columnSource, targetNodeName, sourceNode, metadataTarget, index);
        }
    }

    private void createOutputMetadataColumn(String sourceTreeName, String targetTreeName, AbstractNode sourceNode,
            String targetNodeName, int index) {
        String sourceNodeName = sourceNode.getName();
        IMetadataTable metadataTarget = null;
        List<IMetadataTable> metadataTargets = manager.getMapperComponent().getMetadataList();
        if (metadataTargets != null) {
            for (IMetadataTable target : metadataTargets) {
                if (target.getTableName().equals(targetTreeName)) {
                    metadataTarget = target;
                }
            }
        }
        if (metadataTarget != null) {
            IMetadataColumn columnSource = null;
            if (sourceTreeName != null) {
                columnSource = getSourceColumn(sourceTreeName, sourceNodeName);
            }
            creatMeatadataColumn(columnSource, targetNodeName, sourceNode, metadataTarget, index);
        }
    }

    private void creatMeatadataColumn(IMetadataColumn columnSource, String targetNodeName, AbstractNode sourceNode,
            IMetadataTable metadataTarget, int index) {

        IMetadataColumn createNewColumn = null;
        if (columnSource != null) {
            // dnd schema column
            createNewColumn = new MetadataColumn(columnSource);
            createNewColumn.setLabel(targetNodeName);
        }
        if (index != -1) {
            metadataTarget.getListColumns().add(index, createNewColumn);
        } else {
            metadataTarget.getListColumns().add(createNewColumn);
        }

    }

    private IMetadataColumn getSourceColumn(String sourceTreeName, String sourceNodeName) {
        List<IODataComponent> inputs = manager.getMapperComponent().getIODataComponents().getInputs();
        IMetadataColumn columnSource = null;
        for (IODataComponent incoming : inputs) {
            if (sourceTreeName != null && sourceTreeName.equals(incoming.getConnection().getName())) {
                IMetadataTable metadataSource = incoming.getTable();
                for (IMetadataColumn column : metadataSource.getListColumns()) {
                    if (column.getLabel().equals(sourceNodeName)) {
                        columnSource = column;
                        break;
                    }
                }
                break;
            }
        }
        return columnSource;

    }

    private String validSourceNodeName(final List<? extends AbstractNode> validationList, TableNode sourceNode) {
        String sourceName = sourceNode.getName();
        boolean isValidate = MetadataToolHelper.isValidColumnName(sourceName);
        boolean fixing = false;
        if (!isValidate) {
            if (sourceName.contains(":")) { //$NON-NLS-1$
                if (sourceNode.eContainer() instanceof TreeNode) {
                    if (!fixing) {
                        sourceName = sourceName.substring(sourceName.indexOf(":"), sourceName.length()); //$NON-NLS-1$
                        fixing = true;
                    }
                }
            }
            if (!fixing || !MetadataToolHelper.isValidColumnName(sourceName)) {
                IInputValidator validataor = new IInputValidator() {

                    @Override
                    public String isValid(String newText) {
                        //
                        return "";
                    }

                };
                InputDialog dialog = new InputDialog(
                        null,
                        Messages.getString("InsertNewColumnCommand_createNew"), Messages.getString("InsertNewColumnCommand_message"), sourceName, validataor); //$NON-NLS-1$ //$NON-NLS-2$
                int open = dialog.open();
                if (open == Window.CANCEL) {
                    return null;
                } else {
                    sourceName = dialog.getValue();
                }
            }
        }

        return sourceName;
    }

    private boolean fillTableNode(final List<? extends AbstractNode> validationList, TableNode sourceNode,
            String sourceTableName, String expression, TableNode target) {
        String validSourceName = validSourceNodeName(validationList, sourceNode);
        String name = getUniqueTableEntry(validationList, validSourceName);
        target.setName(name);
        target.setType(sourceNode.getType());
        target.setExpression(expression);
        return true;
    }

    private String getUniqueTableEntry(List<? extends AbstractNode> nodeExisted, String nameToCreate) {
        boolean exists = true;
        int counter = 1;
        String newName = nameToCreate;
        while (exists) {
            boolean found = false;
            for (AbstractNode node : nodeExisted) {
                // TDI-26953: drag-and-drop column name should case-sensitive
                if (node.getName().equalsIgnoreCase(newName)) {
                    found = true;
                    break;
                }
            }
            exists = found;
            if (!exists) {
                break;
            }
            newName = nameToCreate + "_" + counter++; //$NON-NLS-1$
        }
        return newName;
    }

}
