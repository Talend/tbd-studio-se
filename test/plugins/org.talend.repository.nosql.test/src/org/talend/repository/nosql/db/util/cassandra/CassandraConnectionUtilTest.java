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
package org.talend.repository.nosql.db.util.cassandra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talend.commons.utils.VersionUtils;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NosqlFactory;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.cassandra.ICassandraConstants;

/**
 * DOC hwang class global comment. Detailled comment
 */
public class CassandraConnectionUtilTest {

    private NoSQLConnection connection;

    /**
     * DOC hwang Comment method "setUp".
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        connection = NosqlFactory.eINSTANCE.createNoSQLConnection();
    }

    /**
     * Test method for
     * {@link org.talend.repository.nosql.db.util.cassandra#isUpgradeVersion(org.talend.repository.model.nosql.NoSQLConnection)}
     * .
     */
    @Test
    public void testIsUpgradeVersion() {
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, VersionUtils.DEFAULT_VERSION);
        Assert.assertFalse(CassandraConnectionUtil.isUpgradeVersion(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, ICassandraConstants.CASSANDRA200);
        Assert.assertFalse(CassandraConnectionUtil.isUpgradeVersion(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, "CASSANDRA_1_2_2");
        Assert.assertFalse(CassandraConnectionUtil.isUpgradeVersion(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, ICassandraConstants.DBM_DATASTAX_ID);
        Assert.assertFalse(CassandraConnectionUtil.isUpgradeVersion(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, "CASSANDRA_2_2");
        Assert.assertTrue(CassandraConnectionUtil.isUpgradeVersion(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, "CASSANDRA_3_0");
        Assert.assertTrue(CassandraConnectionUtil.isUpgradeVersion(connection));
    }

}
