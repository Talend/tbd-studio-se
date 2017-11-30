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
package org.talend.hadoop.distribution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.ArrayUtils;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.utils.DefaultConfigurationManager;
import org.talend.utils.json.JSONObject;
import org.talend.utils.json.JSONUtil;

/**
 * Base class that describes a Distribution.
 *
 */
public abstract class AbstractDistribution {

    protected static final MultiKeyMap defaultConfigsMap = new MultiKeyMap();

    public abstract String getDistribution();

    public abstract String getVersion();

    public abstract EHadoopVersion getHadoopVersion();

    public boolean isHadoop1() {
        return getHadoopVersion() == EHadoopVersion.HADOOP_1;
    }

    public boolean isHadoop2() {
        return getHadoopVersion() == EHadoopVersion.HADOOP_2;
    }

    public abstract boolean doSupportKerberos();

    /**
     * Mapr ticket is an authentication method only available on MapR.
     */
    public boolean doSupportMapRTicket() {
        return false;
    }

    public boolean doSupportMaprTicketV52API() {
        return false;
    }

    public boolean doSupportGroup() {
        return false;
    }

    public boolean isExecutedThroughWebHCat() {
        return false;
    }

    public boolean isExecutedThroughSparkJobServer() {
        return false;
    }

    public boolean isExecutedThroughLivy() {
        return false;
    }

    public boolean doSupportClouderaNavigator() {
        return false;
    }

    public String getYarnApplicationClasspath() {
        // Not used in Hadoop 1
        return ""; //$NON-NLS-1$
    }

    public boolean doSupportHive1Standalone() {
        return true;
    }

    public boolean doSupportMapRDB() {
        return false;
    }

    // Only used if SparkComponent is implemented
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();
        version.add(ESparkVersion.SPARK_1_3);
        return version;
    }

    public boolean doSupportTezForPig() {
        return false;
    }

    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return null;
    }

    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return new HashSet<>();
    }

    public boolean doSupportOldImportMode() {
        return true;
    }

    public boolean doSupportCustomMRApplicationCP() {
        return false;
    }

    public String getCustomMRApplicationCP() {
        return ""; //$NON-NLS-1$
    }

    public boolean doSupportSparkYarnClusterMode() {
        return true;
    }

    public boolean doSupportS3() {
        return false;
    }

    public boolean doSupportS3V4() {
        return false;
    }

    /**
     * 
     * for Hive
     */
    public boolean doSupportEmbeddedMode() {
        // the embedded mode is not working for TOP
        if (PluginChecker.isOnlyTopLoaded()) { // don't support in TOS for DQ product.
            return false;
        }
        return true;
    }

    /**
     * 
     * for Hive
     */
    public boolean doSupportStandaloneMode() {
        return true;
    }

    /**
     * 
     * load default
     */
    public String getDefaultConfig(String... keys) {
        if (keys != null && keys.length > 0) {
            // check distribution
            final String keyDistr = keys[0];
            final String distribution = getDistribution();
            if (distribution.equals(keyDistr)) {
                final String version = getVersion();
                Object object = defaultConfigsMap.get(distribution, version);
                if (object == null) { // init
                    JSONObject defaultConfig = loadDefaultConfigurations();
                    if (defaultConfig != null) {
                        object = defaultConfig;
                        defaultConfigsMap.put(distribution, version, defaultConfig);
                    }
                }
                if (object instanceof JSONObject) {
                    JSONObject json = (JSONObject) object;
                    String[] keysWithoutDistribution = (String[]) ArrayUtils.remove(keys, 0);
                    if (keysWithoutDistribution.length == 0) {// no key yet,
                        return DefaultConfigurationManager.getValue(json, ""); //$NON-NLS-1$
                    }
                    return DefaultConfigurationManager.getValue(json, keysWithoutDistribution);
                }

            }
        }
        return null;
    }

    protected JSONObject loadDefaultConfigurations() {
        // the class must be AbstractDistribution for load default config in current bundle
        JSONObject globalJson = DefaultConfigurationManager.loadDefaultFile(AbstractDistribution.class, getDistribution()
                .toLowerCase());
        // the class is the version of distribution
        if (getVersion() != null) {
            JSONObject versionJson = DefaultConfigurationManager.loadDefaultFile(this.getClass(), getVersion().toLowerCase());
            return JSONUtil.merge(versionJson, globalJson);
        }
        return globalJson;
    }

    public boolean doSupportSSLwithKerberos() {
        return false;
    }

    public boolean doSupportAtlas() {
        return false;
    }

    public boolean doSupportParquetOutput() {
        return false;
    }

    public int getClouderaNavigatorAPIVersion() {
        // Version 8 is the first version of Cloudera Navigator Supported by SDK
        return 8;
    }

    public String getMapRStreamsJarPath() {
        return null;
    }

    public boolean canCreateMapRStream() {
        return false;
    }

    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
        return SparkStreamingKafkaVersion.KAFKA_0_8;
    }

    public boolean doSupportKerberizedKafka() {
        return false;
    }

    public boolean doSupportHDFSEncryption() {
        return false;
    }

    public boolean doSupportBasicAtlasAuthentification() {
        return false;
    }

    public boolean isImpactedBySqoop2995() {
        return false;
    }

    public boolean doSupportFetchPasswordFromFile() {
        return false;
    }

    public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        return ""; //$NON-NLS-1$
    }

    public boolean isCloudDistribution() {
        return false;
    }

    public boolean useCloudLauncher() {
        return false;
    }

    public boolean isGoogleDataprocDistribution() {
        return false;
    }

    public boolean isAltusDistribution() {
        return false;
    }

    public boolean doSupportOozie() {
        return true;
    }

    public boolean doSupportCreateServiceConnection() {
        return true;
    }

    public List<String> getNecessaryServiceName() {
        return null;
    }

    public boolean doRequireMetastoreVersionOverride() {
        return false;
    }

    public String getHiveMetastoreVersionForSpark() {
        return null;
    }

    public boolean isHortonworksDistribution() {
        return false;
    }

    public boolean doImportDynamoDBDependencies() {
        return false;
    }

    public boolean doSupportAzureBlobStorage() {
        return false;
    }

    public boolean doSupportAzureDataLakeStorage() {
        return false;
    }
    
    public boolean doImportSparkHiveContextDependencies() {
        return false;
    }
    
    public boolean isActivated() {
        return true;
    }
}
