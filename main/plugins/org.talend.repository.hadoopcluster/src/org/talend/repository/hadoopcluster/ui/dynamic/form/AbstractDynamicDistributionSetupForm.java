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
package org.talend.repository.hadoopcluster.ui.dynamic.form;

import org.eclipse.swt.widgets.Composite;
import org.talend.commons.exception.LoginException;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.CorePlugin;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.DynamicDistributionManager;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionPreference;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionsGroup;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.repository.ProjectManager;
import org.talend.repository.RepositoryWorkUnit;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicDistributionSetupData;
import org.talend.repository.hadoopcluster.ui.dynamic.DynamicDistributionSetupData.ActionType;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public abstract class AbstractDynamicDistributionSetupForm extends AbstractDynamicDistributionForm {

    private boolean isDebugging = false;

    public AbstractDynamicDistributionSetupForm(Composite parent, int style, DynamicDistributionSetupData configData) {
        super(parent, style, configData);
        isDebugging = CorePlugin.getDefault().isDebugging();
    }

    protected void saveDynamicDistribution(IDynamicPlugin dynamicPlugin, IDynamicDistributionsGroup dynDistrGroup,
            ActionType actionType, IDynamicMonitor dMonitor) throws Exception {
        IDynamicPluginConfiguration pluginConfiguration = dynamicPlugin.getPluginConfiguration();
        dMonitor.beginTask(
                Messages.getString("AbstractDynamicDistributionSetupForm.progress.saving", pluginConfiguration.getName()), //$NON-NLS-1$
                IDynamicMonitor.UNKNOWN);
        // step 1: clean unused modules
        IDynamicDistributionPreference dynamicDistributionPreference = dynDistrGroup
                .getDynamicDistributionPreference(ProjectManager.getInstance().getCurrentProject());
        DynamicPluginAdapter pluginAdapter = new DynamicPluginAdapter(dynamicPlugin, dynamicDistributionPreference);
        pluginAdapter.cleanUnusedAndRefresh();

        // step 2: save
        final IDynamicPlugin fDynPlugin = pluginAdapter.getPlugin();
        ProxyRepositoryFactory.getInstance().executeRepositoryWorkUnit(new RepositoryWorkUnit<Boolean>(Messages
                .getString("AbstractDynamicDistributionSetupForm.repositoryWorkUnit.title", pluginConfiguration.getName())) { //$NON-NLS-1$

            @Override
            protected void run() throws LoginException, PersistenceException {
                result = false;
                try {
                    DynamicDistributionManager.getInstance().saveUsersDynamicPlugin(fDynPlugin, dMonitor);
                } catch (Exception e) {
                    throw new PersistenceException(e);
                }
                result = true;
            }

        });

        // step 3: regist
        dMonitor.setTaskName(
                Messages.getString("AbstractDynamicDistributionSetupForm.progress.registing", pluginConfiguration.getName())); //$NON-NLS-1$
        if (ActionType.EditExisting.equals(actionType)) {
            dynDistrGroup.unregist(fDynPlugin, dMonitor);
        }
        dynDistrGroup.regist(fDynPlugin, dMonitor);

        // step 4: reset system cache
        dMonitor.setTaskName(Messages.getString("AbstractDynamicDistributionSetupForm.progress.resetCache")); //$NON-NLS-1$
        DynamicDistributionManager.getInstance().resetSystemCache();
    }

    protected boolean isDebuging() {
        return isDebugging;
    }

}
