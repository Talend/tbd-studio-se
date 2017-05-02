package org.talend.repository.hadoopcluster.ui.conf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.hadoopcluster.conf.IPropertyConstants;
import org.talend.repository.hadoopcluster.conf.RetrieveRemoteConfsService;
import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurationManager;
import org.talend.repository.hadoopcluster.configurator.HadoopConfigurator;
import org.talend.repository.hadoopcluster.configurator.IRetrieveConfsJobService;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;

/**
 * 
 * created by ycbai on 2015年5月28日 Detailled comment
 *
 */
public class HadoopImportRemoteOptionPage extends AbstractHadoopImportConfsPage implements PropertyChangeListener {

    private DistributionBean distribution;
    private  DistributionVersion distriVersion;

    private Combo clustersCombo;

    private Button selectClusterButton;

    private HadoopServicesTableComposite servicesTableComp;

    private HadoopConfigurator configurator;

    private AbstractConnectionForm connectionForm;

    public HadoopImportRemoteOptionPage(DistributionBean distribution, DistributionVersion distriVersion) {
        super("HadoopImportRemoteOptionPage", distriVersion); //$NON-NLS-1$
        setTitle(Messages.getString("HadoopImportRemoteOptionPage.title")); //$NON-NLS-1$
        this.distribution = distribution;
        this.distriVersion = distriVersion;
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = createParentComposite(parent);

        addConnectionFields(container);
        addClusterFields(container);
        createFilterPropertiesTable(container);

        addListener();

        setControl(container);
        setPageComplete(false);
    }

    private void addListener() {
        selectClusterButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    IRetrieveConfsJobService retrieveJobServer = connectionForm.getRetrieveJobService();
                    if (retrieveJobServer != null) {
                        retrieveJobServer.setDistributionVersion(distriVersion);
                        retrieveJobServer.setSelectedCluster(clustersCombo.getText());
                        confsService = (IRetrieveConfsService) retrieveJobServer;
                    } else if (configurator != null) {
                        HadoopCluster cluster = configurator.getCluster(clustersCombo.getText());
                        confsService = new RetrieveRemoteConfsService(cluster);
                    }
                    servicesTableComp.setServices(confsService.getAllServices());
                } catch (Exception ex) {
                    ExceptionHandler.process(ex);
                }
            }
        });
    }

    private void addConnectionFields(Composite parent) {
        HadoopConfigurationManager configurationManager = HadoopConfsUtils.getConfigurationManager(distribution);
        if (HadoopConfigurationManager.AMBARI.equals(configurationManager)) {
            connectionForm = new ABRConnectionForm(parent, SWT.NONE);
            setDescription(Messages.getString("HadoopImportRemoteOptionPage.desc.abr")); //$NON-NLS-1$
        } else if (HadoopConfigurationManager.CLOUDERA_MANAGER.equals(configurationManager)) {
            connectionForm = new CMConnectionForm(parent, SWT.NONE);
            setDescription(Messages.getString("HadoopImportRemoteOptionPage.desc.cm")); //$NON-NLS-1$
        }
        if (connectionForm != null) {
            connectionForm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            connectionForm.addPropertyChangeListener(this);
        }
    }

    private void addClusterFields(Composite parent) {
        Group clusterGroup = Form.createGroup(parent, 2, Messages.getString("HadoopImportRemoteOptionPage.group.clusters")); //$NON-NLS-1$
        clusterGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

        clustersCombo = new Combo(clusterGroup, SWT.BORDER | SWT.READ_ONLY);
        clustersCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        selectClusterButton = new Button(clusterGroup, SWT.PUSH);
        GridData selectClusterBtnGD = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
        selectClusterButton.setLayoutData(selectClusterBtnGD);
        selectClusterButton.setText(Messages.getString("HadoopImportRemoteOptionPage.button.fetch")); //$NON-NLS-1$

        servicesTableComp = new HadoopServicesTableComposite(clusterGroup, SWT.NONE, distriVersion);
        GridData servicesTableGD = new GridData(GridData.FILL_BOTH);
        servicesTableGD.horizontalSpan = 2;
        servicesTableComp.setLayoutData(servicesTableGD);

        addCheckListener(servicesTableComp);
    }

    @Override
    public List<String> getSelectedServices() {
        return servicesTableComp.getSelectedServices();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        setErrorMessage(null);
        Exception exception = null;
        String propertyName = evt.getPropertyName();
        Object newValue = evt.getNewValue();
        if (IPropertyConstants.PROPERTY_CONNECT.equals(propertyName)) {
            if (newValue instanceof HadoopConfigurator) {
                configurator = (HadoopConfigurator) newValue;
                try {
                    List<String> clusters = configurator.getAllClusters();
                    if (clusters != null && clusters.size() > 0) {
                        clustersCombo.setItems(clusters.toArray(new String[0]));
                        clustersCombo.select(0);
                    }
                } catch (Exception e) {
                    exception = e;
                }
            } else if (newValue instanceof Exception) {
                exception = (Exception) newValue;
            }
        }

        if (IPropertyConstants.PROPERTY_RETRIEVE_JOB.equals(propertyName)) {
            if (newValue instanceof IRetrieveConfsJobService) {
                IRetrieveConfsJobService retrieveConfigJobService = (IRetrieveConfsJobService) newValue;
                List<String> clusters = retrieveConfigJobService.getAllClusterName();
                clustersCombo.setItems(clusters.toArray(new String[0]));
                clustersCombo.select(0);
            } else if (newValue instanceof Exception) {
                exception = (Exception) newValue;
            }
        }

        if (exception != null) {
            setErrorMessage(Messages.getString("HadoopImportRemoteOptionPage.connectionFailed")); //$NON-NLS-1$
            ExceptionHandler.process(exception);
        }
    }
}
