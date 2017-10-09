// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh5x.handlers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.designer.maven.aether.node.ExclusionNode;
import org.talend.designer.maven.aether.util.DynamicDistributionAetherUtils;
import org.talend.hadoop.distribution.cdh5x.CDH5xDistributionTemplate;
import org.talend.hadoop.distribution.cdh5x.CDH5xPlugin;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.constants.cdh.IClouderaDistribution;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicTemplateAdapter;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class TestDialog extends Dialog {

    private Text templateText;

    private Text generatedText;

    private Text hadoopVersionText;

    private Text hadoopVersionDisplayText;

    private Button generateButton;

    private Text jsonLocationText;

    private Button registButton;

    private static Map<String, IDynamicPlugin> registedPluginMap = new HashMap<>();

    private static Map<String, ServiceRegistration> registedOsgiServiceMap = new HashMap<>();

    protected TestDialog(Shell parentShell) {
        super(parentShell);
        int style = getShellStyle();
        // style = style ^ SWT.APPLICATION_MODAL;
        // style = style | SWT.MIN;
        setShellStyle(style);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        // create a composite with standard margins and spacing
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new FormLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite generateFromTemplate = createGenereateFromTemplate(composite);

        Composite registFromJson = createRegistFromJson(composite, generateFromTemplate);

        // createUtilsGroup(composite, registFromJson);

        applyDialogFont(composite);
        addListeners();
        return composite;
    }

    private Composite createGenereateFromTemplate(Composite composite) {
        Group group = new Group(composite, SWT.NONE);
        group.setText("Generate json from template:");
        group.setLayout(new FormLayout());
        FormData groupFormData = new FormData();
        groupFormData.left = new FormAttachment(0, 10);
        groupFormData.right = new FormAttachment(100, -10);
        groupFormData.top = new FormAttachment(0, 10);
        group.setLayoutData(groupFormData);
        
        Label templateLabel = new Label(group, SWT.NONE);
        templateLabel.setText("Template location:");
        FormData templateLabelFD = new FormData();
        templateLabelFD.top = new FormAttachment(0, 10);
        templateLabelFD.left = new FormAttachment(0, 10);
        templateLabel.setLayoutData(templateLabelFD);
        
        templateText = new Text(group, SWT.BORDER);
        FormData templateTextFD = new FormData();
        templateTextFD.top = new FormAttachment(templateLabel, 0, SWT.CENTER);
        templateTextFD.left = new FormAttachment(templateLabel, 10, SWT.RIGHT);
        templateTextFD.right = new FormAttachment(100, -10);
        templateText.setLayoutData(templateTextFD);
        
        Label generatedLabel = new Label(group, SWT.NONE);
        generatedLabel.setText("Generated json path:");
        FormData generatedLabelFD = new FormData();
        generatedLabelFD.top = new FormAttachment(templateLabel, 10, SWT.BOTTOM);
        generatedLabelFD.left = new FormAttachment(templateLabel, 0, SWT.LEFT);
        generatedLabel.setLayoutData(generatedLabelFD);
        
        generatedText = new Text(group, SWT.BORDER);
        FormData generatedTextFD = new FormData();
        generatedTextFD.top = new FormAttachment(generatedLabel, 0, SWT.CENTER);
        generatedTextFD.left = new FormAttachment(generatedLabel, 10, SWT.RIGHT);
        generatedTextFD.right = new FormAttachment(100, -10);
        generatedText.setLayoutData(generatedTextFD);

        Label hadoopVersionLabel = new Label(group, SWT.NONE);
        hadoopVersionLabel.setText("Cloudera version:");
        FormData hadoopVersionLabelFD = new FormData();
        hadoopVersionLabelFD.left = new FormAttachment(generatedLabel, 0, SWT.LEFT);
        hadoopVersionLabelFD.top = new FormAttachment(generatedLabel, 10, SWT.BOTTOM);
        hadoopVersionLabel.setLayoutData(hadoopVersionLabelFD);

        hadoopVersionText = new Text(group, SWT.BORDER);
        FormData hadoopVersionTextFD = new FormData();
        hadoopVersionTextFD.top = new FormAttachment(hadoopVersionLabel, 0, SWT.CENTER);
        hadoopVersionTextFD.left = new FormAttachment(hadoopVersionLabel, 10, SWT.RIGHT);
        hadoopVersionTextFD.right = new FormAttachment(100, -10);
        hadoopVersionText.setLayoutData(hadoopVersionTextFD);

        Label hadoopVersionDisplayLabel = new Label(group, SWT.NONE);
        hadoopVersionDisplayLabel.setText("Cloudera version display:");
        FormData hadoopVersionDisplayLabelFD = new FormData();
        hadoopVersionDisplayLabelFD.left = new FormAttachment(hadoopVersionLabel, 0, SWT.LEFT);
        hadoopVersionDisplayLabelFD.top = new FormAttachment(hadoopVersionLabel, 10, SWT.BOTTOM);
        hadoopVersionDisplayLabel.setLayoutData(hadoopVersionDisplayLabelFD);

        hadoopVersionDisplayText = new Text(group, SWT.BORDER);
        FormData hadoopVersionDisplayTextFD = new FormData();
        hadoopVersionDisplayTextFD.top = new FormAttachment(hadoopVersionDisplayLabel, 0, SWT.CENTER);
        hadoopVersionDisplayTextFD.left = new FormAttachment(hadoopVersionDisplayLabel, 10, SWT.RIGHT);
        hadoopVersionDisplayTextFD.right = new FormAttachment(100, -10);
        hadoopVersionDisplayText.setLayoutData(hadoopVersionDisplayTextFD);
        
        generateButton = new Button(group, SWT.PUSH);
        generateButton.setText("Generate");
        FormData generateButtonFD = new FormData();
        generateButtonFD.top = new FormAttachment(hadoopVersionDisplayLabel, 10, SWT.BOTTOM);
        generateButtonFD.left = new FormAttachment(hadoopVersionDisplayLabel, 0, SWT.LEFT);
        generateButtonFD.bottom = new FormAttachment(100, -10);
        generateButton.setLayoutData(generateButtonFD);

        return group;
    }
    
    private Composite createRegistFromJson(Composite composite, Composite generateFromTemplate) {

        Group group = new Group(composite, SWT.NONE);
        group.setText("Regist service from json:");
        group.setLayout(new FormLayout());
        FormData groupFormData = new FormData();
        groupFormData.left = new FormAttachment(generateFromTemplate, 0, SWT.LEFT);
        groupFormData.right = new FormAttachment(generateFromTemplate, 0, SWT.RIGHT);
        groupFormData.top = new FormAttachment(generateFromTemplate, 10, SWT.BOTTOM);
        group.setLayoutData(groupFormData);

        Label jsonLocationLabel = new Label(group, SWT.NONE);
        jsonLocationLabel.setText("Json location:");
        FormData jsonLocationLabelFD = new FormData();
        jsonLocationLabelFD.top = new FormAttachment(0, 10);
        jsonLocationLabelFD.left = new FormAttachment(0, 10);
        jsonLocationLabel.setLayoutData(jsonLocationLabelFD);

        jsonLocationText = new Text(group, SWT.BORDER);
        FormData templateTextFD = new FormData();
        templateTextFD.top = new FormAttachment(jsonLocationLabel, 0, SWT.CENTER);
        templateTextFD.left = new FormAttachment(jsonLocationLabel, 10, SWT.RIGHT);
        templateTextFD.right = new FormAttachment(100, -10);
        jsonLocationText.setLayoutData(templateTextFD);

        registButton = new Button(group, SWT.PUSH);
        registButton.setText("Regist");
        FormData generateButtonFD = new FormData();
        generateButtonFD.top = new FormAttachment(jsonLocationLabel, 10, SWT.BOTTOM);
        generateButtonFD.left = new FormAttachment(jsonLocationLabel, 0, SWT.LEFT);
        generateButtonFD.bottom = new FormAttachment(100, -10);
        registButton.setLayoutData(generateButtonFD);

        return group;
    }

    private Composite createUtilsGroup(Composite composite, Composite topComposite) {

        Group group = new Group(composite, SWT.NONE);
        group.setText("Utils");
        group.setLayout(new FormLayout());
        FormData groupFormData = new FormData();
        groupFormData.left = new FormAttachment(topComposite, 0, SWT.LEFT);
        groupFormData.right = new FormAttachment(topComposite, 0, SWT.RIGHT);
        groupFormData.top = new FormAttachment(topComposite, 10, SWT.BOTTOM);
        group.setLayoutData(groupFormData);

        Button collectDependenciesBtn = new Button(group, SWT.PUSH);
        collectDependenciesBtn.setText("Collect Dependencies");
        FormData collectDependenciesBtnFD = new FormData();
        collectDependenciesBtnFD.top = new FormAttachment(0, 10);
        collectDependenciesBtnFD.left = new FormAttachment(0, 10);
        collectDependenciesBtnFD.bottom = new FormAttachment(100, -10);
        collectDependenciesBtn.setLayoutData(collectDependenciesBtnFD);
        collectDependenciesBtn.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                btnPushed(e);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        return group;

    }

    private void btnPushed(SelectionEvent e) {
        collectDependencis(e);
    }

    private void collectDependencis(SelectionEvent e) {
        String remoteUrl = "http://localhost:8081/nexus/content/repositories/central";
        String localPath = "C:/Users/cmeng/.m2/repository";
        String groupId = "org.apache.hadoop";
        String artifactId = "hadoop-auth";
        String version = "2.6.0-cdh5.12.1";
        String classifier = "";
        String scope = "compile";
        // String scope = "runtime";
        IDynamicMonitor monitor = new IDynamicMonitor() {

            @Override
            public void writeMessage(String message) {
                try {
                    System.out.print(message);
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        };

        List<ExclusionNode> exclusionNodes = new ArrayList<>();

        ExclusionNode exclusion1 = new ExclusionNode();
        exclusion1.setGroupId("org.slf4j");
        exclusion1.setArtifactId("slf4j-api");
        exclusionNodes.add(exclusion1);

        try {
            DependencyNode baseNode = new DependencyNode();
            baseNode.setGroupId(groupId);
            baseNode.setArtifactId(artifactId);
            baseNode.setClassifier(classifier);
            baseNode.setScope(scope);
            baseNode.setVersion(version);
            baseNode.setExclusions(exclusionNodes);
            DependencyNode node = DynamicDistributionAetherUtils.collectDepencencies(remoteUrl, localPath, baseNode, monitor);
            node.getDependencies();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    protected void addListeners() {
        generateButton.addSelectionListener(new SelectionListener() {
            
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileOutputStream outStream = null;
                FileOutputStream logStream = null;
                BufferedOutputStream bufferedLogStream = null;
                try {
                    ObjectMapper om = new ObjectMapper();
                    TemplateBean bean = om.readValue(new File(templateText.getText()), TemplateBean.class);
                    DynamicConfiguration configuration = new DynamicConfiguration();
                    configuration.setDistribution(IClouderaDistribution.DISTRIBUTION_NAME);
                    configuration.setVersion(hadoopVersionText.getText());
                    String id = hadoopVersionText.getText();
                    id = id.replaceAll("\\.", "_");
                    id = id.toUpperCase();
                    id = "Cloudera_CDH5_X_" + id;
                    configuration.setId(id);
                    configuration.setName(hadoopVersionDisplayText.getText());
                    DynamicTemplateAdapter templateAdapter = new DynamicTemplateAdapter(bean, configuration);

                    // logStream = new FileOutputStream(new File(generatedText.getText() + ".log"));
                    // bufferedLogStream = new BufferedOutputStream(logStream);
                    final BufferedOutputStream bos = bufferedLogStream;

                    IDynamicMonitor monitor = new IDynamicMonitor() {

                        @Override
                        public void writeMessage(String message) {
                            try {
                                // bos.write(message.getBytes("UTF-8"));
                                // bos.write("\n".getBytes("UTF-8"));
                                Level level = LogManager.getRootLogger().getLevel();
                                if (level != null) {
                                    if (level.getSyslogEquivalent() < Level.INFO_INT) {
                                        System.out.print(message);
                                    }
                                } else {
                                    System.out.print(message);
                                }
                                ExceptionHandler.log(message);
                            } catch (Exception e) {
                                ExceptionHandler.process(e);
                            }
                        }
                    };

                    templateAdapter.adapt(monitor);
                    IDynamicPlugin dynamicPlugin = templateAdapter.getDynamicPlugin();
                    String content = DynamicServiceUtil.formatJsonString(dynamicPlugin.toXmlJson().toString());
                    File outFile = new File(generatedText.getText());
                    outStream = new FileOutputStream(outFile);
                    outStream.write(content.getBytes("UTF-8"));
                    outStream.flush();
                    MessageDialog.openInformation(getShell(), "Succeed", "Json file generated at " + generatedText.getText());
                } catch (Exception ex) {
                    ExceptionHandler.process(ex);
                    MessageDialog.openError(getShell(), "Failed", "Generate failed, please check error log.");
                } finally {
                    if (outStream != null) {
                        try {
                            outStream.close();
                        } catch (Exception e1) {
                            ExceptionHandler.process(e1);
                        }
                    }
                    if (logStream != null) {
                        try {
                            logStream.close();
                        } catch (Exception e1) {
                            ExceptionHandler.process(e1);
                        }
                    }
                    if (bufferedLogStream != null) {
                        try {
                            bufferedLogStream.close();
                        } catch (Exception e1) {
                            ExceptionHandler.process(e1);
                        }
                    }
                }
            }
            
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // nothing to do
            }
        });

        registButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    String jsonContent = DynamicServiceUtil.readFile(new File(jsonLocationText.getText()));
                    IDynamicPlugin dynamicPlugin = DynamicFactory.getInstance().createPluginFromJson(jsonContent);
                    DynamicPluginAdapter pluginAdapter = new DynamicPluginAdapter(dynamicPlugin);
                    pluginAdapter.adapt();
                    // System.out.println(dynamicPlugin.toXmlString());
                    CDH5xDistributionTemplate cdhService = new CDH5xDistributionTemplate(pluginAdapter);

                    CDH5xPlugin cdh5xPlugin = CDH5xPlugin.getInstance();

                    Bundle bundle = cdh5xPlugin.getBundle();

                    IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
                    String id = pluginConfiguration.getId();

                    IDynamicPlugin registedPlugin = registedPluginMap.get(id);
                    if (registedPlugin != null) {
                        DynamicServiceUtil.removeContribution(registedPlugin);
                    }
                    ServiceRegistration registedOsgiService = registedOsgiServiceMap.get(id);
                    if (registedOsgiService != null) {
                        DynamicServiceUtil.unregistOSGiService(registedOsgiService);
                    }

                    DynamicServiceUtil.addContribution(bundle, dynamicPlugin);
                    registedPluginMap.put(id, dynamicPlugin);
                    
                    BundleContext context = bundle.getBundleContext();
                    ServiceRegistration osgiService = DynamicServiceUtil.registOSGiService(context,
                            new String[] { HadoopComponent.class.getName(), HDFSComponent.class.getName(),
                                    HBaseComponent.class.getName(), HCatalogComponent.class.getName(),
                                    HiveComponent.class.getName(), HiveOnSparkComponent.class.getName(),
                                    ImpalaComponent.class.getName(), MRComponent.class.getName(), PigComponent.class.getName(),
                                    SqoopComponent.class.getName(), SparkBatchComponent.class.getName(),
                                    SparkStreamingComponent.class.getName() },
                            cdhService, null);
                    registedOsgiServiceMap.put(id, osgiService);

                    MessageDialog.openInformation(getShell(), "Succeed", "Service regist successfully!");
                } catch (Exception ex) {
                    ExceptionHandler.process(ex);
                    MessageDialog.openError(getShell(), "Failed", "Regist service failed, please check error log.");
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // nothing to do
            }
        });
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Test Dialog");
        shell.setSize(600, 600);
    }
}
