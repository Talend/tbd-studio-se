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

package org.talend.hadoop.distribution;

/**
 * Enumeration that describes the Hadoop version.
 */
public enum EHadoopVersion {
    HADOOP_1, // Hadoop 1 stands for the JobTracker implementation of M/R.
    HADOOP_2; // Hadoop 2 stands for the YARN implementation of M/R.
}
