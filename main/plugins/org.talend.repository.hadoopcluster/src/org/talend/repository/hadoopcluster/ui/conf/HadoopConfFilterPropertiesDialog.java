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
package org.talend.repository.hadoopcluster.ui.conf;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.talend.metadata.managment.ui.dialog.PropertiesDialog;
import org.talend.repository.hadoopcluster.i18n.Messages;

/**
 * created by ycbai on 2016年6月22日 Detailled comment
 *
 */
public class HadoopConfFilterPropertiesDialog extends PropertiesDialog {

    public HadoopConfFilterPropertiesDialog(Shell parentShell) {
        this(parentShell, Collections.EMPTY_LIST);
    }

    public HadoopConfFilterPropertiesDialog(Shell parentShell, List<Map<String, Object>> initProperties) {
        super(parentShell, initProperties);
    }

    @Override
    protected String getTitle() {
        return Messages.getString("HadoopConfFilterPropertiesDialog.title"); //$NON-NLS-1$
    }

    @Override
    protected String getDesc() {
        return Messages.getString("HadoopConfFilterPropertiesDialog.desc"); //$NON-NLS-1$
    }

    @Override
    protected List<String> getHidePropertyColumns() {
        return Collections.singletonList(getPropertiesValueColumnName());
    }

}
