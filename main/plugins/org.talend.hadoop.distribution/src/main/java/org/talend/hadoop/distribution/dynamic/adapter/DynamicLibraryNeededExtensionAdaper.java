// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.adapter;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.talend.commons.utils.threading.TalendCustomThreadPoolExecutor;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.ModuleBean;
import org.talend.hadoop.distribution.dynamic.bean.ModuleGroupBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.hadoop.distribution.i18n.Messages;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicLibraryNeededExtensionAdaper extends DynamicExtensionAdapter {

    public static final String TAG_NAME = "extension"; //$NON-NLS-1$

    public static final String ATTR_POINT = "org.talend.core.runtime.librariesNeeded"; //$NON-NLS-1$

    private IDependencyResolver dependencyResolver;

    private Map<String, DynamicModuleAdapter> moduleBeanAdapterMap;

    private Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap;

    private TalendCustomThreadPoolExecutor threadPool = null;

    private boolean enableMultiThread = false;

    public DynamicLibraryNeededExtensionAdaper(TemplateBean templateBean, DynamicConfiguration configuration,
            IDependencyResolver dependencyResolver, Map<String, DynamicModuleAdapter> moduleBeanAdapterMap,
            Map<String, DynamicModuleGroupAdapter> moduleGroupBeanAdapterMap) {
        super(templateBean, configuration);
        this.dependencyResolver = dependencyResolver;
        this.moduleBeanAdapterMap = Collections.synchronizedMap(moduleBeanAdapterMap);
        this.moduleGroupBeanAdapterMap = Collections.synchronizedMap(moduleGroupBeanAdapterMap);
    }

    public void enableMultiThread(boolean enable) {
        this.enableMultiThread = enable;
    }

    public boolean isEnableMultiThread() {
        return this.enableMultiThread;
    }

    public TalendCustomThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        }
        synchronized (this) {
            threadPool = new TalendCustomThreadPoolExecutor(50, 100, 0, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(200), new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return threadPool;
    }

    public IDynamicExtension adapt(IDynamicMonitor monitor) throws Exception {
        DynamicDistributionUtils.checkCancelOrNot(monitor);
        resolve();

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();
        String templateId = templateBean.getId();

        String distributionName = configuration.getDistribution();
        String id = configuration.getId();

        IDynamicExtension libNeededExtension = DynamicFactory.getInstance().createDynamicExtension();
        libNeededExtension.setExtensionId(DynamicDistributionUtils
                .getExtensionId(DynamicDistributionUtils.getPluginKey(distributionName, templateId, id, ATTR_POINT)));
        libNeededExtension.setExtensionPoint(ATTR_POINT);

        List<ModuleBean> modules = templateBean.getModules();

        if (modules != null) {
            try {
                Exception ex[] = new Exception[1];
                Set<String> registedModules = Collections.synchronizedSet(new LinkedHashSet<>());
                for (ModuleBean moduleBean : modules) {
                    DynamicDistributionUtils.checkCancelOrNot(monitor);
                    Runnable runnable = new Runnable() {

                        int count = 0;

                        @Override
                        public void run() {
                            try {
                                DynamicModuleAdapter dynamicModuleAdapter = new DynamicModuleAdapter(templateBean, configuration,
                                        moduleBean, dependencyResolver, registedModules);
                                List<IDynamicConfiguration> librariesNeeded = dynamicModuleAdapter.adapt(monitor,
                                        isEnableMultiThread());
                                if (librariesNeeded != null && !librariesNeeded.isEmpty()) {
                                    addDynamicConfigurations(libNeededExtension, librariesNeeded);
                                }
                                String beanId = moduleBean.getId();
                                moduleBeanAdapterMap.put(beanId, dynamicModuleAdapter);
                            } catch (Exception e) {
                                Throwable rootCause = ExceptionUtils.getRootCause(e);
                                if (e instanceof FileNotFoundException || rootCause instanceof FileNotFoundException) {
                                    if (count < 5) {
                                        count++;
                                        run();
                                    } else {
                                        ex[0] = e;
                                    }
                                } else {
                                    ex[0] = e;
                                }
                            }
                        }
                    };
                    if (isEnableMultiThread()) {
                        getThreadPool().execute(runnable);
                        continue;
                    } else {
                        runnable.run();
                        if (ex[0] != null) {
                            throw ex[0];
                        }
                    }
                }
                if (isEnableMultiThread()) {
                    int totalTasks = modules.size();
                    if (monitor != null) {
                        monitor.beginTask(
                                Messages.getString("DynamicLibraryNeededExtensionAdaper.monitor.waitAllFinish", totalTasks), //$NON-NLS-1$
                                totalTasks);
                    }
                    int completed = 0;
                    while (true) {
                        int activeCount = getThreadPool().getActiveCount();
                        if (activeCount <= 0) {
                            break;
                        }
                        if (monitor != null) {
                            int newCompleted = (int) getThreadPool().getCompletedTaskCount();
                            int newWorked = newCompleted - completed;
                            completed = newCompleted;
                            monitor.setTaskName(Messages.getString("DynamicLibraryNeededExtensionAdaper.monitor.waitAllFinish", //$NON-NLS-1$
                                    activeCount));
                            monitor.worked(newWorked);
                        }
                        Thread.sleep(200);
                        DynamicDistributionUtils.checkCancelOrNot(monitor);
                        if (ex[0] != null) {
                            throw ex[0];
                        }
                    }
                }
            } finally {
                if (isEnableMultiThread()) {
                    if (monitor != null) {
                        monitor.beginTask("", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
                    }
                    getThreadPool().clearThreads();
                    threadPool = null;
                }
            }
        }

        List<ModuleGroupBean> moduleGroups = templateBean.getModuleGroups();
        if (moduleGroups != null) {
            for (ModuleGroupBean moduleGroupBean : moduleGroups) {
                DynamicDistributionUtils.checkCancelOrNot(monitor);
                DynamicModuleGroupAdapter libNeededGroupAdapter = new DynamicModuleGroupAdapter(templateBean, configuration,
                        moduleGroupBean, moduleBeanAdapterMap);
                IDynamicConfiguration dynamicModuleGroup = libNeededGroupAdapter.adapt(monitor);
                if (dynamicModuleGroup != null) {
                    libNeededExtension.addConfiguration(dynamicModuleGroup);
                    String groupId = moduleGroupBean.getId();
                    moduleGroupBeanAdapterMap.put(groupId, libNeededGroupAdapter);
                }
            }
        }

        return libNeededExtension;
    }

    private void addDynamicConfigurations(IDynamicExtension extension, List<IDynamicConfiguration> configurations) {
        synchronized (extension) {
            for (IDynamicConfiguration configuration : configurations) {
                extension.addConfiguration(configuration);
            }
        }
    }

    @Override
    protected void resolve() throws Exception {
        setResolved(true);
    }

    public IDependencyResolver getDependencyResolver() {
        return this.dependencyResolver;
    }

    public void setDependencyResolver(IDependencyResolver dependencyResolver) {
        this.dependencyResolver = dependencyResolver;
    }

}
