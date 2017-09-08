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
package org.talend.designer.pigmap.ui.tabs;

import java.io.File;

import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.advanced.dataeditor.button.ImportPushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.ImportPushButtonForExtendedTable;
import org.talend.commons.ui.swt.extended.table.AbstractExtendedTableViewer;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.core.ui.metadata.editor.MetadataTableEditorView;
import org.talend.core.ui.metadata.editor.MetadataToolbarEditorView;
import org.talend.designer.pigmap.commands.MetadataImportXmlCommand;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapMetadataTableEditorView extends MetadataTableEditorView {

    public PigMapMetadataTableEditorView(Composite parentComposite, int mainCompositeStyle) {
        super(parentComposite, mainCompositeStyle);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.editor.MetadataTableEditorView#initToolBar()
     */
    @Override
    protected ExtendedToolbarView initToolBar() {
        return new PigMapMetadataToolbarEditorView(getMainComposite(), SWT.NONE, this.getExtendedTableViewer(),
                this.getCurrentDbms());
    }

    /**
     * 
     * DOC hcyi PigMapMetadataTableEditorView class global comment. Detailled comment
     */

    class PigMapMetadataToolbarEditorView extends MetadataToolbarEditorView {

        public PigMapMetadataToolbarEditorView(Composite parent, int style, AbstractExtendedTableViewer extendedTableViewer,
                String dbmsId) {
            super(parent, style, extendedTableViewer, dbmsId);
        }

        @Override
        public ImportPushButton createImportPushButton() {
            return new ImportPushButtonForExtendedTable(toolbar, extendedTableViewer) {

                @Override
                protected Command getCommandToExecute(ExtendedTableModel extendedTableModel, File file) {
                    return new MetadataImportXmlCommand(extendedTableModel, file);
                }
            };
        }
    }
}
