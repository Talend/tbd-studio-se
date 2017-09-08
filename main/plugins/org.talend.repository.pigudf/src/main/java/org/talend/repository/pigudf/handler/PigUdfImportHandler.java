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
package org.talend.repository.pigudf.handler;

import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.PropertiesPackage;
import org.talend.repository.view.di.viewer.handlers.RoutineImportHandler;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class PigUdfImportHandler extends RoutineImportHandler {

    /**
     * DOC ggu PigUdfImportHandler constructor comment.
     */
    public PigUdfImportHandler() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.view.di.viewer.handlers.RoutineImportHandler#validEClass(org.talend.core.model.properties
     * .Item)
     */
    @Override
    protected boolean validEClass(Item item) {
        if (item != null && item.eClass().equals(PropertiesPackage.eINSTANCE.getPigudfItem())) {
            return true;
        }
        return false;

    }

}
