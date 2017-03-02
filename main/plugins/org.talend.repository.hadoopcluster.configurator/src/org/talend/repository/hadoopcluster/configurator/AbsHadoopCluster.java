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
package org.talend.repository.hadoopcluster.configurator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Date;

import org.eclipse.swt.widgets.Display;
import org.talend.core.model.components.ComponentCategory;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.context.ContextUtils;
import org.talend.core.model.process.AbstractNode;
import org.talend.core.model.process.IContext;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.ui.component.ComponentsFactoryProvider;
import org.talend.designer.core.model.process.DataNode;
import org.talend.designer.runprocess.IProcessor;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.designer.runprocess.RunProcessContext;
import org.talend.designer.runprocess.remote.model.TargetExecutionConfiguration;
import org.talend.designer.runprocess.remote.ui.RemoteManager;

public abstract class AbsHadoopCluster implements HadoopCluster {

    // Retrieve configuration job server
    protected TargetExecutionConfiguration retrieveJobServer;

    public TargetExecutionConfiguration getRetrieveJobServer() {
        return retrieveJobServer;
    }

    public void setRetrieveJobServer(TargetExecutionConfiguration retrieveJobServer) {
        this.retrieveJobServer = retrieveJobServer;
    }

    // TODO --KK
    public void retrieveConfigurationByJobServer(String targetFolderPath) throws Exception {
        System.out.println("kkkkkk: begin get configuration files by job server:" + new Date());
        IComponent component = getComponent();
        if (component != null) {
            AbstractNode node = new DataNode(component, "RetrieveNode");
            RetrieveConfigurationContextManager contextManager = new RetrieveConfigurationContextManager();
            RetrieveConfigurationProcess retrieveProcess = new RetrieveConfigurationProcess(getProcessProperty(), node, contextManager);
            TargetExecutionConfiguration originTargetServerConf = (TargetExecutionConfiguration) RemoteManager.getInstance()
                    .getTargetExecutionconfigureation();
            final IContext contextByName = ContextUtils.getContextByName(contextManager, "RetrieveJob",
                    false);
            IProcessor processor = ProcessorUtilities.getProcessor(retrieveProcess, retrieveProcess.getProperty(), contextByName);
            try {
                RetrieveSocketServer server = new RetrieveSocketServer();
                server.setEcnoding("utf-8");
                server.start();
                
                RemoteManager.getInstance().setTargetExecution(retrieveJobServer);
                RunProcessContext runProcessContext = new RunProcessContext(retrieveProcess);
                runProcessContext.exec(Display.getDefault().getActiveShell());
                StringBuilder output = new StringBuilder();
               // waitForProcess(output, processor, server);
            } catch (Exception ex) {

            } finally {
                RemoteManager.getInstance().setTargetExecution(originTargetServerConf);
            }
        }
        System.out.println("kkkkkk: stop get configuration files by job server:" + new Date());
    }

    private Property getProcessProperty() {
        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setLabel("RetrieveConfiguration"); //$NON-NLS-1$ //$NON-NLS-2$
        property.setId("RetrieveConfiguration"); //$NON-NLS-1$ //$NON-NLS-2$
        return  property;
    }
    
    // TODO --KK
    protected IComponent getComponent() {
        return ComponentsFactoryProvider.getInstance().get("tJava", ComponentCategory.CATEGORY_4_DI.getName());
    }
    
    private void waitForProcess(final StringBuilder output, Process process,
            RetrieveSocketServer sockServer) throws InterruptedException, IOException {
        while (true) {

            Thread.sleep(100);

            if (!sockServer.isDonetSocketInput()) {

                if (process.getErrorStream() != null && process.getErrorStream().available() > 0) {
                    readErrorStream(output, process);
                }

                break;
            }

            if (process.getErrorStream() != null && process.getErrorStream().available() > 0) {
                readErrorStream(output, process);
                break;
            }
        }
    }
    
    private void readErrorStream(final StringBuilder output, Process process) throws IOException {
        int read = 0;
        byte[] bytes = new byte[8192];
        BufferedInputStream stream = new BufferedInputStream(process.getErrorStream(), 8192);
        while (read >= 0) {
            read = stream.read(bytes);
            if (read > 0) {
                output.append(new String(bytes, 0, read));
            }
        }
    }
}
