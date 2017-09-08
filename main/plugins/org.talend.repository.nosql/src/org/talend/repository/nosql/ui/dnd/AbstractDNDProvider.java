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

import org.apache.commons.lang.StringUtils;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.repository.model.nosql.NoSQLConnection;

/**
 * created by ycbai on 2014-6-18 Detailled comment
 * 
 */
public abstract class AbstractDNDProvider implements IDNDProvider {

    @Override
    public void setRepositoryValue(NoSQLConnection connection, INode node, IElementParameter param) {
        // Do nothing by default.
    }

    @Override
    public void handleTableRelevantParameters(NoSQLConnection connection, IElement ele, IMetadataTable metadataTable) {
        // Do nothing by default.
    }

    protected static boolean isContextMode(Connection connection, String value) {
        if (connection == null || value == null) {
            return false;
        }

        if (connection.isContextMode() && ContextParameterUtils.isContainContextParam(value)) {
            return true;
        }

        return false;
    }

    protected Object getCanonicalRepositoryValue(NoSQLConnection connection, String repValue) {
        return getCanonicalRepositoryValue(connection, StringUtils.trimToNull(repValue), true);
    }

    protected Object getCanonicalRepositoryValue(NoSQLConnection connection, String repValue, boolean includeQuotes) {
        if (isContextMode(connection, repValue)) {
            return repValue;
        } else {
            if (includeQuotes) {
                return TalendQuoteUtils.addQuotesIfNotExist(repValue);
            } else {
                return TalendQuoteUtils.removeQuotes(repValue);
            }

        }
    }

}
