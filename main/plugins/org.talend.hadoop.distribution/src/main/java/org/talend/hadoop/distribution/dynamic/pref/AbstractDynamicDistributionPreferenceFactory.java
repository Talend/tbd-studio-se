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
package org.talend.hadoop.distribution.dynamic.pref;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.model.general.Project;
import org.talend.core.runtime.CoreRuntimePlugin;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicDistributionPreferenceFactory implements IDynamicDistributionPreferenceFactory {

    private Map<String, IDynamicDistributionPreference> projectLabelPreferenceMap = new HashMap<>();

    abstract protected IDynamicDistributionPreference newPreferenceInstance(ScopedPreferenceStore store);

    @Override
    public IDynamicDistributionPreference getDynamicDistributionPreference(Project project) throws Exception {

        String projectTechLabel = project.getTechnicalLabel();
        IDynamicDistributionPreference distributionPreference = projectLabelPreferenceMap.get(projectTechLabel);
        if (distributionPreference == null) {
            String qualifier = CoreRuntimePlugin.getInstance().getProjectPreferenceManager().getQualifier();
            IProject iProject = ResourceUtils.getProject(projectTechLabel);
            if (iProject == null) {
                throw new Exception("Can't find project: " + projectTechLabel);
            }
            ProjectScope projectScope = new ProjectScope(iProject);
            ScopedPreferenceStore store = new ScopedPreferenceStore(projectScope, qualifier);
            distributionPreference = newPreferenceInstance(store);
            projectLabelPreferenceMap.put(projectTechLabel, distributionPreference);
        }
        return distributionPreference;
    }

    @Override
    public void clearAllPreferenceCache() throws Exception {
        if (projectLabelPreferenceMap == null || projectLabelPreferenceMap.isEmpty()) {
            return;
        }

        for (IDynamicDistributionPreference preference : projectLabelPreferenceMap.values()) {
            try {
                preference.clearCache();
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }

        projectLabelPreferenceMap.clear();
    }

}
