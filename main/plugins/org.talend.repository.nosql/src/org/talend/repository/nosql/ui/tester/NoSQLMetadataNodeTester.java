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
package org.talend.repository.nosql.ui.tester;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;
import org.talend.repository.view.di.metadata.tester.CoMetadataNodeTester;

/**
 * 
 * created by ycbai on 2014-6-18 Detailled comment
 * 
 */
public class NoSQLMetadataNodeTester extends CoMetadataNodeTester {

    private static final String IS_NOSQL_CONNECTION = "isNoSQLConnection"; //$NON-NLS-1$

    @Override
    protected ERepositoryObjectType findType(String property) {
        if (property != null) {
            if (IS_NOSQL_CONNECTION.equals(property)) {
                return NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
            }
        }

        return null;
    }

}
