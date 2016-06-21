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
package org.talend.lineage.cloudera.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.talend.lineage.cloudera.entity.TalendField;

/**
 * Utilities to convert Talend schemas to Cloudera Navigator fiel entities
 *
 */
public class ClouderaFieldConvertor {

    public static List<TalendField> convertToTalendField(Map<String, String> columns) {
        List<TalendField> talendFields = new ArrayList<TalendField>();
        if (MapUtils.isNotEmpty(columns)) {
            for (Entry<String, String> column : columns.entrySet()) {
                talendFields.add(new TalendField(column.getKey(), column.getValue()));
            }
        }
        return talendFields;
    }
}
