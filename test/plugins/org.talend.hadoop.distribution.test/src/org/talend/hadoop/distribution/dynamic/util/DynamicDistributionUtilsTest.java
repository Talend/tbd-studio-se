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
package org.talend.hadoop.distribution.dynamic.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
@SuppressWarnings("nls")
public class DynamicDistributionUtilsTest {

    @Test
    public void testFillTemplate() {
        String template = "test_{0}";
        String template1 = "test_{0}_{1}";
        String value = "value";
        String value1 = "value1";
        String expected = "test_value";
        String expected1 = "test_value_value1";

        String result = DynamicDistributionUtils.fillTemplate(template, value);
        String result1 = DynamicDistributionUtils.fillTemplate(template1, value, value1);

        assertEquals(expected, result);
        assertEquals(expected1, result1);
    }

}
