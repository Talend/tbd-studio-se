// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.Expression;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

/**
 * Test class for the {@link Expression} distribution.
 *
 */
public class ExpressionTest {

    private final static String PARAM_1 = "param1"; //$NON-NLS-1$

    private final static String VALUE_1 = "value1"; //$NON-NLS-1$

    private final static String LEFT_PAR = "("; //$NON-NLS-1$

    private final static String RIGHT_PAR = ")"; //$NON-NLS-1$

    private final static String SINGLE_QUOTE = "'"; //$NON-NLS-1$

    private final static String EQ = "=="; //$NON-NLS-1$

    private final static String NEQ = "!="; //$NON-NLS-1$

    @Test
    public void testGetExpressionString() throws Exception {
        Expression e1 = new BasicExpression(PARAM_1, VALUE_1, EqualityOperator.EQ);
        assertEquals(e1.getExpressionString(), LEFT_PAR + PARAM_1 + EQ + SINGLE_QUOTE + VALUE_1 + SINGLE_QUOTE + RIGHT_PAR);

        e1 = new BasicExpression(PARAM_1, VALUE_1, EqualityOperator.NOT_EQ);
        assertEquals(e1.getExpressionString(), LEFT_PAR + PARAM_1 + NEQ + SINGLE_QUOTE + VALUE_1 + SINGLE_QUOTE + RIGHT_PAR);

        e1 = new LinkedNodeExpression(SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER,
                SparkBatchConstant.SPARK_LOCAL_VERSION_PARAMETER, EqualityOperator.GE, ESparkVersion.SPARK_3_0.getSparkVersion());
        assertEquals("(#LINK@NODE.SPARK_CONFIGURATION.SPARK_LOCAL_VERSION ge 'SPARK_3_0_x')", e1.getExpressionString()); //$NON-NLS-1$
    }

}
