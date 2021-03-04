// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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
import org.talend.core.hadoop.HadoopConstants;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.condition.Expression;
import org.talend.hadoop.distribution.condition.LinkedNodeExpression;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.RawExpression;
import org.talend.hadoop.distribution.condition.ShowExpression;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.SparkBatchConstant;

/**
 * DOC ggu class global comment. Detailled comment
 *
 * Bean class for version of module.
 */
public class DistributionVersionModule {

    public final DistributionVersion distributionVersion;

    public DistributionModuleGroup moduleGroup;

    private List<ModuleNeeded> modulesNeeded = new ArrayList<ModuleNeeded>();

    DistributionVersionModule(DistributionVersion distributionVersion) {
        super();
        this.distributionVersion = distributionVersion;
    }

    public ComponentCondition getModuleRequiredIf() {
        ComponentCondition condition;
        if ( !distributionVersion.distribution.isSparkLocal()) {
            // The import is needed only if the good version and the good distribution are selected, and
            // if the Distribution and Version parameters are shown. The second condition to take the
            // USE_EXISTING_CONNECTIOn into account.

            final ComponentType componentType = distributionVersion.distribution.componentType;
            Expression distributionSelected = new BasicExpression(componentType.getDistributionParameter(), 
                                                                  EqualityOperator.EQ, distributionVersion.distribution.name);
            Expression distributionVersionSelected = new BasicExpression(componentType.getVersionParameter(),
                                                                         EqualityOperator.EQ, distributionVersion.version);
            Expression distributionShown = new ShowExpression(componentType.getDistributionParameter());
            Expression distributionVersionShown = new ShowExpression(componentType.getVersionParameter());

            condition = new MultiComponentCondition(new SimpleComponentCondition(distributionSelected), BooleanOperator.AND,
                    new MultiComponentCondition(new SimpleComponentCondition(distributionVersionSelected), BooleanOperator.AND, new MultiComponentCondition(
                            new SimpleComponentCondition(distributionShown), BooleanOperator.AND, new SimpleComponentCondition(distributionVersionShown))));
        } else {
            // In case of Spark local distribution the import is needed if 
            // - use Spark local is selected and 
            // - spark versions from UI and distribution match 
            // either on node itself (Spark config) or linked Spark Config (tHiveConfig, tHDFSConfig ...)
            Expression useSparkLocal = new BasicExpression(HadoopConstants.SPARK_LOCAL_MODE);
            Expression sameSparkVersions = new BasicExpression(HadoopConstants.SPARK_LOCAL_VERSION, EqualityOperator.EQ, distributionVersion.version);
            ComponentCondition sparkConfigCondition = new MultiComponentCondition(useSparkLocal, BooleanOperator.AND, sameSparkVersions);
        
            Expression useSparkLocalLinked = new LinkedNodeExpression(SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, HadoopConstants.SPARK_LOCAL_MODE, EqualityOperator.EQ, "true");
            Expression sameSparkVersionsLinked = new LinkedNodeExpression(SparkBatchConstant.SPARK_BATCH_SPARKCONFIGURATION_LINKEDPARAMETER, HadoopConstants.SPARK_LOCAL_VERSION, EqualityOperator.EQ, distributionVersion.version);            
            ComponentCondition linkedSparkConfigCondition = new MultiComponentCondition(useSparkLocalLinked, BooleanOperator.AND, sameSparkVersionsLinked);
            
            condition = new MultiComponentCondition(
                    new NestedComponentCondition(sparkConfigCondition),
                    BooleanOperator.OR, 
                    new NestedComponentCondition(linkedSparkConfigCondition));
        }
            
        if (moduleGroup.getRequiredIf() != null) {
            condition = new MultiComponentCondition(condition, BooleanOperator.AND, new NestedComponentCondition(
                    moduleGroup.getRequiredIf()));
        }
        return condition;
    }

    public List<ModuleNeeded> getModulesNeeded() {
        if (modulesNeeded.isEmpty()) {
            if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibrariesService.class)) {
                ILibrariesService libService = (ILibrariesService) GlobalServiceRegister.getDefault().getService(
                        ILibrariesService.class);
                modulesNeeded.addAll(libService.getModuleNeeded(moduleGroup.getModuleName(), true));
            }
        }
        return modulesNeeded;
    }

}
