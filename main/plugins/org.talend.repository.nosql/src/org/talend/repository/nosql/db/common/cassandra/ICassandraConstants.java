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
package org.talend.repository.nosql.db.common.cassandra;

public interface ICassandraConstants {

    public final static String KEY_SPACE = "Keyspace"; //$NON-NLS-1$

    public final static String COLUMN_FAMILY = "Column Family"; //$NON-NLS-1$

    public final static String SUPER_COLUMN_FAMILY = "Column Family(super)"; //$NON-NLS-1$

    public final static String DEFAULT_HOST = "localhost"; //$NON-NLS-1$

    public final static String DEFAULT_PORT = "9160"; //$NON-NLS-1$

    public final static String DATASTAX_PORT = "9042"; //$NON-NLS-1$

    public final static String DBM_ID = "cassandra_id"; //$NON-NLS-1$

    public final static String DBM_DATASTAX_ID = "cassandra_datastax_id"; //$NON-NLS-1$
    
    public final static String DBM22_DATASTAX_ID = "cassandra22_datastax_id"; //$NON-NLS-1$

    public final static String API_TYPE_DATASTAX = "DATASTAX"; //$NON-NLS-1$

    public final static String[] API_TYPES = { API_TYPE_DATASTAX, "HECTOR" };//$NON-NLS-1$
    
    public final static String CASSANDRA200 = "CASSANDRA_2_0_0"; //$NON-NLS-1$

    public final static String DB_VERSION_CASSANDRA_3_0 = "CASSANDRA_3_0";

    public final static String DB_VERSION_CASSANDRA_2_2 = "CASSANDRA_2_2";

    public final static String DB_VERSION_CASSANDRA_2_0_0 = "CASSANDRA_2_0_0";

    public final static String[] DBVERSIONS = { DB_VERSION_CASSANDRA_3_0, DB_VERSION_CASSANDRA_2_2, DB_VERSION_CASSANDRA_2_0_0,
            "CASSANDRA_1_2_2", "CASSANDRA_1_1_2" };//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
}
