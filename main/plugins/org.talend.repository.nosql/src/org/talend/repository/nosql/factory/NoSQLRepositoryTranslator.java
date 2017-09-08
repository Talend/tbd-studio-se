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
package org.talend.repository.nosql.factory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.talend.repository.nosql.i18n.Messages;

/**
 * created by ycbai on 2014-6-18 Detailled comment
 * 
 */
public class NoSQLRepositoryTranslator {

    private static NoSQLRepositoryTranslator instance;

    private static final String FACTORY_MSG_PREFIX = "noSQLRepositoryTranslator."; //$NON-NLS-1$

    private BidiMap value2LabelMap = new DualHashBidiMap();

    private NoSQLRepositoryTranslator() {
    }

    public static NoSQLRepositoryTranslator getInstance() {
        if (instance == null) {
            instance = new NoSQLRepositoryTranslator();
        }

        return instance;
    }

    public String getLabel(String value) {
        if (value2LabelMap.containsKey(value)) {
            return (String) value2LabelMap.get(value);
        }
        String label = Messages.getString(FACTORY_MSG_PREFIX + value);
        value2LabelMap.put(value, label);

        return label;
    }

    public List<String> getLabels(List<String> values) {
        List<String> labels = new ArrayList<String>();
        if (values == null || values.size() == 0) {
            return labels;
        }
        for (String value : values) {
            String label = getLabel(value);
            if (!labels.contains(label)) {
                labels.add(label);
            }
        }

        return labels;
    }

    public String getValue(String label) {
        return (String) value2LabelMap.getKey(label);
    }

}
