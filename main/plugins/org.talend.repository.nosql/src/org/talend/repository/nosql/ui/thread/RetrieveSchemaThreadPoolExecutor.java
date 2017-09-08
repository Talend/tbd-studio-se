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
package org.talend.repository.nosql.ui.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;

import org.talend.commons.utils.threading.TalendCustomThreadPoolExecutor;
import org.talend.repository.nosql.model.INoSQLSchemaNode;

/**
 * 
 * created by ycbai on Jul 7, 2014 Detailled comment
 * 
 */
public class RetrieveSchemaThreadPoolExecutor extends TalendCustomThreadPoolExecutor {

    Map<INoSQLSchemaNode, RetrieveColumnRunnable> remainRunnables = Collections
            .synchronizedMap(new HashMap<INoSQLSchemaNode, RetrieveColumnRunnable>());

    public RetrieveSchemaThreadPoolExecutor(int queueCapacity) {
        super(queueCapacity);
    }

    public RetrieveSchemaThreadPoolExecutor(int queueCapacity, RejectedExecutionHandler handler) {
        super(queueCapacity, handler);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.ThreadPoolExecutor#afterExecute(java.lang.Runnable, java.lang.Throwable)
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        RetrieveColumnRunnable runnable = (RetrieveColumnRunnable) r;
        remainRunnables.remove(runnable.getNode());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.ThreadPoolExecutor#beforeExecute(java.lang.Thread, java.lang.Runnable)
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        RetrieveColumnRunnable runnable = (RetrieveColumnRunnable) r;
        remainRunnables.put(runnable.getNode(), runnable);
    }

    /**
     * If an node is in the list of runningThreads, it means that the item's related thread is running.
     * 
     * @param item
     * @return
     */
    public boolean isThreadRunning(INoSQLSchemaNode node) {
        return remainRunnables.containsKey(node);
    }

    public Map<INoSQLSchemaNode, RetrieveColumnRunnable> getRemainRunnables() {
        return this.remainRunnables;
    }

    /**
     * Find the RetrieveColumnRunnable from map and waiting queue. Map stores running runnables
     * 
     * @param key
     * @return
     */
    public synchronized RetrieveColumnRunnable getRunnable(INoSQLSchemaNode node) {
        // Get the runnable from map first, else then find it in the waiting queue.
        RetrieveColumnRunnable runnable = remainRunnables.get(node);
        if (runnable != null) {
            return runnable;
        }
        for (Object element : getQueue()) {
            RetrieveColumnRunnable ele = (RetrieveColumnRunnable) element;
            if (ele.getNode() == node) {
                return ele;
            }
        }
        return null;
    }
}
