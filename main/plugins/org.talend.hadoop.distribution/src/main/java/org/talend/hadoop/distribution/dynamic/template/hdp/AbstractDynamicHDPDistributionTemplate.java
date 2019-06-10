// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.template.hdp;

import java.util.Map;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;
import org.talend.hadoop.distribution.dynamic.adapter.DynamicPluginAdapter;
import org.talend.hadoop.distribution.dynamic.template.AbstractDynamicDistributionTemplate;
import org.talend.hadoop.distribution.dynamic.template.DynamicPigModuleGroupTemplate;
import org.talend.hadoop.distribution.dynamic.template.IDynamicModuleGroupTemplate;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicHDPDistributionTemplate extends AbstractDynamicDistributionTemplate
        implements IDynamicHDPDistributionTemplate {

    public AbstractDynamicHDPDistributionTemplate(DynamicPluginAdapter pluginAdapter) throws Exception {
        super(pluginAdapter);
    }

    @Override
    protected Map<ComponentType, IDynamicModuleGroupTemplate> buildModuleGroupsTemplateMap() {
        Map<ComponentType, IDynamicModuleGroupTemplate> groupTemplateMap = super.buildModuleGroupsTemplateMap();
        DynamicPluginAdapter pluginAdapter = getPluginAdapter();

        if (this instanceof HDFSComponent) {
            groupTemplateMap.put(ComponentType.HDFS, new DynamicHDPHDFSModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof MRComponent) {
            groupTemplateMap.put(ComponentType.MAPREDUCE, new DynamicHDPMapReduceModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof PigComponent) {
            DynamicPigModuleGroupTemplate dynamicPigModuleGroupTemplate = new DynamicHDPPigModuleGroupTemplate(pluginAdapter);
            groupTemplateMap.put(ComponentType.PIG, dynamicPigModuleGroupTemplate);
            // it is enough that only regist pig template, since the template also contains pigoutput
            // moduleGroupsTemplateMap.put(ComponentType.PIGOUTPUT, dynamicPigModuleGroupTemplate);
        }
        if (this instanceof SparkBatchComponent) {
            groupTemplateMap.put(ComponentType.SPARKBATCH, new DynamicHDPSparkBatchModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof SparkStreamingComponent) {
            groupTemplateMap.put(ComponentType.SPARKSTREAMING, new DynamicHDPSparkStreamingModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof HiveOnSparkComponent) {
            groupTemplateMap.put(ComponentType.HIVEONSPARK, new DynamicHDPHiveOnSparkModuleGroupTemplate(pluginAdapter));
        }
        if (this instanceof SqoopComponent) {
            groupTemplateMap.put(ComponentType.SQOOP, new DynamicHDPSqoopModuleGroupTemplate(pluginAdapter));
        }

        return groupTemplateMap;
    }

    @Override
    public String getDistribution() {
        return DISTRIBUTION_NAME;
    }

    @Override
    public String getDistributionName() {
        return DISTRIBUTION_DISPLAY_NAME;
    }

    @Override
    public boolean isHortonworksDistribution() {
        return true;
    }
}
