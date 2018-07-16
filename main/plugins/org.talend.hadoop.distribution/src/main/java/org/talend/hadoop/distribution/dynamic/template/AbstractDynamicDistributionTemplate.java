package org.talend.hadoop.distribution.dynamic.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ClassUtils;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.MapRDBComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.dynamic.DynamicConstants;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.modulegroup.DynamicModuleGroupConstant;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.hadoop.distribution.kafka.SparkStreamingKafkaVersion;
import org.talend.hadoop.distribution.spark.SparkClassPathUtils;

public abstract class AbstractDynamicDistributionTemplate extends AbstractDistribution
        implements HadoopComponent, IDynamicDistributionTemplate {

    private Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroups;

    private Map<ComponentType, ComponentCondition> displayConditions;

    private Map<ComponentType, IDynamicModuleGroupTemplate> moduleGroupsTemplateMap;

    private DynamicPluginAdapter pluginAdapter;

    private String versionId;

    private String versionDisplay;

    public AbstractDynamicDistributionTemplate(DynamicPluginAdapter pluginAdapter) throws Exception {
        this.pluginAdapter = pluginAdapter;
        IDynamicPluginConfiguration configuration = pluginAdapter.getPluginConfiguration();
        versionId = configuration.getId();
        versionDisplay = configuration.getName();

        moduleGroupsTemplateMap = buildModuleGroupsTemplateMap();

        // Used to add a module group import for the components that have a HADOOP_DISTRIBUTION parameter, aka. the
        // components that have the distribution list.
        moduleGroups = buildModuleGroupsMap(pluginAdapter);

        // Used to add a module group import for a specific node. The given node must have a HADOOP_LIBRARIES parameter.
        nodeModuleGroups = buildNodeModuleGroupsMap(pluginAdapter);

        displayConditions = new HashMap<>();
    }

    protected Map<ComponentType, IDynamicModuleGroupTemplate> buildModuleGroupsTemplateMap() {
        Map<ComponentType, IDynamicModuleGroupTemplate> moduleGroupsTemplateMap = new HashMap<>();

        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        if (this instanceof HDFSComponent) {
            moduleGroupsTemplateMap.put(ComponentType.HDFS, new DynamicHDFSModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof HBaseComponent) {
            moduleGroupsTemplateMap.put(ComponentType.HBASE, new DynamicHBaseModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof HCatalogComponent) {
            moduleGroupsTemplateMap.put(ComponentType.HCATALOG, new DynamicHCatalogModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof HiveComponent) {
            moduleGroupsTemplateMap.put(ComponentType.HIVE, new DynamicHiveModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof HiveOnSparkComponent) {
            moduleGroupsTemplateMap.put(ComponentType.HIVEONSPARK, new DynamicHiveOnSparkModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof MapRDBComponent) {
            moduleGroupsTemplateMap.put(ComponentType.MAPRDB, new DynamicMapRDBModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof MRComponent) {
            moduleGroupsTemplateMap.put(ComponentType.MAPREDUCE, new DynamicMapReduceModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof PigComponent) {
            DynamicPigModuleGroupTemplate dynamicPigModuleGroupTemplate = new DynamicPigModuleGroupTemplate(pluginAdapter);
            moduleGroupsTemplateMap.put(ComponentType.PIG, dynamicPigModuleGroupTemplate);
            // it is enough that only regist pig template, since the template also contains pigoutput
            // moduleGroupsTemplateMap.put(ComponentType.PIGOUTPUT, dynamicPigModuleGroupTemplate);
        }
        if (this instanceof SparkBatchComponent) {
            moduleGroupsTemplateMap.put(ComponentType.SPARKBATCH, new DynamicSparkBatchModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof SparkStreamingComponent) {
            moduleGroupsTemplateMap.put(ComponentType.SPARKSTREAMING,
                    new DynamicSparkStreamingModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof SqoopComponent) {
            moduleGroupsTemplateMap.put(ComponentType.SQOOP, new DynamicSqoopModuleGroupTemplate(pluginAdapter));
        }

        return moduleGroupsTemplateMap;
    }

    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroupsMap(DynamicPluginAdapter pluginAdapter)
            throws Exception {
        Map<ComponentType, Set<DistributionModuleGroup>> moduleGroupsMap = new HashMap<>();

        for (Map.Entry<ComponentType, IDynamicModuleGroupTemplate> entry : moduleGroupsTemplateMap.entrySet()) {
            Map<ComponentType, Set<DistributionModuleGroup>> groupMap = entry.getValue().getModuleGroups();
            if (groupMap != null && !groupMap.isEmpty()) {
                moduleGroupsMap.putAll(groupMap);
            }
        }

        return moduleGroupsMap;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroupsMap(
            DynamicPluginAdapter pluginAdapter) throws Exception {
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> nodeModuleGroupsMap = new HashMap<>();

        for (Map.Entry<ComponentType, IDynamicModuleGroupTemplate> entry : moduleGroupsTemplateMap.entrySet()) {
            Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> groupMap = entry.getValue().getNodeModuleGroups();
            if (groupMap != null && !groupMap.isEmpty()) {
                nodeModuleGroupsMap.putAll(groupMap);
            }
        }

        return nodeModuleGroupsMap;
    }

    @Override
    public List<String> getServices() {
        List<String> services = new ArrayList<>();
        List<Class<?>> allInterfaces = ClassUtils.getAllInterfaces(this.getClass());
        for (Class<?> clz : allInterfaces) {
            if (HadoopComponent.class.isAssignableFrom(clz)) {
                services.add(clz.getName());
            }
        }
        return services;
    }

    @Override
    public String getVersion() {
        return versionId;
    }

    public String getVersionDisplay() {
        return versionDisplay;
    }

    protected DynamicPluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    protected Map<ComponentType, Set<DistributionModuleGroup>> getModuleGroupsMap() {
        return moduleGroups;
    }

    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> getNodeModuleGroupsMap() {
        return nodeModuleGroups;
    }

    protected Map<ComponentType, ComponentCondition> getDisplayConditionsMap() {
        return displayConditions;
    }

    @Override
    public boolean isDynamicDistribution() {
        return true;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_2;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return getModuleGroupsMap().get(componentType);
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType, String componentName) {
        return getModuleGroupsMap().get(new NodeComponentTypeBean(componentType, componentName));
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return getDisplayConditionsMap().get(componentType);
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        return getVersionDisplay();
    }

    @Override
    public Set<ESparkVersion> getSparkVersions() {
        Set<ESparkVersion> version = new HashSet<>();

        IDynamicPluginConfiguration pluginConfiguration = pluginAdapter.getPluginConfiguration();
        List<String> selectedSparkVersions = (List<String>) pluginConfiguration
                .getAttribute(DynamicConstants.ATTR_SELECTED_SPARK_VERSIONS);
        if (selectedSparkVersions != null) {
            List<ESparkVersion> sparkVersions = DynamicDistributionUtils.convert2ESparkVersions(selectedSparkVersions);
            version.addAll(sparkVersions);
        }

        return version;
    }

    @Override
    public String generateSparkJarsPaths(List<String> commandLineJarsPaths) {
        String spark2RuntimeId = getPluginAdapter()
                .getRuntimeModuleGroupIdByTemplateId(DynamicModuleGroupConstant.SPARK2_MODULE_GROUP.getModuleName());
        if (StringUtils.isEmpty(spark2RuntimeId)) {
            throw new RuntimeException(
                    "Can't find configuration for " + DynamicModuleGroupConstant.SPARK2_MODULE_GROUP.getModuleName());
        }
        return SparkClassPathUtils.generateSparkJarsPathsWithNames(commandLineJarsPaths, spark2RuntimeId);
    }

    @Override
    public SparkStreamingKafkaVersion getSparkStreamingKafkaVersion(ESparkVersion sparkVersion) {
        // Using Kafka 0.10 for Spark 2
        if (ESparkVersion.SPARK_2_0.compareTo(sparkVersion) <= 0) {
            return SparkStreamingKafkaVersion.KAFKA_0_10;
        } else {
            return SparkStreamingKafkaVersion.KAFKA_0_8;
        }
    }

    @Override
    public boolean doSupportKerberos() {
        return true;
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return true;
    }

    @Override
    public boolean doSupportOldImportMode() {
        return false;
    }

    @Override
    public boolean doSupportS3() {
        return true;
    }

    @Override
    public boolean doSupportS3V4() {
        return true;
    }

    @Override
    public boolean doSupportHDFSEncryption() {
        return true;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return true;
    }
}
