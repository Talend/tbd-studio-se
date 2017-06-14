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
package org.talend.repository.hadoopcluster.ui.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.hadoop.IHadoopDistributionService;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.hadoop.version.EAuthenticationMode;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.util.EHDFSFieldSeparator;
import org.talend.designer.hdfsbrowse.util.EHDFSRowSeparator;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.metadata.managment.ui.dialog.HadoopPropertiesDialog;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;

/**
 * created by ycbai on 2013-4-2 Detailled comment
 * 
 */
public abstract class AbstractHadoopSubForm<T extends HadoopSubConnection> extends AbstractHadoopForm<T> {

    protected static final int VISIBLE_COMBO_ITEM_COUNT = 5;

    protected LabelledCombo rowSeparatorCombo;

    protected LabelledCombo fieldSeparatorCombo;

    protected Text rowSeparatorText;

    protected Text fieldSeparatorText;

    protected HadoopClusterConnection clusterConnection;

    protected String distribution;

    protected String version;

    protected boolean enableKerberos;

    protected boolean enableGroup;

    protected boolean isHDI;

    private HadoopPropertiesDialog propertiesDialog;

    /**
     * DOC ycbai AbstractHadoopSubForm constructor comment.
     * 
     * @param parent
     * @param style
     */
    protected AbstractHadoopSubForm(Composite parent, int style, ConnectionItem connectionItem) {
        super(parent, style);
        init(connectionItem);
    }

    protected AbstractHadoopSubForm(Composite parent, int style, String[] existingNames, ConnectionItem connectionItem) {
        super(parent, style, existingNames);
        init(connectionItem);
    }

    private void init(ConnectionItem item) {
        setConnectionItem(item);
        clusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(getConnection());
        distribution = clusterConnection.getDistribution();
        version = clusterConnection.getDfVersion();
        enableKerberos = clusterConnection.isEnableKerberos();
        if (!enableKerberos && (isCurrentHadoopVersionSupportGroup()
                || EAuthenticationMode.UGI.getName().equals(clusterConnection.getAuthMode()))) {
            enableGroup = true;
        }
        isHDI = isHDI(clusterConnection);
        setupForm(true);
    }

    private boolean isCurrentHadoopVersionSupportGroup() {
        boolean supportGroup = false;
        DistributionVersion distributionVersion;
        DistributionBean distributionBean = HadoopDistributionsHelper.HADOOP.getDistribution(distribution, false);
        if (distributionBean != null) {
            distributionVersion = distributionBean.getVersion(version, false);
            IHadoopDistributionService hadoopDistributionService = (IHadoopDistributionService) GlobalServiceRegister.getDefault()
                    .getService(IHadoopDistributionService.class);
            if (distributionVersion != null && hadoopDistributionService != null) {
                try {
                    supportGroup = hadoopDistributionService.doSupportMethod(distributionVersion, "doSupportGroup");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return supportGroup;
    }

    private boolean isHDI(HadoopClusterConnection hcConnection) {
        return HCVersionUtil.isHDI(hcConnection);
    }

    protected void addSeparatorFields() {
        Group separatorGroup = Form.createGroup(this, 1, Messages.getString("AbstractHadoopSubForm.separatorSettings")); //$NON-NLS-1$
        separatorGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        ScrolledComposite separatorComposite = new ScrolledComposite(separatorGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        separatorComposite.setExpandHorizontal(true);
        separatorComposite.setExpandVertical(true);
        separatorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite separatorGroupComposite = Form.startNewGridLayout(separatorComposite, 6);
        GridLayout separatorGroupCompLayout = (GridLayout) separatorGroupComposite.getLayout();
        separatorGroupCompLayout.marginHeight = 0;
        separatorGroupCompLayout.marginTop = 0;
        separatorGroupCompLayout.marginBottom = 0;
        separatorGroupCompLayout.marginLeft = 0;
        separatorGroupCompLayout.marginRight = 0;
        separatorGroupCompLayout.marginWidth = 0;
        separatorComposite.setContent(separatorGroupComposite);

        rowSeparatorCombo = new LabelledCombo(separatorGroupComposite, Messages.getString("AbstractHadoopSubForm.rowSeparator"), //$NON-NLS-1$
                Messages.getString("AbstractHadoopSubForm.rowSeparator.tooltip"), EHDFSRowSeparator.getAllRowSeparators(true) //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        rowSeparatorCombo.setVisibleItemCount(VISIBLE_COMBO_ITEM_COUNT);
        rowSeparatorText = new Text(separatorGroupComposite, SWT.BORDER);
        rowSeparatorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        fieldSeparatorCombo = new LabelledCombo(
                separatorGroupComposite,
                Messages.getString("AbstractHadoopSubForm.fieldSeparator"), //$NON-NLS-1$
                Messages.getString("AbstractHadoopSubForm.fieldSeparator.tooltip"), EHDFSFieldSeparator.getAllFieldSeparators(true).toArray(new String[0]), 1, true); //$NON-NLS-1$
        fieldSeparatorCombo.setVisibleItemCount(VISIBLE_COMBO_ITEM_COUNT);
        fieldSeparatorText = new Text(separatorGroupComposite, SWT.BORDER);
        fieldSeparatorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }

    protected void addHadoopPropertiesFields() {
        T connection = getConnection();
        HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
        String hadoopPropertiesOfCluster = StringUtils.trimToEmpty(hcConnection.getHadoopProperties());
        List<Map<String, Object>> hadoopPropertiesListOfCluster = HadoopRepositoryUtil
                .getHadoopPropertiesList(hadoopPropertiesOfCluster);
        propertiesDialog = new HadoopPropertiesDialog(getShell(), hadoopPropertiesListOfCluster, getHadoopProperties()) {

            @Override
            protected boolean isReadOnly() {
                return !isEditable();
            }

            @Override
            protected List<Map<String, Object>> getLatestInitProperties() {
                return getHadoopProperties();
            }

            @Override
            public void applyProperties(List<Map<String, Object>> properties) {
                getConnection().setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(properties));
            }
        };
        propertiesDialog.createPropertiesFields(this);
    }

    protected void updateHadoopPropertiesFields(boolean isEditable) {
        propertiesDialog.updateStatusLabel(getHadoopProperties());
    }

    protected List<Map<String, Object>> getHadoopProperties() {
        String hadoopProperties = getConnection().getHadoopProperties();
        List<Map<String, Object>> hadoopPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties);
        return hadoopPropertiesList;
    }

}
