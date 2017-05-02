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
package org.talend.repository.hadoopcluster.ui.conf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.AbstractCheckedComposite;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;

/**
 * created by ycbai on 2015年5月28日 Detailled comment
 *
 */
public class HadoopServicesTableComposite extends AbstractCheckedComposite {

    private CheckboxTableViewer servicesTableViewer;

    private Button selectAllBtn;

    private Button deselectAllBtn;
    
    private DistributionVersion distriVersion;

    public HadoopServicesTableComposite(Composite parent, int style, DistributionVersion distriVersion) {
        super(parent, style);
        this.distriVersion = distriVersion;
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        this.setLayout(layout);
        createControl();
    }

    private void createControl() {
        servicesTableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER);
        servicesTableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
        servicesTableViewer.setLabelProvider(new ServiceLabelProvider());
        servicesTableViewer.setContentProvider(new ArrayContentProvider());

        Composite buttonComposite = new Composite(this, SWT.NONE);
        buttonComposite.setLayoutData(new GridData(GridData.BEGINNING));
        GridLayout buttonCompLayout = new GridLayout(2, false);
        buttonCompLayout.marginWidth = 0;
        buttonCompLayout.marginHeight = 0;
        buttonComposite.setLayout(buttonCompLayout);

        selectAllBtn = new Button(buttonComposite, SWT.PUSH);
        selectAllBtn.setText(Messages.getString("HadoopServicesTableComposite.button.selectAll")); //$NON-NLS-1$
        deselectAllBtn = new Button(buttonComposite, SWT.PUSH);
        deselectAllBtn.setText(Messages.getString("HadoopServicesTableComposite.button.deselectAll")); //$NON-NLS-1$

        addListener();
        checkServices();
    }

    private void checkServices() {
        boolean hasServiceSelected = servicesTableViewer.getCheckedElements().length > 0;
        if (hasServiceSelected || !isSupportCreateServiceConnection()) {
            updateStatus(IStatus.OK, null);
        } else {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopServicesTableComposite.checkServiceSelected")); //$NON-NLS-1$
        }
    }

    private void addListener() {
        servicesTableViewer.addCheckStateListener(new ICheckStateListener() {

            @Override
            public void checkStateChanged(CheckStateChangedEvent event) {
                checkServices();
            }
        });
        selectAllBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doSelectAll();
            }
        });
        deselectAllBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                doDeselectAll();
            }
        });
    }

    private void doSelectAll() {
        servicesTableViewer.setAllChecked(true);
        checkServices();
    }

    private void doDeselectAll() {
        servicesTableViewer.setAllChecked(false);
        checkServices();
    }

    public void setServices(List<String> services) {
        if (isSupportCreateServiceConnection()) {
            servicesTableViewer.setInput(services);
            doSelectAll();
        }
        checkServices();
    }

    public List<String> getSelectedServices() {
        if (isSupportCreateServiceConnection()) {
            List<String> selectedServices = new ArrayList<>();
            Object[] checkedElements = servicesTableViewer.getCheckedElements();
            for (Object ele : checkedElements) {
                selectedServices.add(String.valueOf(ele));
            }
            return selectedServices;
        }
        return getNecessaryServiceName();
    }

    public class ServiceLabelProvider extends LabelProvider {

        @Override
        public Image getImage(Object object) {
            return ImageProvider.getImage(EHadoopClusterImage.HADOOPCLUSTER_RESOURCE_ICON);
        }

        @Override
        public String getText(Object object) {
            return object.toString();
        }
    }
    
    private boolean isSupportCreateServiceConnection() {
        if (distriVersion != null) {
            return distriVersion.doSupportCreateServiceConnection();
        }
        return true;
    }

    private List<String> getNecessaryServiceName() {
        if (distriVersion != null) {
            return distriVersion.getNecessaryServiceName();
        }
        return null;
    }
}
