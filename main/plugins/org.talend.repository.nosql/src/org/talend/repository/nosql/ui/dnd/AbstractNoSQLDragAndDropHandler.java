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
package org.talend.repository.nosql.ui.dnd;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.utils.AbstractDragAndDropServiceHandler;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.constants.INoSQLConstants;
import org.talend.repository.nosql.factory.NoSQLRepositoryTranslator;
import org.talend.repository.nosql.ui.node.NoSQLRepositoryNodeType;

/**
 * created by ycbai on Jul 1, 2014 Detailled comment
 * 
 */
public abstract class AbstractNoSQLDragAndDropHandler extends AbstractDragAndDropServiceHandler {

    protected static final String UNDEFINE = "UNDEFINE"; //$NON-NLS-1$

    protected static final String COMPONENT_PREFIX = "t"; //$NON-NLS-1$

    protected static final String INPUT = "Input"; //$NON-NLS-1$

    protected static final String OUTPUT = "Output"; //$NON-NLS-1$

    protected static final String MAP = "MAP"; //$NON-NLS-1$

    @Override
    public ERepositoryObjectType getType(String repositoryType) {
        if (repositoryType != null && repositoryType.startsWith(INoSQLConstants.NOSQL_TYPE_PREFIX)) {
            return NoSQLRepositoryNodeType.METADATA_NOSQL_CONNECTIONS;
        }

        return null;
    }

    protected String getRepType(NoSQLConnection connection) {
        if (connection == null || connection.getDbType() == null) {
            return UNDEFINE;
        }

        return connection.getDbType();
    }

    protected String getInputComponentName(NoSQLConnection connection) {
        return getInputOrOutputComponentName(connection, true);
    }

    protected String getOutputComponentName(NoSQLConnection connection) {
        return getInputOrOutputComponentName(connection, false);
    }

    private String getInputOrOutputComponentName(NoSQLConnection connection, boolean isInput) {
        if (connection == null || connection.getDbType() == null) {
            return UNDEFINE;
        }
        String dbTypeLabel = NoSQLRepositoryTranslator.getInstance().getLabel(connection.getDbType());
        if (isInput) {
            return COMPONENT_PREFIX.concat(dbTypeLabel).concat(INPUT);
        }

        return COMPONENT_PREFIX.concat(dbTypeLabel).concat(OUTPUT);
    }

}
