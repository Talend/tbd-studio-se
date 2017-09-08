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
package org.talend.designer.pigmap;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.ui.MapperUI;
import org.talend.designer.pigmap.ui.tabs.MapperManager;
import org.talend.designer.pigmap.util.MapDataHelper;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class MapperMain {

    private PigMapComponent mapperComponent;

    private PigMapData copyOfMapData;

    private MapperUI mapperUI;

    private MapperManager mapperManager;

    public MapperMain(PigMapComponent mapperComponent) {
        this.mapperComponent = mapperComponent;
        prepareModel();
        mapperManager = new MapperManager(mapperComponent, copyOfMapData);
    }

    public Shell createUI(Display display) {
        mapperUI = new MapperUI(mapperManager);
        return mapperUI.createWindow(display);
    }

    private void prepareModel() {
        if (mapperComponent == null) {
            return;
        }
        if (mapperComponent.getExternalEmfData() != null) {
            copyOfMapData = EcoreUtil.copy((PigMapData) mapperComponent.getExternalEmfData());
            MapDataHelper helper = new MapDataHelper();
            helper.rebuildXmlMapData((PigMapData) copyOfMapData, mapperComponent);
        }
    }

    public int getMapperDialogResponse() {
        return mapperUI.getMapperDialogResponse();
    }

    public MapperManager getMapperManager() {
        return this.mapperManager;
    }
}
