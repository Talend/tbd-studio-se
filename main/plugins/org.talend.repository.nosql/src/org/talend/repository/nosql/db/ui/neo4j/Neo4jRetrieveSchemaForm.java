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
package org.talend.repository.nosql.db.ui.neo4j;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.swt.colorstyledtext.ColorStyledText;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.utils.data.text.IndiceHelper;
import org.talend.core.CorePlugin;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.designer.core.ui.preferences.TalendDesignerPrefConstants;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.db.util.neo4j.Neo4jConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.metadata.NoSQLSchemaUtil;
import org.talend.repository.nosql.ui.common.AbstractNoSQLRetrieveSchemaForm;
import org.talend.repository.nosql.validator.NonemptyValidator;

/**
 * 
 * created by ycbai on Jul 28, 2014 Detailled comment
 * 
 */
public class Neo4jRetrieveSchemaForm extends AbstractNoSQLRetrieveSchemaForm {

    private static final String LANGUAGE = "cypher"; //$NON-NLS-1$

    private MetadataTable table;

    private ColorStyledText colorText;

    protected WizardPage parentWizardPage;

    public Neo4jRetrieveSchemaForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, connectionItem, existingNames, creation, parentWizardPage);
        this.parentWizardPage = parentWizardPage;
        setupForm();
    }

    @Override
    protected void initialize() {
        // Do nothing.
    }

    @Override
    public void restoreCheckItems() {
        List<String> tableLabels = NoSQLSchemaUtil.getAllTableLabels(getConnection());
        Iterator<Entry<String, MetadataTable>> hitTablesIter = hitTablesMap.entrySet().iterator();
        while (hitTablesIter.hasNext()) {
            Entry<String, MetadataTable> tabEntry = hitTablesIter.next();
            if (!tableLabels.contains(tabEntry.getKey())) {
                hitTablesIter.remove();
            }
        }
        if (hitTablesMap.size() == 0) {
            colorText.setText(""); //$NON-NLS-1$
            table = null;
        }
    }

    @Override
    protected void addFields() {
        Composite parentComposite = new Composite(this, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = 5;
        gridLayout.marginHeight = 5;
        parentComposite.setLayout(gridLayout);
        parentComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label descLabel = new Label(parentComposite, SWT.NONE);
        descLabel.setText(Messages.getString("Neo4jRetrieveSchemaForm.desc")); //$NON-NLS-1$

        Group cypherGroup = Form.createGroup(parentComposite, 1, Messages.getString("Neo4jRetrieveSchemaForm.cypher.title")); //$NON-NLS-1$
        IPreferenceStore preferenceStore = CorePlugin.getDefault().getPreferenceStore();
        colorText = new ColorStyledText(cypherGroup, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, preferenceStore, LANGUAGE);
        String fontType = preferenceStore.getString(TalendDesignerPrefConstants.MEMO_TEXT_FONT);
        FontData fontData = new FontData(fontType);
        Font font = new Font(null, fontData);
        colorText.setFont(font);
        colorText.setLayoutData(new GridData(GridData.FILL_BOTH));
        addResourceDisposeListener(colorText, font);
    }

    private void addOrUpdateTable() {
        if (table == null) {
            existingNames = NoSQLSchemaUtil.getAllTableLabels(getConnection());
            table = ConnectionFactory.eINSTANCE.createMetadataTable();
            table.setId(ProxyRepositoryFactory.getInstance().getNextId());
            String tableLabel = IndiceHelper.getIndexedLabel(table.getLabel(), existingNames);
            table.setLabel(tableLabel);
            table.setName(tableLabel);
            NoSQLSchemaUtil.addTable2Connection(getConnection(), table, INeo4jConstants.NEO4J_SCHEMA);
            hitTablesMap.put(tableLabel, table);
        }
        table.getAdditionalProperties().put(INeo4jConstants.CYPHER, colorText.getText());
    }

    @Override
    protected void addFieldsListeners() {
        colorText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                checkFieldsValue();
                addOrUpdateTable();
            }

        });
    }

    @Override
    protected void updateValidatorEntries() {
        super.updateValidatorEntries();
        collectValidateEntry(new NonemptyValidator(colorText.getText()),
                Messages.getString("Neo4jRetrieveSchemaForm.cypher.validator")); //$NON-NLS-1$
    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            Neo4jConnectionUtil.closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

}
