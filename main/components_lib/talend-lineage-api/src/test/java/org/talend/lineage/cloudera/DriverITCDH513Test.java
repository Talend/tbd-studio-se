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
package org.talend.lineage.cloudera;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Map;

import org.joda.time.Instant;
import org.junit.Test;
import org.talend.lineage.cloudera.entity.MyCustomEntity;

import com.cloudera.nav.sdk.client.MetadataResultIterator;
import com.cloudera.nav.sdk.client.NavigatorPlugin;
import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.MetadataType;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.google.common.collect.Lists;


public class DriverITCDH513Test {

    @Test
    public void test() {
        
        NavigatorPlugin plugin = NavigatorPlugin.fromConfigFile("src/test/resources/sample.conf");

        plugin.registerModels(DriverITCDH513Test.class.getClass().getPackage().getName());

        MyCustomEntity myCustomEntity = new MyCustomEntity(plugin.getNamespace(), "My custom entity 1");
        myCustomEntity.setDescription("I am a custom entity 1");
        myCustomEntity.setLink("http://fr.talend.com/");
        myCustomEntity.setIndex(10);
        myCustomEntity.setSteward("chang");
        myCustomEntity.setStarted(Instant.now());
        myCustomEntity.setEnded((new Instant(Instant.now().toDate().getTime() + 10000)));

        MyCustomEntity myCustomEntity2 = new MyCustomEntity(plugin.getNamespace(), "My custom entity 2");
        myCustomEntity2.setDescription("I am a custom entity 2");
        myCustomEntity2.setLink("http://fr.talend.com/");
        myCustomEntity2.setIndex(10);
        myCustomEntity2.setSteward("chang");
        myCustomEntity2.setStarted(Instant.now());
        myCustomEntity2.setEnded((new Instant(Instant.now().toDate().getTime() + 10000)));

        EndPointProxy endPointProxy1 = new EndPointProxy(myCustomEntity.getIdentity(), SourceType.SDK,
                EntityType.OPERATION_EXECUTION);
        EndPointProxy endPointProxy2 = new EndPointProxy(myCustomEntity2.getIdentity(), SourceType.SDK,
                EntityType.OPERATION_EXECUTION);

        myCustomEntity.sourceProxies.add(endPointProxy1);
        myCustomEntity.targetProxies.add(endPointProxy2);

        myCustomEntity2.sourceProxies.add(endPointProxy1);
        myCustomEntity2.targetProxies.add(endPointProxy2);

        // Write custom entities to cloudera navigator
        ResultSet results = plugin.write(Arrays.asList(myCustomEntity, myCustomEntity2));
        
        assertFalse(results.hasErrors());
        
        // Read and check custom entities from cloudera navigator
        MetadataResultIterator metadataResultIterator = new MetadataResultIterator(plugin.getClient(), MetadataType.ENTITIES,
                "identity:" + myCustomEntity.generateId(), 1, Lists.newArrayList());
        
        assertTrue(metadataResultIterator.hasNext());
        
        Map<String, Object> result = metadataResultIterator.next();
        assertEquals(myCustomEntity.getSourceType().toString(), result.get("sourceType"));
        assertEquals(myCustomEntity.getEntityType().toString(), result.get("type"));
        assertEquals(myCustomEntity.getName().toString(), result.get("originalName"));
        
        metadataResultIterator = new MetadataResultIterator(plugin.getClient(), MetadataType.ENTITIES,
                "identity:" + myCustomEntity2.generateId(), 1, Lists.newArrayList());
        
        assertTrue(metadataResultIterator.hasNext());
        
        result = metadataResultIterator.next();
        assertEquals(myCustomEntity2.getSourceType().toString(), result.get("sourceType"));
        assertEquals(myCustomEntity2.getEntityType().toString(), result.get("type"));
        assertEquals(myCustomEntity2.getName().toString(), result.get("originalName"));
    }
}
