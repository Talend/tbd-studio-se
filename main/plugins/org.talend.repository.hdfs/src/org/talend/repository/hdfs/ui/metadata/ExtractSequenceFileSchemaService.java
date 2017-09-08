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
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.manager.HadoopServerUtil;
import org.talend.designer.hdfsbrowse.model.EHDFSFileTypes;
import org.talend.designer.hdfsbrowse.model.EHadoopDataTypeMapping;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.repository.hadoopcluster.service.IExtractSchemaService;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.hdfs.util.HDFSModelUtil;
import org.talend.repository.model.hdfs.HDFSConnection;
import orgomg.cwm.objectmodel.core.CoreFactory;
import orgomg.cwm.objectmodel.core.TaggedValue;

/**
 * created by ycbai on 2014-5-29 Detailled comment
 * 
 */
public class ExtractSequenceFileSchemaService implements IExtractSchemaService<HDFSConnection> {

    private final static String KEY_COLUMN_LABEL = "Key"; //$NON-NLS-1$

    private final static String VALUE_COLUMN_LABEL = "Value"; //$NON-NLS-1$

    private ClassLoader classLoader;

    public ExtractSequenceFileSchemaService(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.service.IExtractSchemaService#extractColumns(org.talend.repository.model.
     * hadoopcluster.HadoopSubConnection, org.talend.designer.hdfsbrowse.model.IHDFSNode)
     */
    @Override
    public List<MetadataColumn> extractColumns(HDFSConnection connection, IHDFSNode node) throws Exception {
        if (connection == null || node == null || node.getType() != EHadoopFileTypes.FILE) {
            return Collections.EMPTY_LIST;
        }
        HDFSFile file = (HDFSFile) node;
        file.setFileType(EHDFSFileTypes.SEQUENCE);

        return extractColumns(connection, file.getPath());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.service.IExtractSchemaService#extractColumns(org.talend.repository.model.
     * hadoopcluster.HadoopSubConnection, org.talend.core.model.metadata.builder.connection.MetadataTable)
     */
    @Override
    public List<MetadataColumn> extractColumns(HDFSConnection connection, MetadataTable metadataTable) throws Exception {
        if (connection == null || metadataTable == null) {
            return Collections.EMPTY_LIST;
        }
        EMap<String, String> additionalProperties = metadataTable.getAdditionalProperties();
        String hdfsPath = additionalProperties.get(HDFSConstants.HDFS_PATH);
        if (StringUtils.isEmpty(hdfsPath)) {
            return Collections.EMPTY_LIST;
        }

        return extractColumns(connection, hdfsPath);
    }

    @SuppressWarnings("rawtypes")
    private List<MetadataColumn> extractColumns(HDFSConnection connection, String filePath) throws Exception {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();

        HDFSConnectionBean connectionBean = HDFSModelUtil.convert2HDFSConnectionBean(connection);
        Object fs = HadoopServerUtil.getDFS(connectionBean, classLoader);
        Object conf = HadoopServerUtil.getConfiguration(connectionBean, classLoader);
        Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { filePath }); //$NON-NLS-1$
        Object reader = ReflectionUtils.newInstance("org.apache.hadoop.io.SequenceFile$Reader", classLoader, new Object[] { fs, //$NON-NLS-1$
                pathObj, conf }, Class.forName("org.apache.hadoop.fs.FileSystem", true, classLoader), //$NON-NLS-1$
                Class.forName("org.apache.hadoop.fs.Path", true, classLoader), //$NON-NLS-1$
                Class.forName("org.apache.hadoop.conf.Configuration", true, classLoader)); //$NON-NLS-1$

        Class keyClass = (Class) ReflectionUtils.invokeMethod(reader, "getKeyClass", new Object[0]); //$NON-NLS-1$
        MetadataColumn keyColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
        keyColumn.setTalendType(EHadoopDataTypeMapping.getTalendTypeByHadoopType(keyClass.getName()));
        keyColumn.setLabel(KEY_COLUMN_LABEL);
        EList<TaggedValue> keyTaggedValue = keyColumn.getTaggedValue();
        TaggedValue keyTV = CoreFactory.eINSTANCE.createTaggedValue();
        keyTV.setTag(HDFSConstants.IS_SEQUENCE_KEY);
        keyTV.setValue(Boolean.TRUE.toString());
        keyTaggedValue.add(keyTV);
        columns.add(keyColumn);

        Class valueClass = (Class) ReflectionUtils.invokeMethod(reader, "getValueClass", new Object[0]); //$NON-NLS-1$
        MetadataColumn valueColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
        valueColumn.setTalendType(EHadoopDataTypeMapping.getTalendTypeByHadoopType(valueClass.getName()));
        valueColumn.setLabel(VALUE_COLUMN_LABEL);
        EList<TaggedValue> valTaggedValue = valueColumn.getTaggedValue();
        TaggedValue valTV = CoreFactory.eINSTANCE.createTaggedValue();
        valTV.setTag(HDFSConstants.IS_SEQUENCE_VALUE);
        valTV.setValue(Boolean.TRUE.toString());
        valTaggedValue.add(valTV);
        columns.add(valueColumn);

        return columns;
    }

}
