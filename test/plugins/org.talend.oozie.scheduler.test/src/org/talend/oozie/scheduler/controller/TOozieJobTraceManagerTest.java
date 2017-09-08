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
package org.talend.oozie.scheduler.controller;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Marvin Wang
 * @version 1.0 jdk1.6
 * @date May 24, 2012
 */
public class TOozieJobTraceManagerTest {

    public static final String JOB_ID = "job_id";

    public static final String JOB_ID_2 = "job_id_2";

    public static final String JOB_ID_3 = "job_id_3";

    public static final String TRACE = "This is trace!";

    public static final String TRACE3 = "This is trace3!";

    /**
     * Test method for {@link org.talend.oozie.scheduler.controller.TOozieJobTraceManager#getInstance()}.
     */
    @Test
    public void testGetInstance() {

    }

    @BeforeClass
    @AfterClass
    public static void cleanup() {
        TOozieJobTraceManager traceManager = TOozieJobTraceManager.getInstance();
        traceManager.clear();
    }

    /**
     * Test method for
     * {@link org.talend.oozie.scheduler.controller.TOozieJobTraceManager#putTrace(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testPutTrace() {
        TOozieJobTraceManager traceManager = TOozieJobTraceManager.getInstance();
        traceManager.putTrace(JOB_ID, "");
        Assert.assertEquals("", traceManager.getTrace(JOB_ID));

        traceManager.putTrace(JOB_ID, null);
        Assert.assertNull(traceManager.getTrace(JOB_ID));

        traceManager.putTrace(JOB_ID, TRACE);
        Assert.assertEquals(TRACE, traceManager.getTrace(JOB_ID));
    }

    /**
     * Test method for {@link org.talend.oozie.scheduler.controller.TOozieJobTraceManager#getTrace(java.lang.String)}.
     */
    @Test
    public void testGetTrace() {
        TOozieJobTraceManager traceManager = TOozieJobTraceManager.getInstance();
        traceManager.getTrace(JOB_ID_2);

        Assert.assertNull(traceManager.getTrace(JOB_ID_2));

        traceManager.putTrace(JOB_ID_2, "");
        Assert.assertEquals("", traceManager.getTrace(JOB_ID_2));

        traceManager.putTrace(JOB_ID_2, null);
        Assert.assertNull(traceManager.getTrace(JOB_ID_2));

        traceManager.putTrace(JOB_ID_2, TRACE);
        Assert.assertEquals(TRACE, traceManager.getTrace(JOB_ID_2));
    }

    /**
     * Test method for {@link org.talend.oozie.scheduler.controller.TOozieJobTraceManager#removeTrace(java.lang.String)}
     * .
     */
    @Test
    public void testRemoveTrace() {
        TOozieJobTraceManager traceManager = TOozieJobTraceManager.getInstance();
        traceManager.putTrace(JOB_ID_3, TRACE3);

        traceManager.removeTrace(JOB_ID_3);
        String actualTrace = traceManager.getTrace(JOB_ID_3);
        Assert.assertNull(actualTrace);
    }
}
