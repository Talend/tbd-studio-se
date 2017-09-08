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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoSQLSchemaNode implements INoSQLSchemaNode {

    private String name;

    private ENoSQLSchemaType schemaType;

    private String nodeType;

    private int colNum;

    private String status;

    private boolean hasProblem;

    private boolean retrieved;

    private INoSQLSchemaNode parent;

    private final List<INoSQLSchemaNode> children = new ArrayList<INoSQLSchemaNode>();

    private final Map<String, String> parameters = new HashMap<String, String>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ENoSQLSchemaType getSchemaType() {
        return this.schemaType;
    }

    @Override
    public void setSchemaType(ENoSQLSchemaType schemaType) {
        this.schemaType = schemaType;
    }

    @Override
    public String getNodeType() {
        return this.nodeType;
    }

    @Override
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public int getColNum() {
        return this.colNum;
    }

    @Override
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean isHasProblem() {
        return this.hasProblem;
    }

    @Override
    public void setHasProblem(boolean hasProblem) {
        this.hasProblem = hasProblem;
    }

    @Override
    public boolean isRetrieved() {
        return this.retrieved;
    }

    @Override
    public void setRetrieved(boolean retrieved) {
        this.retrieved = retrieved;
    }

    @Override
    public List<INoSQLSchemaNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(INoSQLSchemaNode child) {
        this.children.add(child);
    }

    @Override
    public void addChildren(List<INoSQLSchemaNode> childrenNodes) {
        this.children.addAll(childrenNodes);
    }

    @Override
    public INoSQLSchemaNode getParent() {
        return parent;
    }

    @Override
    public void setParent(INoSQLSchemaNode parent) {
        this.parent = parent;
    }

    @Override
    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

}
