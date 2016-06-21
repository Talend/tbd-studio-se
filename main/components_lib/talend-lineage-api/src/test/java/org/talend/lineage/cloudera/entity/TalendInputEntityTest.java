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

import java.util.Arrays;

import org.junit.Test;
import org.talend.lineage.cloudera.entity.TalendInputEntity;
import org.talend.lineage.cloudera.util.GeneratorID;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class TalendInputEntityTest {

    @Test
    public void testAddNextEntity() {
        TalendInputEntity tie = new TalendInputEntity("job", "component1");
        assertEquals(0, tie.getNextEntitiesId().size());
        tie.addNextEntity("component4");
        assertEquals(1, tie.getNextEntitiesId().size());
        assertEquals("component4", tie.getNextEntitiesId().get(0));
        assertEquals(1, tie.getTargetProxies().size());

        tie.addNextEntity("component5");
        assertEquals(2, tie.getNextEntitiesId().size());
        assertEquals("component4", tie.getNextEntitiesId().get(0));
        assertEquals("component5", tie.getNextEntitiesId().get(1));
        assertEquals(2, tie.getTargetProxies().size());

        assertEquals("component1 (" + GeneratorID.generateEntityID("job", "component1") + ") --->[component4, component5]",
                tie.toString());
    }

    @Test
    public void testConnectToEntity() {
        TalendInputEntity tie = new TalendInputEntity("job", "component1");
        assertEquals(0, tie.getNextEntitiesId().size());
        tie.connectToEntity(Arrays.asList("component2", "component3"), Arrays.asList("component4", "component5"));
        assertEquals(2, tie.getNextEntitiesId().size());
        assertEquals(2, tie.getTargetProxies().size());

        assertEquals(
                "component1 (" + GeneratorID.generateEntityID("job", "component1") + ") --->["
                        + GeneratorID.generateNodeID("job", "component4") + ", "
                        + GeneratorID.generateNodeID("job", "component5") + "]", tie.toString());
    }
}
