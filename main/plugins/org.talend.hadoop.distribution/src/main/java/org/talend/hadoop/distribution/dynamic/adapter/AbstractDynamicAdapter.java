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
package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.HashMap;
import java.util.Map;

import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicAdapter {

    private boolean resolved;

    private TemplateBean templateBean;

    private DynamicConfiguration configuration;

    private Map<String, Object> attributesMap = new HashMap<>();

    public AbstractDynamicAdapter(TemplateBean templateBean, DynamicConfiguration configuration) {
        this.templateBean = templateBean;
        this.configuration = configuration;
        resolved = false;
    }

    protected void setAttribute(String key, Object value) {
        attributesMap.put(key, value);
    }

    protected Object getAttribute(String key) {
        return attributesMap.get(key);
    }

    public TemplateBean getTemplateBean() {
        return templateBean;
    }

    public void setTemplateBean(TemplateBean templateBean) {
        this.templateBean = templateBean;
    }

    public DynamicConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(DynamicConfiguration configuration) {
        this.configuration = configuration;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    abstract protected void resolve() throws Exception;
}
