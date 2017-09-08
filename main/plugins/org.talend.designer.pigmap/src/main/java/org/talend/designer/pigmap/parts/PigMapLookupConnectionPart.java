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
package org.talend.designer.pigmap.parts;

import java.util.ArrayList;
import java.util.List;

import org.talend.designer.gefabstractmap.part.LookupConnectionPart;
import org.talend.designer.pigmap.model.emf.pigmap.IConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.LookupConnection;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapLookupConnectionPart extends LookupConnectionPart {

    protected int calculateConnOffset() {
        LookupConnection model = (LookupConnection) getModel();
        List<IConnection> outConns = new ArrayList<IConnection>();
        if (model.getSource() != null && model.getSource() instanceof TableNode) {
            TableNode sourceTableNode = (TableNode) model.getSource();
            if (sourceTableNode.eContainer() != null && sourceTableNode.eContainer() instanceof InputTable) {
                outConns.addAll(PigMapUtil.getAllNodeLookConnections((InputTable) sourceTableNode.eContainer()));
            }
            int indexOf = outConns.indexOf(model);
            if (indexOf != -1) {
                return -(indexOf + 1) * 5;
            }
        }
        return 0;
    }
}
