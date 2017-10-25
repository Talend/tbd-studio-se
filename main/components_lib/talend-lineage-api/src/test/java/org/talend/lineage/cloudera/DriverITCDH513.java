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

import java.util.Arrays;
import java.util.Map;

import org.joda.time.Instant;
import org.talend.lineage.cloudera.entity.MyCustomEntity;

import com.cloudera.nav.sdk.client.MetadataResultIterator;
import com.cloudera.nav.sdk.client.NavigatorPlugin;
import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.MetadataType;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.google.common.collect.Lists;

public class DriverITCDH513 {

    public static void main(String[] args) {

        NavigatorPlugin plugin = NavigatorPlugin.fromConfigFile("C:\\Users\\zafkir\\Documents\\sample.conf"); // src/test/resources/sample.conf

        plugin.registerModels(DriverITCDH513.class.getClass().getPackage().getName());

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

        ResultSet results = plugin.write(Arrays.asList(myCustomEntity, myCustomEntity2));

        if (results.hasErrors()) {
            throw new RuntimeException(results.toString());
        }

        MetadataResultIterator metadataResultIterator = new MetadataResultIterator(plugin.getClient(), MetadataType.ENTITIES,
                "identity:" + myCustomEntity.generateId(), 1, Lists.newArrayList());
        if(metadataResultIterator.hasNext()) {
            Map<String, Object> result = metadataResultIterator.next();
            System.out.println("Entity[identity: " + result.get("identity") + ", source: " + result.get("sourceType") + ", type: " + result.get("type") + ", name: " + result.get("originalName") + "]");
        } else {
            throw new RuntimeException("Entity (identity: " + myCustomEntity.generateId() + ") not found");
        }
        
        metadataResultIterator = new MetadataResultIterator(plugin.getClient(), MetadataType.ENTITIES,
                "identity:" + myCustomEntity2.generateId(), 1, Lists.newArrayList());
        if(metadataResultIterator.hasNext()) {
            Map<String, Object> result = metadataResultIterator.next();
            System.out.println("Entity[identity: " + result.get("identity") + ", source: " + result.get("sourceType") + ", type: " + result.get("type") + ", name: " + result.get("originalName") + "]");
        } else {
            throw new RuntimeException("Entity (identity: " + myCustomEntity2.generateId() + ") not found");
        }
    }
}
