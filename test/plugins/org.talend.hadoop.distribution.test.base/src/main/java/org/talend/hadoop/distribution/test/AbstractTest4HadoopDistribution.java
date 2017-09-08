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
package org.talend.hadoop.distribution.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.talend.hadoop.distribution.component.HadoopComponent;

/**
 * DOC ggu class global comment. Detailled comment
 */
public abstract class AbstractTest4HadoopDistribution {

    private Object synObj = new Object();

    private HadoopComponent hadoopComponent;

    protected abstract Class<? extends HadoopComponent> getHadoopComponentClass();

    protected HadoopComponent getHadoopComponent() {
        if (hadoopComponent == null) {
            synchronized (synObj) {
                if (hadoopComponent == null && getHadoopComponentClass() != null) {
                    try {
                        hadoopComponent = getHadoopComponentClass().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        //
                    }
                }
            }
        }
        return hadoopComponent;
    }

    protected String getDistribution() {
        return getHadoopComponent().getDistribution();
    }

    protected String getDistributionDisplay() {
        return getHadoopComponent().getDistributionName();
    }

    protected String getDistributionVersion() {
        return getHadoopComponent().getVersion();
    }

    protected void doTestArray(String baseMessages, String[] expecteds, String[] actuals) {
        assertNotNull(expecteds);
        assertNotNull(actuals);
        if (expecteds.length == actuals.length) {
            assertArrayEquals(baseMessages, expecteds, actuals);
        } else {
            assertEquals(baseMessages + " , " + Arrays.asList(expecteds) + "<==>" + Arrays.asList(actuals), expecteds.length,
                    actuals.length);
        }
    }

    protected void doTestSet(Set<String> expecteds, Set<String> actuals) {
        assertNotNull(expecteds);
        assertNotNull(actuals);

        List<String> expectedList = new ArrayList<String>(expecteds);
        List<String> actualList = new ArrayList<String>(actuals);
        Collections.sort(expectedList);
        Collections.sort(actualList);

        List<String> expectedDiffList = new ArrayList<String>(expectedList);
        List<String> actualDiffList = new ArrayList<String>(actualList);
        expectedDiffList.removeAll(actualList);
        actualDiffList.removeAll(expectedList);

        if (expectedDiffList.isEmpty()) { // all expecteds items have contained in actuals
            if (actualDiffList.isEmpty()) { // equals (same items)
                //
            } else { // the expecteds is less than the actuals, maybe add more in define (actuals).
                assertEquals("The expected items didn't contains those :" + actualDiffList, expectedDiffList.size(),
                        actualDiffList.size());
            }
        } else { // the expecteds is more than the actuals, maybe remove some in define (actuals).
            if (actualDiffList.isEmpty()) { // remove some in actuals
                assertEquals("The expected items contains more those :" + expectedDiffList, expectedDiffList.size(),
                        actualDiffList.size());
            } else { // contained different items in expecteds and actuals
                assertEquals(MessageFormat.format("The {0} existed in expected list only, but the {1} existed in actual list. ",
                        expectedDiffList, actualDiffList), expectedDiffList.size(), actualDiffList.size());
            }
        }
    }
}
