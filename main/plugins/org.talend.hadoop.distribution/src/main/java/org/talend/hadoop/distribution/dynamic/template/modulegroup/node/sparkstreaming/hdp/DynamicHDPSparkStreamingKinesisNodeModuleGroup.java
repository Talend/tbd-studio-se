// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.hdp;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.node.sparkstreaming.DynamicSparkStreamingKinesisNodeModuleGroup;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicHDPSparkStreamingKinesisNodeModuleGroup extends DynamicSparkStreamingKinesisNodeModuleGroup {

	public DynamicHDPSparkStreamingKinesisNodeModuleGroup(DynamicPluginAdapter pluginAdapter) {
		super(pluginAdapter);
	}

	@Override
	protected void init() {
		spark2Condition = new MultiComponentCondition(
				new LinkedNodeExpression(SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
						"SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
						ESparkVersion.SPARK_2_1.getSparkVersion()), //$NON-NLS-1$
						BooleanOperator.OR,
						new MultiComponentCondition(
								new LinkedNodeExpression(SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
										"SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
										ESparkVersion.SPARK_2_2.getSparkVersion()), //$NON-NLS-1$
										BooleanOperator.OR,
										new MultiComponentCondition (
												new LinkedNodeExpression(SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
														"SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
														ESparkVersion.SPARK_2_3.getSparkVersion()),
														BooleanOperator.OR,
														new LinkedNodeExpression(SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
																"SUPPORTED_SPARK_VERSION", EqualityOperator.EQ, //$NON-NLS-1$
																ESparkVersion.SPARK_2_4.getSparkVersion())))
				);
	}

	@Override
	public Set<DistributionModuleGroup> getModuleGroups(String distribution, String version) throws Exception {
		Set<DistributionModuleGroup> moduleGroups = new HashSet<>();
		Set<DistributionModuleGroup> moduleGroupsFromSuper = super.getModuleGroups(distribution, version);
		if (moduleGroupsFromSuper != null && !moduleGroupsFromSuper.isEmpty()) {
			moduleGroups.addAll(moduleGroupsFromSuper);
		}
		DynamicPluginAdapter pluginAdapter = getPluginAdapter();

		String spark2KinesisMrRequiredRuntimeId = pluginAdapter.getRuntimeModuleGroupIdByTemplateId(
				DynamicModuleGroupConstant.SPARK2_KINESIS_MRREQUIRED_MODULE_GROUP.getModuleName());

		checkRuntimeId(spark2KinesisMrRequiredRuntimeId);

		if (StringUtils.isNotBlank(spark2KinesisMrRequiredRuntimeId)) {
			DistributionModuleGroup dmgSpark1 = new DistributionModuleGroup(spark2KinesisMrRequiredRuntimeId, true,
					new NestedComponentCondition(new MultiComponentCondition(
							new SparkStreamingLinkedNodeCondition(distribution, version).getCondition(), BooleanOperator.AND,
							spark2Condition)));
			moduleGroups.add(dmgSpark1);
		}
		return moduleGroups;
	}
}
