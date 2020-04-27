// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
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
import java.util.Arrays;
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

    private String[] deprecatedVersion = { "MONGODB_2_5_X", "MONGODB_2_6_X", "MONGODB_3_0_X", "MONGODB_3_2_X" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

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
        String messageKey = value;
        if("MONGODB_3_5_X".equals(value)) {//$NON-NLS-1$
        	messageKey = "MONGODB_3_5_X_AND_LATER";//$NON-NLS-1$
        } else if (Arrays.asList(deprecatedVersion).contains(value)) {
            messageKey = value + "_DEPRECATED";//$NON-NLS-1$
        }
        String label = Messages.getString(FACTORY_MSG_PREFIX + messageKey);
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
