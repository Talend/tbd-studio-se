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
     * Most of the distributions stick with the official spark-streaming-kafka implementation. Some MapR distributions
     * have their own spark-streaming-kafka implementation and their own API.
     * 
     * @return true if the distribution supports the official spark-streaming-kafka implementation.
     */
    public boolean doSupportOfficialSparkStreamingKafka();

}
