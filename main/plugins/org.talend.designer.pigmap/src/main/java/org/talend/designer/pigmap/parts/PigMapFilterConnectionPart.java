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

import org.talend.designer.gefabstractmap.part.FilterConnectionPart;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.IConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.util.PigMapUtil;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapFilterConnectionPart extends FilterConnectionPart {

    protected int calculateConnOffset() {
        FilterConnection model = (FilterConnection) getModel();
        List<IConnection> conns = new ArrayList<IConnection>();
        if (model.getSource() != null && model.getSource() instanceof TableNode) {
            TableNode sourceTableNode = (TableNode) model.getSource();
            if (sourceTableNode.eContainer() != null && sourceTableNode.eContainer() instanceof InputTable) {
                conns.addAll(PigMapUtil.getInputTableFilterConnections((InputTable) sourceTableNode.eContainer()));
            }
            int indexOf = conns.indexOf(model);
            if (indexOf != -1) {
                return -(indexOf + 1) * 7;
            }
        }
        return 0;
    }
}
