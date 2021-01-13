package org.talend.hadoop.distribution.test.condition.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.common.SparkBatchLocalCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

public class SparkBatchLocalConditionTest {

    @Test
    public void testGetConditionString() {

        String expected = "(#LINK@NODE.SPARK_CONFIGURATION.SPARK_LOCAL_MODE == 'true') AND (#LINK@NODE.SPARK_CONFIGURATION.SPARK_LOCAL_VERSION == 'SPARK_3_0_x')"; //$NON-NLS-1$
        SparkBatchLocalCondition condition = new SparkBatchLocalCondition(
                SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, ESparkVersion.SPARK_3_0);
        assertEquals(expected, condition.getConditionString());
    }
}
