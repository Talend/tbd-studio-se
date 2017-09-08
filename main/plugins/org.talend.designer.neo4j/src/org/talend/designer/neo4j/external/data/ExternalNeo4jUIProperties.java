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
package org.talend.designer.neo4j.external.data;

import java.io.Serializable;

import org.eclipse.swt.graphics.Rectangle;

public class ExternalNeo4jUIProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int[] weightsMainSashForm = new int[0];

    public static final int[] DEFAULT_WEITGHTS_MAIN_SASH_FORM = new int[] { 70, 30 };

    private static int[] weightsDatasFlowViewSashForm = new int[0];

    public static final int[] DEFAULT_WEITGHTS_DATAS_FLOW_SASH_FORM = new int[] { 33, 33, 34 };

    private static Rectangle boundsNeo4j = new Rectangle(50, 50, 800, 600);

    private static boolean shellMaximized = false;

    public static int[] getWeightsMainSashForm() {
        return weightsMainSashForm;
    }

    public static void setWeightsMainSashForm(int[] weightsMainSashForm) {
        ExternalNeo4jUIProperties.weightsMainSashForm = weightsMainSashForm;
    }

    public static int[] getWeightsDatasFlowViewSashForm() {
        return weightsDatasFlowViewSashForm;
    }

    public static void setWeightsDatasFlowViewSashForm(int[] weightsDatasFlowViewSashForm) {
        ExternalNeo4jUIProperties.weightsDatasFlowViewSashForm = weightsDatasFlowViewSashForm;
    }

    public static boolean isShellMaximized() {
        return shellMaximized;
    }

    public static void setShellMaximized(boolean shellMaximized) {
        ExternalNeo4jUIProperties.shellMaximized = shellMaximized;
    }

    public static Rectangle getBoundsNeo4j() {
        return boundsNeo4j;
    }

    public static void setBoundsNeo4j(Rectangle boundsRowGon) {
        ExternalNeo4jUIProperties.boundsNeo4j = boundsRowGon;
    }

}
