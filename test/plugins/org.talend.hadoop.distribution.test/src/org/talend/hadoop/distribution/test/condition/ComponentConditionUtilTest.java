// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.utils.ComponentConditionUtil;

/**
 * Test class for the {@link ComponentConditionUtil} distribution.
 *
 */
public class ComponentConditionUtilTest {

    private static final String ENCODING = "UTF-8"; //$NON-NLS-1$

    private static String result1;

    @Before
    public void setUp() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/resources/complexcondition3"); //$NON-NLS-1$
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, ENCODING);
        result1 = writer.toString();
    }

    @Test
    public void buildDistributionShowIfTest() {
        SimpleComponentCondition sc1 = new SimpleComponentCondition(new BasicExpression("A", "xxx", EqualityOperator.EQ)); //$NON-NLS-1$ //$NON-NLS-2$
        SimpleComponentCondition sc2 = new SimpleComponentCondition(new BasicExpression("A", "yyy", EqualityOperator.NOT_EQ)); //$NON-NLS-1$ //$NON-NLS-2$
        NestedComponentCondition mcc1 = new NestedComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                new BasicExpression("B", "eee", EqualityOperator.EQ)), new SimpleComponentCondition(new BasicExpression("B", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                "ddd", EqualityOperator.EQ)), BooleanOperator.OR)); //$NON-NLS-1$

        assertNull(ComponentConditionUtil.buildDistributionShowIf(null));

        Set<ComponentCondition> set1 = new HashSet<>();
        set1.add(null);
        set1.add(sc1);

        assertNull(ComponentConditionUtil.buildDistributionShowIf(set1));

        set1 = new HashSet<>();
        set1.add(sc1);
        set1.add(null);
        set1.add(sc2);

        assertNull(ComponentConditionUtil.buildDistributionShowIf(set1));

        set1 = new HashSet<>();
        set1.add(null);
        set1.add(sc2);

        assertNull(ComponentConditionUtil.buildDistributionShowIf(set1));

        set1 = new TreeSet<>(new Comparator<ComponentCondition>() {

            @Override
            public int compare(ComponentCondition o1, ComponentCondition o2) {
                return o2.getConditionString().compareTo(o1.getConditionString());
            }
        });

        set1.add(sc1);
        set1.add(sc2);
        set1.add(mcc1);

        assertEquals(result1, ComponentConditionUtil.buildDistributionShowIf(set1).getConditionString());

    }
}
