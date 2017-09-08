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
package org.talend.designer.hdfsbrowse.ui.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.talend.core.repository.model.provider.AbstractMetadataExtractorViewProvider;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.HDFSFolder;
import org.talend.designer.hdfsbrowse.model.HDFSPath;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class FileSelectorTreeViewerProvider extends AbstractMetadataExtractorViewProvider {

    private final static IHDFSNode[] EMPTY_CONTENT = new IHDFSNode[0];

    private final static String EMPTY_STRING = ""; //$NON-NLS-1$

    private final static String ROOT_PATH = "/"; //$NON-NLS-1$

    private static Logger log = Logger.getLogger(HDFSFolder.class);

    private TreeViewer viewer;

    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof HDFSConnectionBean) {
            try {
                HDFSPath root = new HDFSFolder(this, (HDFSConnectionBean) inputElement);
                root.setValue(ROOT_PATH);
                root.setPath(ROOT_PATH);
                return new Object[] { root };
            } catch (Exception e) {
                log.error(e);
            }
        }

        return EMPTY_CONTENT;
    }

    public Object[] getChildren(Object parentElement) {
        if (!(parentElement instanceof IHDFSNode))
            return null;
        IHDFSNode content = (IHDFSNode) parentElement;
        return content.getChildren().toArray(new IHDFSNode[0]);
    }

    public Object getParent(Object element) {
        if (element instanceof HDFSPath) {
            return ((HDFSPath) element).getParent();
        }

        return null;
    }

    public boolean hasChildren(Object element) {
        if (element instanceof IHDFSNode) {
            IHDFSNode content = (IHDFSNode) element;
            return content.hasChildren();
        }
        return false;
    }

    public Image getColumnImage(Object element, int columnIndex) {
        if (columnIndex == 0 && element instanceof IHDFSNode) {
            return ((IHDFSNode) element).getImage();
        }
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {
        IHDFSNode content = (IHDFSNode) element;
        EHadoopFileTypes type = content.getType();
        switch (columnIndex) {
        case 0:
            return StringUtils.trimToEmpty(content.getValue());
        case 1:
            return StringUtils.trimToEmpty(type.getValue());
        case 2:
            if (content instanceof HDFSFile) {
                HDFSFile file = (HDFSFile) content;
                return StringUtils.trimToEmpty(file.getSize());
            } else {
                return EMPTY_STRING;
            }
        default:
            return EMPTY_STRING;
        }
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (viewer instanceof TreeViewer) {
            this.viewer = (TreeViewer) viewer;
        }
    }

    public void refresh() {
        refresh(null);
    }

    public void refresh(final IHDFSNode node) {
        if (viewer != null) {
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    if (node == null) {
                        viewer.refresh();
                    } else {
                        viewer.refresh(node);
                        viewer.expandToLevel(node, 1);
                    }
                }
            });
        }
    }

    public Viewer getViewer() {
        return this.viewer;
    }

}
