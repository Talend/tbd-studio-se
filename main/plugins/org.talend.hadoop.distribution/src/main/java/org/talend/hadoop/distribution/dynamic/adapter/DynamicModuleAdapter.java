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
package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Priority;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.maven.MavenUrlHelper;
import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.designer.maven.aether.node.ExclusionNode;
import org.talend.hadoop.distribution.HadoopDistributionPlugin;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.VersionNotFoundException;
import org.talend.hadoop.distribution.dynamic.bean.ExclusionBean;
import org.talend.hadoop.distribution.dynamic.bean.ModuleBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.hadoop.distribution.i18n.Messages;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class DynamicModuleAdapter extends AbstractDynamicAdapter {

    public static final String TAG_NAME = "libraryNeeded"; //$NON-NLS-1$

    public static final String ATTR_CONTEXT = "context"; //$NON-NLS-1$

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public static final String ATTR_NAME = "name"; //$NON-NLS-1$

    public static final String ATTR_MVN_URI = "mvn_uri"; //$NON-NLS-1$

    public static final String ATTR_URI_PATH = "uripath"; //$NON-NLS-1$

    public static final String ATTR_BUNDLE_ID = "bundleID"; //$NON-NLS-1$

    public static final String ATTR_REQUIRED = "required"; //$NON-NLS-1$

    public static final String ATTR_MESSAGE = "message"; //$NON-NLS-1$

    public static final String ATTR_LANGUAGE = "language"; //$NON-NLS-1$

    public static final String ATTR_EXCLUDE_DEPENDENCIES = "excludeDependencies"; //$NON-NLS-1$

    public static final String ATTR_TEMP_USE_STUDIO_REPOSITORY = "useStudioRepository"; //$NON-NLS-1$

    private ModuleBean moduleBean;

    private IDependencyResolver dependencyResolver;

    private List<String> runtimeIds;

    private Set<String> registedModules;

    private Map<String, ModuleNeeded> existingModuleMap;

    public DynamicModuleAdapter(TemplateBean templateBean, DynamicConfiguration configuration, ModuleBean bean,
            IDependencyResolver resolver, Set<String> registedModules) {
        super(templateBean, configuration);
        this.moduleBean = bean;
        this.dependencyResolver = resolver;
        this.runtimeIds = new ArrayList<>();
        this.registedModules = registedModules;
        this.existingModuleMap = HadoopDistributionPlugin.getInstance().getExistingModuleMap();
    }

    public List<IDynamicConfiguration> adapt(IDynamicMonitor monitor) throws Exception {
        DynamicDistributionUtils.checkCancelOrNot(monitor);
        resolve();
        if (monitor != null) {
            String mvnUri = moduleBean.getMvnUri();
            if (StringUtils.isEmpty(mvnUri)) {
                mvnUri = getMvnUri();
            }
            monitor.setTaskName(
                    Messages.getString("DynamicModuleAdapter.monitor.buildModule", moduleBean.getId(), mvnUri)); //$NON-NLS-1$
        }

        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();
        String distribution = configuration.getDistribution();
        String hadoopVersion = configuration.getVersion();
        String id = configuration.getId();

        List<IDynamicConfiguration> librariesNeeded = new ArrayList<>();

        String type = moduleBean.getType();
        if (ModuleBean.TYPE_BASE.equalsIgnoreCase(type)) {
            String groupId = moduleBean.getGroupId();
            String artifactId = moduleBean.getArtifactId();
            String scope = moduleBean.getScope();
            String classifier = moduleBean.getClassifier();
            String moduleVersion = moduleBean.getVersion();
            String useStudioRepository = moduleBean.getUseStudioRepository();
            if (StringUtils.isNotEmpty(useStudioRepository)) {
                ExceptionHandler.process(
                        new Exception("Currently useStudioRepository is only supported by STANDARD type, will be ignored"), //$NON-NLS-1$
                        Priority.WARN);
            }
            boolean useLatest = Boolean.valueOf(moduleBean.getUseLatest());

            List<ExclusionBean> exclusionBeans = moduleBean.getExclusions();
            List<ExclusionNode> exclusions = null;
            if (exclusionBeans != null && !exclusionBeans.isEmpty()) {
                exclusions = adaptExclusions(exclusionBeans, monitor);
            }
            DependencyNode dependencyNode = null;
            DependencyNode baseNode = new DependencyNode();
            baseNode.setGroupId(groupId);
            baseNode.setArtifactId(artifactId);
            baseNode.setClassifier(classifier);
            baseNode.setScope(scope);
            if (useLatest) {
                moduleVersion = dependencyResolver.getLatestVersion(groupId, artifactId, null, null, monitor);
            }
            baseNode.setVersion(moduleVersion);
            if (exclusions != null && !exclusions.isEmpty()) {
                baseNode.setExclusions(exclusions);
            }
            try {
                dependencyNode = dependencyResolver.collectDependencies(baseNode, monitor);
                librariesNeeded = createLibrariesNeeded(dependencyNode, distribution, hadoopVersion, id, moduleBean, runtimeIds,
                        templateBean);
            } catch (VersionNotFoundException e) {
                ExceptionHandler.process(e);
            }
        } else if (ModuleBean.TYPE_REFERENCE.equalsIgnoreCase(type)) {
            List<ExclusionBean> exclusions = moduleBean.getExclusions();
            if (exclusions != null && !exclusions.isEmpty()) {
                throw new UnsupportedOperationException(
                        Messages.getString("DynamicModuleAdapter.exception.exclusion.unsupport", type)); //$NON-NLS-1$
            }

            String jarName = moduleBean.getJarName();
            ModuleNeeded moduleNeeded = existingModuleMap.get(jarName);
            if (moduleNeeded == null) {
                throw new UnsupportedOperationException(
                        Messages.getString("DynamicModuleAdapter.exception.reference.notFound", jarName)); //$NON-NLS-1$
            }
            runtimeIds.add(moduleNeeded.getId());
        } else if (ModuleBean.TYPE_STANDARD.equalsIgnoreCase(type)) {
            List<ExclusionBean> exclusions = moduleBean.getExclusions();
            if (exclusions != null && !exclusions.isEmpty()) {
                throw new UnsupportedOperationException(
                        Messages.getString("DynamicModuleAdapter.exception.exclusion.unsupport", type)); //$NON-NLS-1$
            }

            String beanId = moduleBean.getId();
            String runtimeId = DynamicDistributionUtils.getPluginKey(distribution, hadoopVersion, id, beanId);
            if (!registedModules.contains(runtimeId)) {
                IDynamicConfiguration libraryNeeded = createLibraryNeeded(moduleBean);
                libraryNeeded.setAttribute(ATTR_ID, runtimeId);
                librariesNeeded.add(libraryNeeded);
                List<String> registedRuntimeIds = new ArrayList<>();
                registedRuntimeIds.add(runtimeId);
                registedModules.add(runtimeId);
            }

            runtimeIds.add(runtimeId);
        }

        return librariesNeeded;
    }

    private List<IDynamicConfiguration> createLibrariesNeeded(DependencyNode node, String distribution, String version, String id,
            ModuleBean bean, List<String> runtimeIds, TemplateBean templateBean) {
        List<IDynamicConfiguration> librariesNeeded = new ArrayList<>();
        if (node == null) {
            return librariesNeeded;
        }

        List<String> registedRuntimeIds = new ArrayList<>();
        String registedModulesKey = getRegistedModulesKey(node);
        String jarName = node.getJarName();
        String runtimeId = DynamicDistributionUtils.getPluginKey(distribution, version, id, jarName);
        if (!registedModules.contains(registedModulesKey)) {
            ModuleNeeded moduleNeeded = null;
            if (reuseExistingJars()) {
                moduleNeeded = existingModuleMap.get(jarName);
            }
            if (moduleNeeded != null) {
                runtimeId = moduleNeeded.getId();
            } else {
                IDynamicConfiguration libraryNeeded = createLibraryNeeded(node, templateBean);
                libraryNeeded.setAttribute(ATTR_CONTEXT, bean.getContext());
                libraryNeeded.setAttribute(ATTR_ID, runtimeId);
                String useStudioRepository = bean.getUseStudioRepository();
                if (StringUtils.isNotEmpty(useStudioRepository)) {
                    libraryNeeded.setAttribute(ATTR_TEMP_USE_STUDIO_REPOSITORY, useStudioRepository);
                } else {
                    libraryNeeded.removeAttribute(ATTR_TEMP_USE_STUDIO_REPOSITORY);
                }
                librariesNeeded.add(libraryNeeded);
            }
            registedModules.add(registedModulesKey);
        }

        registedRuntimeIds.add(runtimeId);

        List<DependencyNode> dependencies = node.getDependencies();
        if (dependencies != null) {
            for (DependencyNode dependency : dependencies) {
                List<IDynamicConfiguration> childLibrariesNeeded = createLibrariesNeeded(dependency, distribution, version, id,
                        bean, registedRuntimeIds, templateBean);
                librariesNeeded.addAll(childLibrariesNeeded);
            }
        }

        runtimeIds.addAll(registedRuntimeIds);

        return librariesNeeded;
    }

    private IDynamicConfiguration createLibraryNeeded(DependencyNode node, TemplateBean templateBean) {
        IDynamicConfiguration libraryNeeded = null;

        libraryNeeded = DynamicFactory.getInstance().createDynamicConfiguration();
        libraryNeeded.setConfigurationName(TAG_NAME);

        String jarname = node.getJarName();
        String mvnUri = DynamicDistributionUtils.getMvnUrl(node);

        libraryNeeded.setAttribute(ATTR_NAME, jarname);
        libraryNeeded.setAttribute(ATTR_MVN_URI, mvnUri);
        libraryNeeded.setAttribute(ATTR_EXCLUDE_DEPENDENCIES, Boolean.TRUE.toString());

        return libraryNeeded;
    }

    public static IDynamicConfiguration createLibraryNeeded(ModuleBean bean) {
        IDynamicConfiguration libraryNeeded = null;

        libraryNeeded = DynamicFactory.getInstance().createDynamicConfiguration();
        libraryNeeded.setConfigurationName(TAG_NAME);

        if (bean != null) {
            String bundleID = bean.getBundleID();
            String context = bean.getContext();
            String id = bean.getId();
            String jarName = bean.getJarName();
            String language = bean.getLanguage();
            String message = bean.getMessage();
            String mvnUri = bean.getMvnUri();
            String required = bean.getRequired();
            String uriPath = bean.getUriPath();
            String excludeDependencies = bean.getExcludeDependencies();
            String useStudioRepository = bean.getUseStudioRepository();

            libraryNeeded.setAttribute(ATTR_BUNDLE_ID, bundleID);
            libraryNeeded.setAttribute(ATTR_CONTEXT, context);
            libraryNeeded.setAttribute(ATTR_ID, id);
            libraryNeeded.setAttribute(ATTR_NAME, jarName);
            libraryNeeded.setAttribute(ATTR_LANGUAGE, language);
            libraryNeeded.setAttribute(ATTR_MESSAGE, message);
            libraryNeeded.setAttribute(ATTR_MVN_URI, mvnUri);
            libraryNeeded.setAttribute(ATTR_REQUIRED, required);
            libraryNeeded.setAttribute(ATTR_URI_PATH, uriPath);
            if (StringUtils.isNotEmpty(excludeDependencies)) {
                libraryNeeded.setAttribute(ATTR_EXCLUDE_DEPENDENCIES, excludeDependencies);
            } else {
                libraryNeeded.setAttribute(ATTR_EXCLUDE_DEPENDENCIES, Boolean.TRUE.toString());
            }
            if (StringUtils.isNotEmpty(useStudioRepository)) {
                libraryNeeded.setAttribute(ATTR_TEMP_USE_STUDIO_REPOSITORY, useStudioRepository);
            } else {
                libraryNeeded.removeAttribute(ATTR_TEMP_USE_STUDIO_REPOSITORY);
            }
        }

        return libraryNeeded;
    }

    private List<ExclusionNode> adaptExclusions(List<ExclusionBean> exclusionBeans, IDynamicMonitor monitor) throws Exception {
        List<ExclusionNode> exclusionNodes = new ArrayList<>();

        if (exclusionBeans != null && !exclusionBeans.isEmpty()) {
            TemplateBean templBean = getTemplateBean();
            DynamicConfiguration dynamicConfiguration = getConfiguration();
            for (ExclusionBean exclusionBean : exclusionBeans) {
                DynamicExclusionAdapter adapter = new DynamicExclusionAdapter(templBean, dynamicConfiguration, exclusionBean);
                adapter.adapt(monitor);
                exclusionNodes.add(adapter.getExclusionNode());
            }
        }

        return exclusionNodes;
    }

    @Override
    protected void resolve() throws Exception {
        if (isResolved()) {
            return;
        }

        TemplateBean templateBean = getTemplateBean();
        String artifactId = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getArtifactId());
        String bundleID = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getBundleID());
        String context = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getContext());
        String classifier = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getClassifier());
        String groupId = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getGroupId());
        String language = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getLanguage());
        String id = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getId());
        String jarName = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getJarName());
        String message = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getMessage());
        String mvnUri = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getMvnUri());
        String required = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getRequired());
        String scope = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getScope());
        String type = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getType());
        String uriPath = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getUriPath());
        String version = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getVersion());
        String useLatest = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getUseLatest());
        String excludeDependencies = (String) DynamicDistributionUtils.calculate(templateBean,
                moduleBean.getExcludeDependencies());
        String useStudioRepository = (String) DynamicDistributionUtils.calculate(templateBean,
                moduleBean.getUseStudioRepository());

        moduleBean.setArtifactId(artifactId);
        moduleBean.setBundleID(bundleID);
        moduleBean.setContext(context);
        moduleBean.setClassifier(classifier);
        moduleBean.setGroupId(groupId);
        moduleBean.setLanguage(language);
        moduleBean.setId(id);
        moduleBean.setJarName(jarName);
        moduleBean.setMessage(message);
        moduleBean.setMvnUri(mvnUri);
        moduleBean.setScope(scope);
        moduleBean.setRequired(required);
        moduleBean.setType(type);
        moduleBean.setUriPath(uriPath);
        moduleBean.setVersion(version);
        moduleBean.setUseLatest(useLatest);
        moduleBean.setExcludeDependencies(excludeDependencies);
        moduleBean.setUseStudioRepository(useStudioRepository);

        setResolved(true);
    }

    private String getRegistedModulesKey(DependencyNode node) {
        return getRegistedModulesKey(node.getGroupId(), node.getArtifactId(), node.getVersion(), node.getClassifier());
    }

    private String getRegistedModulesKey(String groupId, String artifactId, String version, String classifier) {
        return groupId + "/" + artifactId + "/" + version + "/" + classifier; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private String getMvnUri() {
        String mvnUri = null;
        try {
            String repository = null;
            String group = moduleBean.getGroupId();
            String artifact = moduleBean.getArtifactId();
            String vertion = moduleBean.getVersion();
            String classifier = moduleBean.getClassifier();
            if (StringUtils.isEmpty(repository)) {
                repository = null;
            }
            if (StringUtils.isEmpty(group)) {
                group = null;
            }
            if (StringUtils.isEmpty(artifact)) {
                artifact = null;
            }
            if (StringUtils.isEmpty(vertion)) {
                vertion = null;
            }
            if (StringUtils.isEmpty(classifier)) {
                classifier = null;
            }
            mvnUri = MavenUrlHelper.generateMvnUrl(repository, group, artifact, vertion, null, classifier);
        } catch (Exception e) {
            // don't care
        }
        return mvnUri;
    }

    public List<String> getRuntimeIds() {
        return this.runtimeIds;
    }

    public void setRuntimeIds(List<String> runtimeIds) {
        this.runtimeIds = runtimeIds;
    }

    /**
     * Seems can't use existing jars, for example: <br/>
     * If we want to reuse jackson-jaxrs-1.8.8.jar, the existing mvn uri for it is:<br/>
     * mvn_uri="mvn:org.talend.libraries/jackson-jaxrs-1.8.8/6.0.0", <br/>
     * The following dependency will be added into pom.xml:<br/>
     * groupId: org.talend.libraries<br/>
     * artifactId: jackson-jaxrs<br/>
     * version: 1.8.8<br/>
     * <br>
     * While jackson-jaxrs-1.8.8.jar may be also used by other artifact. so the official dependency of
     * jackson-jaxrs-1.8.8.jar will be also added into pom.xml:<br>
     * groupId: org.codehaus.jackson<br/>
     * artifactId: jackson-jaxrs<br/>
     * version: 1.8.8<br/>
     * <br>
     * If in this case, studio can't get jar of org.codehaus.jackson/jackson-jaxrs<br/>
     * <br/>
     * So we need to forbid to reuse existing jars, Unless we disable offline option in studio maven configuration.
     */
    private boolean reuseExistingJars() {
        return false;
    }
}
