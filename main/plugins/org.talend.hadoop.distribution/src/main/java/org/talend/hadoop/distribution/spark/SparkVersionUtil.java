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
package org.talend.hadoop.distribution.spark;

import java.util.List;

import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.component.SparkComponent;

/**
 * Utility class to deal with the Spark versions.
 */
public class SparkVersionUtil {

    private static boolean isSparkCustom(boolean useLocalMode, String sparkLocalVersion, String distributionName) {
        if (useLocalMode) {
            return "CUSTOM".equals(sparkLocalVersion); //$NON-NLS-1$
        } else {
            return "CUSTOM".equals(distributionName); //$NON-NLS-1$
        }
    }

    private static ESparkVersion getSparkVersion(SparkComponent sc) {
        if (sc != null) {
            return sc.getSparkVersion();
        }
        return null;
    }

    private static ESparkVersion getSparkAPIVersion(String apiVersion) {
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

    private static ESparkVersion getSparkLocalVersion(String localVersion) {
        if ("SPARK_1_3_0".equals(localVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_3;
        }
        if ("SPARK_1_4_0".equals(localVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_4;
        }
        if ("SPARK_1_5_0".equals(localVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_5;
        }
        if ("SPARK_1_6_0".equals(localVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_1_6;
        }
        if ("SPARK_2_0_0".equals(localVersion)) { //$NON-NLS-1$
            return ESparkVersion.SPARK_2_0;
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
     * @param sc - the SparkComponent created from the distribution and the version selected in the tSparkConfiguration.
     * @return the {@link ESparkVersion} corresponding to the different parameters.
     */
    public static ESparkVersion getSparkVersion(boolean useLocalMode, String sparkLocalVersion, String sparkDistributionName,
            String sparkApiVersion, SparkComponent sc) {
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
            return getSparkLocalVersion(sparkLocalVersion);
        }

        // Then, we check the Spark Version corresponding to SparkComponent given in parameter.
        return getSparkVersion(sc);
    }

    /**
     * This method returns the {@link ESparkVersion} depending on some parameters value coming from the
     * tSparkConfiguration.
     * 
     * @param sparkConfig Either a Spark configuration node direction (which is the most effective), or any node in a
     * spark process that can be used to find a Spark configuration node.
     * @return the {@link ESparkVersion} corresponding to the different parameters.
     */
    public static ESparkVersion getSparkVersion(INode sparkConfig) {
        IElementParameter sparkLocalModeParameter = sparkConfig.getElementParameter("SPARK_LOCAL_MODE"); //$NON-NLS-1$
        IElementParameter sparkLocalVersionParameter = sparkConfig.getElementParameter("SPARK_LOCAL_VERSION"); //$NON-NLS-1$
        IElementParameter sparkCustomVersionParameter = sparkConfig.getElementParameter("SPARK_API_VERSION"); //$NON-NLS-1$
        IElementParameter distributionParameter = sparkConfig.getElementParameter("DISTRIBUTION"); //$NON-NLS-1$
        IElementParameter versionParameter = sparkConfig.getElementParameter("SPARK_VERSION"); //$NON-NLS-1$
        if (sparkLocalModeParameter != null && sparkLocalVersionParameter != null && sparkCustomVersionParameter != null
                && distributionParameter != null && versionParameter != null) {
            boolean isLocalMode = (Boolean) sparkLocalModeParameter.getValue();
            String sparkLocalVersion = (String) sparkLocalVersionParameter.getValue();
            String sparkCustomVersion = (String) sparkCustomVersionParameter.getValue();
            String distribution = (String) distributionParameter.getValue();
            String version = (String) versionParameter.getValue();

            SparkComponent sparkDistrib = null;
            try {
                sparkDistrib = (SparkComponent) DistributionFactory.buildDistribution(distribution, version);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }

            return getSparkVersion(isLocalMode, sparkLocalVersion, distribution, sparkCustomVersion, sparkDistrib);
        }

        // Try to get the version from any compatible node in the process.
        List<? extends INode> sparkConfigs = sparkConfig.getProcess().getNodesOfType("tSparkConfiguration"); //$NON-NLS-1$
        if (sparkConfigs != null && sparkConfigs.size() > 0 && sparkConfig != sparkConfigs.get(0)) {
            return getSparkVersion(sparkConfigs.get(0));
        }
        return null;
    }

    /**
     * This method returns the {@link ESparkVersion} depending on some parameters value coming from the
     * tSparkConfiguration.
     * 
     * @param sparkConfig Either a Spark configuration node direction (which is the most effective), or any node in a
     * spark process that can be used to find a Spark configuration node.
     * @return the {@link ESparkVersion} corresponding to the different parameters.
     */
    public static ESparkVersion getSparkVersion(IProcess process) {
        IElementParameter sparkLocalModeParameter = process.getElementParameter("SPARK_LOCAL_MODE"); //$NON-NLS-1$
        IElementParameter sparkLocalVersionParameter = process.getElementParameter("SPARK_LOCAL_VERSION"); //$NON-NLS-1$
        IElementParameter sparkCustomVersionParameter = process.getElementParameter("SPARK_API_VERSION"); //$NON-NLS-1$
        IElementParameter distributionParameter = process.getElementParameter("DISTRIBUTION"); //$NON-NLS-1$
        IElementParameter versionParameter = process.getElementParameter("SPARK_VERSION"); //$NON-NLS-1$
        if (sparkLocalModeParameter != null && sparkLocalVersionParameter != null && sparkCustomVersionParameter != null
                && distributionParameter != null && versionParameter != null) {
            boolean isLocalMode = (Boolean) sparkLocalModeParameter.getValue();
            String sparkLocalVersion = (String) sparkLocalVersionParameter.getValue();
            String sparkCustomVersion = (String) sparkCustomVersionParameter.getValue();
            String distribution = (String) distributionParameter.getValue();
            String version = (String) versionParameter.getValue();

            SparkComponent sparkDistrib = null;
            try {
                sparkDistrib = (SparkComponent) DistributionFactory.buildDistribution(distribution, version);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }

            return getSparkVersion(isLocalMode, sparkLocalVersion, distribution, sparkCustomVersion, sparkDistrib);
        }

        // Try to get the version from any compatible node in the process.
        List<? extends INode> sparkConfigs = process.getNodesOfType("tSparkConfiguration"); //$NON-NLS-1$
        if (sparkConfigs != null && sparkConfigs.size() > 0) {
            return getSparkVersion(sparkConfigs.get(0));
        }
        return null;
    }
}
