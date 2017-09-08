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
package org.talend.designer.pigmap.parts.directedit;

import java.util.List;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.designer.pigmap.i18n.Messages;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class EntryContentProposal implements IContentProposal {

    private static final String CARRIAGE_RETURN = "\n";

    private AbstractNode entry;

    private String content = "";

    private MapperManager mapperManager;

    public EntryContentProposal(MapperManager mapperManager, AbstractNode entry) {
        this.mapperManager = mapperManager;
        this.entry = entry;
        if (entry instanceof TableNode) {
            TableNode node = (TableNode) entry;
            if (node.eContainer() != null && node.eContainer() instanceof InputTable) {
                InputTable table = (InputTable) node.eContainer();
                content = table.getName() + "." + node.getName();
            }
        }
    }

    @Override
    public String getContent() {
        return content + " ";
    }

    @Override
    public int getCursorPosition() {
        return content.length() + 1;
    }

    @Override
    public String getLabel() {
        return content;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        String separator = " - "; //$NON-NLS-1$
        if (entry instanceof TableNode) {
            TableNode inputEntry = (TableNode) entry;
            IMetadataColumn metadataColumn = null;
            if (inputEntry.eContainer() instanceof InputTable) {
                InputTable table = (InputTable) inputEntry.eContainer();
                List<IODataComponent> inputs = mapperManager.getMapperComponent().getIODataComponents().getInputs();
                IMetadataTable metaTable = null;
                for (int i = 0; i < inputs.size(); i++) {
                    IODataComponent ioDataComponent = inputs.get(i);
                    if (table.getName() != null && table.getName().equals(ioDataComponent.getConnection().getName())) {
                        metaTable = ioDataComponent.getTable();
                        break;
                    }
                }
                if (metaTable != null && metaTable.getListColumns() != null) {
                    for (IMetadataColumn column : metaTable.getListColumns()) {
                        if (inputEntry.getName().equals(column.getLabel())) {
                            metadataColumn = column;
                        }
                    }
                }
            }
            sb.append(Messages.getString("EntryContentProposal.metadataColumn")).append(" '").append(inputEntry.getName()) //$NON-NLS-1$ //$NON-NLS-2$
                    .append("' "); //$NON-NLS-1$
            sb.append(Messages.getString("EntryContentProposal.properties")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            sb.append(CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.column")).append(inputEntry.getName()); //$NON-NLS-1$
            sb.append(CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.key")).append(inputEntry.isKey()); //$NON-NLS-1$
            sb.append(CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.type")).append(format(inputEntry.getType())); //$NON-NLS-1$
            if (metadataColumn != null) {
                sb.append(CARRIAGE_RETURN);
                sb.append(separator).append(Messages.getString("EntryContentProposal.length")); //$NON-NLS-1$
                if (metadataColumn.getLength() != null && metadataColumn.getLength() > 0) {
                    sb.append(format(metadataColumn.getLength()));
                }

                sb.append(CARRIAGE_RETURN);
                sb.append(separator).append(Messages.getString("EntryContentProposal.precision")); //$NON-NLS-1$
                if (metadataColumn.getPrecision() != null && metadataColumn.getPrecision() > 0) {
                    sb.append(format(metadataColumn.getPrecision()));
                }
                sb.append(CARRIAGE_RETURN);
                sb.append(separator)
                        .append(Messages.getString("EntryContentProposal.default")).append(format(metadataColumn.getDefault())); //$NON-NLS-1$
                sb.append(CARRIAGE_RETURN);
                sb.append(separator)
                        .append(Messages.getString("EntryContentProposal.comment")).append(format(metadataColumn.getComment())); //$NON-NLS-1$
            }
            sb.append(CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.expressionKey")); //$NON-NLS-1$
            sb.append(CARRIAGE_RETURN);
            sb.append(format(entry.getExpression()));
            sb.append(CARRIAGE_RETURN);
        }
        return sb.toString();
    }

    public String format(Object object) {
        if (object == null) {
            return ""; //$NON-NLS-1$
        }
        return String.valueOf(object);
    }
}
