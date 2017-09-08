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
package org.talend.repository.hadoopcluster.util;

import org.talend.commons.ui.runtime.image.IImage;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public enum EHadoopClusterImage implements IImage {

    HADOOPCLUSTER_RESOURCE_ICON("/icons/hadoop-logo-16x16.png"), //$NON-NLS-1$
    HADOOPCLUSTER_WIZ("/icons/hadoop-logo-wiz.png"), //$NON-NLS-1$
    HADOOP_LINK_DB_ICON("/icons/db_link.png"); //$NON-NLS-1$

    private String path;

    EHadoopClusterImage() {
        this.path = "/icons/unknown.gif"; //$NON-NLS-1$
    }

    EHadoopClusterImage(String path) {
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
        return EHadoopClusterImage.class;
    }
}
