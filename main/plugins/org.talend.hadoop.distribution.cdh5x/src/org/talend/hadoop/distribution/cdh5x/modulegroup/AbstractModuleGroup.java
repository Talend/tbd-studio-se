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
package org.talend.hadoop.distribution.cdh5x.modulegroup;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;

/**
 * abstract module group for all cdh5x module group classes, defines all common methods
 */
public abstract class AbstractModuleGroup {

    private DynamicPluginAdapter pluginAdapter;

    public AbstractModuleGroup(DynamicPluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }


    public DynamicPluginAdapter getPluginAdapter() {
        return this.pluginAdapter;
    }

    public void setPluginAdapter(DynamicPluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }

    protected void checkRuntimeId(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            String message = this.getClass().getSimpleName()
                    + ": Can't find runtime id, please make sure that your generated json is not broken and your template has already been configured correctly.";
            Level level = LogManager.getRootLogger().getLevel();
            if (level != null) {
                if (level.getSyslogEquivalent() < Level.INFO_INT) {
                    System.out.println(message);
                }
            }
            CommonExceptionHandler.warn(message);
        }
    }
}
