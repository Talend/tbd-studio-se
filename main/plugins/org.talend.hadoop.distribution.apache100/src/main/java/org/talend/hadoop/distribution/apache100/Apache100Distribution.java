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
package org.talend.hadoop.distribution.apache100;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.AbstractDistribution;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.EHadoopVersion;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.constants.apache.IApacheDistribution;

@SuppressWarnings("nls")
public class Apache100Distribution extends AbstractDistribution implements HDFSComponent, MRComponent, HBaseComponent,
        PigComponent, HiveComponent, IApacheDistribution {

    public static final String VERSION = "APACHE_1_0_0";

    public static final String VERSION_DISPLAY = "Apache 1.0.0";

    public static final String VERSION_PIG_DISPLAY = "Apache 1.0.0 (Pig 0.9.2)";

    public static final String VERSION_HIVE_DISPLAY = "Apache 1.0.0 (Hive 0.9.0)";

    private static Map<ComponentType, Set<DistributionModuleGroup>> moduleGroups;

    private static Map<ComponentType, ComponentCondition> displayConditions = new HashMap<>();

    private static Map<ComponentType, String> customVersionDisplayNames = new HashMap<>();

    static {
        moduleGroups = new HashMap<>();

        ComponentCondition c1 = new SimpleComponentCondition(new BasicExpression(Constant.PIG_STORE_PARAMETER,
                EqualityOperator.NOT_EQ, Constant.PIG_HCATSTORER_PARAMETER));
        displayConditions.put(ComponentType.PIGOUTPUT, c1);

        customVersionDisplayNames.put(ComponentType.PIGOUTPUT, VERSION_PIG_DISPLAY);
        customVersionDisplayNames.put(ComponentType.HIVE, VERSION_HIVE_DISPLAY);
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
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        String customVersionName = customVersionDisplayNames.get(componentType);
        return customVersionName != null ? customVersionName : VERSION_DISPLAY;
    }

    @Override
    public EHadoopVersion getHadoopVersion() {
        return EHadoopVersion.HADOOP_1;
    }

    @Override
    public boolean doSupportKerberos() {
        return true;
    }

    @Override
    public Set<DistributionModuleGroup> getModuleGroups(ComponentType componentType) {
        return moduleGroups.get(componentType);
    }

    @Override
    public boolean doSupportUseDatanodeHostname() {
        return false;
    }

    @Override
    public boolean doSupportSequenceFileShortType() {
        return false;
    }

    @Override
    public boolean doSupportNewHBaseAPI() {
        return false;
    }

    @Override
    public boolean doSupportCrossPlatformSubmission() {
        return false;
    }

    @Override
    public boolean doSupportHCatalog() {
        return false;
    }

    @Override
    public boolean pigVersionPriorTo_0_12() {
        // return false because this distribution doesn't support HCatalog.
        return false;
    }

    @Override
    public boolean doSupportHBase() {
        return true;
    }

    @Override
    public boolean doSupportImpersonation() {
        return false;
    }

    @Override
    public boolean doSupportHive1() {
        return true;
    }

    @Override
    public boolean doSupportHive2() {
        return false;
    }

    @Override
    public boolean doSupportTezForHive() {
        return false;
    }

    @Override
    public boolean doSupportHBaseForHive() {
        return true;
    }

    @Override
    public boolean doSupportSSL() {
        return false;
    }

    @Override
    public boolean doSupportORCFormat() {
        return false;
    }

    @Override
    public boolean doSupportAvroFormat() {
        return false;
    }

    @Override
    public boolean doSupportParquetFormat() {
        return false;
    }

    @Override
    public boolean doSupportStoreAsParquet() {
        return false;
    }

    @Override
    public boolean doSupportOozie() {
        return false;
    }

    @Override
    public ComponentCondition getDisplayCondition(ComponentType componentType) {
        return displayConditions.get(componentType);
    }

}
