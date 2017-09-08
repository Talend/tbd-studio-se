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
package org.talend.hadoop.distribution.test.condition;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.Expression;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;

/**
 * Test class for the {@link ComponentCondition} distribution.
 *
 */
public class ComponentConditionTest {

    private final static String PARAM_1 = "param1"; //$NON-NLS-1$

    private final static String VALUE_1 = "value1"; //$NON-NLS-1$

    private final static String LEFT_PAR = "("; //$NON-NLS-1$

    private final static String RIGHT_PAR = ")"; //$NON-NLS-1$

    private final static String SINGLE_QUOTE = "'"; //$NON-NLS-1$

    private final static String EQ = "=="; //$NON-NLS-1$

    private final static String ENCODING = "UTF-8"; //$NON-NLS-1$

    private static String result1, result2;

    @Before
    public void setUp() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/resources/complexcondition1"); //$NON-NLS-1$
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, ENCODING);
        result1 = writer.toString();

        inputStream = getClass().getResourceAsStream("/resources/complexcondition2"); //$NON-NLS-1$
        writer = new StringWriter();
        IOUtils.copy(inputStream, writer, ENCODING);
        result2 = writer.toString();

    }

    @Test
    public void testGetConditionString() throws Exception {
        ComponentCondition dc1 = new SimpleComponentCondition(new BasicExpression(PARAM_1, VALUE_1, EqualityOperator.EQ));
        assertEquals(dc1.getConditionString(), LEFT_PAR + PARAM_1 + EQ + SINGLE_QUOTE + VALUE_1 + SINGLE_QUOTE + RIGHT_PAR);

        Expression e1 = new BasicExpression("A", "aaa", EqualityOperator.EQ); //$NON-NLS-1$ //$NON-NLS-2$
        Expression e2 = new BasicExpression("Z", "ccc", EqualityOperator.EQ); //$NON-NLS-1$ //$NON-NLS-2$
        Expression e3 = new BasicExpression("A", "bbb", EqualityOperator.EQ); //$NON-NLS-1$ //$NON-NLS-2$
        Expression e4 = new BasicExpression("B", "ccc", EqualityOperator.NOT_EQ); //$NON-NLS-1$ //$NON-NLS-2$

        dc1 = new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(e1),
                new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(e4),
                        new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(e2),
                                new SimpleComponentCondition(e3), BooleanOperator.AND)), BooleanOperator.OR)),
                BooleanOperator.AND));
        assertEquals(dc1.getConditionString(), result1);

        dc1 = new MultiComponentCondition(new SimpleComponentCondition(e1), new MultiComponentCondition(
                new SimpleComponentCondition(e2), new NestedComponentCondition(new MultiComponentCondition(
                        new SimpleComponentCondition(e4), new SimpleComponentCondition(e3), BooleanOperator.OR)),
                BooleanOperator.AND), BooleanOperator.AND);
        assertEquals(dc1.getConditionString(), result2);
    }

}
