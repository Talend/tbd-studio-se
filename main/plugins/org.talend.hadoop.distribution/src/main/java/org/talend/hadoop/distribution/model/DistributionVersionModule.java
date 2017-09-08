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
import java.util.List;

import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.ShowExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;

/**
 * DOC ggu class global comment. Detailled comment
 * 
 * Bean class for version of module.
 */
public class DistributionVersionModule {

    public final DistributionVersion distributionVersion;

    public DistributionModuleGroup moduleGrop;

    private List<ModuleNeeded> modulesNeeded = new ArrayList<ModuleNeeded>();

    DistributionVersionModule(DistributionVersion distributionVersion) {
        super();
        this.distributionVersion = distributionVersion;
    }

    public ComponentCondition getModuleRequiredIf() {
        final ComponentType componentType = distributionVersion.distribution.componentType;
        org.talend.hadoop.distribution.condition.Expression e1 = new BasicExpression(componentType.getDistributionParameter(),
                EqualityOperator.EQ, distributionVersion.distribution.name);
        org.talend.hadoop.distribution.condition.Expression e2 = new BasicExpression(componentType.getVersionParameter(),
                EqualityOperator.EQ, distributionVersion.version);
        org.talend.hadoop.distribution.condition.Expression e3 = new ShowExpression(componentType.getDistributionParameter());
        org.talend.hadoop.distribution.condition.Expression e4 = new ShowExpression(componentType.getVersionParameter());

        // The import is needed only if the good version and the good distribution are selected, and
        // if the Distribution and Version parameters are shown. The second condition to take the
        // USE_EXISTING_CONNECTIOn into account.

        ComponentCondition condition;
        condition = new MultiComponentCondition(new SimpleComponentCondition(e1), BooleanOperator.AND,
                new MultiComponentCondition(new SimpleComponentCondition(e2), BooleanOperator.AND, new MultiComponentCondition(
                        new SimpleComponentCondition(e3), BooleanOperator.AND, new SimpleComponentCondition(e4))));

        if (moduleGrop.getRequiredIf() != null) {
            condition = new MultiComponentCondition(condition, BooleanOperator.AND, new NestedComponentCondition(
                    moduleGrop.getRequiredIf()));
        }
        return condition;
    }

    public List<ModuleNeeded> getModulesNeeded() {
        if (modulesNeeded.isEmpty()) {
            if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibrariesService.class)) {
                ILibrariesService libService = (ILibrariesService) GlobalServiceRegister.getDefault().getService(
                        ILibrariesService.class);
                modulesNeeded.addAll(libService.getModuleNeeded(moduleGrop.getModuleName(), true));
            }
        }
        return modulesNeeded;
    }

}
