// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.kafka;

/**
 * Since Spark 2.0, the spark-streaming-kafka connector has 2 flavours : 0.8 and 0.10.
 * 
 * All distributions running on Spark versions prior to 2.0 were running on the 0.8 version... excepted MapR that comes
 * with its own spark-streaming-kafka having its own API to be compilant with both MapR Streams and Kafka (not the
 * Apache version but their own version of Kafka, of course).
 * 
 * Warning : "0.8" does not mean that it was actually compiled against Kafka 0.8... that could be against 0.9 depending
 * of how the vendor packaged the connector.
 * 
 * This enum lists all the current versions of the spark-streaming-kafka connector.
 *
 */
public enum SparkStreamingKafkaVersion {
    KAFKA_0_8,
    KAFKA_0_10,
    MAPR_5X0_KAFKA,
    MAPR_600_KAFKA;
}