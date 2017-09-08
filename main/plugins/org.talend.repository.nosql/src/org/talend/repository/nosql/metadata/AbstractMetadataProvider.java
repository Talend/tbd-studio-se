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
package org.talend.repository.nosql.metadata;

import java.util.List;

import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.exceptions.NoSQLExtractSchemaException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.model.INoSQLSchemaNode;

/**
 * created by ycbai on 2014-6-18 Detailled comment
 * 
 */
public abstract class AbstractMetadataProvider implements IMetadataProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.metadata.IMetadataProvider#checkConnection(org.talend.repository.model.nosql.
     * NoSQLConnection)
     */
    @Override
    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.metadata.IMetadataProvider#extractColumns(org.talend.repository.model.nosql.
     * NoSQLConnection, org.talend.repository.nosql.model.INoSQLSchemaNode)
     */
    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, INoSQLSchemaNode node)
            throws NoSQLExtractSchemaException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.metadata.IMetadataProvider#extractColumns(org.talend.repository.model.nosql.
     * NoSQLConnection, org.talend.core.model.metadata.builder.connection.MetadataTable,
     * org.talend.repository.nosql.model.INoSQLSchemaNode)
     */
    @Override
    public List<MetadataColumn> extractColumns(NoSQLConnection connection, String tableName) throws NoSQLExtractSchemaException {
        return null;
    }
}
