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
package org.talend.repository.hcatalog.model;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.talend.cwm.relational.TdTable;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public interface IHCatalogNode {

    public String getValue();

    public void setValue(String value);

    public TdTable getTable();

    public void setTable(TdTable table);

    public Image getImage();

    public IHCatalogNode getParent();

    public void setParent(IHCatalogNode parent);

    public List<IHCatalogNode> getChildren();

    public boolean hasChildren();

    public void addChild(IHCatalogNode child);

    public void addChildren(List<IHCatalogNode> children);

}
