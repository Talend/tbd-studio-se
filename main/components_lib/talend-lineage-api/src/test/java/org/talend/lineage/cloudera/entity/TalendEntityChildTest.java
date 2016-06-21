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

import org.junit.Test;
import org.talend.lineage.cloudera.entity.TalendEntityChild;
import org.talend.lineage.cloudera.entity.TalendInputEntity;
import org.talend.lineage.cloudera.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.EntityType;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class TalendEntityChildTest {

    @Test
    public void test() {
        TalendEntityChild field = new TalendEntityChild("job", "parent", "id", "integer");
        assertEquals("null__id(" + GeneratorID.generateEntityChildID("job", "parent", "id") + ") --->[]", field.toString());
        assertEquals(GeneratorID.generateEntityChildID("job", "parent", "id"), field.generateId());
        assertEquals(SourceType.SDK, field.getSourceType());
        assertEquals(EntityType.FIELD, field.getEntityType());
        assertEquals(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, field.getNamespace());

        assertNull(field.getParent());
        TalendInputEntity tie = new TalendInputEntity("job", "component1");
        field.setParent(tie);
        assertEquals("component1", field.getParent().getName());
        assertEquals(tie.getEntityId(), field.getParentEntityId());

        assertEquals(0, field.getTargets().size());
        assertEquals(0, field.getTargetEntitiesId().size());
        field.addTarget("target1");
        assertEquals(1, field.getTargets().size());
        assertEquals(1, field.getTargetEntitiesId().size());
        assertEquals("target1", field.getTargetEntitiesId().get(0));
        field.addTarget("target2");
        assertEquals(2, field.getTargets().size());
        assertEquals(2, field.getTargetEntitiesId().size());
        assertEquals("target1", field.getTargetEntitiesId().get(0));
        assertEquals("target2", field.getTargetEntitiesId().get(1));
        assertEquals(tie.getEntityId() + "__id(" + GeneratorID.generateEntityChildID("job", "parent", "id")
                + ") --->[target1, target2]", field.toString());

    }
}
