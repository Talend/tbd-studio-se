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
package org.talend.designer.hdfsbrowse.hadoop.service.check;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;

/**
 * created by ycbai on Aug 14, 2014 Detailled comment
 *
 */
public abstract class CheckedWorkUnit implements ICheckedWorkUnit {

    private Integer timeout;

    private ClassLoader classLoader;

    @Override
    public Object execute() throws HadoopServerException {
        Object result = null;
        int to = DEFAULT_TIMEOUT;
        if (timeout != null) {
            to = timeout;
        }
        Callable<Object> callable = getCallable();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Object> future = executor.submit(callable);
        try {
            result = future.get(to, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(true);
            throw new HadoopServerException(e);
        }

        return result;
    }

    private Callable<Object> getCallable() {
        return new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                final ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
                try {
                    if (classLoader == null) {
                        classLoader = oldClassLoaderLoader;
                    } else {
                        Thread.currentThread().setContextClassLoader(classLoader);
                    }
                    return run(classLoader);
                } finally {
                    Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
                }
            }
        };
    }

    protected abstract Object run(ClassLoader cl) throws Exception;

    @Override
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

}
