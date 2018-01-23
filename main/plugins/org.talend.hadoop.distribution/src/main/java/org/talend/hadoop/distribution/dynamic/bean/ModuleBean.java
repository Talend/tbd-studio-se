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

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class ModuleBean implements IVariable {

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public static final String ATTR_TYPE = "type"; //$NON-NLS-1$

    public static final String ATTR_JAR_NAME = "jarName"; //$NON-NLS-1$

    public static final String ATTR_MVN_URI = "mvnUri"; //$NON-NLS-1$

    public static final String ATTR_GROUP_ID = "groupId"; //$NON-NLS-1$

    public static final String ATTR_ARTIFACT_ID = "artifactId"; //$NON-NLS-1$

    public static final String ATTR_VERSION = "version"; //$NON-NLS-1$

    public static final String ATTR_CLASSIFIER = "classifier"; //$NON-NLS-1$

    public static final String ATTR_CONTEXT = "context"; //$NON-NLS-1$

    public static final String ATTR_URI_PATH = "uripath"; //$NON-NLS-1$

    public static final String ATTR_BUNDLE_ID = "bundleID"; //$NON-NLS-1$

    public static final String ATTR_REQUIRED = "required"; //$NON-NLS-1$

    public static final String ATTR_MESSAGE = "message"; //$NON-NLS-1$

    public static final String ATTR_LANGUAGE = "language"; //$NON-NLS-1$

    public static final String ATTR_SCOPE = "scope"; //$NON-NLS-1$

    public static final String ATTR_EXCLUSIONS = "exclusions"; //$NON-NLS-1$

    public static final String ATTR_EXCLUDE_DEPENDENCIES = "excludeDependencies"; //$NON-NLS-1$

    public static final String ATTR_DESCRIPTION = "description"; //$NON-NLS-1$

    public static final String ATTR_USE_LATETST = "useLatest"; //$NON-NLS-1$

    public static final String ATTR_USE_STUDIO_REPOSITORY = "useStudioRepository"; //$NON-NLS-1$

    public static final String TYPE_STANDARD = "STANDARD"; //$NON-NLS-1$

    public static final String TYPE_BASE = "BASE"; //$NON-NLS-1$

    public static final String TYPE_REFERENCE = "REFERENCE"; //$NON-NLS-1$

    private String id;

    private String type;

    private String jarName;

    private String mvnUri;

    private String groupId;

    private String artifactId;

    private String version;

    private String classifier;

    private String scope;

    private String context;

    private String uriPath;

    private String bundleID;

    private String required;

    private String message;

    private String language;

    private String excludeDependencies;

    private String description;

    private String useLatest;

    private String useStudioRepository;

    private List<ExclusionBean> exclusions;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJarName() {
        return this.jarName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public String getMvnUri() {
        return this.mvnUri;
    }

    public void setMvnUri(String mvnUri) {
        this.mvnUri = mvnUri;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClassifier() {
        return this.classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUriPath() {
        return this.uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public String getBundleID() {
        return this.bundleID;
    }

    public void setBundleID(String bundleID) {
        this.bundleID = bundleID;
    }

    public String getRequired() {
        return this.required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExcludeDependencies() {
        return this.excludeDependencies;
    }

    public void setExcludeDependencies(String excludeDependencies) {
        this.excludeDependencies = excludeDependencies;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExclusionBean> getExclusions() {
        return this.exclusions;
    }

    public void setExclusions(List<ExclusionBean> exclusions) {
        this.exclusions = exclusions;
    }

    public String getUseLatest() {
        return this.useLatest;
    }

    public void setUseLatest(String useLatest) {
        this.useLatest = useLatest;
    }

    public String getUseStudioRepository() {
        return this.useStudioRepository;
    }

    public void setUseStudioRepository(String useStudioRepository) {
        this.useStudioRepository = useStudioRepository;
    }

    @Override
    public Object getVariableValue(String variable) throws Exception {
        Object value = null;
        switch (variable) {
        case ATTR_ARTIFACT_ID:
            value = getArtifactId();
            break;
        case ATTR_BUNDLE_ID:
            value = getBundleID();
            break;
        case ATTR_CONTEXT:
            value = getContext();
            break;
        case ATTR_CLASSIFIER:
            value = getClassifier();
            break;
        case ATTR_DESCRIPTION:
            value = getDescription();
            break;
        case ATTR_EXCLUDE_DEPENDENCIES:
            value = getExcludeDependencies();
            break;
        case ATTR_EXCLUSIONS:
            value = getExclusions();
            break;
        case ATTR_GROUP_ID:
            value = getGroupId();
            break;
        case ATTR_ID:
            value = getId();
            break;
        case ATTR_JAR_NAME:
            value = getJarName();
            break;
        case ATTR_LANGUAGE:
            value = getLanguage();
            break;
        case ATTR_MESSAGE:
            value = getMessage();
            break;
        case ATTR_MVN_URI:
            value = getMvnUri();
            break;
        case ATTR_REQUIRED:
            value = getRequired();
            break;
        case ATTR_SCOPE:
            value = getScope();
            break;
        case ATTR_TYPE:
            value = getType();
            break;
        case ATTR_URI_PATH:
            value = getUriPath();
            break;
        case ATTR_VERSION:
            value = getVersion();
            break;
        case ATTR_USE_LATETST:
            value = getUseLatest();
            break;
        case ATTR_USE_STUDIO_REPOSITORY:
            value = getUseStudioRepository();
            break;
        default:
            throw new UnsupportedOperationException(
                    "Currently don't support to get " + variable + ", please implement it if needed");
        }
        return value;
    }

}
