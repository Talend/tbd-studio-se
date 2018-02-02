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
package org.talend.repository.hadoopcluster.ui.dynamic.form;

import java.util.Arrays;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerColumn;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class RowNumberLabelProvider extends ColumnLabelProvider {

    private TableViewer viewer;

    @Override
    protected void initialize(ColumnViewer viewer, ViewerColumn column) {
        super.initialize(viewer, column);
        this.viewer = null;
        if (viewer instanceof TableViewer) {
            this.viewer = (TableViewer) viewer;
        }
    }

    @Override
    public void update(ViewerCell cell) {
        super.update(cell);
        if (viewer != null) {
            int index = Arrays.asList(viewer.getTable().getItems()).indexOf(cell.getItem());
            cell.setText("" + (index + 1)); //$NON-NLS-1$
        }
    }
}
