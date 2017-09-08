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
package org.talend.lineage.atlas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

public class AtlasLineageCreatorIT {

    private static final String ENTITY_DESCRIPTION = "Talend Component";

    private static final String ENTITY_LINK        = "http://www.talend.com/";

    private static String       ENDPOINT_URL       = "http://localhost:21000/";

    private static String       ATLAS_LOGIN        = "admin";

    private static String       ATLAS_PASSWORD     = "admin";

    private AtlasLineageCreator atlasLineageCreator;

    @Before
    public void setupTests() {
        try {
            Properties prop = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("atlas-application.properties");
            prop.load(stream);
            ENDPOINT_URL = prop.getProperty("atlas.http.address");
        } catch (Exception e) {
            // Nothing
        }
        atlasLineageCreator = new AtlasLineageCreator(ENDPOINT_URL, ATLAS_LOGIN, ATLAS_PASSWORD);
    }

    private static Map<String, Object> createGenericMetadata() {
        final long id = System.currentTimeMillis();
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("description", ENTITY_DESCRIPTION + "-" + id);
        // metadata.put("link", ENTITY_LINK);
        metadata.put("owner", "scott");
        // tags are traits in atlas (at least visually)
        metadata.put("startTime", id);
        metadata.put("endTime", id + 10000);
        metadata.put("script", "SELECT FROM WHERE");
        return metadata;
    }

    private static Map<String, Object> createInputMetadata() {
        Map<String, Object> metadata = createGenericMetadata();
        metadata.put("path", "hdfs://foo/bar");
        metadata.put("format", "CSV");
        return metadata;
    }

    private void addJobInfo(String name) {
        long currentTime = System.currentTimeMillis();

        Map<String, Object> jobMetadata = new HashMap<>();
        jobMetadata.put("name", name + "_" + currentTime);
        jobMetadata.put("description", name + "_description");
        jobMetadata.put("purpose", name + "_purpose");
        jobMetadata.put("author", name + "_author");
        jobMetadata.put("version", name + "_version");
        jobMetadata.put("jobType", name + "_jobType");
        jobMetadata.put("framework", name + "_framework");
        jobMetadata.put("creationTime", currentTime - 10000);
        jobMetadata.put("lastModificationTime", currentTime);
        jobMetadata.put("startTime", currentTime);
        jobMetadata.put("endTime", currentTime);

        atlasLineageCreator.addJobInfo(jobMetadata);
    }

    @Test
    public void test_simple() {
        Map<String, String> schema1 = new HashMap<>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<>();
        schema2.put("a", "string");
        schema2.put("b", "string");
        schema2.put("c", "int");

        addJobInfo("test_simple");
        Referenceable jobRef = atlasLineageCreator.getJobRef();
        assertNotNull(jobRef);

        atlasLineageCreator.addNodeToLineage("input", schema1, new ArrayList<String>(), Arrays.asList("out"),
                createInputMetadata());
        atlasLineageCreator.addNodeToLineage("out", schema2, Arrays.asList("input"), new ArrayList<String>(),
                createInputMetadata());

        List<Referenceable> refs = atlasLineageCreator.getRefs();
        assertNotNull(refs);
        assertEquals(refs.size(), 2);

        atlasLineageCreator.sendToLineageProvider(true);
        atlasLineageCreator.logPersistedEntities();
        testPersistence(1, 2, 1);
    }

    @Test
    public void test_hard() {
        Map<String, String> schema1 = new HashMap<>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<>();
        schema2.put("id", "int");
        schema2.put("name", "string");
        schema2.put("value", "int");

        Map<String, String> schema3 = new HashMap<>();
        schema3.put("id", "int");

        addJobInfo("test_hard");
        atlasLineageCreator.addNodeToLineage("input", schema1, new ArrayList<String>(), Arrays.asList("middle1"),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("middle1", schema1, Arrays.asList("input"), Arrays.asList("middle2", "out1"),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("middle2", schema2, Arrays.asList("middle1"), Arrays.asList("out2"),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("out1", schema3, Arrays.asList("middle1"), new ArrayList<String>(),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("out2", schema3, Arrays.asList("middle2"), new ArrayList<String>(),
                createGenericMetadata());

        atlasLineageCreator.sendToLineageProvider(true);
        atlasLineageCreator.logPersistedEntities();
        testPersistence(1, 5, 1);
    }

    @Test
    public void test_hardMultipleInput() {
        Map<String, String> schema1 = new HashMap<>();
        schema1.put("id", "int");
        schema1.put("name", "string");

        Map<String, String> schema2 = new HashMap<>();
        schema2.put("id", "int");
        schema2.put("name", "string");
        schema2.put("value", "int");

        Map<String, String> schema3 = new HashMap<>();
        schema3.put("id", "int");

        addJobInfo("test_hardMultipleInput");
        atlasLineageCreator.addNodeToLineage("input1", schema1, new ArrayList<String>(), Arrays.asList("middle1"),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("input2", schema2, new ArrayList<String>(), Arrays.asList("middle1"),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("middle1", schema1, Arrays.asList("input1", "input2"),
                Arrays.asList("middle2", "out1"), createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("middle2", schema2, Arrays.asList("middle1"), Arrays.asList("out2"),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("out1", schema3, Arrays.asList("middle1"), new ArrayList<String>(),
                createGenericMetadata());
        atlasLineageCreator.addNodeToLineage("out2", schema3, Arrays.asList("middle2"), new ArrayList<String>(),
                createGenericMetadata());

        atlasLineageCreator.sendToLineageProvider(true);
        atlasLineageCreator.logPersistedEntities();
        testPersistence(1, 6, 1);
    }

    private void testPersistence(int numPersistedJobInstances, int numPersistedInstances, int numPersistedArticialInstances) {
        Map<String, Pair<Referenceable, Id>> persistedJobs = atlasLineageCreator.getPersistedJobs();
        assertNotNull(persistedJobs);
        assertEquals(persistedJobs.values().size(), numPersistedJobInstances);

        Map<String, Pair<Referenceable, Id>> persistedComponents = atlasLineageCreator.getPersistedComponents();
        assertNotNull(persistedComponents);
        assertEquals(persistedComponents.values().size(), numPersistedInstances);

        Map<String, Pair<Referenceable, Id>> persistedArtificialComponents = atlasLineageCreator
                .getPersistedArtificialComponents();
        assertNotNull(persistedArtificialComponents);
        assertEquals(persistedArtificialComponents.values().size(), numPersistedArticialInstances);
    }

}
