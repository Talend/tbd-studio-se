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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import orgomg.cwm.resource.relational.Table;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSNode implements IHDFSNode {

    protected static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    protected ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();

    private String value;

    private Table table;

    private EHadoopFileTypes type;

    private EHDFSFileTypes fileType;

    private IHDFSNode parent;

    private String path = PATH_SEPARATOR;

    private List<IHDFSNode> children = new ArrayList<IHDFSNode>();

    private Map<String, String> parameters = new HashMap<String, String>();

    protected boolean hasFetchedChildren = false;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public EHadoopFileTypes getType() {
        return this.type;
    }

    @Override
    public void setType(EHadoopFileTypes type) {
        this.type = type;
    }

    /**
     * Getter for fileType.
     * 
     * @return the fileType
     */
    public EHDFSFileTypes getFileType() {
        return this.fileType;
    }

    /**
     * Sets the fileType.
     * 
     * @param fileType the fileType to set
     */
    public void setFileType(EHDFSFileTypes fileType) {
        this.fileType = fileType;
    }

    @Override
    public Table getTable() {
        return this.table;
    }

    @Override
    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public IHDFSNode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(IHDFSNode parent) {
        this.parent = parent;
    }

    @Override
    public List<IHDFSNode> getChildren() {
        return this.children;
    }

    @Override
    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    @Override
    public void addChild(IHDFSNode child) {
        this.children.add(child);
    }

    @Override
    public void addChildren(List<IHDFSNode> children) {
        this.children.addAll(children);
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void forceFetchChildren() {
        this.hasFetchedChildren = false;
    }

    /**
     * Getter for parameters.
     * 
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Override
    public void refresh() {
    }
}
