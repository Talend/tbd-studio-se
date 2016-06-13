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
package org.talend.hadoop.distribution;

/**
 * Describes the spark version.
 * 
 * The enum order is used to determine whether a spark version is later than another.
 */
public enum ESparkVersion {
    SPARK_1_3,
    SPARK_1_4,
    SPARK_1_5,
    SPARK_1_6,
    SPARK_2_0;
}
