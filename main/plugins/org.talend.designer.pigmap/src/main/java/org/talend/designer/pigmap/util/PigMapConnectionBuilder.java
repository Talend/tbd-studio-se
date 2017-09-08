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
package org.talend.designer.pigmap.util;

import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapConnectionBuilder {

    public static void createConnection(AbstractNode sourceNode, AbstractNode targetNode, PigMapData mapData) {
        Connection conn = PigmapFactory.eINSTANCE.createConnection();
        conn.setSource(sourceNode);
        conn.setTarget(targetNode);
        targetNode.getIncomingConnections().add(conn);
        sourceNode.getOutgoingConnections().add(conn);
        mapData.getConnections().add(conn);
    }

    public static void createFilterConnection(AbstractNode sourceNode, AbstractInOutTable targetTree, PigMapData mapData) {
        FilterConnection connection = PigmapFactory.eINSTANCE.createFilterConnection();
        connection.setSource(sourceNode);
        connection.setTarget(targetTree);
        targetTree.getFilterIncomingConnections().add(connection);
        sourceNode.getFilterOutGoingConnections().add(connection);
        mapData.getConnections().add(connection);
    }
}
