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
package org.talend.designer.hdfsbrowse.model;

/**
 * created by ycbai on 2014-5-30 Detailled comment
 * 
 */
public enum EHadoopDataTypeMapping {

    BOOLEAN_WRITABLE("org.apache.hadoop.io.BooleanWritable", "id_Boolean"), //$NON-NLS-1$ //$NON-NLS-2$

    BYTE_WRITABLE("org.apache.hadoop.io.ByteWritable", "id_Byte"), //$NON-NLS-1$ //$NON-NLS-2$

    BYTES_WRITABLE("org.apache.hadoop.io.BytesWritable", "id_byte[]"), //$NON-NLS-1$ //$NON-NLS-2$

    DOUBLE_WRITABLE("org.apache.hadoop.io.DoubleWritable", "id_Double"), //$NON-NLS-1$ //$NON-NLS-2$

    FLOAT_WRITABLE("org.apache.hadoop.io.FloatWritable", "id_Float"), //$NON-NLS-1$ //$NON-NLS-2$

    INT_WRITABLE("org.apache.hadoop.io.IntWritable", "id_Integer"), //$NON-NLS-1$ //$NON-NLS-2$

    LONG_WRITABLE("org.apache.hadoop.io.LongWritable", "id_Long"), //$NON-NLS-1$ //$NON-NLS-2$

    SHORT_WRITABLE("org.apache.hadoop.io.ShortWritable", "id_Short"), //$NON-NLS-1$ //$NON-NLS-2$

    TEXT("org.apache.hadoop.io.Text", "id_String"), //$NON-NLS-1$ //$NON-NLS-2$

    ;

    private String dataType;

    private String talendType;

    EHadoopDataTypeMapping(String dataType, String talendType) {
        this.dataType = dataType;
        this.talendType = talendType;
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getTalendType() {
        return this.talendType;
    }

    public static String getTalendTypeByHadoopType(String hadoopType) {
        for (EHadoopDataTypeMapping mapping : values()) {
            if (mapping.getDataType().equalsIgnoreCase(hadoopType)) {
                return mapping.getTalendType();
            }
        }

        return null;
    }

    public static String getHadoopTypeByTalendType(String talendType) {
        for (EHadoopDataTypeMapping mapping : values()) {
            if (mapping.getTalendType().equalsIgnoreCase(talendType)) {
                return mapping.getDataType();
            }
        }

        return null;
    }

}
