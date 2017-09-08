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

package org.talend.hadoop.distribution.emr460;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.NodeComponentTypeBean;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.PigOutputConstant;
import org.talend.hadoop.distribution.emr450.EMR450Distribution;
import org.talend.hadoop.distribution.emr460.modulegroup.EMR460HBaseModuleGroup;
import org.talend.hadoop.distribution.emr460.modulegroup.EMR460HiveModuleGroup;
import org.talend.hadoop.distribution.emr460.modulegroup.EMR460PigModuleGroup;
import org.talend.hadoop.distribution.emr460.modulegroup.EMR460PigOutputModuleGroup;
import org.talend.hadoop.distribution.emr460.modulegroup.node.pigoutput.EMR460PigOutputNodeModuleGroup;

// This class extends {@see EMR450Distribution} as it shares the same behaviour and dependencies.
public class EMR460Distribution extends EMR450Distribution implements HBaseComponent {

    @SuppressWarnings("hiding")
    public static final String VERSION = "EMR_4_6_0"; //$NON-NLS-1$

    @SuppressWarnings("hiding")
    public static final String VERSION_DISPLAY = "EMR 4.6.0 (Apache 2.7.2)"; //$NON-NLS-1$

    public static final String PIG_EMR460_DISPLAY = "EMR 4.6.0 (Pig 0.14.0)";//$NON-NLS-1$

    public static final String SQOOP_EMR460_DISPLAY = "EMR 4.6.0 (Sqoop 1.4.6)"; //$NON-NLS-1$

    public static final String HIVE_EMR460_DISPLAY = "EMR 4.6.0 (Hive 1.0.0)"; //$NON-NLS-1$

    public static final String HBASE_EMR460_DISPLAY = "EMR 4.6.0 (HBase 1.2.0)"; //$NON-NLS-1$

    public EMR460Distribution() {
        super();
    }

    @Override
    protected Map<ComponentType, ComponentCondition> buildDisplayConditions() {
        Map<ComponentType, ComponentCondition> result = super.buildDisplayConditions();
        // Clear PIGOUTPUT display condition
        result.remove(ComponentType.PIGOUTPUT);
        return result;
    }

    @Override
    protected Map<ComponentType, String> buildCustomVersionDisplayNames() {
        Map<ComponentType, String> result = new HashMap<>();
        result.put(ComponentType.PIG, PIG_EMR460_DISPLAY);
        result.put(ComponentType.HIVE, HIVE_EMR460_DISPLAY);
        result.put(ComponentType.SQOOP, SQOOP_EMR460_DISPLAY);
        result.put(ComponentType.HBASE, HBASE_EMR460_DISPLAY);
        return result;
    }

    @Override
    protected Map<ComponentType, Set<DistributionModuleGroup>> buildModuleGroups() {
        // Re-use EMR 4.5.0 module groups
        Map<ComponentType, Set<DistributionModuleGroup>> result = super.buildModuleGroups();

        // Add HBase module group
        result.put(ComponentType.HBASE, EMR460HBaseModuleGroup.getModuleGroups());

        // Override few module groups to enable HBase features
        result.put(ComponentType.HIVE, EMR460HiveModuleGroup.getModuleGroups());
        result.put(ComponentType.PIG, EMR460PigModuleGroup.getModuleGroups());
        result.put(ComponentType.PIGOUTPUT, EMR460PigOutputModuleGroup.getModuleGroups());
        return result;
    }

    @Override
    protected Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> buildNodeModuleGroups(String distribution, String version) {
        // Re-use EMR 4.5.0 module groups
        Map<NodeComponentTypeBean, Set<DistributionModuleGroup>> result = super.buildNodeModuleGroups(distribution, version);

        // Override few module groups to enable HBase features
        result.put(new NodeComponentTypeBean(ComponentType.PIG, PigOutputConstant.PIGSTORE_COMPONENT),
                EMR460PigOutputNodeModuleGroup.getModuleGroups(distribution, version));
        return result;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        String customVersionName = customVersionDisplayNames.get(componentType);
        return customVersionName != null ? customVersionName : VERSION_DISPLAY;
    }

    @Override
    public boolean doSupportNewHBaseAPI() {
        return true;
    }

    @Override
    public boolean doSupportHBase() {
        return true;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        return false;
    }

    @Override
    public boolean doSupportKerberos() {
        return false;
    }

    // Note :
    // Azure Blob & Datalake support have been disabled for now on this distribution
    // New versions of this distribution should be tested for Azure support and
    // the changes backported to all earlier versions
    @Override
    public boolean doSupportAzureBlobStorage() {
        return false;
    }

    @Override
    public boolean doSupportAzureDataLakeStorage() {
        return false;
    }
    // End
}
