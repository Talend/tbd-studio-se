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
package org.talend.designer.core.ui.editor.cmd;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.core.model.components.ComponentCategory;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.ui.component.ComponentsFactoryProvider;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.process.Process;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;

/**
 * DOC xwen  class global comment. Detailled comment
 */
public class ChangeRepositoryValueFromCdhtoEmrTest {

    // test for TUP-17721 xwen
    @Test
    public void testExecute_cdh() {
        Property propertyCDH = PropertiesFactory.eINSTANCE.createProperty();
        IProcess2 processCDH = new Process(propertyCDH);
        IComponent sourceComCDH = ComponentsFactoryProvider.getInstance().get("tSparkConfiguration",
                ComponentCategory.CATEGORY_4_SPARK.getName());

        HadoopClusterConnection hadoopClusterConnection = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnection();
        hadoopClusterConnection.setAuthMode("USERNAME");
        hadoopClusterConnection.setDfVersion("Cloudera_CDH5_8");
        hadoopClusterConnection.setDistribution("CLOUDERA");
        hadoopClusterConnection.setLabel("cdh");
        hadoopClusterConnection.setEnableKerberos(true);
        INode newElemCDH = new Node(sourceComCDH, processCDH);
        newElemCDH.getElementParameter("USE_KRB").setValue(new Boolean(true));
        ChangeValuesFromRepository changeValuesFromRepo = new ChangeValuesFromRepository(newElemCDH, hadoopClusterConnection,
                "MR_PROPERTY:REPOSITORY_PROPERTY_TYPE", "_31iU4GIREeegQqu8SjIMUw");
        changeValuesFromRepo.execute();
        assertEquals(newElemCDH.getElementParameter("USE_KRB").getValue(), true);
    }

    // test for TUP-17721 xwen
    @Test
    public void testExecute_cdhToEmr() {
        Property propertyEMR = PropertiesFactory.eINSTANCE.createProperty();
        IProcess2 processEMR = new Process(propertyEMR);
        IComponent sourceComEMR = ComponentsFactoryProvider.getInstance().get("tSparkConfiguration",
                ComponentCategory.CATEGORY_4_SPARK.getName());
        HadoopClusterConnection hadoopClusterConnection2 = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnection();
        hadoopClusterConnection2.setAuthMode("USERNAME");
        hadoopClusterConnection2.setDfVersion("EMR_5_0_0");
        hadoopClusterConnection2.setDistribution("AMAZON_EMR");
        hadoopClusterConnection2.setLabel("emr");
        hadoopClusterConnection2.setEnableKerberos(false);
        INode newElemEMR = new Node(sourceComEMR, processEMR);
        newElemEMR.getElementParameter("USE_KRB").setValue(new Boolean(false));
        ChangeValuesFromRepository changeValuesFromCDHtoEMR = new ChangeValuesFromRepository(newElemEMR, hadoopClusterConnection2,
                "MR_PROPERTY:REPOSITORY_PROPERTY_TYPE", "_y7dnkGIREeegQqu8SjIMUw");
        changeValuesFromCDHtoEMR.execute();
        assertEquals(newElemEMR.getElementParameter("USE_KRB").getValue(), false);
    }

}
