// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hcatalog.ui.handler;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.talend.repository.items.importexport.handlers.imports.MetadataConnectionImportHandler;
import org.talend.repository.items.importexport.handlers.model.ImportItem;
import org.talend.repository.items.importexport.handlers.model.ImportItem.State;
import org.talend.repository.items.importexport.manager.ResourcesManager;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HCatalogImportHandler extends MetadataConnectionImportHandler {

    /**
     * DOC ggu HCatalogImportHandler constructor comment.
     */
    public HCatalogImportHandler() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.ImportExecutableBasePathHandler#createImportItem(org
     * .eclipse.core.runtime .IProgressMonitor,
     * org.talend.repository.items.importexport.ui.wizard.imports.managers.ResourcesManager,
     * org.eclipse.core.runtime.IPath, boolean, java.util.List)
     */
    @Override
    public ImportItem createImportItem(IProgressMonitor progressMonitor, ResourcesManager resManager, IPath resourcePath,
            boolean overwrite, List<ImportItem> existeditems) throws Exception {
        final ImportItem importItem = super.createImportItem(progressMonitor, resManager, resourcePath, overwrite, existeditems);
        if (importItem != null) {
            // won't display in import dialog, will be implict to import by hadoop cluster(method
            // findRelatedItemRecord).
            importItem.setVisible(false);
        }
        return importItem;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.ImportBasicHandler#checkItem(org.talend.repository.
     * items.importexport.manager.ResourcesManager, org.talend.repository.items.importexport.handlers.model.ImportItem,
     * boolean)
     */
    @Override
    public boolean checkItem(ResourcesManager resManager, ImportItem itemRecord, boolean overwrite) {
        boolean check = super.checkItem(resManager, itemRecord, overwrite);
        if (State.ID_EXISTED.equals(itemRecord.getState())) {
            itemRecord.setState(State.NAME_AND_ID_EXISTED);
        }

        return check;
    }

}
