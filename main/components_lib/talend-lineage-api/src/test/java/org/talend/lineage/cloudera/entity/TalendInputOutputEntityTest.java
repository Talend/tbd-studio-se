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
package org.talend.lineage.cloudera.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.talend.lineage.cloudera.entity.TalendInputOutputEntity;
import org.talend.lineage.cloudera.util.GeneratorID;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class TalendInputOutputEntityTest {

    @Test
    public void testAddNextEntity() {
        TalendInputOutputEntity tioe = new TalendInputOutputEntity("job", "component1");
        assertEquals(0, tioe.getPreviousEntitiesId().size());
        tioe.addPreviousEntity("component2");
        assertEquals(1, tioe.getPreviousEntitiesId().size());
        assertEquals("component2", tioe.getPreviousEntitiesId().get(0));
        assertEquals(1, tioe.getSourceProxies().size());

        tioe.addPreviousEntity("component3");
        assertEquals(2, tioe.getPreviousEntitiesId().size());
        assertEquals("component2", tioe.getPreviousEntitiesId().get(0));
        assertEquals("component3", tioe.getPreviousEntitiesId().get(1));
        assertEquals(2, tioe.getSourceProxies().size());

        assertEquals(0, tioe.getNextEntitiesId().size());
        tioe.addNextEntity("component4");
        assertEquals(1, tioe.getNextEntitiesId().size());
        assertEquals("component4", tioe.getNextEntitiesId().get(0));
        assertEquals(1, tioe.getTargetProxies().size());

        tioe.addNextEntity("component5");
        assertEquals(2, tioe.getNextEntitiesId().size());
        assertEquals("component4", tioe.getNextEntitiesId().get(0));
        assertEquals("component5", tioe.getNextEntitiesId().get(1));
        assertEquals(2, tioe.getTargetProxies().size());

        assertEquals("[component2, component3]---> component1 (" + GeneratorID.generateEntityID("job", "component1")
                + ") --->[component4, component5]", tioe.toString());
    }

    @Test
    public void testConnectToEntity() {
        TalendInputOutputEntity tioe = new TalendInputOutputEntity("job", "component1");
        assertEquals(0, tioe.getPreviousEntitiesId().size());
        tioe.connectToEntity(Arrays.asList("component2", "component3"), Arrays.asList("component4", "component5"));
        assertEquals(2, tioe.getPreviousEntitiesId().size());
        assertEquals(2, tioe.getSourceProxies().size());
        assertEquals(2, tioe.getNextEntitiesId().size());
        assertEquals(2, tioe.getTargetProxies().size());

        assertEquals(
                "[" + GeneratorID.generateNodeID("job", "component2") + ", " + GeneratorID.generateNodeID("job", "component3")
                        + "]---> component1 (" + GeneratorID.generateEntityID("job", "component1") + ") --->["
                        + GeneratorID.generateNodeID("job", "component4") + ", "
                        + GeneratorID.generateNodeID("job", "component5") + "]", tioe.toString());

    }

    @Test
    public void testConnectToEntity_tFileNoInputNoOutput() {
        TalendInputOutputEntity tioe = new TalendInputOutputEntity("job", "tfile_component1");
        assertEquals(0, tioe.getPreviousEntitiesId().size());
        tioe.connectToEntity(new ArrayList<String>(), new ArrayList<String>());
        assertEquals(1, tioe.getPreviousEntitiesId().size());
        assertEquals(1, tioe.getSourceProxies().size());
        assertEquals(1, tioe.getNextEntitiesId().size());
        assertEquals(1, tioe.getTargetProxies().size());

        assertEquals(
                "[" + GeneratorID.generateDatasetID("job", "tfile_component1") + "]---> tfile_component1 ("
                        + GeneratorID.generateEntityID("job", "tfile_component1") + ") --->["
                        + GeneratorID.generateDatasetID("job", "tfile_component1") + "]", tioe.toString());

    }

    @Test
    public void testConnectToEntity_NoInputNoOutputErrorcase() {
        TalendInputOutputEntity tioe = new TalendInputOutputEntity("job", "component1");
        assertEquals(0, tioe.getPreviousEntitiesId().size());
        tioe.connectToEntity(new ArrayList<String>(), new ArrayList<String>());
        assertEquals(0, tioe.getPreviousEntitiesId().size());
        assertEquals(0, tioe.getSourceProxies().size());
        assertEquals(0, tioe.getNextEntitiesId().size());
        assertEquals(0, tioe.getTargetProxies().size());

        assertEquals("[]---> component1 (" + GeneratorID.generateEntityID("job", "component1") + ") --->[]", tioe.toString());

    }
}
