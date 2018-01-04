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
package org.talend.hadoop.distribution.dynamic;

import java.util.List;

import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public interface IDynamicDistribution {

    /**
     * must return a sorted list
     */
    public List<String> getCompatibleVersions(IDynamicMonitor monitor) throws Exception;

    public List<String> getAllVersions(IDynamicMonitor monitor) throws Exception;

    public List<String> getSupportedTemplateIds(IDynamicMonitor monitor) throws Exception;

    public List<TemplateBean> getTemplates(IDynamicMonitor monitor) throws Exception;

    public IDynamicPlugin buildDynamicPlugin(IDynamicMonitor monitor, DynamicConfiguration configuration) throws Exception;

    public List<IDynamicPlugin> getAllBuildinDynamicPlugins(IDynamicMonitor monitor) throws Exception;

    public String getDistributionName();

    public void regist(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception;

    public void unregist(IDynamicPlugin dynamicPlugin, IDynamicMonitor monitor) throws Exception;

    public void registAllBuildin(IDynamicMonitor monitor) throws Exception;

    public void unregistAllBuildin(IDynamicMonitor monitor) throws Exception;

}
