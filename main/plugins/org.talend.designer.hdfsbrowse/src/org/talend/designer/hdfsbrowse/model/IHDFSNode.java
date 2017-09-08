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
package org.talend.designer.hdfsbrowse.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import orgomg.cwm.resource.relational.Table;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public interface IHDFSNode {

    public final static List<IHDFSNode> EMPTY_CONTENT = new ArrayList<IHDFSNode>();

    public void setParent(IHDFSNode parent);

    public IHDFSNode getParent();

    public boolean hasChildren();

    public List<IHDFSNode> getChildren();

    public void addChild(IHDFSNode child);

    public void addChildren(List<IHDFSNode> children);

    public String getValue();

    public void setValue(String value);

    public EHadoopFileTypes getType();

    public void setType(EHadoopFileTypes type);

    public Table getTable();

    public void setTable(Table table);

    public String getPath();

    public void setPath(String path);

    public Image getImage();

    public void forceFetchChildren();

    public void refresh();

}
