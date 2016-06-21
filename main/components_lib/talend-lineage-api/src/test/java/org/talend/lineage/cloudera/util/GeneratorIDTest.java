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

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.lineage.cloudera.util.GeneratorID;

import com.cloudera.nav.sdk.model.CustomIdGenerator;

/**
 * created by pbailly on 21 Oct 2015 Detailled comment
 *
 */
public class GeneratorIDTest {

    @Test
    public void test() {
        assertEquals(CustomIdGenerator.generateIdentity(GeneratorID.DATASET_MARKER,
                GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, "job", "component"), GeneratorID.generateDatasetID("job",
                "component"));
        assertEquals(
                CustomIdGenerator.generateIdentity(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, "job", "component"),
                GeneratorID.generateNodeID("job", "component"));
        assertEquals(
                CustomIdGenerator.generateIdentity(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, "job", "component"),
                GeneratorID.generateEntityID("job", "component"));
        assertEquals(CustomIdGenerator.generateIdentity(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE, "job", "component",
                "columnname"), GeneratorID.generateEntityChildID("job", "component", "columnname"));
    }
}
