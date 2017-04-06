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
package org.talend.hadoop.distribution.test.condition;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
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
        SimpleComponentCondition sc1 = new SimpleComponentCondition(new BasicExpression("A", EqualityOperator.EQ, "xxx")); //$NON-NLS-1$ //$NON-NLS-2$
        SimpleComponentCondition sc2 = new SimpleComponentCondition(new BasicExpression("A", EqualityOperator.NOT_EQ, "yyy")); //$NON-NLS-1$ //$NON-NLS-2$
        NestedComponentCondition mcc1 = new NestedComponentCondition(
                new MultiComponentCondition(
                        new SimpleComponentCondition(new BasicExpression("B", EqualityOperator.EQ, "eee")), BooleanOperator.OR, new SimpleComponentCondition(new BasicExpression("B", EqualityOperator.EQ, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                        "ddd")))); //$NON-NLS-1$

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

    @Test
    public void generateSparkVersionShowIfConditionsTest() {

        assertNull(ComponentConditionUtil.generateSparkVersionShowIfConditions(null));

        Map<ESparkVersion, Set<DistributionVersion>> sparkVersionsMap = new HashMap<>();
        Set<DistributionVersion> distributionVersions = new HashSet<>();
        distributionVersions.add(new DistributionVersion(null,
                new DistributionBean(ComponentType.SPARKBATCH, "DISTRIB1", ""), "VERSION1", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        sparkVersionsMap.put(ESparkVersion.SPARK_2_0, distributionVersions);
        distributionVersions = new HashSet<>();
        distributionVersions.add(new DistributionVersion(null,
                new DistributionBean(ComponentType.SPARKBATCH, "DISTRIB2", ""), "VERSION2", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        distributionVersions.add(new DistributionVersion(null,
                new DistributionBean(ComponentType.SPARKBATCH, "DISTRIB3", ""), "VERSION3", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        sparkVersionsMap.put(ESparkVersion.SPARK_1_6, distributionVersions);
        String[] showIfs = ComponentConditionUtil.generateSparkVersionShowIfConditions(sparkVersionsMap);
        assertTrue(showIfs.length == 2);
        assertThat(Arrays.asList(showIfs), hasItem("(((DISTRIBUTION=='DISTRIB1') AND (SPARK_VERSION=='VERSION1')))")); //$NON-NLS-1$
        assertThat(
                Arrays.asList(showIfs),
                hasItem("(((DISTRIBUTION=='DISTRIB3') AND (SPARK_VERSION=='VERSION3')) OR ((DISTRIBUTION=='DISTRIB2') AND (SPARK_VERSION=='VERSION2')))")); //$NON-NLS-1$
    }
}
