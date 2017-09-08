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
package org.talend.repository.hadoopcluster.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * created by ycbai on 2015年5月29日 Detailled comment
 *
 */
public abstract class AbstractCheckedComposite extends Composite {

    private ICheckListener listener;

    private int statusLevel = IStatus.OK;

    private String status;

    public AbstractCheckedComposite(Composite parent, int style) {
        super(parent, style);
    }

    public void setListener(ICheckListener listener) {
        this.listener = listener;
    }

    protected void updateStatus(int level, final String statusLabelText) {
        this.statusLevel = level;
        this.status = statusLabelText;
        if (listener != null) {
            listener.checkPerformed(this);
        }
    }

    public boolean isStatusOnError() {
        return this.statusLevel == IStatus.ERROR;
    }

    public String getStatus() {
        return this.status;
    }

    public int getStatusLevel() {
        return this.statusLevel;
    }

}
