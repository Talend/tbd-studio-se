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

import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class DynamicExtensionAdapter extends AbstractDynamicAdapter {

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public DynamicExtensionAdapter(TemplateBean templateBean, DynamicConfiguration configuration) {
        super(templateBean, configuration);
    }

}
