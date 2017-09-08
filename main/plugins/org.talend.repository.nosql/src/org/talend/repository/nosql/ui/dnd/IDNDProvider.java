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

import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.repository.model.nosql.NoSQLConnection;

/**
 * created by ycbai on 2014-6-18 Detailled comment
 * 
 */
public interface IDNDProvider {

    public Object getRepositoryValue(NoSQLConnection connection, String value, IMetadataTable table, String targetComponent);

    public void setRepositoryValue(NoSQLConnection connection, INode node, IElementParameter param);

    public void handleTableRelevantParameters(NoSQLConnection connection, IElement ele, IMetadataTable metadataTable);

}
