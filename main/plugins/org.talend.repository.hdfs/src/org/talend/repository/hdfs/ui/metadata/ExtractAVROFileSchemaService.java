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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EMap;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.utils.ReflectionUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.manager.HadoopOperationManager;
import org.talend.designer.hdfsbrowse.model.EHDFSFileTypes;
import org.talend.designer.hdfsbrowse.model.EHadoopFileTypes;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;
import org.talend.repository.ProjectManager;
import org.talend.repository.hadoopcluster.service.IExtractSchemaService;
import org.talend.repository.hdfs.util.HDFSConstants;
import org.talend.repository.hdfs.util.HDFSModelUtil;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * created by ycbai on 2014-5-29 Detailled comment
 * 
 */
public class ExtractAVROFileSchemaService implements IExtractSchemaService<HDFSConnection> {

    private enum ReflectedAVROEnumType {
        BOOLEAN,
        BYTES,
        DOUBLE,
        FLOAT,
        INT,
        LONG,
        STRING,
        UNION,
        NULL,
        OTHERS
    }

    // an avro formatted file
    private final static String FILE_FORMAT_AVSC = "AVSC"; //$NON-NLS-1$

    private final static String FILE_FORMAT_AVRO = "AVRO"; //$NON-NLS-1$

    private ClassLoader classLoader;

    public ExtractAVROFileSchemaService(ClassLoader _clsLoader) {
        this.classLoader = _clsLoader;
    }

    /**
     * DOC cmeng Comment method "isAnAVROFormatedFile".
     * 
     * @param fileFullPathName I only want the file name to judge the file format type,<br>
     * means this function don't care whether it is a full path name, but must have the file name with it's file
     * extentions
     * @return
     */
    public static boolean isAnAVROFormattedFile(String fileFullPathName) {
        if (StringUtils.isEmpty(fileFullPathName)) {
            return false;
        }
        String fileNameUpperCase = fileFullPathName.toUpperCase();
        if (fileNameUpperCase.endsWith("." + FILE_FORMAT_AVRO) //$NON-NLS-1$
                || fileNameUpperCase.endsWith("." + FILE_FORMAT_AVSC)) { //$NON-NLS-1$
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MetadataColumn> extractColumns(HDFSConnection connection, IHDFSNode node) throws Exception {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        if (connection == null || node == null || node.getType() != EHadoopFileTypes.FILE) {
            return columns;
        }

        HDFSFile file = (HDFSFile) node;
        file.setFileType(EHDFSFileTypes.AVRO);
        InputStream inputStream = HadoopOperationManager.getInstance().getFileContent(
                HDFSModelUtil.convert2HDFSConnectionBean(connection), classLoader, file.getPath());

        return extractColumns(connection, inputStream, file.getTable().getName(), file.getValue());
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

        return extractColumns(connection, inputStream, metadataTable.getLabel(), hdfsPath);
    }

    private List<MetadataColumn> extractColumns(HDFSConnection connection, InputStream inputStream, String tmpFileName,
            String fullPathName) throws CoreException {
        List<MetadataColumn> columns = new ArrayList<MetadataColumn>();
        if (connection == null || inputStream == null || tmpFileName == null || fullPathName == null) {
            return columns;
        }
        File tmpFile = createTmpFile(inputStream, tmpFileName);
        try {
            ReflectedAVROSchema storedSchema = new ReflectedAVROSchema(fullPathName, tmpFile, classLoader);
            if (storedSchema != null) {
                columns = guessSchemaFromAVRO(storedSchema.getFields());
            }
        } catch (Exception e) {
            IStatus template = Status.CANCEL_STATUS;
            Status expStatus = new Status(template.getSeverity(), template.getPlugin(), e.getMessage(), e);
            throw new CoreException(expStatus);
        }
        return columns;
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
    private File createTmpFile(InputStream inputStream, String fileName) {
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
        OutputStream outputStream = null;
        try {
            if (!tmpParentFile.exists()) {
                tmpParentFile.mkdirs();
            }
            if (tmpfile.exists()) {
                tmpfile.delete();
            }
            outputStream = new FileOutputStream(tmpfile);
            // buffer size: 1M == 1024*1024 == 1048576
            byte[] buffer = new byte[1048576];
            int readSize = 0;
            while (0 <= (readSize = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, readSize);
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        } finally {
            try {
                inputStream.close();
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return tmpfile;
    }

    /**
     * For now, it only support primitive types
     * 
     * @param avroFields
     * @return
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws CoreException
     */
    private List<MetadataColumn> guessSchemaFromAVRO(List<ReflectedAVROField> avroFields) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException, CoreException {
        if (avroFields == null || avroFields.size() == 0) {
            return new ArrayList<MetadataColumn>();
        }
        List<MetadataColumn> metadataColumns = new ArrayList<MetadataColumn>(avroFields.size());
        Set<String> labelSet = new HashSet<String>();

        for (ReflectedAVROField field : avroFields) {
            MetadataColumn metadataColumn = ConnectionFactory.eINSTANCE.createMetadataColumn();
            metadataColumn.setPattern("\"dd-MM-yyyy\""); //$NON-NLS-1$
            ReflectedAVROSchema avroSchema = field.schema();
            metadataColumn.setTalendType(mapAvroTypeToTalendType(avroSchema));
            String label = field.name();
            if (labelSet.contains(label)) {
                IStatus template = Status.CANCEL_STATUS;
                Exception e = new Exception(
                        org.talend.repository.hdfs.i18n.Messages
                                .getString("HDFSSchemaForm.retrieveSchema.checkSchema.errorMessage.duplicatedColumns") + label); //$NON-NLS-1$
                Status expStatus = new Status(template.getSeverity(), template.getPlugin(), e.getMessage(), e);
                throw new CoreException(expStatus);
            } else {
                labelSet.add(label);
            }
            metadataColumn.setNullable(isNullable(avroSchema));
            metadataColumn.setLabel(field.name());
            ReflectedJsonNode defaultValue = field.defaultValue();
            if (defaultValue != null) {
                metadataColumn.setDefaultValue(mapAvroValueToTalendValue(defaultValue.asText(), avroSchema));
            }
            String comment = field.doc();
            if (comment != null) {
                metadataColumn.setComment(comment);
            }
            metadataColumns.add(metadataColumn);
        }
        return metadataColumns;
    }

    private String formatToTalendString(String avroString) {
        if (avroString == null) {
            return null;
        }
        String talendString = avroString.replaceAll("\\\\", "\\\\"); //$NON-NLS-1$ //$NON-NLS-2$
        talendString = TalendQuoteUtils.addQuotes(talendString);
        return talendString;
    }

    private String mapAvroValueToTalendValue(String avroValue, ReflectedAVROSchema avroSchema) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException {
        if (avroValue == null || avroSchema == null) {
            return null;
        }
        if ("null".equals(avroValue)) { //$NON-NLS-1$
            return "null"; //$NON-NLS-1$
        }
        ReflectedAVROEnumType avroType = avroSchema.getType();
        if (ReflectedAVROEnumType.UNION == avroType) {
            avroType = getMainTypeFromUnionSchema(avroSchema);
        }
        String talendValue = null;
        switch (avroType) {
        case BOOLEAN:
            talendValue = avroValue;
            break;
        case BYTES:
            talendValue = formatToTalendString(avroValue) + ".getBytes()"; //$NON-NLS-1$
            break;
        case DOUBLE:
            talendValue = avroValue;
            break;
        case FLOAT:
            talendValue = avroValue;
            String avroUpperValue = avroValue.toUpperCase();
            if (!avroUpperValue.endsWith("F")) { //$NON-NLS-1$
                talendValue += "F"; //$NON-NLS-1$
            }
            break;
        case INT:
            talendValue = avroValue;
            break;
        case LONG:
            talendValue = avroValue;
            avroUpperValue = avroValue.toUpperCase();
            if (!avroUpperValue.endsWith("L")) { //$NON-NLS-1$
                talendValue += "L"; //$NON-NLS-1$
            }
            break;
        case STRING:
            talendValue = formatToTalendString(avroValue);
            break;
        default:
            talendValue = formatToTalendString(avroValue);
            break;
        }
        return talendValue;
    }

    /**
     * For now, it only support primitive types
     * 
     * @param avroType
     * @return
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private static String mapAvroTypeToTalendType(ReflectedAVROSchema avroSchema) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException {
        ReflectedAVROEnumType avroType = avroSchema.getType();
        if (ReflectedAVROEnumType.UNION == avroType) {
            avroType = getMainTypeFromUnionSchema(avroSchema);
        }
        String talendType = null;
        switch (avroType) {
        case BOOLEAN:
            talendType = JavaTypesManager.BOOLEAN.getId();
            break;
        case BYTES:
            talendType = JavaTypesManager.BYTE_ARRAY.getId();
            break;
        case DOUBLE:
            talendType = JavaTypesManager.DOUBLE.getId();
            break;
        case FLOAT:
            talendType = JavaTypesManager.FLOAT.getId();
            break;
        case INT:
            talendType = JavaTypesManager.INTEGER.getId();
            break;
        case LONG:
            talendType = JavaTypesManager.LONG.getId();
            break;
        case STRING:
            talendType = JavaTypesManager.STRING.getId();
            break;
        default:
            talendType = JavaTypesManager.OBJECT.getId();
            break;
        }
        return talendType;
    }

    /**
     * DOC cmeng Comment method "getMainTypeFromUnion".
     * 
     * @param avroSchema
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     */
    private static ReflectedAVROEnumType getMainTypeFromUnionSchema(ReflectedAVROSchema avroSchema) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        List<ReflectedAVROSchema> unionSchemas = avroSchema.parseToUnionSchema().getTypes();
        ReflectedAVROEnumType avroEnumType = null;
        if (unionSchemas == null) {
            avroEnumType = ReflectedAVROEnumType.OTHERS;
        } else {
            int size = unionSchemas.size();
            if (0 == size) {
                avroEnumType = ReflectedAVROEnumType.OTHERS;
            } else if (1 == size) {
                ReflectedAVROSchema reflectedAVROSchema = unionSchemas.get(0);
                ReflectedAVROEnumType reflectedAVROType = reflectedAVROSchema.getType();
                if (ReflectedAVROEnumType.UNION == reflectedAVROType) {
                    avroEnumType = ReflectedAVROEnumType.OTHERS;
                } else {
                    avroEnumType = reflectedAVROType;
                }
            } else if (2 == size) {
                if (isContainsNullType(unionSchemas)) {
                    for (ReflectedAVROSchema reflectedAVROSchema : unionSchemas) {
                        ReflectedAVROEnumType reflectedAVROType = reflectedAVROSchema.getType();
                        if (ReflectedAVROEnumType.NULL == reflectedAVROType || ReflectedAVROEnumType.UNION == reflectedAVROType) {
                            continue;
                        } else {
                            avroEnumType = reflectedAVROType;
                            break;
                        }
                    }
                    if (avroEnumType == null) {
                        avroEnumType = ReflectedAVROEnumType.OTHERS;
                    }
                } else {
                    avroEnumType = ReflectedAVROEnumType.OTHERS;
                }
            } else {
                avroEnumType = ReflectedAVROEnumType.OTHERS;
            }
        }
        return avroEnumType;
    }

    private boolean isNullable(ReflectedAVROSchema avroSchema) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        ReflectedAVROSchema.ReflectedAVROUnionSchema unionSchema = avroSchema.parseToUnionSchema();
        if (unionSchema != null) {
            List<ReflectedAVROSchema> unionSchemas = unionSchema.getTypes();
            return isContainsNullType(unionSchemas);
        } else {
            return false;
        }
    }

    private static boolean isContainsNullType(List<ReflectedAVROSchema> schemaList) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException {
        if (schemaList == null || schemaList.size() == 0) {
            return false;
        }
        for (ReflectedAVROSchema avroSchema : schemaList) {
            if (ReflectedAVROEnumType.NULL == avroSchema.getType()) {
                return true;
            }
        }
        return false;
    }

    private class ReflectedAVROSchema {

        Object reflectedSchema = null;

        ClassLoader classLoader;

        /**
         * DOC cmeng ExtractAVROFileSchemaService.ReflectedAVROSchema constructor comment.
         * 
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         * @throws InstantiationException
         * @throws IllegalArgumentException
         * @throws NoSuchMethodException
         * @throws SecurityException
         * @throws ClassNotFoundException
         */
        public ReflectedAVROSchema(String orginalFullFileName, File file, ClassLoader clsLoader) throws ClassNotFoundException,
                SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
                IllegalAccessException, InvocationTargetException {
            this.classLoader = clsLoader;
            String upperFullPathName = orginalFullFileName.toUpperCase();
            if (upperFullPathName.endsWith("." + FILE_FORMAT_AVSC)) { //$NON-NLS-1$
                initSchemaFromAVSCFile(file);
            } else if (upperFullPathName.endsWith("." + FILE_FORMAT_AVRO)) { //$NON-NLS-1$
                initSchemaFromAVROFile(file);
            }
        }

        public ReflectedAVROSchema(Object avroSchema, ClassLoader clsLoader) {
            this.reflectedSchema = avroSchema;
            this.classLoader = clsLoader;
        }

        private void initSchemaFromAVSCFile(File avscFile) throws ClassNotFoundException, SecurityException,
                NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException,
                InvocationTargetException {
            // storedSchema = new Schema.Parser().parse(tmpFile);
            // Class.forName("org.apache.hadoop.fs.FileSystem", true, classLoader)
            Object parser = ReflectionUtils.newInstance("org.apache.avro.Schema$Parser", classLoader, new Object[0]); //$NON-NLS-1$
            reflectedSchema = ReflectionUtils.invokeMethod(parser, "parse", new Object[] { avscFile }, File.class); //$NON-NLS-1$
        }

        public void initSchemaFromAVROFile(File avroFile) throws ClassNotFoundException, SecurityException,
                NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException,
                InvocationTargetException {
            // DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>();
            // DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(tmpFile, datumReader);
            // storedSchema = dataFileReader.getSchema();
            // dataFileReader.close();

            Object datumReader = ReflectionUtils.newInstance("org.apache.avro.generic.GenericDatumReader", classLoader, //$NON-NLS-1$
                    new Object[0]);
            Object dataFileReader = ReflectionUtils.newInstance("org.apache.avro.file.DataFileReader", classLoader, new Object[] { //$NON-NLS-1$
                    avroFile, datumReader }, File.class, Class.forName("org.apache.avro.io.DatumReader", false, classLoader)); //$NON-NLS-1$
            reflectedSchema = ReflectionUtils.invokeMethod(dataFileReader, "getSchema", new Object[0]); //$NON-NLS-1$
            ReflectionUtils.invokeMethod(dataFileReader, "close", new Object[0]); //$NON-NLS-1$
        }

        public List<ReflectedAVROField> getFields() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
                IllegalAccessException, InvocationTargetException {
            Object objFields = ReflectionUtils.invokeMethod(reflectedSchema, "getFields", new Object[0]); //$NON-NLS-1$
            List<ReflectedAVROField> reflectedFields = null;
            if (objFields instanceof List<?>) {
                List<?> listFields = (List<?>) objFields;
                reflectedFields = new ArrayList<ExtractAVROFileSchemaService.ReflectedAVROField>(listFields.size());
                for (Object fields : listFields) {
                    ReflectedAVROField reflectedField = new ReflectedAVROField(fields, classLoader);
                    reflectedFields.add(reflectedField);
                }
            }
            return reflectedFields;
        }

        public ReflectedAVROEnumType getType() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
                IllegalAccessException, InvocationTargetException, ClassNotFoundException {
            Object type = ReflectionUtils.invokeMethod(reflectedSchema, "getType", new Object[0]); //$NON-NLS-1$

            Class enumType = Class.forName("org.apache.avro.Schema$Type", false, classLoader); //$NON-NLS-1$
            ReflectedAVROEnumType reflectedType = ReflectedAVROEnumType.OTHERS;
            if (type == Enum.valueOf(enumType, "BOOLEAN")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.BOOLEAN;
            } else if (type == Enum.valueOf(enumType, "BYTES")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.BYTES;
            } else if (type == Enum.valueOf(enumType, "DOUBLE")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.DOUBLE;
            } else if (type == Enum.valueOf(enumType, "FLOAT")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.FLOAT;
            } else if (type == Enum.valueOf(enumType, "INT")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.INT;
            } else if (type == Enum.valueOf(enumType, "LONG")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.LONG;
            } else if (type == Enum.valueOf(enumType, "STRING")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.STRING;
            } else if (type == Enum.valueOf(enumType, "UNION")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.UNION;
            } else if (type == Enum.valueOf(enumType, "NULL")) { //$NON-NLS-1$
                reflectedType = ReflectedAVROEnumType.NULL;
            } else {
                reflectedType = ReflectedAVROEnumType.OTHERS;
            }

            return reflectedType;
        }

        public ReflectedAVROUnionSchema parseToUnionSchema() throws SecurityException, NoSuchMethodException,
                IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
            if (this.getType() == ReflectedAVROEnumType.UNION) {
                return new ReflectedAVROUnionSchema(reflectedSchema, classLoader);
            } else {
                return null;
            }
        }

        // can not use class cast, because the obtained schema are always be an instance of ReflectedAVROSchema
        public class ReflectedAVROUnionSchema extends ReflectedAVROSchema {

            public ReflectedAVROUnionSchema(Object unionSchema, ClassLoader clsLoader) {
                super(unionSchema, clsLoader);
            }

            public List<ReflectedAVROSchema> getTypes() throws SecurityException, NoSuchMethodException,
                    IllegalArgumentException, IllegalAccessException, InvocationTargetException {
                Object avroTypes = ReflectionUtils.invokeMethod(this.reflectedSchema, "getTypes", new Object[0]); //$NON-NLS-1$
                if (!(avroTypes instanceof List<?>)) {
                    return null;
                } else {
                    List<?> schemaList = (List<?>) avroTypes;
                    List<ReflectedAVROSchema> retSchemaList = new ArrayList<ExtractAVROFileSchemaService.ReflectedAVROSchema>(
                            schemaList.size());
                    for (Object objSchema : schemaList) {
                        ReflectedAVROSchema reflectedAVROSchema = new ReflectedAVROSchema(objSchema, classLoader);
                        retSchemaList.add(reflectedAVROSchema);
                    }
                    return retSchemaList;
                }
            }
        }
    }

    private class ReflectedAVROField {

        Object reflectedField;

        ClassLoader classLoader;

        public ReflectedAVROField(Object field, ClassLoader clsLoader) {
            this.reflectedField = field;
            this.classLoader = clsLoader;
        }

        public ReflectedAVROSchema schema() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
                IllegalAccessException, InvocationTargetException {
            Object avroSchema = ReflectionUtils.invokeMethod(reflectedField, "schema", new Object[0]); //$NON-NLS-1$
            if (avroSchema == null) {
                return null;
            } else {
                return new ReflectedAVROSchema(avroSchema, classLoader);
            }
        }

        public String name() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
                InvocationTargetException {
            Object name = ReflectionUtils.invokeMethod(reflectedField, "name", new Object[0]); //$NON-NLS-1$
            if (name != null) {
                return (String) name;
            } else {
                return null;
            }
        }

        public ReflectedJsonNode defaultValue() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
                IllegalAccessException, InvocationTargetException {
            Object jsonNode = ReflectionUtils.invokeMethod(reflectedField, "defaultValue", new Object[0]); //$NON-NLS-1$
            if (jsonNode == null) {
                return null;
            } else {
                return new ReflectedJsonNode(jsonNode, classLoader);
            }
        }

        public String doc() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
                InvocationTargetException {
            Object doc = ReflectionUtils.invokeMethod(reflectedField, "doc", new Object[0]); //$NON-NLS-1$
            if (doc != null) {
                return (String) doc;
            } else {
                return null;
            }
        }

    }

    private class ReflectedJsonNode {

        Object jsonNode;

        ClassLoader classLoader;

        public ReflectedJsonNode(Object jacksonNode, ClassLoader clsLoader) {
            this.jsonNode = jacksonNode;
            this.classLoader = clsLoader;
        }

        public String asText() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
                InvocationTargetException {
            Object value = ReflectionUtils.invokeMethod(jsonNode, "getValueAsText", new Object[0]); //$NON-NLS-1$
            if (value != null) {
                return (String) value;
            } else {
                return null;
            }
        }
    }

}
