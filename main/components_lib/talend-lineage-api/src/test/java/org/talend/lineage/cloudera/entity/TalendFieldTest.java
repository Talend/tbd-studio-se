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
import org.talend.lineage.cloudera.entity.TalendField;
import org.talend.lineage.cloudera.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class TalendFieldTest {

    @Test
    public void test() {
        TalendField field = new TalendField();
        assertEquals("{\"name\":\"null\", \"dataType\":\"null\"}", field.toString());
        assertEquals(SourceType.SDK, field.getSourceType());
        assertEquals(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, field.getNamespace());
        field = new TalendField("id", "int");
        assertEquals("{\"name\":\"id\", \"dataType\":\"int\"}", field.toString());
        assertEquals(SourceType.SDK, field.getSourceType());
        assertEquals(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, field.getNamespace());
    }
}
