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
package org.talend.hadoop.distribution.test.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.talend.hadoop.distribution.utils.DefaultConfigurationManager;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class DefaultConfigurationManagerTest {

    @Test
    public void testGetValue_Null() {
        assertNull(DefaultConfigurationManager.getValue(null));
        assertNull(DefaultConfigurationManager.getValue(null, new String[0]));
        JSONObject json = new JSONObject();
        assertNull(DefaultConfigurationManager.getValue(json, new String[0]));
    }

    @Test
    public void testGetValue_NotExisted() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("key1", "123");
        assertNull(DefaultConfigurationManager.getValue(json, "abc"));
    }

    @Test
    public void testGetValue_Existed() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("key1", "123");
        String value = DefaultConfigurationManager.getValue(json, "key1");
        assertNotNull(value);
        assertEquals("123", value);
    }

    @Test
    public void testGetValue_Whole() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("key1", "123");
        String value = DefaultConfigurationManager.getValue(json, "");
        assertNotNull(value);
        JSONObject getJson = null;
        try {
            getJson = new JSONObject(value);
        } catch (JSONException e) {
            //
        }
        assertNotNull("Can't get the whole JSON", getJson);
        assertTrue(getJson.has("key1"));
        assertEquals("123", getJson.getString("key1"));
    }

    @Test
    public void testGetValue_DeepLevel() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("key1", "123");

        JSONObject level1 = new JSONObject();
        level1.put("level1", "789");
        json.put("L1", level1);

        JSONObject level2 = new JSONObject();
        level2.put("level2", 10);
        level1.put("L2", level2);

        String value = DefaultConfigurationManager.getValue(json, "key1");
        assertNotNull(value);
        assertEquals("123", value);

        value = DefaultConfigurationManager.getValue(json, "L1", "level1");
        assertNotNull(value);
        assertEquals("789", value);

        value = DefaultConfigurationManager.getValue(json, "L1", "L2", "level2");
        assertNotNull(value);
        assertEquals("10", value);

        value = DefaultConfigurationManager.getValue(json, "L1", "L2");
        assertNotNull(value);
        JSONObject getJson = null;
        try {
            getJson = new JSONObject(value);
        } catch (JSONException e) {
            //
        }
        assertNotNull(getJson);
        assertEquals(10, getJson.getInt("level2"));
    }

    @Test
    public void testGetValue_Array() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("key1", "123");
        JSONArray arr = new JSONArray();
        arr.put(1);
        arr.put(2);
        arr.put(13);
        arr.put("abc");
        json.put("arr1", arr);
        String value = DefaultConfigurationManager.getValue(json, "arr1");
        assertNotNull(value);

        JSONArray getArr = null;
        try {
            getArr = new JSONArray(value);
        } catch (JSONException e) {
            //
        }
        assertNotNull("Can't support to get array", getArr);
        assertEquals(4, getArr.length());
        assertEquals(1, getArr.get(0));
        assertEquals(2, getArr.get(1));
        assertEquals(13, getArr.get(2));
        assertEquals("abc", getArr.get(3));
    }

}
