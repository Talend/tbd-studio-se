// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.ui.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.process.IContextParameter;
import org.talend.metadata.managment.ui.model.IConnParamName;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hdfs.ui.handler.testutils.HDFSConnectionTestUtils;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * created by ycbai on 2017年1月13日 Detailled comment
 *
 */
public class HdfsContextHandlerTest {

    private HdfsContextHandler contextHandler;

    @Before
    public void before() {
        contextHandler = new HdfsContextHandler();
    }

    @Test
    public void testCreateContextParameters() {
        String prefixName = "testHDFS"; //$NON-NLS-1$
        HDFSConnection connection = (HDFSConnection) HDFSConnectionTestUtils.createDefaultHDFSItem().getConnection();

        Set<IConnParamName> paramSet = new HashSet<>();
        paramSet.add(EHadoopParamName.HdfsUser);
        paramSet.add(EHadoopParamName.HdfsFileSeparator);
        paramSet.add(EHadoopParamName.HdfsRowSeparator);
        paramSet.add(EHadoopParamName.HdfsRowHeader);

        List<String> contextParameterNames = new ArrayList<>();
        for (IConnParamName paramName : paramSet) {
            contextParameterNames.add(prefixName + ConnectionContextHelper.LINE + paramName);
        }

        List<IContextParameter> contextParameters = contextHandler.createContextParameters(prefixName, connection, paramSet);
        for (IContextParameter contextParameter : contextParameters) {
            String contextParameterName = contextParameter.getName();
            assertTrue(contextParameterNames.contains(contextParameterName));
            checkContextParameter(contextParameter, connection);
        }
    }

    private void checkContextParameter(IContextParameter contextParameter, HDFSConnection connection) {
        String contextParameterName = contextParameter.getName();
        String contextParameterNameWithoutPrefix = contextParameterName
                .substring(contextParameterName.lastIndexOf(ConnectionContextHelper.LINE) + 1);
        switch (EHadoopParamName.valueOf(contextParameterNameWithoutPrefix)) {
        case HdfsUser:
            assertEquals(JavaTypesManager.STRING.getId(), contextParameter.getType());
            assertEquals(connection.getUserName(), contextParameter.getValue());
            break;
        case HdfsRowSeparator:
            assertEquals(JavaTypesManager.STRING.getId(), contextParameter.getType());
            assertEquals(connection.getRowSeparator(), contextParameter.getValue());
            break;
        case HdfsFileSeparator:
            assertEquals(JavaTypesManager.STRING.getId(), contextParameter.getType());
            assertEquals(connection.getFieldSeparator(), contextParameter.getValue());
            break;
        case HdfsRowHeader:
            assertEquals(JavaTypesManager.INTEGER.getId(), contextParameter.getType());
            assertEquals(connection.getHeaderValue(), contextParameter.getValue());
            break;
        default:
        }
    }

}
