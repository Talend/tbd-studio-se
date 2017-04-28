package org.talend.repository.hadoopcluster.ui.conf;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by hwang on 2015年5月28日 Detailled comment
 *
 */
public class ClouderaNavigatorOptionPage extends AbstractHadoopImportConfsPage {

    private Button navigator_autocommitBtn;
    
    private Button navigator_disable_sslBtn;
    
    private Button navigator_die_on_errorBtn;
    
    private LabelledText navigator_usernameText;
    
    private LabelledText navigator_passwordText;
    
    private LabelledText navigator_urlText;
    
    private LabelledText navigator_metatata_urlText;
    
    private LabelledText navigator_client_urlText;
    
    private HadoopClusterConnectionItem connectionItem;
    
    private boolean creation;
    
    public ClouderaNavigatorOptionPage(HadoopClusterConnectionItem connectionItem,boolean creation) {
        super("ClouderaNavigatorOptionPage", null); //$NON-NLS-1$
        setTitle(Messages.getString("ClouderaNavigatorOptionPage.title")); //$NON-NLS-1$
        setDescription(Messages.getString("ClouderaNavigatorOptionPage.title")); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.creation = creation;
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = createParentComposite(parent);

        addNavigatorFields(container);

        addListener();
        init();

        setControl(container);
        setPageComplete(true);
    }

    private void addListener() {
        ButtonSelectionListener buttonSelectionListener = new ButtonSelectionListener();
        navigator_autocommitBtn.addSelectionListener(buttonSelectionListener);
        navigator_disable_sslBtn.addSelectionListener(buttonSelectionListener);
        navigator_die_on_errorBtn.addSelectionListener(buttonSelectionListener);
    }

    class ButtonSelectionListener extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            updateWizardButtons();
        }
    }

    private void init() {
        HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
        navigator_usernameText.setText(connection.getClouderaNaviUserName());
        navigator_passwordText.setText(connection.getClouderaNaviPassword());
        navigator_urlText.setText(connection.getClouderaNaviUrl());
        navigator_metatata_urlText.setText(connection.getClouderaNaviMetadataUrl());
        navigator_client_urlText.setText(connection.getClouderaNaviClientUrl());
        navigator_autocommitBtn.setSelection(connection.isClouderaAutoCommit());
        navigator_disable_sslBtn.setSelection(connection.isClouderaDisableSSL());
        if(!creation){
            navigator_die_on_errorBtn.setSelection(connection.isClouderaDieNoError());
        }
    }

    private void addNavigatorFields(Composite parent) {
      Group naviGroup = Form.createGroup(parent, 1, Messages.getString("HadoopClusterForm.navigatorSettings"), 110); //$NON-NLS-1$
      naviGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      Composite naviPartComposite = new Composite(naviGroup, SWT.NULL);
      GridLayout authPartLayout = new GridLayout(1, false);
      authPartLayout.marginWidth = 0;
      authPartLayout.marginHeight = 0;
      naviPartComposite.setLayout(authPartLayout);
      naviPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      Composite naviCommonComposite = new Composite(naviPartComposite, SWT.NULL);
      GridLayout naviCommonCompLayout = new GridLayout(4, false);
      naviCommonCompLayout.marginWidth = 0;
      naviCommonCompLayout.marginHeight = 0;
      naviCommonComposite.setLayout(naviCommonCompLayout);
      naviCommonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      navigator_usernameText = new LabelledText(naviCommonComposite,
          Messages.getString("HadoopClusterForm.text.navigator_username"), 1); //$NON-NLS-1$
        navigator_passwordText = new LabelledText(naviCommonComposite,
                Messages.getString("HadoopClusterForm.text.navigator_password"), 1, SWT.PASSWORD | SWT.BORDER); //$NON-NLS-1$
      navigator_urlText = new LabelledText(naviCommonComposite,
          Messages.getString("HadoopClusterForm.text.navigator_url"), 1); //$NON-NLS-1$
      navigator_metatata_urlText = new LabelledText(naviCommonComposite,
          Messages.getString("HadoopClusterForm.text.navigator_metadata_url"), 1); //$NON-NLS-1$
      navigator_client_urlText = new LabelledText(naviCommonComposite,
          Messages.getString("HadoopClusterForm.text.navigator_client_url"), 1); //$NON-NLS-1$

      Composite naviKeytabComposite = new Composite(naviGroup, SWT.NULL);
      GridLayout naviKeytabCompLayout = new GridLayout(2, false);
      naviKeytabCompLayout.marginWidth = 0;
      naviKeytabCompLayout.marginHeight = 0;
      naviKeytabComposite.setLayout(naviKeytabCompLayout);
      naviKeytabComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      navigator_autocommitBtn = new Button(naviKeytabComposite, SWT.CHECK);
      navigator_autocommitBtn.setText(Messages.getString("HadoopClusterForm.button.navigator_autocommit")); //$NON-NLS-1$
      navigator_autocommitBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
  
      navigator_disable_sslBtn = new Button(naviKeytabComposite, SWT.CHECK);
      navigator_disable_sslBtn.setText(Messages.getString("HadoopClusterForm.button.navigator_disable_ssl")); //$NON-NLS-1$
      navigator_disable_sslBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
  
      navigator_die_on_errorBtn = new Button(naviKeytabComposite, SWT.CHECK);
      navigator_die_on_errorBtn.setText(Messages.getString("HadoopClusterForm.button.navigator_die_on_error")); //$NON-NLS-1$
      navigator_die_on_errorBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
      navigator_die_on_errorBtn.setSelection(true);
    }

    @Override
    public IWizardPage getNextPage() {
        IWizardPage nextPage = super.getNextPage();
        return nextPage;
    }

    private void updateWizardButtons() {
        IWizardContainer container = getContainer();
        container.updateButtons();
    }

    @Override
    public boolean isLastPage() {
        return true;
    }
    public void finish(){
        HadoopClusterConnection connection = (HadoopClusterConnection) connectionItem.getConnection();
        connection.setClouderaNaviUserName(navigator_usernameText.getText());
        connection.setClouderaNaviPassword(navigator_passwordText.getText());
        connection.setClouderaNaviUrl(navigator_urlText.getText());
        connection.setClouderaNaviClientUrl(navigator_client_urlText.getText());
        connection.setClouderaNaviMetadataUrl(navigator_metatata_urlText.getText());
        connection.setClouderaAutoCommit(navigator_autocommitBtn.getSelection());
        connection.setClouderaDisableSSL(navigator_disable_sslBtn.getSelection());
        connection.setClouderaDieNoError(navigator_die_on_errorBtn.getSelection());
    }
}
