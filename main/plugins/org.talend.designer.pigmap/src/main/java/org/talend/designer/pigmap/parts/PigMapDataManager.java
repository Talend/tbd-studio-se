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

import java.util.List;

import org.talend.designer.gefabstractmap.figures.manager.RootModelManager;
import org.talend.designer.gefabstractmap.model.abstractmap.MapperTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapDataManager extends RootModelManager {

    public PigMapDataManager(PigMapData model, PigMapDataEditPart editPart) {
        super(model, editPart);
    }

    @Override
    public PigMapData getModel() {
        return (PigMapData) super.getModel();
    }

    @Override
    public PigMapDataEditPart getEditPart() {
        return (PigMapDataEditPart) super.getEditPart();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.RootModelManager#getInputTables()
     */
    @Override
    public List<? extends MapperTable> getInputTables() {
        return getModel().getInputTables();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.RootModelManager#getOutputTables()
     */
    @Override
    public List<? extends MapperTable> getOutputTables() {
        return getModel().getOutputTables();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.manager.RootModelManager#getVarTables()
     */
    @Override
    public List<? extends MapperTable> getVarTables() {
        return getModel().getVarTables();
    }
}
