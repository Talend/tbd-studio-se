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
package org.talend.repository.nosql.model;

import java.util.List;
import java.util.Map;

public interface INoSQLSchemaNode {

    public final static String EMPTY_STR = ""; //$NON-NLS-1$

    public String getName();

    public void setName(String name);

    public ENoSQLSchemaType getSchemaType();

    public void setSchemaType(ENoSQLSchemaType schemaType);

    public String getNodeType();

    public void setNodeType(String nodeType);

    public int getColNum();

    public void setColNum(int colNum);

    public String getStatus();

    public void setStatus(String status);

    public boolean isHasProblem();

    public void setHasProblem(boolean hasProblem);

    public boolean isRetrieved();

    public void setRetrieved(boolean retrieved);

    public List<INoSQLSchemaNode> getChildren();

    public INoSQLSchemaNode getParent();

    public void setParent(INoSQLSchemaNode parent);

    public boolean hasChildren();

    public boolean hasParent();

    public void addChild(INoSQLSchemaNode child);

    public void addChildren(List<INoSQLSchemaNode> children);

    public Map<String, String> getParameters();

}
