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
package org.talend.designer.pigmap.figures.tablesettings;

import org.talend.core.model.process.ILookupMode;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public enum JOIN_OPTIMIZATION implements ILookupMode {
    NONE,
    REPLICATED,
    SKEWED,
    MERGE, ;

    public static JOIN_OPTIMIZATION parse(String joinOptimization) {
        JOIN_OPTIMIZATION multipleJoinOptimizationResult = null;
        JOIN_OPTIMIZATION[] multiplJoinOptimizationModes = values();
        for (JOIN_OPTIMIZATION multiplJoinOptimizationMode : multiplJoinOptimizationModes) {
            if (multiplJoinOptimizationMode.toString().equals(joinOptimization)) {
                multipleJoinOptimizationResult = multiplJoinOptimizationMode;
                break;
            }
        }
        return multipleJoinOptimizationResult;
    }

}
