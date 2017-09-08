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
package org.talend.designer.hdfsbrowse.controllers;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.process.BigDataNode;
import org.talend.core.model.process.EParameterFieldType;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IContextManager;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.Item;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.repository.model.connection.ConnectionStatus;
import org.talend.core.ui.CoreUIPlugin;
import org.talend.core.ui.properties.tab.IDynamicProperty;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.components.EParameterName;
import org.talend.designer.core.model.components.EmfComponent;
import org.talend.designer.core.model.process.DataNode;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.properties.controllers.AbstractElementPropertySectionController;
import org.talend.designer.core.ui.editor.properties.controllers.creator.SelectAllTextControlCreator;
import org.talend.designer.hdfsbrowse.HDFSPlugin;
import org.talend.designer.hdfsbrowse.i18n.Messages;
import org.talend.designer.hdfsbrowse.manager.EHadoopParameter;
import org.talend.designer.hdfsbrowse.manager.HadoopMappingManager;
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.hadoop.distribution.constants.dataproc.IGoogleDataprocDistribution;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public abstract class AbstractHDFSBrowseController extends AbstractElementPropertySectionController {

    protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

    public AbstractHDFSBrowseController(IDynamicProperty dp) {
        super(dp);
    }

    protected Command createCommand(SelectionEvent event, HDFSConnectionBean connection) {
        HDFSBrowseDialog dial = new HDFSBrowseDialog(composite.getShell(), getHDFSType(), connection);
        Button btn = (Button) event.getSource();
        String propertyName = (String) btn.getData(PARAMETER_NAME);
        Text filePathText = (Text) hashCurControls.get(propertyName);
        if (dial.open() == Window.OK) {
            IHDFSNode result = dial.getResult();
            String path = result.getPath();
            path = TalendQuoteUtils.addQuotesIfNotExist(path);
            if (!elem.getPropertyValue(propertyName).equals(path)) {
                filePathText.setText(path);
                return new PropertyChangeCommand(elem, propertyName, path);
            }

        }
        return null;
    }

    protected abstract EHadoopFileTypes getHDFSType();

    protected abstract String getControllerName();

    protected HDFSConnectionBean getHDFSConnectionBean() {
        INode node = (INode) elem;
        IProcess process = node.getProcess();
        IContext context = null;
        IContextManager cm = process.getContextManager();
        if (cm != null) {
            context = cm.getDefaultContext();
        }

        boolean isMr = false;
        HDFSConnectionBean connectionBean = new HDFSConnectionBean();
        String useExistingConnection = ElementParameterParser.getValue(elem, "__USE_EXISTING_CONNECTION__"); //$NON-NLS-1$
        if ("true".equalsIgnoreCase(useExistingConnection)) { //$NON-NLS-1$
            String connectionName = ElementParameterParser.getValue(node, "__CONNECTION__"); //$NON-NLS-1$
            List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
            for (INode iNode : nodes) {
                if (iNode.getUniqueName().equals(connectionName)) {
                    node = iNode;
                    break;
                }
            }
        } else if (node != null && node.getComponent() != null && node.getComponent().getPaletteType() != null
                && node.getComponent().getPaletteType().equals("MR")) { //$NON-NLS-1$
            List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
            for (INode iNode : nodes) {
                IComponent component = iNode.getComponent();
                if (component != null && component.getName() != null && component.getName().equals("tMRConfiguration")) { //$NON-NLS-1$
                    isMr = true;
                    node = iNode;
                    if (node instanceof DataNode || node instanceof BigDataNode) {
                        String versionParameter = (String) getParameterValue(node, EHadoopParameter.VERSION.getName());
                        if (versionParameter != null) {
                            connectionBean.setDfVersion(versionParameter);
                        }
                        String nameNodeParameter = getParameterValueWithContext(node, context,
                                EHadoopParameter.NAMENODE_URI.getName());
                        if (nameNodeParameter != null) {
                            connectionBean.setNameNodeURI(nameNodeParameter);
                        }
                    }
                }
            }
            String distribution = (String) getParameterValue(node, EHadoopParameter.DISTRIBUTION.getName());
            if(IGoogleDataprocDistribution.DISTRIBUTION_NAME.equals(distribution)) {
                displayUnsupportedOperationDialog();
                return null;
            }
        } else if (node != null && node.getComponent() != null && node.getComponent().getPaletteType() != null
                && node.getComponent().getPaletteType().startsWith("SPARK")) { //$NON-NLS-1$
            // SPARK and SPARKSTREAMING
            boolean browseIsSupported = false;
            boolean defineStorageConfiguration = (Boolean) getParameterValue(node, "DEFINE_STORAGE_CONFIGURATION"); //$NON-NLS-1$
            if (defineStorageConfiguration) {
                String configurationComponent = (String) getParameterValue(node, "STORAGE_CONFIGURATION"); //$NON-NLS-1$
                if (configurationComponent.startsWith("tHDFSConfiguration")) { //$NON-NLS-1$
                    List<? extends INode> nodes = node.getProcess().getGeneratingNodes();
                    for (INode iNode : nodes) {
                        if (configurationComponent.equals(iNode.getUniqueName())) {
                            browseIsSupported = true;
                            isMr = true;
                            node = iNode;
                            String versionParameter = (String) getParameterValue(node, EHadoopParameter.VERSION.getName());
                            if (versionParameter != null) {
                                connectionBean.setDfVersion(versionParameter);
                            }
                            String nameNodeParameter = getParameterValueWithContext(node, context,
                                    EHadoopParameter.NAMENODE_URI.getName());
                            if (nameNodeParameter != null) {
                                connectionBean.setNameNodeURI(nameNodeParameter);
                            }
                        }
                    }
                }
            }
            if (!browseIsSupported) {
                displayUnsupportedOperationDialog();
                return null;
            }
        }

        String distribution = (String) getParameterValue(node, EHadoopParameter.DISTRIBUTION.getName());
        if (!isMr) {
            String version = (String) getParameterValue(node, EHadoopParameter.VERSION.getName());
            String nameNodeUri = getParameterValueWithContext(node, context, EHadoopParameter.NAMENODE_URI.getName());
            connectionBean.setDfVersion(version);
            connectionBean.setNameNodeURI(nameNodeUri);
        }
        // check support the group or not
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(connectionBean.getDfVersion());
        if (version4Drivers != null && version4Drivers.isSupportGroup()) {
            String group = getParameterValueWithContext(node, context, EHadoopParameter.GROUP.getName());
            connectionBean.setGroup(group);
        }
        String userName = getParameterValueWithContext(node, context, EHadoopParameter.USERNAME.getName());
        Boolean useKrb = (Boolean) getParameterValue(node, EHadoopParameter.USE_KRB.getName());
        Boolean useMaprTicket = (Boolean) getParameterValue(node, EHadoopParameter.USE_MAPRTICKET.getName());
        String nnPrincipal = getParameterValueWithContext(node, context, EHadoopParameter.NAMENODE_PRINCIPAL.getName());
        Boolean useKeytab = (Boolean) getParameterValue(node, EHadoopParameter.USE_KEYTAB.getName());
        String ktPrincipal = getParameterValueWithContext(node, context, EHadoopParameter.PRINCIPAL.getName());
        String ktPath = getParameterValueWithContext(node, context, EHadoopParameter.KEYTAB_PATH.getName());
        Boolean isUseCustom = EHadoopDistributions.CUSTOM.getName().equals(distribution);
        if (isUseCustom) {
            Object authMode = getParameterValue(node, EHadoopParameter.AUTHENTICATION_MODE.getName());
            if ("KRB".equals(authMode)) { //$NON-NLS-1$
                useKrb = true;
            }
        }

        if (!isMr && node != null && node.getComponent() != null && node.getComponent().getName().equals("tHDFSInput")) {
            if ("true".equalsIgnoreCase(useExistingConnection) || useKrb || (distribution.equals("MAPR") && !useMaprTicket)) {
                userName = "";
            }
        }
        String customJars = (String) getParameterValue(node, EHadoopParameter.HADOOP_CUSTOM_JARS.getName());

        connectionBean.setDistribution(distribution);
        connectionBean.setUserName(userName);
        connectionBean.setEnableKerberos(useKrb != null ? useKrb : false);
        connectionBean.setPrincipal(nnPrincipal);
        connectionBean.setUseKeytab(useKeytab != null ? useKeytab : false);
        connectionBean.setKeytabPrincipal(ktPrincipal);
        connectionBean.setKeytab(ktPath);
        connectionBean.setUseCustomVersion(isUseCustom != null ? isUseCustom : false);
        if (StringUtils.isNotBlank(customJars)) {
            connectionBean.getAdditionalProperties().put(ECustomVersionGroup.COMMON.getName(), customJars);
        }

        if (process instanceof IProcess2) {
            IProcess2 pro = (IProcess2) process;
            connectionBean.setRelativeHadoopClusterId(pro.getProperty().getId());
            IElementParameter propertyParam = node.getElementParameterFromField(EParameterFieldType.PROPERTY_TYPE);
            if (propertyParam != null) {
                IElementParameter repositoryType = propertyParam.getChildParameters().get(EParameterName.PROPERTY_TYPE.getName());
                if (repositoryType != null && EmfComponent.REPOSITORY.equals(repositoryType.getValue())) {
                    IHadoopClusterService hadoopClusterService = HadoopRepositoryUtil.getHadoopClusterService();
                    String relativeHadoopClusterId = null;
                    IElementParameter repositoryId = propertyParam.getChildParameters().get(
                            (EParameterName.REPOSITORY_PROPERTY_TYPE.getName()));
                    if (hadoopClusterService != null) {
                        Item item = hadoopClusterService.getHadoopClusterBySubitemId(((String) repositoryId.getValue()));
                        if (item != null) {
                            relativeHadoopClusterId = item.getProperty().getId();
                            if (relativeHadoopClusterId != null) {
                                connectionBean.setRelativeHadoopClusterId(relativeHadoopClusterId);
                            }
                        }
                    }
                }
            }
        }

        connectionBean.getConfigurations().putAll(getHadoopProperties(node, context));

        return connectionBean;
    }

    protected Map<String, Object> getHadoopProperties(INode node, IContext context) {
        Map<String, Object> propertiesMap = new HashMap<>();
        Object parameterValue = getParameterValue(node, EHadoopParameter.HADOOP_ADVANCED_PROPERTIES.getName());
        if (parameterValue instanceof List) {
            List propertiesList = (List) parameterValue;
            for (Object obj : propertiesList) {
                if (obj instanceof Map) {
                    Map<String, Object> propertyMap = (Map) obj;
                    String key = (String) propertyMap.get("PROPERTY"); //$NON-NLS-1$
                    Object value = propertyMap.get("VALUE"); //$NON-NLS-1$
                    if (key != null && value != null) {
                        key = TalendQuoteUtils.removeQuotesIfExist(key);
                        if (value instanceof String) {
                            String strValue = TalendQuoteUtils.removeQuotesIfExist((String) value);
                            if (context != null && ContextParameterUtils.isContainContextParam(strValue)) {
                                strValue = ContextParameterUtils.parseScriptContextCode(strValue, context);
                            }
                            value = strValue;
                        }
                        propertiesMap.put(key, value);
                    }
                }
            }
        }
        return propertiesMap;
    }

    protected String getParameterValueWithContext(IElement ele, IContext context, String paramName) {
        Object parameterValue = getParameterValue(ele, paramName);
        if (parameterValue != null && context != null) {
            if (parameterValue instanceof String) {
                return ContextParameterUtils.parseScriptContextCode((String) parameterValue, context);
            } else if (parameterValue instanceof List) {
                // for jdbc parm driver jars
                String jarValues = "";
                List list = (List) parameterValue;
                for (int i = 0; i < list.size(); i++) {
                    Object object = list.get(i);
                    if (object instanceof Map) {
                        Map valueMap = (Map) object;
                        if (valueMap.get("JAR_NAME") != null) {
                            if (jarValues.equals("")) {
                                jarValues = jarValues + valueMap.get("JAR_NAME");
                            } else {
                                jarValues = jarValues + ";" + valueMap.get("JAR_NAME");
                            }
                        }
                    }
                }
                return ContextParameterUtils.parseScriptContextCode(jarValues, context);
            }

        }
        return "";
    }

    private Object getParameterValue(IElement node, String paramName) {
        Map<String, List<String>> componentParamsMap = HadoopMappingManager.getInstance().getComponentParamsMap();
        List<String> paramslist = componentParamsMap.get(paramName);
        if (paramslist != null && paramslist.size() > 0) {
            Object value = null;
            for (String param : paramslist) {
                value = getTheParameterValue(node, param);
                if (value != null) {
                    return value;
                }
            }
        }

        return getTheParameterValue(node, paramName);
    }

    private Object getTheParameterValue(IElement node, String paramName) {
        Object value = null;
        if (node instanceof DataNode || node instanceof BigDataNode) {
            IElementParameter parameter = node.getElementParameter(paramName);
            if (parameter != null) {
                value = parameter.getValue();
            }
        } else {
            value = node.getPropertyValue(paramName);
        }

        return value;
    }

    protected boolean checkHDFSConnection(final HDFSConnectionBean connection) {
        AtomicBoolean result = new AtomicBoolean(true);
        IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(Messages.getString("AbstractHDFSBrowseController.checkConnection"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                ConnectionStatus connectionStatus = HadoopOperationManager.getInstance().testConnection(connection);
                if (!connectionStatus.getResult()) {
                    PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                        @Override
                        public void run() {
                            String mainMsg = Messages.getString("AbstractHDFSBrowseController.connectionFailure.mainMsg"); //$NON-NLS-1$
                            new ErrorDialogWidthDetailArea(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                                    HDFSPlugin.PLUGIN_ID, mainMsg, connectionStatus.getMessageException());
                            result.set(false);
                            return;
                        }
                    });
                }
            }
        };
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
        try {
            dialog.run(true, true, runnableWithProgress);
        } catch (Exception e) {
            result.set(false);
            ExceptionHandler.process(e);
        }

        return result.get();
    }

    @Override
    public Control createControl(final Composite subComposite, final IElementParameter param, final int numInRow,
            final int nbInRow, final int top, final Control lastControl) {
        this.curParameter = param;
        FormData data;

        CLabel labelLabel = getWidgetFactory().createCLabel(subComposite, param.getDisplayName());
        data = new FormData();
        if (lastControl != null) {
            data.left = new FormAttachment(lastControl, 0);
        } else {
            data.left = new FormAttachment((((numInRow - 1) * MAX_PERCENT) / nbInRow), 0);
        }
        data.top = new FormAttachment(0, top);
        labelLabel.setLayoutData(data);
        if (numInRow != 1) {
            labelLabel.setAlignment(SWT.RIGHT);
        }

        Button btnEdit = getWidgetFactory().createButton(subComposite, EMPTY_STRING, SWT.PUSH);

        btnEdit.setImage(ImageProvider.getImage(CoreUIPlugin.getImageDescriptor(DOTS_BUTTON)));

        data = new FormData();
        data.left = new FormAttachment(((numInRow * MAX_PERCENT) / nbInRow), -STANDARD_BUTTON_WIDTH);
        data.right = new FormAttachment(((numInRow * MAX_PERCENT) / nbInRow), 0);
        data.top = new FormAttachment(labelLabel, 0, SWT.CENTER);
        data.height = STANDARD_HEIGHT - 2;
        btnEdit.setLayoutData(data);
        btnEdit.setData(NAME, getControllerName());
        btnEdit.setData(PARAMETER_NAME, param.getName());
        btnEdit.setEnabled(!param.isReadOnly());
        btnEdit.addSelectionListener(listenerSelection);

        DecoratedField dField = new DecoratedField(subComposite, SWT.BORDER, new SelectAllTextControlCreator());
        if (param.isRequired()) {
            FieldDecoration decoration = FieldDecorationRegistry.getDefault().getFieldDecoration(
                    FieldDecorationRegistry.DEC_REQUIRED);
            dField.addFieldDecoration(decoration, SWT.RIGHT | SWT.TOP, false);
        }

        Control cLayout = dField.getLayoutControl();
        Text labelText = (Text) dField.getControl();

        cLayout.setBackground(subComposite.getBackground());
        if (!elem.isReadOnly()) {
            labelText.setEditable(!param.isReadOnly() && !param.isRepositoryValueUsed());
        } else {
            labelText.setEditable(false);
        }
        labelText.setData(PARAMETER_NAME, param.getName());

        editionControlHelper.register(param.getName(), labelText);

        addDragAndDropTarget(labelText);
        if (elem instanceof Node) {
            labelText.setToolTipText(VARIABLE_TOOLTIP + param.getVariableName());
        }

        // **************************
        data = new FormData();
        int currentLabelWidth = STANDARD_LABEL_WIDTH;
        GC gc = new GC(labelLabel);
        Point labelSize = gc.stringExtent(param.getDisplayName());
        gc.dispose();

        if ((labelSize.x + ITabbedPropertyConstants.HSPACE) > currentLabelWidth) {
            currentLabelWidth = labelSize.x + ITabbedPropertyConstants.HSPACE;
        }

        if (numInRow == 1) {
            if (lastControl != null) {
                data.left = new FormAttachment(lastControl, currentLabelWidth);
            } else {
                data.left = new FormAttachment(0, currentLabelWidth);
            }
        } else {
            data.left = new FormAttachment(labelLabel, 0, SWT.RIGHT);
        }
        data.right = new FormAttachment(btnEdit, 0);
        data.top = new FormAttachment(labelLabel, 0, SWT.CENTER);
        cLayout.setLayoutData(data);

        hashCurControls.put(param.getName(), labelText);

        Point initialSize = dField.getLayoutControl().computeSize(SWT.DEFAULT, SWT.DEFAULT);

        dynamicProperty.setCurRowSize(initialSize.y + ITabbedPropertyConstants.VSPACE);

        return btnEdit;
    }

    @Override
    public int estimateRowSize(Composite subComposite, IElementParameter param) {
        DecoratedField dField = new DecoratedField(subComposite, SWT.BORDER, new TextControlCreator());
        Point initialSize = dField.getLayoutControl().computeSize(SWT.DEFAULT, SWT.DEFAULT);
        dField.getLayoutControl().dispose();

        return initialSize.y + ITabbedPropertyConstants.VSPACE;
    }

    private SelectionListener listenerSelection = new SelectionListener() {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {

        }

        @Override
        public void widgetSelected(SelectionEvent event) {
            HDFSConnectionBean connection = getHDFSConnectionBean();
            if (connection != null && checkHDFSConnection(connection)) {
                Command command = createCommand(event, connection);
                executeCommand(command);
            }
        }

    };

    @Override
    public void refresh(IElementParameter param, boolean checkErrorsWhenViewRefreshed) {
        Text labelText = (Text) hashCurControls.get(param.getName());
        Object value = param.getValue();
        if (labelText == null || labelText.isDisposed()) {
            return;
        }
        boolean valueChanged = false;
        if (value == null) {
            labelText.setText(EMPTY_STRING);
        } else {
            if (!value.equals(labelText.getText())) {
                labelText.setText((String) value);
                valueChanged = true;
            }
        }
        if (checkErrorsWhenViewRefreshed || valueChanged) {
            checkErrorsForPropertiesOnly(labelText);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
    }

    private void displayUnsupportedOperationDialog() {
        IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

            @Override
            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                monitor.beginTask(
                        Messages.getString("AbstractHDFSBrowseController.checkConnection"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

                    @Override
                    public void run() {
                        new ErrorDialogWidthDetailArea(
                                PlatformUI.getWorkbench().getDisplay().getActiveShell(),
                                HDFSPlugin.PLUGIN_ID,
                                Messages.getString("AbstractHDFSBrowseController.unsupportedOperation.warningMsg"), //$NON-NLS-1$
                                Messages.getString("AbstractHDFSBrowseController.unsupportedOperation.detailMsg1")); //$NON-NLS-1$
                        return;
                    }
                });
            }
        };
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
        try {
            dialog.run(true, true, runnableWithProgress);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }
}
