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
package org.talend.lineage.cloudera;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class NavigatorNodeTest {

    @Test
    public void test() {
        Map<String, String> schema = new HashMap<String, String>();
        schema.put("id", "int");
        schema.put("name", "string");
        NavigatorNode navigatorNode = new NavigatorNode("name", schema, Arrays.asList("in1", "in2"),
                Arrays.asList("out1", "out2"));
        assertEquals("[name|{name=string, id=int}|[in1, in2]|[out1, out2]]", navigatorNode.toString());
        navigatorNode.setName("name2");
        assertEquals("name2", navigatorNode.getName());

        Map<String, String> schema2 = new HashMap<String, String>();
        schema2.put("id", "Integer");
        schema2.put("name", "String");
        navigatorNode.setSchema(schema2);
        assertEquals(schema2, navigatorNode.getSchema());

        navigatorNode.setInputNodes(Arrays.asList("in1", "in2", "in3"));
        assertEquals(Arrays.asList("in1", "in2", "in3"), navigatorNode.getInputNodes());

        navigatorNode.setOutputNodes(Arrays.asList("out1", "out2", "out3"));
        assertEquals(Arrays.asList("out1", "out2", "out3"), navigatorNode.getOutputNodes());
        assertEquals("[name2|{name=String, id=Integer}|[in1, in2, in3]|[out1, out2, out3]]", navigatorNode.toString());
    }

}
