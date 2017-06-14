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
package org.talend.repository.hcatalog.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.repository.model.connection.ConnectionStatus;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopSubForm;
import org.talend.repository.hcatalog.Activator;
import org.talend.repository.hcatalog.i18n.Messages;
import org.talend.repository.hcatalog.service.HCatalogServiceUtil;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public abstract class AbstractHCatalogForm extends AbstractHadoopSubForm<HCatalogConnection> {

    protected boolean hcatalogSettingIsValide = false;

    protected AbstractHCatalogForm(Composite parent, int style, ConnectionItem connectionItem) {
        super(parent, style, connectionItem);
    }

    protected AbstractHCatalogForm(Composite parent, int style, String[] existingNames, ConnectionItem connectionItem) {
        super(parent, style, existingNames, connectionItem);
    }

    protected ConnectionStatus checkConnection() {
        return checkConnection(true);
    }

    protected ConnectionStatus checkConnection(boolean displayDialog) {
        ConnectionStatus connectionStatus = HCatalogServiceUtil.testConnection(getConnection());
        hcatalogSettingIsValide = connectionStatus.getResult();
        String connectException = connectionStatus.getMessageException();

        if (hcatalogSettingIsValide) {
            if (!isReadOnly()) {
                updateStatus(IStatus.OK, null);
            }
            if (displayDialog) {
                MessageDialog.openInformation(getShell(),
                        Messages.getString("AbstractHCatalogForm.checkConnection.success.title"), //$NON-NLS-1$
                        Messages.getString("AbstractHCatalogForm.checkConnection.success.msg", connectionItem.getProperty() //$NON-NLS-1$
                                .getDisplayName()));
            }
        } else {
            String mainMsg = Messages.getString("AbstractHCatalogForm.checkConnection.failure.msg"); //$NON-NLS-1$
            if (!isReadOnly()) {
                updateStatus(IStatus.WARNING, mainMsg);
            }
            if (displayDialog) {
                new ErrorDialogWidthDetailArea(getShell(), Activator.PLUGIN_ID, mainMsg, connectException);
            }
        }

        return connectionStatus;
    }

}
