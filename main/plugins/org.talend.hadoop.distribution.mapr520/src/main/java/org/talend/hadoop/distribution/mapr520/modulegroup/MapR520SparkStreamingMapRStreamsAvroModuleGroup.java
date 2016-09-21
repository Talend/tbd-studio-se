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
package org.talend.hadoop.distribution.mapr520.modulegroup;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.common.SparkStreamingLinkedNodeCondition;
import org.talend.hadoop.distribution.constants.SparkStreamingConstant;
import org.talend.hadoop.distribution.mapr520.MapR520Constant;
import org.talend.hadoop.distribution.mapr520.MapR520Distribution;

public class MapR520SparkStreamingMapRStreamsAvroModuleGroup {

    public static Set<DistributionModuleGroup> getModuleGroups() {
        Set<DistributionModuleGroup> hs = new HashSet<>();
        // This is not a typo on the module group. Both Kafka and MapR Streams share the same Avro dependencies.
        DistributionModuleGroup dmg = new DistributionModuleGroup(
                MapR520Constant.SPARK_KAFKA_AVRO_MRREQUIRED_MODULE_GROUP.getModuleName(), true,
                new SparkStreamingLinkedNodeCondition(EHadoopDistributions.MAPR.getName(), MapR520Distribution.VERSION,
                        SparkStreamingConstant.MAPRSTREAMS_SPARKCONFIGURATION_LINKEDPARAMETER).getCondition());
        hs.add(dmg);
        // Add Spark Streaming MapR Streams dependencies as well
        hs.addAll(MapR520SparkStreamingMapRStreamsAssemblyModuleGroup.getModuleGroups());
        return hs;
    }
}
