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
package org.talend.oozie.scheduler.jobsubmission.model;

import java.util.Date;

import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.designer.hdfsbrowse.exceptions.HadoopReflectionException;
import org.talend.designer.hdfsbrowse.reflection.HadoopClassConstants;
import org.talend.designer.hdfsbrowse.reflection.HadoopReflection;
import org.talend.oozie.scheduler.utils.OozieClassLoaderFactory;

public class JobContext {

    public static final String NAME_NODE_END_POINT = "nameNodeEndPoint";

    public static final String JOB_TRACKER_END_POINT = "jobTrackerEndPoint";

    public static final String OOZIE_END_POINT = "oozieEndPoint";

    public static final String JOB_PATH_ON_HDFS = "jobPathOnHDFS";

    public static final String TIME_UNIT = "timeunit";

    public static final String FREQUENCY = "frequency";

    public static final String JOB_FQ_CLASS_NAME = "jobFQClassName";

    public static final String JOB_NAME = "jobName";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    public static final String TOS_CONTEXT_PATH = "tosContext";

    private static ClassLoader classLoader;

    /**
     * Defines the possible frequency unit of an Oozie application.
     */
    public static enum Timeunit {
        MINUTE,
        HOUR,
        DAY,
        WEEK,
        MONTH,
        END_OF_DAY,
        END_OF_MONTH,
        NONE
    }

    private final Object configuration;

    public JobContext() {
        this(null);
    }

    public JobContext(Object configuration) {
        classLoader = OozieClassLoaderFactory.getClassLoader();
        if (configuration == null) {
            try {
                configuration = HadoopReflection.newInstance(HadoopClassConstants.CONFIGURATION, classLoader);
            } catch (HadoopReflectionException e) {
                ExceptionHandler.process(e);
            }
        }
        this.configuration = configuration;
    }

    public void set(String name, String value) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { name, value });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public String get(String name) {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { name });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public String getJobName() {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { JOB_NAME });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setJobName(String jobName) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { JOB_NAME, jobName });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     * Does not include the NameNode scheme://host:port
     * 
     * @return job path on hdfs that does not include the NameNode scheme://host:port @
     */
    public String getJobPathOnHDFS() {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { JOB_PATH_ON_HDFS });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    /**
     * Plain path that should not include the NameNode scheme://host:port
     * 
     * @param jobPathOnHDFS @
     */
    public void setJobPathOnHDFS(String jobPathOnHDFS) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { JOB_PATH_ON_HDFS, jobPathOnHDFS });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     * Return the fully qualified Class Name for the ETL Job
     * 
     * @return TOS ETL Job's Fully Qualified Class Name @
     */
    public String getJobFQClassName() {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { JOB_FQ_CLASS_NAME });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    /**
     * Set the fully qualified Class Name for the ETL Job
     * 
     * @param jobFQClassName TOS ETL Job's Fully Qualified Class Name @
     */
    public void setJobFQClassName(String jobFQClassName) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { JOB_FQ_CLASS_NAME, jobFQClassName });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public int getFrequency() {
        int result = 0;
        try {
            result = (Integer) HadoopReflection.invokeMethod(configuration, "getInt", new Object[] { FREQUENCY, 0 },
                    String.class, int.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setFrequency(int frequency) {
        try {
            HadoopReflection
                    .invokeMethod(configuration, "setInt", new Object[] { FREQUENCY, frequency }, String.class, int.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public Timeunit getTimeUnit() {
        Timeunit result = null;
        try {
            result = (Timeunit) HadoopReflection.invokeMethod(configuration, "getEnum",
                    new Object[] { TIME_UNIT, Timeunit.NONE }, String.class, Enum.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setTimeUnit(Timeunit timeunit) {
        try {
            HadoopReflection.invokeMethod(configuration, "setEnum", new Object[] { TIME_UNIT, timeunit }, String.class,
                    Enum.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public Date getStartTime() {
        long startTime = 0;
        try {
            startTime = (Long) HadoopReflection.invokeMethod(configuration, "getLong", new Object[] { START_TIME, 0L },
                    String.class, long.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
        return (startTime != 0) ? new Date(startTime) : null;
    }

    public void setStartTime(Date startTime) {
        try {
            HadoopReflection.invokeMethod(configuration, "setLong", new Object[] { START_TIME, startTime.getTime() },
                    String.class, long.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public Date getEndTime() {
        long endTime = 0;
        try {
            endTime = (Long) HadoopReflection.invokeMethod(configuration, "getLong", new Object[] { END_TIME, 0L }, String.class,
                    long.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
        return (endTime != 0) ? new Date(endTime) : null;
    }

    public void setEndTime(Date endTime) {
        try {
            HadoopReflection.invokeMethod(configuration, "setLong", new Object[] { END_TIME, endTime.getTime() }, String.class,
                    long.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public String getJobTrackerEndPoint() {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { JOB_TRACKER_END_POINT });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setJobTrackerEndPoint(String jobTrackerEndPoint) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { JOB_TRACKER_END_POINT, jobTrackerEndPoint });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public String getNameNodeEndPoint() {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { NAME_NODE_END_POINT });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setNameNodeEndPoint(String nameNodeEndPoint) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { NAME_NODE_END_POINT, nameNodeEndPoint });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public String getOozieEndPoint() {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { OOZIE_END_POINT });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setOozieEndPoint(String oozieEndPoint) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { OOZIE_END_POINT, oozieEndPoint });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public int getDebug() {
        int result = 0;
        try {
            result = (Integer) HadoopReflection.invokeMethod(configuration, "getInt", new Object[] { "debug", 0 }, String.class,
                    int.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setDebug(int debug) {
        try {
            HadoopReflection.invokeMethod(configuration, "setInt", new Object[] { "debug", 1 }, String.class, int.class);
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

    public String getTosContextPath() {
        String result = null;
        try {
            result = (String) HadoopReflection.invokeMethod(configuration, "get", new Object[] { TOS_CONTEXT_PATH });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }

        return result;
    }

    public void setTosContextPath(String tosContextPath) {
        try {
            HadoopReflection.invokeMethod(configuration, "set", new Object[] { TOS_CONTEXT_PATH, tosContextPath });
        } catch (HadoopReflectionException e) {
            ExceptionHandler.process(e);
        }
    }

}
