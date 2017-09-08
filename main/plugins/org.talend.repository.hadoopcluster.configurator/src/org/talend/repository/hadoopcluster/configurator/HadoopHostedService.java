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
package org.talend.repository.hadoopcluster.configurator;

import com.google.common.base.Joiner;

/**
 * created by bchen on Jun 1, 2015 Detailled comment
 *
 */
public enum HadoopHostedService {
    YARN,
    HDFS,
    HIVE,
    HBASE,
    OOZIE,
    SPARK, // CDH
    MAPREDUCE2, // AMBARI
    PIG, // AMBARI
    SQOOP, // AMBARI
    STORM, // AMBARI
    TEZ, // AMBARI
    ZOOKEEPER, // AMBARI
    WEBHCAT, // AMBARI
    ;

    private static String SUPPORTED_SERVICES = Joiner.on(", ").join(values()).toLowerCase();

    public static HadoopHostedService fromString(String s) {
        try {
            return valueOf(s.toUpperCase());
        } catch (IllegalArgumentException t) {
            throw new IllegalArgumentException(
                    String.format("Illegal service='%s'. Supported service: %s", s, SUPPORTED_SERVICES));
        }
    }

    public static boolean isSupport(String s) {
        try {
            fromString(s);
            return true;
        } catch (IllegalArgumentException t) {
        }
        return false;
    }
}
