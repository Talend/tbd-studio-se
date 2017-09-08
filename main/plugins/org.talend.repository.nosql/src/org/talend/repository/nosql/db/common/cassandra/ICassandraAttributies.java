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
package org.talend.repository.nosql.db.common.cassandra;

import org.talend.repository.nosql.constants.INoSQLCommonAttributes;

public interface ICassandraAttributies extends INoSQLCommonAttributes {

    public static final String COLUMN_FAMILY = "COLUMN_FAMILY"; //$NON-NLS-1$

    public static final String COLUMN_FAMILY_TYPE = "COLUMN_FAMILY_TYPE"; //$NON-NLS-1$

    public static final String COLUMN_FAMILY_TYPE_SUPER = "SUPER"; //$NON-NLS-1$

    public static final String COLUMN_FAMILY_TYPE_STANDARD = "STANDARD"; //$NON-NLS-1$

    public final static String KEY_SPACE = "KEY_SPACE"; //$NON-NLS-1$

}
