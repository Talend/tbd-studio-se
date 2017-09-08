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
package org.talend.designer.neo4j.ui.tabs;

import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButtonForExtendedTable;
import org.talend.commons.ui.swt.extended.table.AbstractExtendedTableViewer;

public class ParameterAddButton<T> extends AddPushButtonForExtendedTable {

    private Class<T> objectToAdd;

    public ParameterAddButton(Composite parent, AbstractExtendedTableViewer<T> extendedTableViewer, Class<T> objectToAdd) {
        super(parent, extendedTableViewer);
        this.objectToAdd = objectToAdd;
    }

    @Override
    protected T getObjectToAdd() {
        try {
            return objectToAdd.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
