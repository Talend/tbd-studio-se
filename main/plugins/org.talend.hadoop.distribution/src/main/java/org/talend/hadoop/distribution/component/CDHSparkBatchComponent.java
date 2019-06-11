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
package org.talend.hadoop.distribution.component;

import org.talend.hadoop.distribution.kudu.KuduVersion;

/**
 * Interface that exposes specific CDH Spark Batch methods.
 *
 */
public interface CDHSparkBatchComponent extends SparkBatchComponent {
    /**
     * This method defines which version of the kudu_spark connector the distribution does support.
     *
     * @return the version of the spark-streaming-kafka connector.
     */
    public KuduVersion getKuduVersion();
}
