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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdTable;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogNode implements IHCatalogNode {

    private String value;

    private TdTable table;

    private IHCatalogNode parent;

    private List<IHCatalogNode> children = new ArrayList<IHCatalogNode>();

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public TdTable getTable() {
        return this.table;
    }

    @Override
    public void setTable(TdTable table) {
        this.table = table;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public IHCatalogNode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(IHCatalogNode parent) {
        this.parent = parent;
    }

    @Override
    public List<IHCatalogNode> getChildren() {
        return this.children;
    }

    @Override
    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    @Override
    public void addChild(IHCatalogNode child) {
        this.children.add(child);
    }

    @Override
    public void addChildren(List<IHCatalogNode> children) {
        this.children.addAll(children);
    }

    protected TdTable createTable(String tableName) {
        TdTable table = RelationalFactory.eINSTANCE.createTdTable();
        table.setName(tableName);
        table.setLabel(tableName);

        return table;
    }

}
