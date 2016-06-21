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
import org.talend.lineage.cloudera.entity.TalendOutputEntity;
import org.talend.lineage.cloudera.util.GeneratorID;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class TalendOutputEntityTest {

    @Test
    public void testAddNextEntity() {
        TalendOutputEntity toe = new TalendOutputEntity("job", "component1");
        assertEquals(0, toe.getPreviousEntitiesId().size());
        toe.addPreviousEntity("component2");
        assertEquals(1, toe.getPreviousEntitiesId().size());
        assertEquals("component2", toe.getPreviousEntitiesId().get(0));
        assertEquals(1, toe.getSourceProxies().size());

        toe.addPreviousEntity("component3");
        assertEquals(2, toe.getPreviousEntitiesId().size());
        assertEquals("component2", toe.getPreviousEntitiesId().get(0));
        assertEquals("component3", toe.getPreviousEntitiesId().get(1));
        assertEquals(2, toe.getSourceProxies().size());

        assertEquals("[component2, component3]---> component1 (" + GeneratorID.generateEntityID("job", "component1") + ")",
                toe.toString());
    }

    @Test
    public void testConnectToEntity() {
        TalendOutputEntity toe = new TalendOutputEntity("job", "component1");
        assertEquals(0, toe.getPreviousEntitiesId().size());
        toe.connectToEntity(Arrays.asList("component2", "component3"), Arrays.asList("component4", "component5"));
        assertEquals(2, toe.getPreviousEntitiesId().size());
        assertEquals(2, toe.getSourceProxies().size());

        assertEquals(
                "[" + GeneratorID.generateNodeID("job", "component2") + ", " + GeneratorID.generateNodeID("job", "component3")
                        + "]---> component1 (" + GeneratorID.generateEntityID("job", "component1") + ")", toe.toString());

    }
}
