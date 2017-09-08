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

import org.eclipse.jface.action.IMenuManager;
import org.talend.designer.gefabstractmap.dnd.MapperDragSourceListener;
import org.talend.designer.gefabstractmap.dnd.MapperDropTargetListener;
import org.talend.designer.gefabstractmap.editor.MapperGraphicalEditor;
import org.talend.designer.gefabstractmap.editor.MapperGraphicalViewer;
import org.talend.designer.pigmap.dnd.PigDragSourceListener;
import org.talend.designer.pigmap.dnd.PigDropTargetListener;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapEditor extends MapperGraphicalEditor {

    public PigMapEditor(MapperManager mapperManager) {
        super(mapperManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.editor.MapperGraphicalEditor#configureGraphicalViewer()
     */
    @Override
    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        getGraphicalViewer().setEditPartFactory(new PigMapPartFactory());
    }

    @Override
    protected void createActions() {
        //
    }

    public void buildContextMenu(IMenuManager menu) {
        //
    }

    @Override
    public MapperManager getMapperManager() {
        return (MapperManager) super.getMapperManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.editor.MapperGraphicalEditor#createMapperGraphicalViewer()
     */
    @Override
    protected MapperGraphicalViewer createMapperGraphicalViewer() {
        return new PigMapGraphicViewer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.editor.MapperGraphicalEditor#createDragSourceListener()
     */
    @Override
    protected MapperDragSourceListener createDragSourceListener() {
        return new PigDragSourceListener(getGraphicalViewer());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.editor.MapperGraphicalEditor#createDropTargetListener()
     */
    @Override
    protected MapperDropTargetListener createDropTargetListener() {
        return new PigDropTargetListener(getGraphicalViewer());
    }

}
