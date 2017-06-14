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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.graphics.Image;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.repository.model.provider.AbstractMetadataExtractorViewProvider;
import org.talend.cwm.relational.TdTable;
import org.talend.repository.hcatalog.metadata.ExtractMetaDataFromHCatalog;
import org.talend.repository.hcatalog.model.HCatalogNode;
import org.talend.repository.hcatalog.model.IHCatalogNode;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class TableSelectorTreeViewerProvider extends AbstractMetadataExtractorViewProvider {

    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        List<IHCatalogNode> nodes = new ArrayList<IHCatalogNode>();
        if (parentElement instanceof HCatalogConnection) {
            try {
                List<TdTable> tables = ExtractMetaDataFromHCatalog.extractTables((HCatalogConnection) parentElement);
                for (TdTable table : tables) {
                    IHCatalogNode node = new HCatalogNode();
                    node.setValue(table.getLabel());
                    node.setTable(table);
                    nodes.add(node);
                }
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }

        return nodes.toArray();
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof HCatalogConnection) {
            return true;
        }
        return false;
    }

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        if (columnIndex == 0 && element instanceof IHCatalogNode) {
            return ((IHCatalogNode) element).getImage();
        }
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        IHCatalogNode content = (IHCatalogNode) element;
        switch (columnIndex) {
        case 0:
            return StringUtils.trimToEmpty(content.getValue());
        default:
            return "";
        }
    }

}
