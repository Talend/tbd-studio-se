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
package org.talend.hadoop.distribution.dynamic.template.modulegroup.cdp;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicSparkStreamingModuleGroup;

/**
 * DOC rhaddou class global comment. Detailled comment
 */
public class DynamicCDPSparkStreamingModuleGroup extends DynamicSparkStreamingModuleGroup {

	public DynamicCDPSparkStreamingModuleGroup(DynamicPluginAdapter pluginAdapter) {
		super(pluginAdapter);
	}

	@Override
	protected void init() {
		spark2Condition = new MultiComponentCondition(
				new BasicExpression(SparkBatchConstant.SPARK_LOCAL_MODE_PARAMETER, EqualityOperator.EQ, "false"), //$NON-NLS-1$
				BooleanOperator.AND, new BasicExpression("SUPPORTED_SPARK_VERSION", EqualityOperator.EQ,
						ESparkVersion.SPARK_2_4.getSparkVersion())); // $NON-NLS-1$
	}

	@Override
	public Set<DistributionModuleGroup> getModuleGroups() throws Exception {
		Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
		Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups();
		if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
			moduleGroups.addAll(moduleGroupsFromSuper);
		}
		DynamicPluginAdapter pluginAdapter = getPluginAdapter();

		String hdfsSpark2RuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
				DynamicModuleGroupConstant.HDFS_MODULE_GROUP_SPARK2_1.getModuleName());
		String hdfsCommonRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
				DynamicModuleGroupConstant.HDFS_MODULE_GROUP_COMMON.getModuleName());
		String mrRuntimeId = pluginAdapter
				.getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.MAPREDUCE_MODULE_GROUP.getModuleName());

		checkRuntimeId(hdfsSpark2RuntimeId);
		checkRuntimeId(hdfsCommonRuntimeId);
		checkRuntimeId(mrRuntimeId);

		if (StringUtils.isNotBlank(hdfsSpark2RuntimeId)) {
			moduleGroups.add(new DistributionModuleGroup(hdfsSpark2RuntimeId, false, spark2Condition));
		}
		if (StringUtils.isNotBlank(hdfsCommonRuntimeId)) {
			moduleGroups.add(new DistributionModuleGroup(hdfsCommonRuntimeId, false, spark2Condition));
		}
		if (StringUtils.isNotBlank(mrRuntimeId)) {
			moduleGroups.add(new DistributionModuleGroup(mrRuntimeId, false, spark2Condition));
		}

		return moduleGroups;
	}
}
