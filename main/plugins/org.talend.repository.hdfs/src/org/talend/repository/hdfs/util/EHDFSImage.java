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
package org.talend.repository.hdfs.util;

import org.talend.commons.ui.runtime.image.IImage;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHDFSImage implements IImage {

    HDFS_RESOURCE_ICON("/icons/HDFS_icon16.png"), //$NON-NLS-1$
    HDFS_WIZ("/icons/hadoop-logo-wiz.png"); //$NON-NLS-1$

    private String path;

    EHDFSImage() {
        this.path = "/icons/unknown.gif"; //$NON-NLS-1$
    }

    EHDFSImage(String path) {
        this.path = path;
    }

    /**
     * Getter for path.
     * 
     * @return the path
     */
    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * Getter for clazz.
     * 
     * @return the clazz
     */
    @Override
    public Class getLocation() {
        return EHDFSImage.class;
    }
}
