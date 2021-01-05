// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
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

import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.dynamic.template.AbstractDynamicDistributionTemplate;
import org.talend.hadoop.distribution.dynamic.template.IDynamicDistributionTemplate;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistributionVersion extends DistributionVersion {

    private ComponentType componentType;

//    private final Object modulesLock = new Object();

    public DynamicDistributionVersion(AbstractDynamicDistributionTemplate hadoopComponent, DistributionBean distribution,
            ComponentType componentType, String version, String displayVersion) {
        super(hadoopComponent, distribution, version, displayVersion);
        this.componentType = componentType;
    }

    public IDynamicDistributionTemplate getDistributionTemplate() {
        return (IDynamicDistributionTemplate) hadoopComponent;
    }

//    @Override
//    protected Set<DistributionVersionModule> getModules() {
//        if (this.modules == null) {
//            synchronized (modulesLock) {
//                if (this.modules == null) {
//                    this.modules = new LinkedHashSet<>();
//                    ((AbstractDynamicDistributionTemplate) this.hadoopComponent).registPluginExtensions();
//                    addModuleGroups(this.hadoopComponent.getModuleGroups(componentType));
//                }
//            }
//        }
//        return this.modules;
//    }

}
