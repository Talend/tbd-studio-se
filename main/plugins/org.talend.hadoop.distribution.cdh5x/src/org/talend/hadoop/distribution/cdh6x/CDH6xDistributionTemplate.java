// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.cdh6x;

import org.talend.hadoop.distribution.cdh5x.CDH5xDistributionTemplate;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
@SuppressWarnings("nls")
public class CDH6xDistributionTemplate extends CDH5xDistributionTemplate implements ICDH6xDistributionTemplate {

    public final static String TEMPLATE_ID = "CDH6xDistributionTemplate";

    public CDH6xDistributionTemplate(DynamicPluginAdapter pluginAdapter) throws Exception {
        super(pluginAdapter);
    }

    @Override
    public String getTemplateId() {
        return TEMPLATE_ID;
    }

}
