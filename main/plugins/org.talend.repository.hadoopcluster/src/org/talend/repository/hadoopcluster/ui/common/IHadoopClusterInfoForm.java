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
package org.talend.repository.hadoopcluster.ui.common;

import org.talend.metadata.managment.ui.wizard.AbstractForm.ICheckListener;

/**
 * created by ycbai on 2014年9月15日 Detailled comment
 *
 */
public interface IHadoopClusterInfoForm {

    public void updateForm();

    public void init();

    public void setListener(ICheckListener listener);

    public void setReadOnly(boolean readOnly);

    public boolean checkFieldsValue();

    public void dispose();

}
