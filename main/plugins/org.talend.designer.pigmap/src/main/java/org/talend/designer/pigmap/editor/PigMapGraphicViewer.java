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

import org.talend.designer.gefabstractmap.editor.MapperGraphicalViewer;
import org.talend.designer.gefabstractmap.figures.manager.FiguresManager;
import org.talend.designer.pigmap.figures.manager.PigMapFiguresManager;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapGraphicViewer extends MapperGraphicalViewer {

    public PigMapGraphicViewer() {
    }

    @Override
    public MapperManager getMapperManager() {
        return (MapperManager) super.getMapperManager();
    }

    @Override
    public PigMapFiguresManager getFiguresManager() {
        return (PigMapFiguresManager) super.getFiguresManager();
    }

    @Override
    protected FiguresManager createFiguresManager() {
        return new PigMapFiguresManager(this);
    }
}
