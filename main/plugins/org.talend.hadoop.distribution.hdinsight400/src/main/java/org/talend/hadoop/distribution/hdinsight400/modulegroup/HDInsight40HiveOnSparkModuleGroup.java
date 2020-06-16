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
package org.talend.hadoop.distribution.hdinsight400.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.hdinsight400.HDInsight40Constant;

public class HDInsight40HiveOnSparkModuleGroup {

	private final static ComponentCondition conditionSpark2_3 = new MultiComponentCondition(
			new SimpleComponentCondition(
					new LinkedNodeExpression(
							SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
							"SUPPORTED_SPARK_VERSION",   //$NON-NLS-1$
							EqualityOperator.EQ,
							ESparkVersion.SPARK_2_3.getSparkVersion()
					)
			),
			BooleanOperator.OR,
			new SimpleComponentCondition(
					new LinkedNodeExpression(
							SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
							"SUPPORTED_SPARK_VERSION",  //$NON-NLS-1$
							EqualityOperator.EQ,
							ESparkVersion.SPARK_2_3.getSparkVersion()
					)
			)
	);

	private final static ComponentCondition conditionSpark2_4 = new MultiComponentCondition(
			new SimpleComponentCondition(
					new LinkedNodeExpression(
							SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
							"SUPPORTED_SPARK_VERSION",   //$NON-NLS-1$
							EqualityOperator.EQ,
							ESparkVersion.SPARK_2_4.getSparkVersion()
					)
			),
			BooleanOperator.OR,
			new SimpleComponentCondition(
					new LinkedNodeExpression(
							SparkStreamingConstant.SPARK_STREAMING_SPARKCONFIGURATION_LINKEDPARAMETER,
							"SUPPORTED_SPARK_VERSION",  //$NON-NLS-1$
							EqualityOperator.EQ,
							ESparkVersion.SPARK_2_4.getSparkVersion()
					)
			)
	);

	public static Set<DistributionModuleGroup> getModuleGroups() {
		Set<DistributionModuleGroup> hs = new HashSet<>();
		hs.add(new DistributionModuleGroup(
				HDInsight40Constant.BIGDATALAUNCHER_MODULE_GROUP.getModuleName(),
				true,
				null
		));
		hs.add(new DistributionModuleGroup(
				HDInsight40Constant.SPARK23_HIVE_MRREQUIRED_MODULE_GROUP.getModuleName(),
                false,
                conditionSpark2_3
        ));
        hs.add(new DistributionModuleGroup(
                HDInsight40Constant.SPARK24_HIVE_MRREQUIRED_MODULE_GROUP.getModuleName(),
                false,
                conditionSpark2_4
        ));
        hs.add(new DistributionModuleGroup(
                HDInsight40Constant.SPARK23_SQL_MRREQUIRED_MODULE_GROUP.getModuleName(),
                false,
                conditionSpark2_3
        ));
        hs.add(new DistributionModuleGroup(
                HDInsight40Constant.SPARK24_SQL_MRREQUIRED_MODULE_GROUP.getModuleName(),
                false,
                conditionSpark2_4
        ));
        return hs;

    }
}
