package org.talend.hadoop.distribution.test;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;

public abstract class AbstractDistributionTest {

    protected HadoopComponent distribution;

    protected SqoopComponent sqoop;

    public AbstractDistributionTest(HadoopComponent distribution) {
        this.distribution = distribution;
        try {
            sqoop = (SqoopComponent) distribution;
        } catch (Exception ex) {
            // Some distribution do not implement SqoopComponent, ignore
        }
    }

    @Test
    public void isImpactedBySqoop2995() {
        if (sqoop != null) {
            assertFalse(sqoop.isImpactedBySqoop2995());
        }
    }

    @Test
    public void doSupportFetchPasswordFromFile() {
        if (sqoop != null) {
            assertFalse(sqoop.doSupportFetchPasswordFromFile());
        }
    }
}
