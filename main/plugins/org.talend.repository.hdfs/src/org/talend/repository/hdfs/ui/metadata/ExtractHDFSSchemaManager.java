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
package org.talend.repository.hdfs.ui.metadata;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EMap;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.manager.HadoopServerUtil;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.repository.hadoopcluster.service.IExtractSchemaService;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.hdfs.util.HDFSModelUtil;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * created by ycbai on 2014-5-29 Detailled comment
 * 
 */
public class ExtractHDFSSchemaManager {

    private static ExtractHDFSSchemaManager instance;

    private ExtractHDFSSchemaManager() {
    }

    public static synchronized final ExtractHDFSSchemaManager getInstance() {
        if (instance == null) {
            instance = new ExtractHDFSSchemaManager();
        }

        return instance;
    }

    public List<MetadataColumn> extractColumns(HDFSConnection connection, ClassLoader classLoader, IHDFSNode node)
            throws Exception {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        if (connection == null || node == null || node.getType() != EHadoopFileTypes.FILE) {
            return columns;
        }

        HDFSFile file = (HDFSFile) node;
        HDFSConnectionBean connectionBean = HDFSModelUtil.convert2HDFSConnectionBean(connection);
        Object filePath = getHDFSFilePath(connectionBean, classLoader, file.getPath());

        IExtractSchemaService<HDFSConnection> service = ExtractHDFSMetaServiceFactory.getService(connectionBean, classLoader,
                filePath);
        return service.extractColumns(connection, node);
    }

    public List<MetadataColumn> extractColumns(HDFSConnection connection, ClassLoader classLoader, MetadataTable metadataTable)
            throws Exception {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        if (connection == null || metadataTable == null) {
            return columns;
        }
        EMap<String, String> additionalProperties = metadataTable.getAdditionalProperties();
        String hdfsPath = additionalProperties.get(HDFSConstants.HDFS_PATH);
        if (StringUtils.isEmpty(hdfsPath)) {
            return columns;
        }

        HDFSConnectionBean connectionBean = HDFSModelUtil.convert2HDFSConnectionBean(connection);
        Object filePath = getHDFSFilePath(connectionBean, classLoader, hdfsPath);

        IExtractSchemaService<HDFSConnection> service = ExtractHDFSMetaServiceFactory.getService(connectionBean, classLoader,
                filePath);
        return service.extractColumns(connection, metadataTable);
    }

    private Object getHDFSFilePath(HDFSConnectionBean connection, ClassLoader classLoader, String path)
            throws HadoopServerException {
        Object pathObj = null;
        try {
            Object fileSystem = HadoopServerUtil.getDFS(connection, classLoader);
            if (fileSystem == null) {
                return null;
            }
            pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { path }); //$NON-NLS-1$
        } catch (Exception e) {
            throw new HadoopServerException(e);
        }

        return pathObj;
    }

}
