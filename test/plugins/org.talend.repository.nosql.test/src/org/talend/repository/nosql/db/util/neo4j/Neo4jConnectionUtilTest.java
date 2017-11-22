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
package org.talend.repository.nosql.db.util.neo4j;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.emf.common.util.EMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talend.commons.utils.VersionUtils;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NosqlFactory;
import org.talend.repository.nosql.constants.INoSQLCommonAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.utils.io.FilesUtils;

/**
 * created by ycbai on 2016年12月30日 Detailled comment
 *
 */
public class Neo4jConnectionUtilTest {

    NoSQLConnection connection;

    NoSQLConnection localConnection;

    File tmpFolder = null;

    @Before
    public void prepare() {
        tmpFolder = org.talend.utils.files.FileUtils.createTmpFolder("neo4jLocalConnection", "test"); //$NON-NLS-1$ //$NON-NLS-2$
        connection = NosqlFactory.eINSTANCE.createNoSQLConnection();
        localConnection = NosqlFactory.eINSTANCE.createNoSQLConnection();
    }

    @After
    public void clean() {
        if (tmpFolder != null) {
            FilesUtils.deleteFolder(tmpFolder, true);
        }
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

    @Test
    public void testLocalConnection() throws Exception {
        EMap<String, String> attributes = localConnection.getAttributes();
        attributes.put(INeo4jAttributes.REMOTE_SERVER, "false"); //$NON-NLS-1$

        attributes.put(INeo4jAttributes.DATABASE_PATH, tmpFolder.getCanonicalPath());

        attributes.put(INeo4jAttributes.DB_VERSION, INeo4jConstants.NEO4J_2_3_X);

        localConnection.setDbType("NEO4J");
        // TUP-15696:"Check Service" for Neo4j does not work after first check.
        // so check twice here
        assertTrue(Neo4jConnectionUtil.checkConnection(localConnection));
        assertTrue(Neo4jConnectionUtil.checkConnection(localConnection));
    }
    
    @Test
    public void testIsVersionSince32() throws Exception {

        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, VersionUtils.DEFAULT_VERSION);
        Assert.assertFalse(Neo4jConnectionUtil.isVersionSince32(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, INeo4jConstants.NEO4J_2_3_X);
        Assert.assertFalse(Neo4jConnectionUtil.isVersionSince32(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, "NEO4J_1_2_2");
        Assert.assertFalse(Neo4jConnectionUtil.isVersionSince32(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, INeo4jConstants.NEO4J_2_2_X);
        Assert.assertFalse(Neo4jConnectionUtil.isVersionSince32(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, "NEO4J_3_1_X");
        Assert.assertFalse(Neo4jConnectionUtil.isVersionSince32(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, INeo4jConstants.NEO4J_3_2_X);
        Assert.assertTrue(Neo4jConnectionUtil.isVersionSince32(connection));
        
        connection.getAttributes().put(INoSQLCommonAttributes.DB_VERSION, "NEO4J_4_1_X");
        Assert.assertTrue(Neo4jConnectionUtil.isVersionSince32(connection));
    
    }
    

}
