// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic;

import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.hadoop.distribution.i18n.Messages;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class VersionNotFoundException extends Exception {

    private String version;

    private DependencyNode baseNode;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DependencyNode getBaseNode() {
        return this.baseNode;
    }

    public void setBaseNode(DependencyNode baseNode) {
        this.baseNode = baseNode;
    }

    @Override
    public String getMessage() {
        String groupId = ""; //$NON-NLS-1$
        String artifactId = ""; //$NON-NLS-1$
        if (baseNode != null) {
            groupId = baseNode.getGroupId();
            artifactId = baseNode.getArtifactId();
        }
        return Messages.getString("DynamicModuleAdapter.exception.version.notFound", version, groupId, artifactId); //$NON-NLS-1$
    }

}
