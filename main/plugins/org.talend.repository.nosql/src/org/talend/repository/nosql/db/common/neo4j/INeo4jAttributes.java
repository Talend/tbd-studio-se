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
package org.talend.repository.nosql.db.common.neo4j;

import org.talend.repository.nosql.constants.INoSQLCommonAttributes;

/**
 *
 * created by ycbai on Jul 22, 2014 Detailled comment
 *
 */
public interface INeo4jAttributes extends INoSQLCommonAttributes {

    public final static String REMOTE_SERVER = "REMOTE_SERVER"; //$NON-NLS-1$

    public final static String DATABASE_PATH = "DATABASE_PATH"; //$NON-NLS-1$

    public final static String SERVER_URL = "SERVER_URL"; //$NON-NLS-1$

    public final static String SET_USERNAME = "SET_USERNAME"; //$NON-NLS-1$

    public final static String COLUMN_MAPPING = "COLUMN_MAPPING"; //$NON-NLS-1$

}
