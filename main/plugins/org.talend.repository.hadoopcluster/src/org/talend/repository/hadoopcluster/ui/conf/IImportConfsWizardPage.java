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

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;

/**
 * created by ycbai on 2015年5月29日 Detailled comment
 *
 */
public interface IImportConfsWizardPage extends IWizardPage {

    public boolean isLastPage();

    public IRetrieveConfsService getConfsService();

    public List<String> getSelectedServices();

    public void applyFilter();
    
    public boolean isSupportCreateServiceConnection();
    
    public List<String> getNecessaryServiceName();

}
