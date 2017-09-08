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
package org.talend.oozie.scheduler.ui;

import java.util.HashMap;
import java.util.List;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.talend.core.model.process.IProcess2;
import org.talend.designer.core.model.components.EOozieParameterName;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.oozie.scheduler.constants.TOozieUIConstants;
import org.talend.oozie.scheduler.utils.TOozieParamUtils;
import org.talend.oozie.scheduler.views.OozieJobTrackerListener;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for Talend Oozie Scheduler setup dialog.
 */
public class TOozieSettingDialog extends Dialog {

    public static final String REPOSITORY = "Repository";

    public static final String BUILT_IN = "Built-in";

    private String hadoopDistributionValue;

    private String hadoopVersionValue;

    private String nameNodeEndPointValue;

    private String jobTrackerEndPointValue;

    private String oozieEndPointValue;

    private boolean useYarn;

    private String authMode;

    private String userNameValue;

    private String group;

    private String customJars;

    private OozieSettingComposite settingComposite;

    private String repositoryId;

    private boolean enableKerberos;

    private boolean enableOoKerberos;

    private String principalValue;

    private boolean useKeytab;

    private String ktPrincipal;

    private String keytab;

    private List<HashMap<String, Object>> propertiesValue;

    private HashMap<String, Object> propertyMap = new HashMap<String, Object>();

    private String propertyTypeValue;

    /**
     * @param parentShell
     */
    public TOozieSettingDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(this.getShellStyle() | SWT.RESIZE);
    }

    @Override
    protected void configureShell(Shell parentShell) {
        super.configureShell(parentShell);
        parentShell.setText(TOozieUIConstants.OOZIE_DLG_SETTING_TITLE);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        settingComposite = new OozieSettingComposite(parent, SWT.BORDER, this) {

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.oozie.scheduler.ui.OozieSettingComposite#preInitialization ()
             */
            @Override
            protected void preInitialization() {
                super.preInitialization();
                setPropertyType(propertyTypeValue);
                setNameNodeEndPointValue(nameNodeEndPointValue);
                setJobTrackerEndPointValue(jobTrackerEndPointValue);
                setOozieEndPointValue(oozieEndPointValue);
                setUserNameValue(userNameValue);
                setUseYarn(useYarn);
                setAuthMode(authMode);
                setGroup(group);
                setCustomJars(customJars);
                setRepositoryId(repositoryId);
                setEnableKerberos(enableKerberos);
                setEnableOoKerberos(enableOoKerberos);
                setPrincipal(principalValue);
                setUseKeytab(useKeytab);
                setKtPrincipal(ktPrincipal);
                setKeytab(keytab);
                setProperties(propertiesValue);
            }

            @Override
            protected void createContents(Composite parent) {
                super.createContents(parent);
                setHadoopDistributionValue(hadoopDistributionValue);
                setHadoopVersionValue(hadoopVersionValue);
                initUI();
            }
        };

        return parent;
    }

    public String getHadoopDistributionValue() {

        return this.hadoopDistributionValue;

    }

    public HashMap<String, Object> getPropertyMap() {
        return propertyMap;
    }

    public void setHadoopDistributionValue(String hadoopDistributionValue) {
        propertyMap.put(EOozieParameterName.OOZIE_HADOOP_DISTRIBUTION.getName(), hadoopDistributionValue);
        this.hadoopDistributionValue = hadoopDistributionValue;
    }

    public String getHadoopVersionValue() {
        return this.hadoopVersionValue;
    }

    public void setHadoopVersionValue(String hadoopVersionValue) {
        propertyMap.put(EOozieParameterName.OOZIE_HADOOP_VERSION.getName(), hadoopVersionValue);
        this.hadoopVersionValue = hadoopVersionValue;
    }

    public String getNameNodeEndPointValue() {
        return settingComposite.getNameNodeEndPointValue();
    }

    public void setNameNodeEndPointValue(String nameNodeEndPointValue) {
        propertyMap.put(EOozieParameterName.OOZIE_NAME_NODE_END_POINT.getName(), nameNodeEndPointValue);
        this.nameNodeEndPointValue = nameNodeEndPointValue;
    }

    public String getJobTrackerEndPointValue() {
        return settingComposite.getJobTrackerEndPointValue();
    }

    public void setJobTrackerEndPointValue(String jobTrackerEndPointValue) {
        propertyMap.put(EOozieParameterName.OOZIE_JOB_TRACKER_ENDPOINT.getName(), jobTrackerEndPointValue);
        this.jobTrackerEndPointValue = jobTrackerEndPointValue;
    }

    public String getOozieEndPointValue() {
        return settingComposite.getOozieEndPointValue();
    }

    public void setOozieEndPointValue(String oozieEndPointValue) {
        propertyMap.put(EOozieParameterName.OOZIE_END_POINT.getName(), oozieEndPointValue);
        this.oozieEndPointValue = oozieEndPointValue;
    }

    public String getUserNameValue() {
        return settingComposite.getUserNameValue();
    }

    public void setUserNameValue(String userNameValue) {
        propertyMap.put(EOozieParameterName.OOZIE_USERNAME.getName(), userNameValue);
        this.userNameValue = userNameValue;
    }

    public String getCustomJars() {
        return settingComposite.getCustomJars();
    }

    public void setCustomJars(String customJars) {
        this.customJars = customJars;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryId() {
        return settingComposite.getRepositoryId();
    }

    public boolean isEnableKerberos() {
        return settingComposite.isEnableKerberos();
    }

    public void setEnableKerberos(boolean enableKerberos) {
        propertyMap.put(EOozieParameterName.OOZIE_ENABLE_KERBEROS.getName(), enableKerberos);
        this.enableKerberos = enableKerberos;
    }

    /**
     * Getter for enableOoKerberos.
     * 
     * @return the enableOoKerberos
     */
    public boolean isEnableOoKerberos() {
        return settingComposite.isEnableOoKerberos();
    }

    /**
     * Sets the enableOoKerberos.
     * 
     * @param enableOoKerberos the enableOoKerberos to set
     */
    public void setEnableOoKerberos(boolean enableOoKerberos) {
        propertyMap.put(EOozieParameterName.OOZIE_ENABLE_OO_KERBEROS.getName(), enableOoKerberos);
        this.enableOoKerberos = enableOoKerberos;
    }

    public String getPrincipalValue() {
        return settingComposite.getPrincipal();
    }

    public void setPrincipalValue(String principalValue) {
        propertyMap.put(EOozieParameterName.OOZIE_NAME_NODE_PRINCIPAL.getName(), principalValue);
        this.principalValue = principalValue;
    }

    public void setPropertiesValue(List<HashMap<String, Object>> propertiesValue) {
        this.propertiesValue = propertiesValue;
    }

    public List<HashMap<String, Object>> getPropertiesValue() {
        return settingComposite.getProperties();
    }

    /**
     * Getter for group.
     * 
     * @return the group
     */
    public String getGroup() {
        return settingComposite.getGroup();
    }

    /**
     * Sets the group.
     * 
     * @param group the group to set
     */
    public void setGroup(String group) {
        propertyMap.put(EOozieParameterName.OOZIE_GROUP.getName(), group);
        this.group = group;
    }

    /**
     * Getter for useKeytab.
     * 
     * @return the useKeytab
     */
    public boolean isUseKeytab() {
        return settingComposite.isUseKeytab();
    }

    /**
     * Sets the useKeytab.
     * 
     * @param useKeytab the useKeytab to set
     */
    public void setUseKeytab(boolean useKeytab) {
        propertyMap.put(EOozieParameterName.OOZIE_USE_KEYTAB.getName(), useKeytab);
        this.useKeytab = useKeytab;
    }

    /**
     * Getter for ktPrincipal.
     * 
     * @return the ktPrincipal
     */
    public String getKtPrincipal() {
        return settingComposite.getKtPrincipal();
    }

    public OozieSettingComposite getSettingComposite() {
        return settingComposite;
    }

    public void setSettingComposite(OozieSettingComposite settingComposite) {
        this.settingComposite = settingComposite;
    }

    public String getPropertyTypeValue() {
        return this.settingComposite.getPropertyTypeCombo().getText();
    }

    public String getDialogPropertyTypeValue() {
        return this.propertyTypeValue;
    }

    public void setPropertyTypeValue(String propertyTypeValue) {
        propertyMap.put(EOozieParameterName.OOZIE_PROPERTY_TYPENAME.getName(), propertyTypeValue);
        this.propertyTypeValue = propertyTypeValue;
    }

    /**
     * Sets the ktPrincipal.
     * 
     * @param ktPrincipal the ktPrincipal to set
     */
    public void setKtPrincipal(String ktPrincipal) {
        propertyMap.put(EOozieParameterName.OOZIE_KT_PRINCIPAL.getName(), ktPrincipal);
        this.ktPrincipal = ktPrincipal;
    }

    /**
     * Getter for keytab.
     * 
     * @return the keytab
     */
    public String getKeytab() {
        return settingComposite.getKeytab();
    }

    /**
     * Sets the keytab.
     * 
     * @param keytab the keytab to set
     */
    public void setKeytab(String keytab) {
        propertyMap.put(EOozieParameterName.OOZIE_KEY_TAB.getName(), keytab);
        this.keytab = keytab;
    }

    /**
     * Getter for useYarn.
     * 
     * @return the useYarn
     */
    public boolean isUseYarn() {
        return settingComposite.isUseYarn();
    }

    /**
     * Sets the useYarn.
     * 
     * @param useYarn the useYarn to set
     */
    public void setUseYarn(boolean useYarn) {
        this.useYarn = useYarn;
    }

    /**
     * Getter for authMode.
     * 
     * @return the authMode
     */
    public String getAuthMode() {
        return settingComposite.getAuthMode();
    }

    /**
     * Sets the authMode.
     * 
     * @param authMode the authMode to set
     */
    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    @Override
    protected void okPressed() {
        this.setHadoopDistributionValue(settingComposite.getHadoopDistributionValue());
        this.setHadoopVersionValue(settingComposite.getHadoopVersionValue());
        if (BUILT_IN.equals(settingComposite.getPropertyTypeCombo().getText())) {
            this.setPropertyTypeValue(settingComposite.getPropertyTypeCombo().getText());
            this.setEnableKerberos(settingComposite.isEnableKerberos());
            this.setEnableOoKerberos(settingComposite.isEnableOoKerberos());
            this.setGroup(settingComposite.getGroup());
            this.setJobTrackerEndPointValue(settingComposite.getJobTrackerEndPointValue());
            this.setKeytab(settingComposite.getKeytab());
            this.setKtPrincipal(settingComposite.getKtPrincipal());
            this.setNameNodeEndPointValue(settingComposite.getNameNodeEndPointValue());
            this.setOozieEndPointValue(settingComposite.getOozieEndPointValue());
            this.setPrincipalValue(settingComposite.getPrincipal());
            this.setUseKeytab(settingComposite.isUseKeytab());
            this.setUserNameValue(settingComposite.getUserNameValue());

            IProcess2 process = OozieJobTrackerListener.getProcess();
            /*
             * List<ElementParameter> list=(List<ElementParameter>) process.getElementParameters(); for
             * (EOozieParameterName oozieParam : EOozieParameterName.values()) {
             * if(!"REPOSITORY_CONNECTION_ID".equals(oozieParam
             * .getName())&&!"JOBID_FOR_OOZIE".equals(oozieParam.getName(
             * ))&&!"HADOOP_APP_PATH".equals(oozieParam.getName())) { ElementParameter param = new
             * ElementParameter(process); param.setName(oozieParam.getName());
             * param.setDisplayName(oozieParam.getDisplayName()); param.setCategory(EComponentCategory.TECHNICAL);
             * param.setFieldType(EParameterFieldType.TEXT); param.setShow(false); param.setValue("");
             * param.setReadOnly(false); list.add(param); } }
             */

            CompoundCommand command = new CompoundCommand();
            for (EOozieParameterName param : EOozieParameterName.values()) {
                if (!"REPOSITORY_CONNECTION_ID".equals(param.getName()) && !"JOBID_FOR_OOZIE".equals(param.getName())
                        && !"HADOOP_APP_PATH".equals(param.getName())) {
                    PropertyChangeCommand pcc = new PropertyChangeCommand(process, param.getName(), propertyMap.get(param
                            .getName()));
                    command.add(pcc);
                }
            }
            ((org.talend.designer.core.ui.editor.process.Process) process).getCommandStack().execute(command);
            TOozieParamUtils.setBuiltInForOozie(true);
        } else {
            TOozieParamUtils.setBuiltInForOozie(false);
        }
        super.okPressed();
    }

    public void setPropertyType(String propertyTypeValue) {
        propertyMap.put(EOozieParameterName.OOZIE_PROPERTY_TYPENAME.getName(), propertyTypeValue);
        this.propertyTypeValue = propertyTypeValue;

    }

}
