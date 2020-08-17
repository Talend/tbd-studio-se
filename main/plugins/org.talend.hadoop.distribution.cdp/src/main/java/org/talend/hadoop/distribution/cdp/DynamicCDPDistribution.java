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
package org.talend.hadoop.distribution.cdp;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.cdp.AbstractDynamicCDPDistribution;
import org.talend.hadoop.distribution.dynamic.template.IDynamicDistributionTemplate;

/**
 * DOC class global comment. Detailled comment
 */
public class DynamicCDPDistribution extends AbstractDynamicCDPDistribution {

	private List<String> templateIds = new ArrayList<>();
	public static final String TEMPLATE_FOLDER_PATH = "resources/template/cdp/"; //$NON-NLS-1$
	public static final String BUILD_IN_FOLDER_PATH = "resources/builtin/cdp/"; //$NON-NLS-1$

	public DynamicCDPDistribution() {
		templateIds.add(CDP7xDistributionTemplate.TEMPLATE_ID);
	}

	@Override
	protected IDynamicDistributionTemplate initTemplate(DynamicPluginAdapter pluginAdapter, IDynamicMonitor monitor)
			throws Exception {
		return new CDP7xDistributionTemplate(pluginAdapter);
	}

	@Override
	protected String getTemplateFolderPath() {
		return TEMPLATE_FOLDER_PATH;
	}

	@Override
	protected Bundle getBundle() {
		CDPPlugin cdpPlugin = CDPPlugin.getInstance();
		return cdpPlugin.getBundle();
	}

	@Override
	protected String getBuiltinFolderPath() {
		return BUILD_IN_FOLDER_PATH;
	}

	@Override
	public List<String> getSupportedTemplateIds(IDynamicMonitor monitor) throws Exception {
		return templateIds;
	}

}
