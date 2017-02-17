// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.db.util.neo4j;

import static org.junit.Assert.*;

import org.eclipse.emf.common.util.EMap;
import org.junit.Before;
import org.junit.Test;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NosqlFactory;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;

/**
 * created by ycbai on 2016年12月30日 Detailled comment
 *
 */
public class Neo4jConnectionUtilTest {

    NoSQLConnection connection;

    @Before
    public void before() {
        connection = NosqlFactory.eINSTANCE.createNoSQLConnection();
    }

    @Test
    public void testAuthorization() {
        EMap<String, String> attributes = connection.getAttributes();
        attributes.put(INeo4jAttributes.REMOTE_SERVER, "true"); //$NON-NLS-1$
        attributes.put(INeo4jAttributes.DB_VERSION, INeo4jConstants.NEO4J_1_X_X);
        assertTrue(Neo4jConnectionUtil.isHasSetUsernameOption(connection));
        assertFalse(Neo4jConnectionUtil.isNeedAuthorization(connection));
        attributes.put(INeo4jAttributes.SET_USERNAME, "true"); //$NON-NLS-1$
        assertTrue(Neo4jConnectionUtil.isNeedAuthorization(connection));

        attributes.put(INeo4jAttributes.DB_VERSION, INeo4jConstants.NEO4J_2_1_X);
        assertTrue(Neo4jConnectionUtil.isHasSetUsernameOption(connection));
        assertTrue(Neo4jConnectionUtil.isNeedAuthorization(connection));
        attributes.put(INeo4jAttributes.SET_USERNAME, "false"); //$NON-NLS-1$
        assertFalse(Neo4jConnectionUtil.isNeedAuthorization(connection));

        attributes.put(INeo4jAttributes.DB_VERSION, INeo4jConstants.NEO4J_2_2_X);
        assertFalse(Neo4jConnectionUtil.isHasSetUsernameOption(connection));
        assertTrue(Neo4jConnectionUtil.isNeedAuthorization(connection));

        attributes.put(INeo4jAttributes.DB_VERSION, INeo4jConstants.NEO4J_2_3_X);
        assertFalse(Neo4jConnectionUtil.isHasSetUsernameOption(connection));
        assertTrue(Neo4jConnectionUtil.isNeedAuthorization(connection));

        attributes.put(INeo4jAttributes.REMOTE_SERVER, "false"); //$NON-NLS-1$
        assertFalse(Neo4jConnectionUtil.isHasSetUsernameOption(connection));
        assertFalse(Neo4jConnectionUtil.isNeedAuthorization(connection));
    }

}
