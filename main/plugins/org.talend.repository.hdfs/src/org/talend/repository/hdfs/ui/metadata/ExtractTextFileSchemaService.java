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
package org.talend.repository.hdfs.ui.metadata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.jface.preference.IPreferenceStore;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.data.text.IndiceHelper;
import org.talend.commons.utils.encoding.CharsetToolkit;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.MetadataToolHelper;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.types.JavaDataTypeHelper;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.ui.preference.metadata.MetadataTypeLengthConstants;
import org.talend.core.ui.services.IDesignerCoreUIService;
import org.talend.core.utils.CsvArray;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
import org.talend.designer.hdfsbrowse.model.EHDFSFileTypes;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.metadata.managment.ui.preview.ProcessDescription;
import org.talend.metadata.managment.ui.preview.ShadowProcessPreview;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ShadowProcessHelper;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.service.IExtractSchemaService;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.hdfs.util.HDFSModelUtil;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * created by ycbai on 2014-5-29 Detailled comment
 *
 */
public class ExtractTextFileSchemaService implements IExtractSchemaService<HDFSConnection> {

    private final static String DEFAULT_SHADOW_TYPE = "FILE_DELIMITED"; //$NON-NLS-1$

    private final static String DEFAULT_FILE_SERVER = "Localhost 127.0.0.1"; //$NON-NLS-1$

    private ClassLoader classLoader;

    public ExtractTextFileSchemaService(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public List<MetadataColumn> extractColumns(HDFSConnection connection, IHDFSNode node) throws Exception {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        if (connection == null || node == null || node.getType() != EHadoopFileTypes.FILE) {
            return columns;
        }

        HDFSFile file = (HDFSFile) node;
        file.setFileType(EHDFSFileTypes.TEXT);
        InputStream inputStream = HadoopOperationManager.getInstance().getFileContent(
                HDFSModelUtil.convert2HDFSConnectionBean(connection), classLoader, file.getPath());

        return extractColumns(connection, inputStream, file.getTable().getName());
    }

    @Override
    public List<MetadataColumn> extractColumns(HDFSConnection connection, MetadataTable metadataTable)
            throws HadoopServerException, CoreException, IOException {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        if (connection == null || metadataTable == null) {
            return columns;
        }
        EMap<String, String> additionalProperties = metadataTable.getAdditionalProperties();
        String hdfsPath = additionalProperties.get(HDFSConstants.HDFS_PATH);
        if (StringUtils.isEmpty(hdfsPath)) {
            return columns;
        }
        InputStream inputStream = HadoopOperationManager.getInstance().getFileContent(
                HDFSModelUtil.convert2HDFSConnectionBean(connection), classLoader, hdfsPath);

        return extractColumns(connection, inputStream, metadataTable.getLabel());
    }

    private List<MetadataColumn> extractColumns(HDFSConnection connection, InputStream inputStream, String tmpFileName)
            throws CoreException, IOException {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        if (connection == null || inputStream == null || tmpFileName == null) {
            return columns;
        }
        File tmpFile = createTmpFile(inputStream, tmpFileName);
        ProcessDescription processDescription = getProcessDescription(connection, tmpFile);
        CsvArray csvArray = ShadowProcessHelper.getCsvArray(processDescription, DEFAULT_SHADOW_TYPE, true);
        return guessSchemaFromArray(csvArray, connection.isFirstLineCaption(), processDescription.getHeaderRow());
    }

    private ProcessDescription getProcessDescription(HDFSConnection connection, File tmpFile) throws IOException {
        ProcessDescription processDescription = new ProcessDescription();
        Charset guessedCharset = CharsetToolkit.guessEncoding(tmpFile, 4096);
        processDescription.setEncoding(TalendQuoteUtils.addQuotesIfNotExist(guessedCharset.displayName()));
        processDescription.setFieldSeparator(TalendQuoteUtils.addQuotesIfNotExist(ContextParameterUtils.getOriginalValue(
                ConnectionContextHelper.getContextTypeForContextMode(connection), connection.getFieldSeparator())));
        processDescription.setRowSeparator(TalendQuoteUtils.addQuotesIfNotExist(ContextParameterUtils.getOriginalValue(
                ConnectionContextHelper.getContextTypeForContextMode(connection), connection.getRowSeparator())));
        processDescription.setFilepath(TalendQuoteUtils.addQuotesIfNotExist(formatFilePath(tmpFile.getAbsolutePath())));
        processDescription.setFooterRow(0);
        int i = 0;
        if (connection.isUseHeader()) {
            String header = ContextParameterUtils.getOriginalValue(
                    ConnectionContextHelper.getContextTypeForContextMode(connection), connection.getHeaderValue());
            i = ConnectionContextHelper.convertValue(header);
            if (i != -1) {
                i--;
            }
        }
        processDescription.setHeaderRow(i);
        processDescription.setCSVOption(false);
        processDescription.setLimitRows(DEFAULT_READ_LINE_NUM);
        processDescription.setPattern(TalendQuoteUtils.addQuotesIfNotExist(ContextParameterUtils.getOriginalValue(
                ConnectionContextHelper.getContextTypeForContextMode(connection), connection.getFieldSeparator())));
        processDescription.setRemoveEmptyRow(false);
        processDescription.setServer(TalendQuoteUtils.addQuotesIfNotExist(DEFAULT_FILE_SERVER));
        processDescription.setSplitRecord(false);

        return processDescription;
    }

    private File createTmpFile(InputStream inputStream, String fileName) {
        return createTmpFile(inputStream, fileName, DEFAULT_READ_LINE_NUM);
    }

    /**
     * DOC ycbai Comment method "createTmpFile".
     *
     * Create a temporary file which contents are readed from the inputStream.
     *
     * @param inputStream the inputStream to read
     * @param fileName the name of temporary file
     * @param maxLineNum the max quantity lines to read. If is "-1" means not limit the quantity.
     * @return
     */
    private File createTmpFile(InputStream inputStream, String fileName, int maxLineNum) {
        Project project = ProjectManager.getInstance().getCurrentProject();
        IProject fsProject = null;
        try {
            fsProject = ResourceUtils.getProject(project);
        } catch (PersistenceException e2) {
            ExceptionHandler.process(e2);
        }
        if (fsProject == null) {
            return null;
        }
        File tmpParentFile = new File(fsProject.getLocationURI().getPath() + File.separator + "temp" + File.separator + "hdfs"); //$NON-NLS-1$ //$NON-NLS-2$
        File tmpfile = new File(tmpParentFile, fileName);
        BufferedReader reader = null;
        Writer writer = null;
        try {
            if (!tmpParentFile.exists()) {
                tmpParentFile.mkdirs();
            }
            if (tmpfile.exists()) {
                tmpfile.delete();
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpfile)));
            int totalLines = 0;
            String strLine = null;
            do {
                totalLines++;
                strLine = reader.readLine();
                if (strLine != null) {
                    writer.append(strLine);
                    writer.append("\r\n"); //$NON-NLS-1$
                }
            } while (strLine != null && (maxLineNum == -1 || totalLines < maxLineNum));
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            try {
                inputStream.close();
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return tmpfile;
    }

    private String formatFilePath(String path) {
        if (path == null) {
            return ""; //$NON-NLS-1$
        }
        return path.replace("\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public List<MetadataColumn> guessSchemaFromArray(final CsvArray csvArray, boolean isFirstLineCaption, int headerValue) {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        List<String> exisColumnNames = new ArrayList<String>();
        if (csvArray == null) {
            return columns;
        } else {
            List<String[]> csvRows = csvArray.getRows();
            if (csvRows.isEmpty()) {
                return columns;
            }

            String[] fields = csvRows.get(0);
            int numberOfCol = getNumbersOfColumns(csvRows);

            // define the label to the metadata width the content of the first row
            int firstRowToExtractMetadata = headerValue;

            // the first rows is used to define the label of any metadata
            String[] label = new String[numberOfCol];
            for (int i = 0; i < numberOfCol; i++) {
                label[i] = DEFAULT_COLUMN_LABEL + i;
                if (isFirstLineCaption) {
                    if (numberOfCol <= fields.length) {// if current field size
                        // is greater than or
                        // equals bigest column
                        // size
                        if (fields[i] != null && !("").equals(fields[i])) { //$NON-NLS-1$
                            label[i] = fields[i].trim().replaceAll(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
                            label[i] = MetadataToolHelper.validateColumnName(label[i], i);
                        } else {
                            label[i] = DEFAULT_COLUMN_LABEL + i;
                        }
                    } else {// current field size is less than bigest column
                        // size
                        if (i < fields.length) {
                            if (fields[i] != null && !("").equals(fields[i])) { //$NON-NLS-1$
                                label[i] = fields[i].trim().replaceAll(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
                            } else {
                                label[i] = DEFAULT_COLUMN_LABEL + " " + i; //$NON-NLS-1$
                            }
                        } else {
                            label[i] = DEFAULT_COLUMN_LABEL + " " + i; //$NON-NLS-1$
                        }
                    }
                }
            }
            // fix bug 5694: column names check in FileDelimited wizard fails to
            // rename duplicate column name
            ShadowProcessPreview.fixDuplicateNames(label);

            for (int i = 0; i < numberOfCol; i++) {
                // define the first currentType and assimile it to globalType
                String globalType = null;
                int lengthValue = 0;
                int precisionValue = 0;

                int current = firstRowToExtractMetadata;
                while (globalType == null) {
                    // see the feature 6296,qli comment
                    if (current == csvRows.size()) {
                        globalType = "id_String";//$NON-NLS-1$
                        continue;
                    } else if (i >= csvRows.get(current).length) {
                        globalType = "id_String"; //$NON-NLS-1$
                    } else {
                        globalType = JavaDataTypeHelper.getTalendTypeOfValue(csvRows.get(current)[i]);
                        current++;
                    }
                }

                // for another lines
                for (int f = firstRowToExtractMetadata; f < csvRows.size(); f++) {
                    fields = csvRows.get(f);
                    if (fields.length > i) {
                        String value = fields[i];
                        if (!value.equals("")) { //$NON-NLS-1$
                            if (!JavaDataTypeHelper.getTalendTypeOfValue(value).equals(globalType)) {
                                globalType = JavaDataTypeHelper.getCommonType(globalType,
                                        JavaDataTypeHelper.getTalendTypeOfValue(value));
                            }
                            if (lengthValue < value.length()) {
                                lengthValue = value.length();
                            }
                            int positionDecimal = 0;
                            if (value.indexOf(',') > -1) {
                                positionDecimal = value.lastIndexOf(',');
                                precisionValue = lengthValue - positionDecimal;
                            } else if (value.indexOf('.') > -1) {
                                positionDecimal = value.lastIndexOf('.');
                                precisionValue = lengthValue - positionDecimal;
                            }
                        } else {
                            IPreferenceStore preferenceStore = null;
                            if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreUIService.class)) {
                                IDesignerCoreUIService designerCoreUiService = (IDesignerCoreUIService) GlobalServiceRegister
                                        .getDefault().getService(IDesignerCoreUIService.class);
                                preferenceStore = designerCoreUiService.getPreferenceStore();
                            }
                            if (preferenceStore != null
                                    && preferenceStore.getString(MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE) != null
                                    && !preferenceStore.getString(MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE).equals("")) { //$NON-NLS-1$
                                globalType = preferenceStore.getString(MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE);
                                if (preferenceStore.getString(MetadataTypeLengthConstants.VALUE_DEFAULT_LENGTH) != null
                                        && !preferenceStore.getString(MetadataTypeLengthConstants.VALUE_DEFAULT_LENGTH)
                                                .equals("")) { //$NON-NLS-1$
                                    lengthValue = Integer.parseInt(preferenceStore
                                            .getString(MetadataTypeLengthConstants.VALUE_DEFAULT_LENGTH));
                                }
                            }
                        }
                    }
                }
                // see the feature 6296,qli comment
                if (csvRows.size() <= 1 && firstRowToExtractMetadata == 1) {
                    lengthValue = 255;
                }

                // define the metadataColumn to field i
                MetadataColumn metadataColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
                metadataColumn.setPattern("\"dd-MM-yyyy\""); //$NON-NLS-1$
                // Convert javaType to TalendType
                String talendType = globalType;
                if (globalType.equals(JavaTypesManager.FLOAT.getId()) || globalType.equals(JavaTypesManager.DOUBLE.getId())) {
                    metadataColumn.setPrecision(precisionValue);
                } else {
                    metadataColumn.setPrecision(0);
                }
                metadataColumn.setTalendType(talendType);
                metadataColumn.setLength(lengthValue);
                String columnLabel = IndiceHelper.getIndexedLabel(label[i], exisColumnNames);
                metadataColumn.setLabel(columnLabel);
                if (!exisColumnNames.contains(columnLabel)) {
                    exisColumnNames.add(columnLabel);
                }
                columns.add(i, metadataColumn);
            }
        }
        return columns;
    }

    private int getNumbersOfColumns(List<String[]> csvRows) {
        int numbersOfColumns = 0;
        int parserLine = csvRows.size();
        if (parserLine > 50) {
            parserLine = 50;
        }
        for (int i = 0; i < parserLine; i++) {
            if (csvRows.get(i) != null) {
                String[] nbRow = csvRows.get(i);
                if (nbRow.length >= numbersOfColumns) {
                    numbersOfColumns = nbRow.length;
                }
            }
        }
        return numbersOfColumns;
    }

}
