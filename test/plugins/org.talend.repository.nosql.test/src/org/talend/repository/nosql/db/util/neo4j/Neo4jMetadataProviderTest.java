package org.talend.repository.nosql.db.util.neo4j;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.model.nosql.NosqlFactory;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.utils.io.FilesUtils;


public class Neo4jMetadataProviderTest {

    NoSQLConnection localConnection;

    File tmpFolder = null;

    @Before
    public void prepare() {
        tmpFolder = org.talend.utils.files.FileUtils.createTmpFolder("neo4jLocalConnection", "test"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @After
    public void clean() {
        if (tmpFolder != null) {
            FilesUtils.deleteFolder(tmpFolder, true);
        }
    }

    @Before
    public void before() {
        localConnection = NosqlFactory.eINSTANCE.createNoSQLConnection();
    }

    @Test
    public void testLocalRetrieveSchema() throws NoSQLServerException {
        EMap<String, String> attributes = localConnection.getAttributes();
        attributes.put(INeo4jAttributes.REMOTE_SERVER, "false"); //$NON-NLS-1$

        try {
            attributes.put(INeo4jAttributes.DATABASE_PATH, tmpFolder.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        attributes.put(INeo4jAttributes.DB_VERSION, INeo4jConstants.NEO4J_2_3_X);
        localConnection.setDbType("NEO4J");
        Neo4jConnectionUtil.checkConnection(localConnection);
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>();
        String cypher = "create (n:Test {first_name : 'Peppa', last_name : 'Pig'})\r\nreturn n;";
        IMetadataProvider metadataProvider = NoSQLRepositoryFactory.getInstance()
                .getMetadataProvider(localConnection.getDbType());
        try {
            metadataColumns = metadataProvider.extractColumns(localConnection, cypher);
        } catch (NoSQLExtractSchemaException e) {
            e.printStackTrace();
        }
        String lastName = metadataColumns.get(0).getName();
        assertEquals("last_name", lastName);
        String firstName = metadataColumns.get(0).getName();
        assertEquals("first_name", firstName);

    }

}
