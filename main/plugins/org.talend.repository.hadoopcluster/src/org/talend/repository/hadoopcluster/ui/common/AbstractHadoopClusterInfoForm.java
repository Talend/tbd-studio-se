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
package org.talend.repository.hadoopcluster.ui.common;

import org.eclipse.swt.widgets.Composite;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractHadoopClusterInfoForm<T extends HadoopClusterConnection> extends AbstractHadoopForm<T>
        implements IHadoopClusterInfoForm {

    protected AbstractHadoopClusterInfoForm(Composite parent, int style) {
        super(parent, style);
    }

    protected AbstractHadoopClusterInfoForm(Composite parent, int style, String[] existingNames) {
        super(parent, style, existingNames);
    }

}
