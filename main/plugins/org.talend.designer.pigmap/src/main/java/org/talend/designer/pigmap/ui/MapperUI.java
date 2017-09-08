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
package org.talend.designer.pigmap.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.runtime.image.ImageUtils.ICON_SIZE;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IExternalNode;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.core.ui.branding.IBrandingService;
import org.talend.core.ui.images.CoreImageProvider;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.cmd.ExternalNodeChangeCommand;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.gefabstractmap.resource.ColorProviderMapper;
import org.talend.designer.gefabstractmap.resource.FontProviderMapper;
import org.talend.designer.gefabstractmap.resource.ImageProviderMapper;
import org.talend.designer.pigmap.PigMapComponent;
import org.talend.designer.pigmap.editor.PigMapEditor;
import org.talend.designer.pigmap.i18n.Messages;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.ui.footer.FooterComposite;
import org.talend.designer.pigmap.ui.tabs.MapperManager;
import org.talend.designer.pigmap.ui.tabs.TabFolderEditors;
import org.talend.designer.pigmap.util.MapDataHelper;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class MapperUI {

    private PigMapComponent mapperComponent;

    private PigMapData copyOfMapData;

    private int mapperResponse;

    private Shell mapperShell;

    private SashForm datasViewSashForm;

    private SashForm mainSashForm;

    private TabFolderEditors tabFolderEditors;

    private MapperManager mapperManager;

    private PigMapEditor editor;

    private FooterComposite footerComposite;

    private boolean closeWithoutPrompt;

    public MapperUI(MapperManager mapperManager) {
        this.mapperManager = mapperManager;
        this.mapperComponent = mapperManager.getMapperComponent();
        this.copyOfMapData = mapperManager.getExternalData();
        mapperManager.setMapperUI(this);

    }

    public Shell createWindow(final Display display) {
        Shell activeShell = display.getActiveShell();
        int style = SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX | SWT.APPLICATION_MODAL | SWT.RESIZE;
        if (activeShell == null) {
            mapperShell = new Shell(mapperShell, style);
        } else {
            mapperShell = new Shell(activeShell, style);
        }

        mapperShell.addShellListener(new ShellListener() {

            @Override
            public void shellActivated(ShellEvent e) {
            }

            @Override
            public void shellClosed(ShellEvent e) {
                if (editor != null && editor.isDirty() && !closeWithoutPrompt) {
                    boolean closeWindow = MessageDialog.openConfirm(mapperShell, "Close without save",//$NON-NLS-1$
                            Messages.getString("MapperUI.CloseWindow.Message"));//$NON-NLS-1$
                    if (!closeWindow) {
                        e.doit = false;
                    } else {
                        prepareClosing(SWT.CANCEL);
                    }
                }
            }

            @Override
            public void shellDeactivated(ShellEvent e) {
            }

            @Override
            public void shellDeiconified(ShellEvent e) {
            }

            @Override
            public void shellIconified(ShellEvent e) {
            }

        });

        mapperShell.setMaximized(true);
        mapperShell.setImage(CoreImageProvider.getComponentIcon(mapperComponent.getComponent(), ICON_SIZE.ICON_32));

        IBrandingService brandingService = (IBrandingService) GlobalServiceRegister.getDefault().getService(
                IBrandingService.class);
        String productName = brandingService.getFullProductName();
        mapperShell.setText(productName
                + " - " + mapperComponent.getComponent().getName() + " - " + mapperComponent.getUniqueName()); //$NON-NLS-1$//$NON-NLS-2$

        GridLayout parentLayout = new GridLayout(1, true);
        mapperShell.setLayout(parentLayout);
        mainSashForm = new SashForm(mapperShell, SWT.SMOOTH | SWT.VERTICAL);
        GridData mainSashFormGridData = new GridData(GridData.FILL_BOTH);
        mainSashForm.setLayoutData(mainSashFormGridData);

        datasViewSashForm = new SashForm(mainSashForm, SWT.SMOOTH | SWT.HORIZONTAL | SWT.BORDER);
        editor = new PigMapEditor(mapperManager);
        editor.createPartControl(datasViewSashForm);

        if (copyOfMapData.getVarTables().isEmpty()) {
            VarTable varTable = PigmapFactory.eINSTANCE.createVarTable();
            varTable.setName(Messages.getString("VarTableContainer.varTable.title"));//$NON-NLS-1$
            varTable.setMinimized(true);
            copyOfMapData.getVarTables().add(varTable);
        }
        editor.setContent(copyOfMapData);

        tabFolderEditors = new TabFolderEditors(mainSashForm, mapperManager, SWT.BORDER);
        mainSashForm.setWeights(new int[] { 70, 30 });

        footerComposite = new FooterComposite(mapperShell, this);
        footerComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        mapperShell.addDisposeListener(new DisposeListener() {

            @Override
            public void widgetDisposed(DisposeEvent e) {
                ColorProviderMapper.releaseColors();
                FontProviderMapper.releaseFonts();
                ImageProviderMapper.releaseImages();
            }

        });

        editor.makeDefaultSelection();
        mapperShell.open();
        return mapperShell;
    }

    public void closeMapperDialog(int response) {
        mapperResponse = response;

        if (response == SWT.OK || response == SWT.APPLICATION_MODAL) {
            prepareClosing(response);
            mapperComponent.setExternalEmfData(copyOfMapData);
            if (response == SWT.APPLICATION_MODAL) {
                IExternalNode externalNode = mapperComponent;
                IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

                if (externalNode != null && (part instanceof AbstractMultiPageTalendEditor)) {
                    INode node = externalNode.getOriginalNode();
                    if (node != null && node instanceof Node) {
                        Command cmd = new ExternalNodeChangeCommand((Node) node, externalNode);
                        CommandStack cmdStack = (CommandStack) part.getAdapter(CommandStack.class);
                        cmdStack.execute(cmd);
                    }
                }
            }

            IElementParameter elementParameter = mapperComponent.getElementParameter("DIE_ON_ERROR");//$NON-NLS-1$
            if (elementParameter != null) {
                elementParameter.setValue(mapperManager.isDieOnError());
            }
            // Send back the change to the tPigLoad node if you modified the configure of define dunctions
            VarTable varTable = copyOfMapData.getVarTables().get(0);
            List<INode> iNodes = MapDataHelper.iNodesDefineFunctions;
            if (varTable != null && iNodes != null) {
                MapDataHelper.convertVarNodesToDefineFunctions(varTable, iNodes);
            }
        }
        if (response == SWT.OK) {
            closeWithoutPrompt = true;
        }
        if (response == SWT.OK || response == SWT.CANCEL) {
            mapperShell.close();
        }
    }

    public void prepareClosing(int response) {
        List<IMetadataTable> newMetadatas = new ArrayList<IMetadataTable>();
        EList<OutputTable> outputTables = null;
        if (response == SWT.OK || response == SWT.APPLICATION_MODAL) {
            // fix for bug TDI-18185
            mapperManager.fireCurrentDirectEditApply();
            // if press ok or apply , use copyOfMapData to check the metadata list
            outputTables = copyOfMapData.getOutputTables();
        } else {
            // if outputTables cancel , use the original mapData
            outputTables = ((PigMapData) mapperComponent.getExternalEmfData()).getOutputTables();
        }

        List<IMetadataTable> copyOfMetadata = new ArrayList<IMetadataTable>(mapperComponent.getMetadataList());
        for (OutputTable outputTable : outputTables) {
            IMetadataTable found = null;
            for (IMetadataTable table : mapperComponent.getMetadataList()) {
                if (outputTable.getName().equals(table.getTableName())) {
                    found = table;
                }
            }
            if (found != null) {
                newMetadatas.add(found);
            } else {
                // create a new metadata if needed
                MetadataTable metadataTable = new MetadataTable();
                metadataTable.setTableName(outputTable.getName());
                mapperComponent.getProcess().addUniqueConnectionName(outputTable.getName());
                List<IMetadataColumn> listColumns = new ArrayList<IMetadataColumn>();
                for (TableNode tableNode : outputTable.getNodes()) {
                    MetadataColumn column = new MetadataColumn();
                    column.setLabel(tableNode.getName());
                    column.setKey(tableNode.isKey());
                    column.setTalendType(tableNode.getType());
                    column.setNullable(tableNode.isNullable());
                    column.setPattern(tableNode.getPattern());
                    listColumns.add(column);
                }
                metadataTable.setListColumns(listColumns);
                newMetadatas.add(metadataTable);
            }
        }
        mapperComponent.setMetadataList(newMetadatas);
        copyOfMetadata.removeAll(newMetadatas);
        List<IODataComponent> outputs = mapperComponent.getIODataComponents().getOuputs();
        List<String> connectionNames = new ArrayList<String>();
        for (IODataComponent output : outputs) {
            connectionNames.add(output.getUniqueName());
        }
        for (IMetadataTable leftTree : copyOfMetadata) {
            if (!connectionNames.contains(leftTree.getTableName())) {
                mapperComponent.getProcess().removeUniqueConnectionName(leftTree.getTableName());
            }
        }
    }

    public int getMapperDialogResponse() {
        return mapperResponse;
    }

    public PigMapComponent getMapperComponent() {
        return mapperComponent;
    }

    public TabFolderEditors getTabFolderEditors() {
        return tabFolderEditors;
    }

    public String openNewOutputCreationDialog() {
        final IProcess process = mapperManager.getMapperComponent().getProcess();
        String outputName = process.generateUniqueConnectionName("out"); //$NON-NLS-1$
        InputDialog id = new InputDialog(mapperShell, "Add a output", "New Output :", outputName, new IInputValidator() {//$NON-NLS-1$//$NON-NLS-2$

                    @Override
                    public String isValid(String newText) {
                        if (!process.checkValidConnectionName(newText)) {
                            return "Output is invalid.";//$NON-NLS-1$
                        }
                        return null;
                    }

                });
        int response = id.open();
        if (response == InputDialog.OK) {
            return id.getValue();
        }
        return null;
    }

    public MapperManager getMapperManager() {
        return this.mapperManager;
    }

    public void updateStatusBar() {
    }
}
