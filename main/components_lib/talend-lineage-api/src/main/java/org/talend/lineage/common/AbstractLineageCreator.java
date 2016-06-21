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
package org.talend.lineage.common;

import java.util.Map;

/**
 * 
 * Default implementations of methods from ILineageCreator that are not supported by a given provider
 *
 */
public abstract class AbstractLineageCreator {

    public void addDataset(Map<String, String> schema, String componentName, String fileSystemPath, String fileFormat) {
        // Nothing
    }

    public void addJobInfo(Map<String, Object> jobMetadata) {
        // Nothing
    }

}
