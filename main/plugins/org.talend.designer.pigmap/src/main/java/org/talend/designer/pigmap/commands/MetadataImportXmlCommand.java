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
package org.talend.designer.pigmap.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.utils.data.list.ListenableList;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.MetadataSchema;
import org.xml.sax.SAXException;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class MetadataImportXmlCommand extends org.talend.core.ui.metadata.extended.command.MetadataImportXmlCommand {

    public MetadataImportXmlCommand(ExtendedTableModel extendedTableModel, File file) {
        super(extendedTableModel, file);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.extended.command.MetadataImportXmlCommand#execute()
     */
    @Override
    public void execute() {
        try {
            removed = new ArrayList<IMetadataColumn>(extendedTableModel.getBeansList());
            extendedTableModel.removeAll(removed, true, false);
            added = MetadataSchema.initializeColumns(file);
            extendedTableModel.addAll(added, true, false);
            if (extendedTableModel.getBeansList() instanceof ListenableList) {
                ListenableList beanList = (ListenableList) extendedTableModel.getBeansList();
                beanList.fireReplacedEvent(0, removed, added, false);
            }

        } catch (ParserConfigurationException e) {
            ExceptionHandler.process(e);
        } catch (SAXException e) {
            // bug 17654:import the xml file as the schema will throw error.
            ExceptionHandler.processForSchemaImportXml(e);
        } catch (IOException e) {
            ExceptionHandler.process(e);
        }
    }

}
