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
package org.talend.designer.bigdata.problems;

import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.core.model.components.ComponentCategory;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.Problem.ProblemStatus;
import org.talend.designer.bigdata.i18n.Messages;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.nodes.NodeProblem;
import org.talend.designer.core.ui.views.problems.Problems;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.MapRStreamsComponent;

/**
 * This class displays a red cross on the standard jobs, when a tMapRStreamsCreateStream is connected with a
 * tMapRStreamsConnection while the latter is configured with a distribution not supporting the MapR Streams admin API.
 */
public class MapRStreamsCreateStreamProblem implements NodeProblem {

    private static final String MAPRSTREAMS_COMPONENTS = "tMapRStreamsCreateStream"; //$NON-NLS-1$

    @Override
    public boolean needsCheck(Node node) {
        ComponentCategory cat = ComponentCategory.getComponentCategoryFromName(node.getComponent().getType());
        if (ComponentCategory.CATEGORY_4_DI == cat) {
            String currentComponentName = node.getComponent().getName();
            if (MAPRSTREAMS_COMPONENTS.equals(currentComponentName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void check(Node node) {
        String currentComponentName = node.getComponent().getName();
        if (MAPRSTREAMS_COMPONENTS.equals(currentComponentName)) {
            INode tMapRStreamsConnection = ElementParameterParser.getLinkedNodeValue(node, "__CONNECTION__"); //$NON-NLS-1$
            if (tMapRStreamsConnection != null) {
                IElementParameter distributionParameter = tMapRStreamsConnection.getElementParameter("DISTRIBUTION"); //$NON-NLS-1$
                IElementParameter versionParameter = tMapRStreamsConnection.getElementParameter("MAPRSTREAMS_VERSION"); //$NON-NLS-1$
                if (distributionParameter != null && versionParameter != null) {
                    String distribution = (String) distributionParameter.getValue();
                    String version = (String) versionParameter.getValue();
                    try {
                        HadoopComponent hadoopDistrib = DistributionFactory.buildDistribution(distribution, version);
                        if (distributionDoesNotSupportMapRStreamsAdminAPI(hadoopDistrib)) {
                            Problems.add(ProblemStatus.ERROR, node,
                                    Messages.getString("Node.checkMapRStreamsCreateStreamVersion")); //$NON-NLS-1$;
                        }
                    } catch (java.lang.Exception e) {
                        CommonExceptionHandler.process(e);
                    }
                }

            }
        }
    }

    private boolean distributionDoesNotSupportMapRStreamsAdminAPI(HadoopComponent hadoopDistrib) {
        if (hadoopDistrib instanceof MapRStreamsComponent) {
            return ((MapRStreamsComponent) hadoopDistrib).canCreateMapRStream() ? false : true;
        } else {
            return true;
        }
    }
}
