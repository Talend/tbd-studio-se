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

import org.talend.designer.maven.aether.IDynamicMonitor;
import org.talend.designer.maven.aether.node.ExclusionNode;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.ExclusionBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicExclusionAdapter extends AbstractDynamicAdapter {

    private ExclusionBean exclusionBean;

    private ExclusionNode exclusion;

    public DynamicExclusionAdapter(TemplateBean templateBean, DynamicConfiguration configuration, ExclusionBean exclusionBean) {
        super(templateBean, configuration);
        this.exclusionBean = exclusionBean;
    }

    public void adapt(IDynamicMonitor monitor) throws Exception {
        DynamicDistributionUtils.checkCancelOrNot(monitor);
        resolve();

        exclusion = new ExclusionNode();
        exclusion.setArtifactId(exclusionBean.getArtifactId());
        exclusion.setClassifier(exclusionBean.getClassifier());
        exclusion.setExtension(exclusionBean.getExtension());
        exclusion.setGroupId(exclusionBean.getGroupId());

    }

    @Override
    protected void resolve() throws Exception {
        if (isResolved()) {
            return;
        }

        TemplateBean templateBean = getTemplateBean();

        String artifactId = (String) DynamicDistributionUtils.calculate(templateBean, exclusionBean.getArtifactId());
        String classifier = (String) DynamicDistributionUtils.calculate(templateBean, exclusionBean.getClassifier());
        String extension = (String) DynamicDistributionUtils.calculate(templateBean, exclusionBean.getExtension());
        String groupId = (String) DynamicDistributionUtils.calculate(templateBean, exclusionBean.getGroupId());

        exclusionBean.setArtifactId(artifactId);
        exclusionBean.setClassifier(classifier);
        exclusionBean.setExtension(extension);
        exclusionBean.setGroupId(groupId);

        setResolved(true);
    }

    public ExclusionNode getExclusionNode() {
        return exclusion;
    }

}
