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
package org.talend.repository.nosql.ui.node;

import org.talend.commons.ui.runtime.image.IImage;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public enum ENoSQLImage implements IImage {

    NOSQL_RESOURCE_ICON("/icons/nosqlConnectionsNode.gif"), //$NON-NLS-1$
    NOSQL_WIZ_ICON("/icons/nosqlConnectionsWizard.png"), //$NON-NLS-1$

    ;

    private String path;

    ENoSQLImage() {
        this.path = "/icons/unknown.gif"; //$NON-NLS-1$
    }

    ENoSQLImage(String path) {
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
        return ENoSQLImage.class;
    }
}
