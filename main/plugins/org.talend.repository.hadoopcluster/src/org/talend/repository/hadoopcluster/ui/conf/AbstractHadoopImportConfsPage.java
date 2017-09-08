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
package org.talend.repository.hadoopcluster.ui.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;
import org.talend.repository.hadoopcluster.ui.AbstractCheckedComposite;
import org.talend.repository.hadoopcluster.ui.ICheckListener;

/**
 * created by ycbai on 2015年5月29日 Detailled comment
 *
 */

public abstract class AbstractHadoopImportConfsPage extends WizardPage implements IImportConfsWizardPage {

    protected IRetrieveConfsService confsService;

    protected DistributionVersion distriVersion;

    private List<String> filterProperties;

    protected AbstractHadoopImportConfsPage(String pageName, DistributionVersion distriVersion) {
        super(pageName);
        this.distriVersion = distriVersion;
        filterProperties = new ArrayList<>(getInitFilterProperties());
    }

    protected Composite createParentComposite(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 5;
        layout.marginHeight = 5;
        container.setLayout(layout);
        return container;
    }

    protected void createFilterPropertiesTable(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);
        container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        HadoopConfFilterPropertiesDialog propertiesDialog = new HadoopConfFilterPropertiesDialog(getShell()) {

            @Override
            public void applyProperties(List<Map<String, Object>> properties) {
                if (properties != null && properties.size() > 0) {
                    filterProperties = convertProperties(this, properties);
                }
            }

        };
        propertiesDialog.setInitProperties(getInitFilterProperties(propertiesDialog));
        propertiesDialog.createPropertiesFields(container);
    }

    @Override
    public void applyFilter() {
        if (getConfsService() != null) {
            getConfsService().applyFilter(filterProperties);
        }
    }

    private List<String> convertProperties(HadoopConfFilterPropertiesDialog propertiesDialog, List<Map<String, Object>> properties) {
        List<String> filterProps = new ArrayList<>();
        String propertiesKeyName = propertiesDialog.getPropertiesKeyName();
        for (Map<String, Object> propMap : properties) {
            filterProps.add(String.valueOf(propMap.get(propertiesKeyName)));
        }
        return filterProps;
    }

    private List<Map<String, Object>> getInitFilterProperties(HadoopConfFilterPropertiesDialog propertiesDialog) {
        List<Map<String, Object>> initProperties = new ArrayList<>();
        String propertiesKeyName = propertiesDialog.getPropertiesKeyName();
        for (String prop : getInitFilterProperties()) {
            Map<String, Object> propMap = new HashMap<>();
            propMap.put(propertiesKeyName, prop);
            initProperties.add(propMap);
        }
        return initProperties;
    }

    protected Set<String> getInitFilterProperties() {
        Set<String> fProps = new HashSet<>();
        fProps.add("net.topology.script.file.name"); //$NON-NLS-1$
        fProps.add("hbase.rpc.controllerfactory.class"); //$NON-NLS-1$
        fProps.add("hive.exec.failure.hooks"); //$NON-NLS-1$
        fProps.add("hive.exec.post.hooks"); //$NON-NLS-1$
        fProps.add("hive.exec.pre.hooks"); //$NON-NLS-1$
        return fProps;
    }

    protected void addCheckListener(AbstractCheckedComposite checkedComposite) {
        ICheckListener checkListener = new ICheckListener() {

            @Override
            public void checkPerformed(AbstractCheckedComposite source) {
                if (source.isStatusOnError()) {
                    setErrorMessage(source.getStatus());
                    updatePageStatus();
                } else {
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                    updatePageStatus();
                }
            }
        };
        checkedComposite.setListener(checkListener);
    }

    protected void updatePageStatus() {
        setPageComplete(getErrorMessage() == null);
    }

    @Override
    public boolean isLastPage() {
        return true;
    }

    @Override
    public IRetrieveConfsService getConfsService() {
        return confsService;
    }

    @Override
    public List<String> getSelectedServices() {
        return null;
    }

    @Override
    public boolean isSupportCreateServiceConnection() {
        if (distriVersion != null) {
            return distriVersion.doSupportCreateServiceConnection();
        }
        return true;
    }

    @Override
    public List<String> getNecessaryServiceName() {
        if (distriVersion != null) {
            return distriVersion.getNecessaryServiceName();
        }
        return null;
    }
}
