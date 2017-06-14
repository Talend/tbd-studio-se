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
package org.talend.oozie.scheduler.views;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.talend.core.model.process.IProcess2;
import org.talend.designer.core.ui.IJobTrackerListener;

/**
 * DOC nrousseau class global comment. Detailled comment
 */
public class OozieJobTrackerListener implements IJobTrackerListener {

    private static IProcess2 process;

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.IJobTrackerListener#focusOnJob(org.talend.core.model.process.IProcess2)
     */
    @Override
    public void focusOnJob(IProcess2 process) {
        this.process = process;
        // System.out.println("Job:" + process);
        IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (ww != null && ww.getActivePage() != null) {
            IViewPart viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                    .findView("org.talend.oozie.scheduler.views.OozieSchedulerView");
            if (viewPart != null) {
                ((TOozieView) viewPart).refresh();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.IJobTrackerListener#allJobClosed()
     */
    @Override
    public void allJobClosed() {
        process = null;
        // System.out.println("Job:" + process);
        IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (ww != null && ww.getActivePage() != null) {
            IViewPart viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                    .findView("org.talend.oozie.scheduler.views.OozieSchedulerView");
            if (viewPart != null) {
                ((TOozieView) viewPart).refresh();
            }
        }
    }

    public static IProcess2 getProcess() {
        return process;
    }

}
