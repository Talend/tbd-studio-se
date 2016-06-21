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
package org.talend.lineage.cloudera.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.talend.lineage.cloudera.entity.TalendField;
import org.talend.lineage.cloudera.util.ClouderaFieldConvertor;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class ClouderaFieldConvertorTest {

    @Test
    public void testConvertToTalendField() {
        assertNotNull(ClouderaFieldConvertor.convertToTalendField(null).size());
        assertEquals(0, ClouderaFieldConvertor.convertToTalendField(null).size());

        Map<String, String> schema = new HashMap<String, String>();
        schema.put("id", "int");
        schema.put("name", "String");
        List<TalendField> fields = ClouderaFieldConvertor.convertToTalendField(schema);
        assertNotNull(fields);
        assertEquals("[{\"name\":\"name\", \"dataType\":\"String\"}, {\"name\":\"id\", \"dataType\":\"int\"}]", fields.toString());
    }
}
