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

package org.talend.hadoop.distribution.hdinsight320;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;

public class HDInsight32Distribution extends AbstractDistribution implements MRComponent, PigComponent, HiveComponent,
        SparkBatchComponent, SparkStreamingComponent, IMicrosoftHDInsightDistribution {

    public static final String VERSION = "MICROSOFT_HD_INSIGHT_3_2";

    public static final String VERSION_DISPLAY = "Microsoft HD Insight 3.2";

    private final static String YARN_APPLICATION_CLASSPATH = "$HADOOP_CONF_DIR,$HADOOP_COMMON_HOME/*,$HADOOP_COMMON_HOME/lib/*,$HADOOP_HDFS_HOME/*,$HADOOP_HDFS_HOME/lib/*,$HADOOP_MAPRED_HOME/*,$HADOOP_MAPRED_HOME/lib/*,$YARN_HOME/*,$YARN_HOME/lib/*,$HADOOP_YARN_HOME/*,$HADOOP_YARN_HOME/lib/*,$HADOOP_COMMON_HOME/share/hadoop/common/*,$HADOOP_COMMON_HOME/share/hadoop/common/lib/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/*,$HADOOP_HDFS_HOME/share/hadoop/hdfs/lib/*,$HADOOP_YARN_HOME/share/hadoop/yarn/*,$HADOOP_YARN_HOME/share/hadoop/yarn/lib/*"; //$NON-NLS-1$

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<ComponentType, ComponentCondition> displayConditions = new HashMap<>();

    static {
        moduleGroups = new HashMap<>();

        ComponentCondition c1 = new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                new BasicExpression(Constant.PIG_STORE_PARAMETER, EqualityOperator.NOT_EQ, Constant.PIG_HBASESTORAGE_PARAMETER)),
                BooleanOperator.AND, new SimpleComponentCondition(new BasicExpression(Constant.PIG_STORE_PARAMETER,
                        EqualityOperator.NOT_EQ, Constant.PIG_HCATSTORER_PARAMETER))));
        displayConditions.put(ComponentType.PIGOUTPUT, c1);
    }

    @Override
    public String getDistribution() {
        return DISTRIBUTION_NAME;
    }

    @Override
    public String getDistributionName() {
        return DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        return VERSION_DISPLAY;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_2;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public boolean isExecutedThroughWebHCat() {
        return true;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return false;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return false;
    }

    @Override
    public String getYarnApplicationClasspath() {
        return YARN_APPLICATION_CLASSPATH;
    }

    @Override
    public boolean doSupportHCatalog() {
        return true;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        return false;
    }

    @Override
    public boolean doSupportHBase() {
        return false;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
    }

    @Override
    public boolean doSupportEmbeddedMode() {
        return false;
    }

    @Override
    public boolean doSupportStandaloneMode() {
        return false;
    }

    @Override
    public boolean doSupportHive1() {
        return false;
    }

    @Override
    public boolean doSupportHive2() {
        return false;
    }

    @Override
    public boolean doSupportTezForHive() {
        return false;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        return false;
    }

    @Override
    public boolean doSupportSSL() {
        return false;
    }

    @Override
    public boolean doSupportORCFormat() {
        return true;
    }

    @Override
    public boolean doSupportAvroFormat() {
        return true;
    }

    @Override
    public boolean doSupportParquetFormat() {
        return true;
    }

    @Override
    public boolean doSupportStoreAsParquet() {
        return false;
    }

    @Override
    public boolean doSupportDynamicMemoryAllocation() {
        return false;
    }

    @Override
    public boolean isExecutedThroughSparkJobServer() {
        return true;
    }

    @Override
    public boolean doSupportCheckpointing() {
        return false;
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return displayConditions.get(componentType);
    }

    @Override
    public boolean doSupportSparkStandaloneMode() {
        return true;
    }

    @Override
    public boolean doSupportSparkYarnClientMode() {
        return false;
    }

    @Override
    public boolean doSupportBackpressure() {
        return false;
    }

    @Override
    public boolean doSupportHDFSEncryption() {
        return true;
    }

    @Override
    public boolean isCloudDistribution() {
        return true;
    }

    @Override
    public boolean doSupportOozie() {
        return false;
    }
}
