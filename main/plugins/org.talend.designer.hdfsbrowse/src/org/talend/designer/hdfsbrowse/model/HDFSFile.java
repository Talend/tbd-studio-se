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

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;

/**
 * DOC ycbai class global comment. Detailled comment
 * 
 * File handling methods for the DFS
 * 
 */
public class HDFSFile extends HDFSPath {

    public HDFSFile(HDFSPath parent) {
        super(parent);
    }

    /**
     * Download and view contents of a file
     * 
     * @return a InputStream for the file
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public InputStream open() throws HadoopServerException {
        return getOperationManager().getFileContent(connection, classLoader, getPath());
    }

    @Override
    public String toString() {
        return getPath().toString();
    }

    @Override
    public List<IHDFSNode> getChildren() {
        return null;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public void refresh() {
        getParent().refresh();
    }

    @Override
    public Image getImage() {
        return sharedImages.getImage(ISharedImages.IMG_OBJ_FILE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hdfs.model.HDFSNode#getType()
     */
    @Override
    public EHadoopFileTypes getType() {
        return EHadoopFileTypes.FILE;
    }

    public String getSize() {
        final String[] units = { "b", "Kb", "Mb", "Gb", "Tb" };
        int unit = 0;
        double l;
        try {
            l = getOperationManager().getFileSize(fileSystem, classLoader, getPath());
        } catch (Exception e) {
            return "fail to get file size: " + e.getMessage();
        }
        while ((l >= 1024.0) && (unit < units.length)) {
            unit += 1;
            l /= 1024.0;
        }

        return String.format("%.1f %s", l, units[unit]); //$NON-NLS-1$
    }

}
