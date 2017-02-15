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

import java.util.List;

import org.talend.hadoop.distribution.ESparkVersion;

/**
 * Interface that exposes specific Spark methods.
 *
 */
public interface SparkComponent extends MRComponent {

    /**
     * A distribution can be using Spark 1.3 or Spark 1.4. This method returns the used Spark version.
     * 
     * @return the @link{ESparkVersion} of the distribution.
     */
    public ESparkVersion getSparkVersion();

    /**
     * @return true if the Spark version included in the distribution is 1.3.
     */
    public boolean isSpark13();

    /**
     * @return true if the Spark version included in the distribution is 1.4.
     */
    public boolean isSpark14();

    /**
     * @return true if the Spark version included in the distribution is 1.5.
     */
    public boolean isSpark15();

    /**
     * @return true if the Spark version included in the distribution is 1.6 or more.
     */
    public boolean isSpark16();

    /**
     * @return true if the Spark version included in the distribution is 2.0.
     */
    public boolean isSpark20();

    /**
     * @return true if the distribution supports the Spark Standalone mode.
     */
    public boolean doSupportSparkStandaloneMode();

    /**
     * @return true if the distribution supports the Spark Yarn Client mode.
     */
    public boolean doSupportSparkYarnClientMode();

    /**
     * @return true if the distribution supports the Spark Yarn Cluster mode.
     */
    public boolean doSupportSparkYarnClusterMode();

    /**
     * @return true if the distribution supports the Dynamic Allocation feature.
     * @see http://spark.apache.org/docs/latest/configuration.html#dynamic-allocation
     */
    public boolean doSupportDynamicMemoryAllocation();

    /**
     * @return true if the distribution executes its Spark job through Spark JobServer.
     */
    public boolean isExecutedThroughSparkJobServer();

    /**
     * @return true if the distribution executes its Spark job through Livy.
     */
    public boolean isExecutedThroughLivy();

    /**
     * @return A string with all of the Spark jars (from the module group SPARK) mapped to local Studio paths.
     */
    public String generateSparkJarsPaths(List<String> commandLineJarsPaths);
}
