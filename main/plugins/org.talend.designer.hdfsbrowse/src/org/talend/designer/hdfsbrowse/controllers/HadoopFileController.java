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
package org.talend.designer.hdfsbrowse.controllers;

import org.talend.core.ui.properties.tab.IDynamicProperty;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopFileController extends AbstractHDFSBrowseController {

    private static final String FILE_HADOOP = "FILE_HADOOP"; //$NON-NLS-1$

    public HadoopFileController(IDynamicProperty dp) {
        super(dp);
    }

    @Override
    protected String getControllerName() {
        return FILE_HADOOP;
    }

    @Override
    protected EHadoopFileTypes getHDFSType() {
        return EHadoopFileTypes.FILE;
    }

}
