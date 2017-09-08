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

import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.runtime.swt.tableviewer.behavior.ColumnCellModifier;
import org.talend.commons.ui.runtime.swt.tableviewer.behavior.IColumnColorProvider;
import org.talend.commons.ui.runtime.swt.tableviewer.behavior.IColumnImageProvider;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.CELL_EDITOR_STATE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.celleditor.DialogErrorForCellEditorListener;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.ui.metadata.editor.MetadataEmfTableEditorView;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.repository.hdfs.util.HDFSConstants;

import orgomg.cwm.objectmodel.core.TaggedValue;

/**
 * created by ycbai on 2014-6-3 Detailled comment
 * 
 */
public class HDFSMetadataTableEditorView extends MetadataEmfTableEditorView {

    /**
     * DOC ycbai HDFSMetadataTableEditorView constructor comment.
     * 
     * @param parentComposite
     * @param mainCompositeStyle
     * @param initGraphicsComponents
     */
    public HDFSMetadataTableEditorView(Composite parentComposite, int mainCompositeStyle, boolean initGraphicsComponents) {
        super(parentComposite, mainCompositeStyle, initGraphicsComponents);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.ui.metadata.editor.AbstractMetadataTableEditorView#configureNameColumn(org.talend.commons.ui.
     * swt.tableviewer.TableViewerCreator)
     */
    @Override
    protected void configureNameColumn(TableViewerCreator<MetadataColumn> tableViewerCreator) {
        TableViewerCreatorColumn column;
        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setId(ID_COLUMN_NAME);
        column.setTitle("Column");
        column.setToolTipHeader("Column");

        column.setBeanPropertyAccessors(getLabelAccessor());
        final Image imageKey = ImageProvider.getImage(EImage.KEY_ICON);
        final Image imageEmpty = ImageProvider.getImage(EImage.EMPTY);
        column.setImageProvider(new IColumnImageProvider() {

            @Override
            public Image getImage(Object element) {
                if (getKeyAccesor().get((MetadataColumn) element)) {
                    return imageKey;
                } else {
                    return imageEmpty;
                }
            }

        });
        column.setWeight(25);
        column.setModifiable(!isReadOnly());
        column.setMinimumWidth(45);

        final ColumnCellModifier columnCellModifier = new ColumnCellModifier(column) {

            @Override
            public boolean canModify(Object bean) {
                MetadataColumn columnBean = (MetadataColumn) bean;
                TaggedValue seqKeyTV = TaggedValueHelper.getTaggedValue(HDFSConstants.IS_SEQUENCE_KEY,
                        columnBean.getTaggedValue());
                TaggedValue seqValTV = TaggedValueHelper.getTaggedValue(HDFSConstants.IS_SEQUENCE_VALUE,
                        columnBean.getTaggedValue());
                if (seqKeyTV != null || seqValTV != null) {
                    return false;
                }
                return true;
            }

        };

        column.setColorProvider(new IColumnColorProvider() {

            @Override
            public Color getBackgroundColor(Object bean) {
                if (!columnCellModifier.canModify(bean)) {
                    return READONLY_CELL_BG_COLOR;
                }
                return null;
            }

            @Override
            public Color getForegroundColor(Object bean) {
                return null;
            }

        });
        column.setColumnCellModifier(columnCellModifier);

        final TextCellEditor cellEditor = new TextCellEditorExtendTab(tableViewerCreator.getTable());
        cellEditor.addListener(new DialogErrorForCellEditorListener(cellEditor, column) {

            @Override
            public void newValidValueTyped(int itemIndex, Object previousValue, Object newValue, CELL_EDITOR_STATE state) {
            }

            @Override
            public String validateValue(String newValue, int beanPosition) {
                return validateColumnName(newValue, beanPosition);
            }

        });
        column.setCellEditor(cellEditor);
    }

    @Override
    protected ExtendedToolbarView initToolBar() {
        return new HDFSMetadataToolbarEditor(getMainComposite(), SWT.NONE, this.getExtendedTableViewer());
    }

}
