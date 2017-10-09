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
package org.talend.hadoop.distribution.spark;

import java.util.List;

import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.hadoop.distribution.ESparkVersion;

/**
 * Utility class to deal with the Spark versions.
 */
public class SparkVersionUtil {

    public static boolean isSparkCustom(boolean useLocalMode, String sparkLocalVersion, String distributionName) {
        if (useLocalMode) {
            return "CUSTOM".equals(sparkLocalVersion); //$NON-NLS-1$
        } else {
            return "CUSTOM".equals(distributionName); //$NON-NLS-1$
        }
    }

    public static ESparkVersion getSparkAPIVersion(String apiVersion) {
        if ("SPARK_130".equals(apiVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_3;
        }
        if ("SPARK_140".equals(apiVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_4;
        }
        if ("SPARK_150".equals(apiVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_5;
        }
        if ("SPARK_160".equals(apiVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_6;
        }
        if ("SPARK_200".equals(apiVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_2_0;
        }
        return null;
    }

    public static ESparkVersion getSparkVersion(String version) {
        if ("SPARK_1_3_0".equals(version)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_3;
        }
        if ("SPARK_1_4_0".equals(version)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_4;
        }
        if ("SPARK_1_5_0".equals(version)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_5;
        }
        if ("SPARK_1_6_0".equals(version)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_6;
        }
        if ("SPARK_2_0_0".equals(version)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_2_0;
        }
        if ("SPARK_2_1_0".equals(version)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_2_1;
        }
        if ("SPARK_2_2_0".equals(version)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_2_2;
        }
        return null;
    }

    /**
     * 
     * Strongly coupled to a tSparkConfiguration node (in Spark Batch or Streaming), this method returns the
     * {@link ESparkVersion} depending on some parameters value coming from the tSparkConfiguration.
     * 
     * @param useLocalMode - a boolean that defines whether the tSparkConfiguration is configured in local mode.
     * @param sparkLocalVersion - the Spark version selected when the local mode set to true.
     * @param sparkDistributionName - the name of the distribution when the local mode is set to false.
     * @param sparkApiVersion - the Spark version selected when the custom mode is chosen.
     * @param sparkVersion - the Spark version selected in the tSparkConfiguration when the custom mode is not chosen.
     * @return the {@link ESparkVersion} corresponding to the different parameters.
     */
    public static ESparkVersion getSparkVersion(boolean useLocalMode, String sparkLocalVersion, String sparkDistributionName,
            String sparkApiVersion, String sparkVersion) {
        // First we check if the Custom mode is chosen, in local mode or not.
        boolean isSparkCustom = isSparkCustom(useLocalMode, sparkLocalVersion, sparkDistributionName);
        if (isSparkCustom) {
            // If the Custom mode is chosen, then we return the ESparkVersion corresponding to the sparkApiVersion
            // parameter.
            return getSparkAPIVersion(sparkApiVersion);
        }
        // Second, we wheck if the local mode is set to true
        if (useLocalMode) {
            // If the local mode is chosen, then we return the ESparkVersion corresponding to the sparkLocalVersion
            // parameter.
            return getSparkVersion(sparkLocalVersion);
        }

        // Then, we check the Spark Version corresponding to SparkComponent given in parameter.
        return getSparkVersion(sparkVersion);
    }

    /**
     * @param element An element that can contain Spark {@link IElementParameter}, and which can be used to determine
     * the spark version.
     * @return A spark version if all of the parameters are present in that element, or null otherwise.
     */
    private static ESparkVersion getSparkVersionFromElementParameters(IElement element) {
        IElementParameter sparkLocalModeParameter = element.getElementParameter("SPARK_LOCAL_MODE"); //$NON-NLS-1$
        IElementParameter sparkLocalVersionParameter = element.getElementParameter("SPARK_LOCAL_VERSION"); //$NON-NLS-1$
        IElementParameter sparkCustomVersionParameter = element.getElementParameter("SPARK_API_VERSION"); //$NON-NLS-1$
        IElementParameter distributionParameter = element.getElementParameter("DISTRIBUTION"); //$NON-NLS-1$
        IElementParameter supportedVersionParameter = element.getElementParameter("SUPPORTED_SPARK_VERSION"); //$NON-NLS-1$
        if (sparkLocalModeParameter != null && sparkLocalVersionParameter != null && sparkCustomVersionParameter != null
                && distributionParameter != null) {
            boolean isLocalMode = (Boolean) sparkLocalModeParameter.getValue();
            String sparkLocalVersion = (String) sparkLocalVersionParameter.getValue();
            String sparkCustomVersion = (String) sparkCustomVersionParameter.getValue();
            String distribution = (String) distributionParameter.getValue();
            String supportedVersion = (String) supportedVersionParameter.getValue();

            return getSparkVersion(isLocalMode, sparkLocalVersion, distribution, sparkCustomVersion, supportedVersion);
        }
        return null;
    }

    /**
     * This method returns the {@link ESparkVersion} depending on some parameters value coming from the
     * tSparkConfiguration in a job.
     * 
     * @param node Either a Spark configuration node direction (which is the most effective), or any node in a spark
     * process that can be used to find a Spark configuration node.
     * @return A spark version if one can be found from the node, or the process associated with the node. Otherwise
     * null.
     */
    public static ESparkVersion getSparkVersion(INode node) {
        return getSparkVersion(node, true);

    }

    /**
     * @param node Either a Spark configuration node direction (which is the most effective), or any node in a spark
     * process that can be used to find a Spark configuration node.
     * @param fallbackOnProcess If a spark version can't be found in the node, and this is true, check over any node in
     * the entire process associated with the node.
     * @return A spark version if one can be found from the node, or the process associated with the node. Otherwise
     * null.
     */
    private static ESparkVersion getSparkVersion(INode node, boolean fallbackOnProcess) {
        // Try to get the version directly from the node.
        ESparkVersion sparkVersion = getSparkVersionFromElementParameters(node);
        if (sparkVersion != null) {
            return sparkVersion;
        }

        // Otherwise, either return null or check any compatible node in the process.
        return fallbackOnProcess ? getSparkVersion(node.getProcess()) : null;
    }

    /**
     * This method returns the {@link ESparkVersion} depending on some parameters value coming from the
     * tSparkConfiguration in a job.
     * 
     * @param process a job that contains spark configuration information.
     * @return A spark version if one can be found from the process. Otherwise null.
     */
    public static ESparkVersion getSparkVersion(IProcess process) {
        if (process == null) {
            return null;
        }

        // Try to get the version directly from the node.
        ESparkVersion sparkVersion = getSparkVersionFromElementParameters(process);
        if (sparkVersion != null) {
            return sparkVersion;
        }

        // Try to get the version from any compatible node in the process.
        List<? extends INode> sparkConfigs = process.getNodesOfType("tSparkConfiguration"); //$NON-NLS-1$
        if (sparkConfigs != null && sparkConfigs.size() > 0) {
            return getSparkVersion(sparkConfigs.get(0), false);
        }
        return null;
    }
}
