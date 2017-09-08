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
package org.talend.repository.nosql.ui.provider;

import java.util.List;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.talend.core.repository.model.provider.AbstractMetadataExtractorViewProvider;
import org.talend.repository.nosql.model.ENoSQLSchemaType;
import org.talend.repository.nosql.model.INoSQLSchemaNode;

public class NoSQLSelectorTreeViewerProvider extends AbstractMetadataExtractorViewProvider implements ITableFontProvider,
        ITableColorProvider {

    FontRegistry registry = new FontRegistry();

    @Override
    public Object[] getChildren(Object element) {
        if (element instanceof List) {
            List tables = (List) element;
            return tables.toArray();
        }
        INoSQLSchemaNode node = (INoSQLSchemaNode) element;
        return node.getChildren().toArray();
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        INoSQLSchemaNode node = (INoSQLSchemaNode) element;
        if (columnIndex == 0) {
            return node.getName();
        }
        if (columnIndex == 1) {
            return node.getNodeType();
        }
        boolean isRetrievedTable = node.getSchemaType() == ENoSQLSchemaType.TABLE && node.isRetrieved();
        if (columnIndex == 2 && isRetrievedTable) {
            return String.valueOf(node.getColNum());
        }
        if (columnIndex == 3 && isRetrievedTable) {
            return node.getStatus();
        }
        return INoSQLSchemaNode.EMPTY_STR;
    }

    @Override
    public Object getParent(Object element) {
        INoSQLSchemaNode node = (INoSQLSchemaNode) element;
        return node.getParent();
    }

    @Override
    public boolean hasChildren(Object element) {
        INoSQLSchemaNode node = (INoSQLSchemaNode) element;
        return node.hasChildren();
    }

    @Override
    public Font getFont(Object element, int columnIndex) {
        INoSQLSchemaNode node = (INoSQLSchemaNode) element;
        if (columnIndex == 3 && node.isHasProblem()) {
            return registry.getBold(Display.getCurrent().getSystemFont().getFontData()[0].getName());
        }
        return null;
    }

    @Override
    public Color getBackground(Object element, int columnIndex) {
        INoSQLSchemaNode node = (INoSQLSchemaNode) element;
        if (node.isHasProblem()) {
            return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
        }
        return null;
    }

    @Override
    public Color getForeground(Object element, int columnIndex) {
        return null;
    }

}
