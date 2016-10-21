package org.talend.repository.hadoopcluster.ui.conf;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.formtools.LabelledDirectoryField;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.conf.RetrieveLocalConfsService;
import org.talend.repository.hadoopcluster.i18n.Messages;

/**
 * 
 * created by ycbai on 2015年5月28日 Detailled comment
 *
 */
public class HadoopImportLocalOptionPage extends AbstractHadoopImportConfsPage {

    private LabelledDirectoryField locationDirField;

    private HadoopServicesTableComposite servicesTableComp;
    
    DistributionVersion distributionVersion;

    public HadoopImportLocalOptionPage(DistributionVersion distributionVersion) {
        super("HadoopImportLocalOptionPage"); //$NON-NLS-1$
        this.distributionVersion = distributionVersion;
        setTitle(Messages.getString("HadoopImportLocalOptionPage.title")); //$NON-NLS-1$
        setDescription(Messages.getString("HadoopImportLocalOptionPage.desc")); //$NON-NLS-1$
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = createParentComposite(parent);

        Composite pathComp = new Composite(container, SWT.NONE);
        pathComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout pathCompLayout = new GridLayout(3, false);
        pathCompLayout.marginWidth = 0;
        pathCompLayout.marginHeight = 0;
        pathComp.setLayout(pathCompLayout);

        locationDirField = new LabelledDirectoryField(pathComp, Messages.getString("HadoopImportLocalOptionPage.location.label")); //$NON-NLS-1$ 

        Composite servicesComp = new Composite(container, SWT.NONE);
        servicesComp.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout servicesCompLayout = new GridLayout(2, false);
        servicesCompLayout.marginWidth = 0;
        servicesCompLayout.marginHeight = 0;
        servicesComp.setLayout(servicesCompLayout);

        servicesTableComp = new HadoopServicesTableComposite(servicesComp, SWT.NONE);
        servicesTableComp.setLayoutData(new GridData(GridData.FILL_BOTH));
        addCheckListener(servicesTableComp);

        createFilterPropertiesTable(container);

        addListener();

        setControl(container);
        setPageComplete(false);
    }

    private void addListener() {
        locationDirField.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                confsService = new RetrieveLocalConfsService(locationDirField.getText(), distributionVersion);
                try {
                    servicesTableComp.setServices(confsService.getAllServices());
                } catch (Exception ex) {
                    ExceptionHandler.process(ex);
                }
            }
        });
    }

    @Override
    public List<String> getSelectedServices() {
        return servicesTableComp.getSelectedServices();
    }

}
