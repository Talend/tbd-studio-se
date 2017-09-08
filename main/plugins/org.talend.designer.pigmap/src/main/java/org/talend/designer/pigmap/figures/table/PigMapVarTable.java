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
package org.talend.designer.pigmap.figures.table;

import org.talend.designer.gefabstractmap.figures.table.AbstractTable;
import org.talend.designer.gefabstractmap.figures.table.ColumnKeyConstant;
import org.talend.designer.gefabstractmap.figures.table.ColumnSash;
import org.talend.designer.gefabstractmap.figures.table.TableColumn;
import org.talend.designer.pigmap.i18n.Messages;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapVarTable extends AbstractTable {

    public PigMapVarTable(PigMapVarTableManager tableModelManager) {
        super(tableModelManager);
        createColumns();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.table.AbstractTable#createColumns()
     */
    @Override
    protected void createColumns() {
        TableColumn column = new TableColumn(ColumnKeyConstant.KEY_TYPE);
        column.setText(Messages.getString("PigMapVarTable.node"));//$NON-NLS-1$
        addColumn(column);

        ColumnSash sash = new ColumnSash(this);
        sash.setLeftColumn(column);
        addSeparator(sash);

        column = new TableColumn(ColumnKeyConstant.KEY_VARIABLE);
        column.setText(Messages.getString("PigMapVarTable.alias"));//$NON-NLS-1$
        sash.setRightColumn(column);
        addColumn(column);

        sash = new ColumnSash(this);
        sash.setLeftColumn(column);
        addSeparator(sash);

        column = new TableColumn(ColumnKeyConstant.KEY_EXPRESSION);
        column.setText(Messages.getString("PigMapVarTable.UDF_function"));//$NON-NLS-1$
        sash.setRightColumn(column);
        addColumn(column);
    }
}
