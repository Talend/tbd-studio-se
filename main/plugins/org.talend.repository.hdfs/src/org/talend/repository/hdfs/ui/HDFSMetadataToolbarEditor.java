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
package org.talend.repository.hdfs.ui;

import java.io.File;

import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.button.ImportPushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.ImportPushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.button.PastePushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.PastePushButtonForExtendedTable;
import org.talend.commons.ui.swt.advanced.dataeditor.button.RemovePushButton;
import org.talend.commons.ui.swt.advanced.dataeditor.button.RemovePushButtonForExtendedTable;
import org.talend.commons.ui.swt.extended.table.AbstractExtendedTableViewer;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.ui.metadata.editor.MetadataEmfTableEditor;
import org.talend.core.ui.metadata.editor.MetadataEmfToolbarEditor;
import org.talend.core.ui.metadata.extended.command.MetadataEmfImportXmlCommand;
import org.talend.core.ui.metadata.extended.command.MetadataEmfPasteCommand;
import org.talend.designer.hdfsbrowse.model.EHDFSFileTypes;
import org.talend.repository.hdfs.util.HDFSConstants;

/**
 * created by ycbai on 2014-6-3 Detailled comment
 * 
 */
public class HDFSMetadataToolbarEditor extends MetadataEmfToolbarEditor {

    /**
     * DOC ycbai HDFSMetadataToolbarEditor constructor comment.
     * 
     * @param parent
     * @param style
     * @param extendedTableViewer
     */
    public HDFSMetadataToolbarEditor(Composite parent, int style, AbstractExtendedTableViewer<MetadataColumn> extendedTableViewer) {
        super(parent, style, extendedTableViewer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.editor.MetadataEmfToolbarEditor#createAddPushButton()
     */
    @Override
    protected AddPushButton createAddPushButton() {
        return new AddPushButtonForExtendedTable(this.toolbar, getExtendedTableViewer()) {

            @Override
            protected Object getObjectToAdd() {
                MetadataEmfTableEditor tableEditorModel = (MetadataEmfTableEditor) getExtendedTableViewer()
                        .getExtendedControlModel();
                return tableEditorModel.createNewMetadataColumn(null);
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton#getEnabledState()
             */
            @Override
            public boolean getEnabledState() {
                if (isSequenceFileSchema()) {
                    return false;
                }
                return super.getEnabledState();
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.editor.MetadataEmfToolbarEditor#createPastePushButton()
     */
    @Override
    public PastePushButton createPastePushButton() {
        return new PastePushButtonForExtendedTable(toolbar, extendedTableViewer) {

            @Override
            protected Command getCommandToExecute(ExtendedTableModel extendedTableModel, Integer indexWhereInsert) {
                return new MetadataEmfPasteCommand(extendedTableModel, indexWhereInsert);
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton#getEnabledState()
             */
            @Override
            public boolean getEnabledState() {
                if (isSequenceFileSchema()) {
                    return false;
                }
                return super.getEnabledState();
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.editor.MetadataEmfToolbarEditor#createImportPushButton()
     */
    @Override
    public ImportPushButton createImportPushButton() {
        return new ImportPushButtonForExtendedTable(toolbar, extendedTableViewer) {

            @Override
            protected Command getCommandToExecute(ExtendedTableModel extendedTableModel, File file) {
                return new MetadataEmfImportXmlCommand(extendedTableModel, file);
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton#getEnabledState()
             */
            @Override
            public boolean getEnabledState() {
                if (isSequenceFileSchema()) {
                    return false;
                }
                return super.getEnabledState();
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView#createRemovePushButton()
     */
    @Override
    protected RemovePushButton createRemovePushButton() {

        return new RemovePushButtonForExtendedTable(toolbar, extendedTableViewer) {

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.commons.ui.swt.advanced.dataeditor.button.AddPushButton#getEnabledState()
             */
            @Override
            public boolean getEnabledState() {
                if (isSequenceFileSchema()) {
                    return false;
                }
                return super.getEnabledState();
            }
        };
    }

    private boolean isSequenceFileSchema() {
        ExtendedTableModel extTableModel = getExtendedTableViewer().getExtendedTableModel();
        if (extTableModel != null && extTableModel instanceof MetadataEmfTableEditor) {
            MetadataEmfTableEditor tableEditor = (MetadataEmfTableEditor) extTableModel;
            MetadataTable metadataTable = tableEditor.getMetadataTable();
            if (metadataTable != null) {
                if (EHDFSFileTypes.SEQUENCE.getName().equals(
                        metadataTable.getAdditionalProperties().get(HDFSConstants.HDFS_FILE_TYPE))) {
                    return true;
                }
            }
        }

        return false;
    }

}
