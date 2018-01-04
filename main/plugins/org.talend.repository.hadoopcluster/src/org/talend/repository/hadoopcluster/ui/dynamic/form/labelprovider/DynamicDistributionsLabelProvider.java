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
package org.talend.repository.hadoopcluster.ui.dynamic.form.labelprovider;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.LabelProvider;
import org.talend.core.model.general.Project;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.dynamic.DynamicConstants;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.i18n.Messages;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistributionsLabelProvider extends LabelProvider {

    @Override
    public String getText(Object element) {
        if (element instanceof IDynamicPlugin) {
            IDynamicPluginConfiguration pluginConfiguration = ((IDynamicPlugin) element).getPluginConfiguration();
            String name = pluginConfiguration.getName();
            String isBuildinStr = (String) pluginConfiguration.getAttribute(DynamicConstants.ATTR_IS_BUILDIN);
            boolean isBuildin = Boolean.valueOf(isBuildinStr);
            if (isBuildin) {
                name = Messages.getString("DynamicDistributionsLabelProvider.label.existing.buildin", name); //$NON-NLS-1$
            } else {
                Project curProj = ProjectManager.getInstance().getCurrentProject();
                String curProjTechName = curProj.getTechnicalLabel();
                String projTechName = (String) pluginConfiguration.getAttribute(DynamicConstants.ATTR_PROJECT_TECHNICAL_NAME);
                if (StringUtils.equals(curProjTechName, projTechName)) {
                    name = Messages.getString("DynamicDistributionsLabelProvider.label.existing.currentProject", name); //$NON-NLS-1$
                } else {
                    name = Messages.getString("DynamicDistributionsLabelProvider.label.existing.otherProject", name, //$NON-NLS-1$
                            projTechName);
                }
            }
            return name;
        } else {
            return element == null ? "" : element.toString();//$NON-NLS-1$
        }
    }

}
