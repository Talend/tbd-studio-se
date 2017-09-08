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
package org.talend.designer.pigmap.editor;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.talend.designer.gefabstractmap.part.ConnectionEditPart;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.LookupConnection;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;
import org.talend.designer.pigmap.parts.PigMapDataEditPart;
import org.talend.designer.pigmap.parts.PigMapFilterConnectionPart;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapLookupConnectionPart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;
import org.talend.designer.pigmap.parts.PigMapVarNodeEditPart;
import org.talend.designer.pigmap.parts.PigMapVarTablePart;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapPartFactory implements EditPartFactory {

    @Override
    public EditPart createEditPart(EditPart context, Object model) {
        EditPart part = null;
        if (model instanceof PigMapData) {
            part = new PigMapDataEditPart();
        } else if (model instanceof InputTable) {
            part = new PigMapInputTablePart();
        } else if (model instanceof OutputTable) {
            part = new PigMapOutputTablePart();
        } else if (model instanceof VarTable) {
            part = new PigMapVarTablePart();
        } else if (model instanceof TableNode) {
            part = new PigMapTableNodePart();
        } else if (model instanceof VarNode) {
            part = new PigMapVarNodeEditPart();
        } else if (model instanceof Connection) {
            part = new ConnectionEditPart();
        } else if (model instanceof LookupConnection) {
            part = new PigMapLookupConnectionPart();
        } else if (model instanceof FilterConnection) {
            part = new PigMapFilterConnectionPart();
        }
        if (part != null) {
            part.setModel(model);
        }
        return part;
    }

}
