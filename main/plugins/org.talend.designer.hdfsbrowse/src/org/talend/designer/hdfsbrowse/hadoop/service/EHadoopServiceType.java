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
package org.talend.designer.hdfsbrowse.hadoop.service;

/**
 * created by ycbai on Aug 6, 2014 Detailled comment
 *
 */
public enum EHadoopServiceType {

    NAMENODE,

    JOBTRACKER,

    RESOURCE_MANAGER,

    GOOGLE_DATAPROC,
    ;

    public String getName() {
        return this.name();
    }
}
