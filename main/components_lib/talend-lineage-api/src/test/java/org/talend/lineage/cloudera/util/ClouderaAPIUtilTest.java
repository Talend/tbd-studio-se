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
package org.talend.lineage.cloudera.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.talend.lineage.cloudera.NavigatorNode;

/**
 * created by pbailly on 16 Oct 2015 Detailled comment
 *
 */
public class ClouderaAPIUtilTest {

    @Test
    public void testGetDatasetName() {
        assertEquals("my", ClouderaAPIUtil.getDatasetName("/my"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("/my/path"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("/my/path/"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("/my/long/path"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("this/is/my/long//path"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("path/"));
        assertEquals("path", ClouderaAPIUtil.getDatasetName("path////"));
        assertEquals("UnamedTalendDataset", ClouderaAPIUtil.getDatasetName("/"));
        assertEquals("UnamedTalendDataset", ClouderaAPIUtil.getDatasetName("////"));
        assertEquals("UnamedTalendDataset", ClouderaAPIUtil.getDatasetName(""));
        assertEquals("UnamedTalendDataset", ClouderaAPIUtil.getDatasetName(null));
    }

    @Test
    public void TestClouderaAPIUtil() {
        for (String myAllowedPrefix : ClouderaAPIUtil.FILE_INPUT_OUTPUT_COMPONENT_PREFIXS) {
            assertTrue(ClouderaAPIUtil.isFileInputOutputComponent(myAllowedPrefix));
            assertTrue(ClouderaAPIUtil.isFileInputOutputComponent(myAllowedPrefix + "_"));
            assertTrue(ClouderaAPIUtil.isFileInputOutputComponent(myAllowedPrefix.toUpperCase() + "_"));
            assertFalse(ClouderaAPIUtil.isFileInputOutputComponent("nope_" + myAllowedPrefix.toUpperCase() + "_"));
        }
    }

    @Test
    public void TestRetrieveNavigatorNode() {
        List<NavigatorNode> navigatorNodes = new ArrayList<NavigatorNode>();
        Map<String, String> schema = new HashMap<String, String>();
        schema.put("id", "int");
        NavigatorNode nav1 = new NavigatorNode("comp1", schema, null, null);
        navigatorNodes.add(nav1);
        NavigatorNode nav2 = new NavigatorNode("comp2", schema, null, null);
        navigatorNodes.add(nav2);
        assertNotNull(ClouderaAPIUtil.retrieveNavigatorNode("comp1", navigatorNodes));
        assertEquals("comp1", ClouderaAPIUtil.retrieveNavigatorNode("comp1", navigatorNodes).getName());
        assertNotNull(ClouderaAPIUtil.retrieveNavigatorNode("comp2", navigatorNodes));
        assertEquals("comp2", ClouderaAPIUtil.retrieveNavigatorNode("comp2", navigatorNodes).getName());
        assertNull(ClouderaAPIUtil.retrieveNavigatorNode("comp3", navigatorNodes));

    }

    @Test
    public void TestRetrieveNavigatorNodeTODO() {
        List<NavigatorNode> navigatorNodes = new ArrayList<NavigatorNode>();
        assertTrue(CollectionUtils.isEmpty(navigatorNodes));
    }

    @Test
    public void TestIsThisComponentContainsThisField() {
        List<NavigatorNode> navigatorNodes = new ArrayList<NavigatorNode>();
        Map<String, String> schema = new HashMap<String, String>();
        schema.put("id", "int");
        NavigatorNode nav1 = new NavigatorNode("comp1", schema, null, null);
        navigatorNodes.add(nav1);
        NavigatorNode nav2 = new NavigatorNode("comp2", schema, null, null);
        navigatorNodes.add(nav2);

        assertTrue(ClouderaAPIUtil.isFieldinComponent("comp1", "id", navigatorNodes));
        assertTrue(ClouderaAPIUtil.isFieldinComponent("comp2", "id", navigatorNodes));
        assertFalse(ClouderaAPIUtil.isFieldinComponent("comp1", "nope", navigatorNodes));
        assertFalse(ClouderaAPIUtil.isFieldinComponent("comp2", "nope", navigatorNodes));
        assertFalse(ClouderaAPIUtil.isFieldinComponent("comp3", "id", navigatorNodes));
    }

    @Test
    public void TestExtractNavigatorURL()

    {
        assertEquals("http://domain:3333", ClouderaAPIUtil.extractNavigatorURL("http://domain:3333/api/v1/"));
        assertEquals("https://domain:3333", ClouderaAPIUtil.extractNavigatorURL("https://domain:3333/api/v1/"));
        assertEquals("http://sd01.sd02.domain:3333", ClouderaAPIUtil.extractNavigatorURL("http://sd01.sd02.domain:3333/api/v1/"));
        assertEquals("https://sd01.sd02.domain:3333",
                ClouderaAPIUtil.extractNavigatorURL("https://sd01.sd02.domain:3333/api/v1/"));
        assertEquals("http://sd01.sd02.sd03.sd04.x.y.z.domain:3333",
                ClouderaAPIUtil.extractNavigatorURL("http://sd01.sd02.sd03.sd04.x.y.z.domain:3333/api/v1/"));
        assertEquals("http://sd01.sd02.domain:3333",
                ClouderaAPIUtil.extractNavigatorURL("http://sd01.sd02.domain:3333/api/v1/x/y/z/e/u/"));
        assertEquals("http://sd01.sd02.domain:3333",
                ClouderaAPIUtil.extractNavigatorURL("http://sd01.sd02.domain:3333/api/v1/x/y/z/:e/u/"));
        assertEquals("http://sd01.sd02.domain:12345678910",
                ClouderaAPIUtil.extractNavigatorURL("http://sd01.sd02.domain:12345678910/api/v1/"));
        assertEquals("http://sd01.sd02.domain:12345678910",
                ClouderaAPIUtil.extractNavigatorURL("http://sd01.sd02.domain:12345678910/api/v1/"));
        assertEquals("http://sd01/sd02/domain:3333", ClouderaAPIUtil.extractNavigatorURL("http://sd01/sd02/domain:3333/api/v1/"));
        assertEquals("httpXX://domain:3333/api/v1/", ClouderaAPIUtil.extractNavigatorURL("httpXX://domain:3333/api/v1/"));
    }
}
