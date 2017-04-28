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
package org.talend.hadoop.distribution.component;

import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;

/**
 * Interface that exposes specific Spark Streaming methods.
 *
 */
public interface SparkStreamingComponent extends SparkComponent {

    /**
     * This method defines if a distribution supports the checkpointing in Spark Streaming
     * 
     * @return true if the distribution supports the checkpointing
     */
    public boolean doSupportCheckpointing();

    /**
     * This method defines if a distribution supports the backpressure feature in Spark Streaming
     * 
     * @return true if the distribution supports the backpressure feature
     */
    public boolean doSupportBackpressure();

    /**
     * This method defines which version of the spark-streaming-kafka connector the distribution does support.
     * @param sparkVersion version of spark used for streaming
     * 
     * @return the version of the spark-streaming-kafka connector.
     */
    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion);

}
