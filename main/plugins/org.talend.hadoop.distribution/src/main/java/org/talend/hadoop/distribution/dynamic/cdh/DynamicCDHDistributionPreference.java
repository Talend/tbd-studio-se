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
package org.talend.hadoop.distribution.dynamic.cdh;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.model.general.Project;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.core.runtime.projectsetting.ProjectPreferenceManager;
import org.talend.hadoop.distribution.dynamic.IDynamicDistributionPreference;
import org.talend.utils.security.CryptoHelper;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicCDHDistributionPreference implements IDynamicDistributionPreference {

    private static final String PREF_OVERRIDE_DEFAULT_SETUP = "distribution.dynamic.repository.cdh.overrideDefaultSetup"; //$NON-NLS-1$

    private static final boolean PREF_OVERRIDE_DEFAULT_SETUP_DEFAULT = false;

    private static final String PREF_REPOSITORY = "distribution.dynamic.repository.cdh.repository"; //$NON-NLS-1$

    private static final String PREF_REPOSITORY_DEFAULT = "https://talend-update.talend.com/nexus/content/repositories/cdh-releases-rcs/"; //$NON-NLS-1$

    private static final String PREF_ANONYMOUS = "distribution.dynamic.repository.cdh.isAnonymous"; //$NON-NLS-1$

    private static final boolean PREF_ANONYMOUS_DEFAULT = false;

    private static final String PREF_USERNAME = "distribution.dynamic.repository.cdh.username"; //$NON-NLS-1$

    private static final String PREF_USERNAME_DEFAULT = "studio-dl-client"; //$NON-NLS-1$

    private static final String PREF_PASSWORD = "distribution.dynamic.repository.cdh.password"; //$NON-NLS-1$

    private static final String PREF_PASSWORD_DEFAULT = "studio-dl-client"; //$NON-NLS-1$

    private IPreferenceStore prefStore;

    private CryptoHelper cryptoHelper;

    private static Map<String, IDynamicDistributionPreference> projectLabelPreferenceMap = new HashMap<>();

    private DynamicCDHDistributionPreference(ScopedPreferenceStore store) {
        prefStore = store;
        cryptoHelper = CryptoHelper.getDefault();
        initDefaultPreference();
    }

    public static IDynamicDistributionPreference getDynamicDistributionPreference(Project project) throws Exception {
        String projectTechLabel = project.getTechnicalLabel();
        IDynamicDistributionPreference distributionPreference = projectLabelPreferenceMap.get(projectTechLabel);
        if (distributionPreference == null) {
            String qualifier = CoreRuntimePlugin.getInstance().getProjectPreferenceManager().getQualifier();
            IProject iProject = ResourceUtils.getProject(projectTechLabel);
            if (iProject == null) {
                throw new Exception("Can't find project: " + projectTechLabel);
            }
            ProjectScope projectScope = new ProjectScope(iProject);
            ScopedPreferenceStore store = new ScopedPreferenceStore(projectScope, qualifier);
            distributionPreference = new DynamicCDHDistributionPreference(store);
            projectLabelPreferenceMap.put(projectTechLabel, distributionPreference);
        }
        return distributionPreference;
    }

    @Override
    public void initDefaultPreference() {
        prefStore.setDefault(PREF_OVERRIDE_DEFAULT_SETUP, getDefaultOverrideDefaultSetup());
        prefStore.setDefault(PREF_REPOSITORY, getDefaultRepository());
        prefStore.setDefault(PREF_ANONYMOUS, getDefaultIsAnonymous());
        prefStore.setDefault(PREF_USERNAME, getDefaultUsername());
        prefStore.setDefault(PREF_PASSWORD, cryptoHelper.encrypt(getDefaultPassword()));
    }

    @Override
    public boolean overrideDefaultSetup() {
        return prefStore.getBoolean(PREF_OVERRIDE_DEFAULT_SETUP);
    }

    @Override
    public boolean getDefaultOverrideDefaultSetup() {
        return PREF_OVERRIDE_DEFAULT_SETUP_DEFAULT;
    }

    @Override
    public void setOverrideDefaultSetup(boolean override) {
        prefStore.setValue(PREF_OVERRIDE_DEFAULT_SETUP, override);
    }

    @Override
    public String getRepository() {
        return prefStore.getString(PREF_REPOSITORY);
    }

    @Override
    public String getDefaultRepository() {
        return PREF_REPOSITORY_DEFAULT;
    }

    @Override
    public void setRepository(String repository) {
        prefStore.setValue(PREF_REPOSITORY, repository);
    }

    @Override
    public boolean isAnonymous() {
        return prefStore.getBoolean(PREF_ANONYMOUS);
    }

    @Override
    public boolean getDefaultIsAnonymous() {
        return PREF_ANONYMOUS_DEFAULT;
    }

    @Override
    public void setAnonymous(boolean anonymous) {
        prefStore.setValue(PREF_ANONYMOUS, anonymous);
    }

    @Override
    public String getUsername() {
        return prefStore.getString(PREF_USERNAME);
    }

    @Override
    public String getDefaultUsername() {
        return PREF_USERNAME_DEFAULT;
    }

    @Override
    public void setUsername(String username) {
        prefStore.setValue(PREF_USERNAME, username);
    }

    @Override
    public String getPassword() {
        String password = prefStore.getString(PREF_PASSWORD);
        if (StringUtils.isNotEmpty(password)) {
            password = cryptoHelper.decrypt(password);
        }
        return password;
    }

    @Override
    public String getDefaultPassword() {
        return PREF_PASSWORD_DEFAULT;
    }

    @Override
    public void setPassword(String password) {
        if (password == null) {
            password = ""; //$NON-NLS-1$
        }
        prefStore.setValue(PREF_PASSWORD, cryptoHelper.encrypt(password));
    }

    @Override
    public void save() throws Exception {
        ((IPersistentPreferenceStore) prefStore).save();
    }

    @Override
    public String getPreferencePath() {
        ProjectPreferenceManager projectPreferenceManager = CoreRuntimePlugin.getInstance().getProjectPreferenceManager();
        IPath path = projectPreferenceManager.getLocation();
        IProject project = projectPreferenceManager.getProject();
        path = path.makeRelativeTo(project.getLocation());
        return path.toPortableString();
    }

    @Override
    public void clearCache() throws Exception {
        /**
         * DON'T save here, it may bring bugs when switching branches(and even for reference projects), we SHOULD save
         * preference immediately after changed instead of saving here;
         */
        // save();
    }

    public static void clearAllPreferenceCache() throws Exception {
        if (projectLabelPreferenceMap == null || projectLabelPreferenceMap.isEmpty()) {
            return;
        }

        for (IDynamicDistributionPreference preference : projectLabelPreferenceMap.values()) {
            try {
                preference.clearCache();
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }

        projectLabelPreferenceMap.clear();
    }

}
