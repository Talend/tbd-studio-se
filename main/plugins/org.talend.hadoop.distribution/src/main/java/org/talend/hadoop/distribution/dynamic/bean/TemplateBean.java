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
package org.talend.hadoop.distribution.dynamic.bean;

import java.util.List;
import java.util.Map;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class TemplateBean implements IVariable {

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public static final String ATTR_NAME = "name"; //$NON-NLS-1$

    public static final String ATTR_DISTRIBUTION = "distribution"; //$NON-NLS-1$

    public static final String ATTR_DESCRIPTION = "description"; //$NON-NLS-1$

    public static final String ATTR_REPOSITORY = "repository"; //$NON-NLS-1$

    public static final String ATTR_PROPERTIES = "properties"; //$NON-NLS-1$

    public static final String ATTR_SERVICES = "services"; //$NON-NLS-1$

    public static final String ATTR_CONDITIONS = "conditions"; //$NON-NLS-1$

    public static final String ATTR_MODULES = "modules"; //$NON-NLS-1$

    public static final String ATTR_MODULE_GROUPS = "moduleGroups"; //$NON-NLS-1$

    public static final String ATTR_TYPE_MAP = "typeMap"; //$NON-NLS-1$

    public static final String ATTR_COMPONENT_TYPE_MAP = "componentTypeMap"; //$NON-NLS-1$

    public static final String ATTR_BASE_VERSION = "baseVersion"; //$NON-NLS-1$

    public static final String ATTR_TOP_VERSION = "topVersion"; //$NON-NLS-1$

    public static final String ATTR_CLASSLOADERS = "classloaders"; //$NON-NLS-1$

    public static final String ATTR_DYNAMIC_VERSION = "dynamicVersion"; //$NON-NLS-1$

    public static final String ATTR_ADD_REPOSITORY_IN_MVNURI = "addRepositoryInMvnUri"; //$NON-NLS-1$

    private String id;

    private String name;

    private String distribution;

    private String dynamicVersion;

    private String description;

    private String repository;

    private String baseVersion;

    private String topVersion;

    private String addRepositoryInMvnUri;

    private Map<String, String> properties;

    private List<String> services;

    private List<ConditionBean> conditions;

    private List<ModuleBean> modules;

    private List<ModuleGroupBean> moduleGroups;

    private List<TypeMapEntity> typeMap;

    private List<ComponentTypeMapEntity> componentTypeMap;

    private List<ClassLoaderBean> classLoaders;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistribution() {
        return this.distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepository() {
        return this.repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getBaseVersion() {
        return this.baseVersion;
    }

    public void setBaseVersion(String baseVersion) {
        this.baseVersion = baseVersion;
    }

    public String getTopVersion() {
        return this.topVersion;
    }

    public void setTopVersion(String topVersion) {
        this.topVersion = topVersion;
    }

    public String getAddRepositoryInMvnUri() {
        return this.addRepositoryInMvnUri;
    }

    public void setAddRepositoryInMvnUri(String addRepositoryInMvnUri) {
        this.addRepositoryInMvnUri = addRepositoryInMvnUri;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public List<String> getServices() {
        return this.services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<ConditionBean> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<ConditionBean> conditions) {
        this.conditions = conditions;
    }

    public List<ModuleBean> getModules() {
        return this.modules;
    }

    public void setModules(List<ModuleBean> modules) {
        this.modules = modules;
    }

    public List<ModuleGroupBean> getModuleGroups() {
        return this.moduleGroups;
    }

    public void setModuleGroups(List<ModuleGroupBean> moduleGroups) {
        this.moduleGroups = moduleGroups;
    }

    public List<TypeMapEntity> getTypeMap() {
        return this.typeMap;
    }

    public void setTypeMap(List<TypeMapEntity> typeMap) {
        this.typeMap = typeMap;
    }

    public List<ComponentTypeMapEntity> getComponentTypeMap() {
        return this.componentTypeMap;
    }

    public void setComponentTypeMap(List<ComponentTypeMapEntity> componentTypeMap) {
        this.componentTypeMap = componentTypeMap;
    }

    public List<ClassLoaderBean> getClassLoaders() {
        return this.classLoaders;
    }

    public void setClassLoaders(List<ClassLoaderBean> classLoaders) {
        this.classLoaders = classLoaders;
    }

    public String getDynamicVersion() {
        return this.dynamicVersion;
    }

    public void setDynamicVersion(String dynamicVersion) {
        this.dynamicVersion = dynamicVersion;
    }

    @Override
    public Object getVariableValue(String variable) throws Exception {
        Object value = null;
        switch (variable) {
        case ATTR_ADD_REPOSITORY_IN_MVNURI:
            value = getAddRepositoryInMvnUri();
            break;
        case ATTR_BASE_VERSION:
            value = getBaseVersion();
            break;
        case ATTR_CLASSLOADERS:
            value = getClassLoaders();
            break;
        case ATTR_COMPONENT_TYPE_MAP:
            value = getComponentTypeMap();
            break;
        case ATTR_CONDITIONS:
            value = getConditions();
            break;
        case ATTR_DESCRIPTION:
            value = getDescription();
            break;
        case ATTR_DISTRIBUTION:
            value = getDistribution();
            break;
        case ATTR_DYNAMIC_VERSION:
            value = getDynamicVersion();
            break;
        case ATTR_ID:
            value = getId();
            break;
        case ATTR_MODULE_GROUPS:
            value = getModuleGroups();
            break;
        case ATTR_MODULES:
            value = getModules();
            break;
        case ATTR_NAME:
            value = getName();
            break;
        case ATTR_PROPERTIES:
            value = getProperties();
            break;
        case ATTR_REPOSITORY:
            value = getRepository();
            break;
        case ATTR_SERVICES:
            value = getServices();
            break;
        case ATTR_TOP_VERSION:
            value = getTopVersion();
            break;
        case ATTR_TYPE_MAP:
            value = getTypeMap();
            break;

        default:
            throw new UnsupportedOperationException(
                    "Currently don't support to get " + variable + ", please implement it if needed.");
        }
        return value;
    }

}
