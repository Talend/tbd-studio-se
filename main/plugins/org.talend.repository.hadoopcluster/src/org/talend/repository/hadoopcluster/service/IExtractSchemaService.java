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
package org.talend.repository.hadoopcluster.service;

import java.util.List;

import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.designer.hdfsbrowse.util.EHDFSFieldSeparator;
import org.talend.designer.hdfsbrowse.util.EHDFSRowSeparator;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;

/**
 * created by ycbai on 2014-5-29 Detailled comment
 * 
 */
public interface IExtractSchemaService<T extends HadoopSubConnection> {

    public final static String DEFAULT_FIELD_SEPARATOR = EHDFSFieldSeparator.getDefaultSeparator().getValue();

    public final static String DEFAULT_ROW_SEPARATOR = EHDFSRowSeparator.getDefaultSeparator().getValue();

    public final static int DEFAULT_READ_LINE_NUM = 50;

    public final static String DEFAULT_COLUMN_LABEL = "Column"; //$NON-NLS-1$

    public List<MetadataColumn> extractColumns(T connection, IHDFSNode node) throws Exception;

    public List<MetadataColumn> extractColumns(T connection, MetadataTable metadataTable) throws Exception;

}
