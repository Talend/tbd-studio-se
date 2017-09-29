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
package org.talend.hadoop.distribution.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.utils.ComponentConditionUtil;

public class DistributionBean implements IHDistribution {

    public final ComponentType componentType;

    /**
     * Distribution name and display name.
     */
    public final String name, displayName;

    public DistributionBean(ComponentType componentType, String name, String displayName) {
        super();
        this.componentType = componentType;
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * All versions.
     */
    private List<DistributionVersion> versions = new ArrayList<>();

    public void addVersion(DistributionVersion v) {
        if (v != null) {
            versions.add(v);
        }
    }

    public void removeVersion(DistributionVersion v) {
        if (v != null) {
            versions.remove(v);
        }
    }

    public DistributionVersion[] getVersions() {
        // DESC
        Collections.sort(versions, new Comparator<DistributionVersion>() {

            @Override
            public int compare(DistributionVersion b1, DistributionVersion b2) {
                // Versions are sorted by weight, then by name in a descending order
                if (b1.hadoopComponent != null && b2.hadoopComponent != null) {
                    if (b1.hadoopComponent.orderingWeight() > b2.hadoopComponent.orderingWeight()) {
                        return -1;
                    } else if (b1.hadoopComponent.orderingWeight() < b2.hadoopComponent.orderingWeight()) {
                        return 1;
                    }
                }
                return b1.version != null && b2.version != null ? b2.version.compareTo(b1.version) : 0;
            }

        });
        return versions.toArray(new DistributionVersion[versions.size()]);
    }

    @Override
    public IHDistributionVersion[] getHDVersions() {
        return getVersions();
    }

    @Override
    public String[] getVersionsDisplay() {
        List<String> versionsDisplay = new ArrayList<>();
        for (IHDistributionVersion v : getHDVersions()) {
            if (v.getDisplayVersion() != null) {
                versionsDisplay.add(v.getDisplayVersion());
            }
        }
        return versionsDisplay.toArray(new String[0]);
    }

    /**
     * 
     * The version can be null, especially for the Custom without any versions, but still need null one to keep the
     * {@link HadoopComponent} object.
     */
    public DistributionVersion getVersion(String v, boolean byDisplay) {
        if (useCustom()) { // because custom, only existed one fake version with null version name
            return getDefaultVersion();
        }
        for (DistributionVersion dv : versions) {
            if (byDisplay) {
                if (v == null && dv.displayVersion == null || v != null && v.equals(dv.displayVersion)) {
                    return dv;
                }
            } else if (v == null && dv.version == null || v != null && v.equals(dv.version)) {
                return dv;
            }
        }
        return null;
    }

    @Override
    public IHDistributionVersion getHDVersion(String v, boolean byDisplay) {
        return getVersion(v, byDisplay);
    }

    /**
     * Default version.
     */
    private DistributionVersion defaultVersion;

    public void setDefaultVersion(DistributionVersion v) {
        defaultVersion = v;
    }

    @Override
    public DistributionVersion getDefaultVersion() {
        if (defaultVersion == null) {
            DistributionVersion[] versionsArrays = getVersions();
            List<DistributionVersion> versionsList = Arrays.asList(versionsArrays);
            if (versionsList.size() > 0) {
                setDefaultVersion(versionsList.get(0));
                return defaultVersion;
            }
            return null;
        } else {
            return defaultVersion;
        }
    }

    /**
     * Conditions
     */
    private Set<ComponentCondition> conditions = new HashSet<>();

    public void addCondition(ComponentCondition c) {
        /*
         * TBD-3338, do it like before,add condition always, even it's null. Because when build condition, will ignore
         * all with the null
         * 
         * @see the method getDisplayShowIf with api ComponentConditionUtil.buildDistributionShowIf
         */
        conditions.add(c);
    }

    public ComponentCondition[] getConditions() {
        return conditions.toArray(new ComponentCondition[conditions.size()]);
    }

    public String getDisplayShowIf() {
        // We compose a ShowIf parameter for a distribution, only if the list of its version all have a display
        // condition.
        ComponentCondition cc = ComponentConditionUtil.buildDistributionShowIf(conditions);
        return cc == null ? null : cc.getConditionString();
    }

    @Override
    public boolean useCustom() {
        return Constant.DISTRIBUTION_CUSTOM.equals(name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DistributionBean)) {
            return false;
        }
        DistributionBean other = (DistributionBean) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ',' + displayName;
    }

}
