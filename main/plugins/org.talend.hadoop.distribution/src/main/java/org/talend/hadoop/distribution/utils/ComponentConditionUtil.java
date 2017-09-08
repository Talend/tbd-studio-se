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
package org.talend.hadoop.distribution.utils;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * Util class that operates on {@link ComponentCondition} objects.
 *
 */
public class ComponentConditionUtil {

    /**
     * This method creates a condition made of other conditions, contained in the given Set of conditions.
     * 
     * @param conditions - the Set of conditions to merge in a single condition.
     * @return a new ComponentCondition made of the other conditions. Returns null in case of at least one condition is
     * null.
     */
    public static ComponentCondition buildDistributionShowIf(Set<ComponentCondition> conditions) {
        if (conditions != null) {
            Iterator<ComponentCondition> iter = conditions.iterator();
            ComponentCondition previous = null;
            while (iter.hasNext()) {
                ComponentCondition cc = iter.next();
                if (cc == null) {
                    return null;
                }
                ComponentCondition wrappedCondition = new NestedComponentCondition(cc);
                if (previous != null) {
                    wrappedCondition = new MultiComponentCondition(previous, BooleanOperator.OR, wrappedCondition);
                }
                previous = wrappedCondition;
            }

            if (previous != null) {
                return new NestedComponentCondition(previous);
            }
        }
        return null;
    }

    /**
     * Generates the "SHOW_IF" condition for the "SUPPORTED_SPARK_VERSION" drop down list. Given a map of Spark versions
     * and corresponding supported hadoop versions, it builds a {@link ComponentCondition} for each entry in the map.
     * 
     * @param supportedSparkVersions the map of Spark versions
     * @return an array of a String reprensation of a {@link ComponentCondition}
     */
    public static String[] generateSparkVersionShowIfConditions(
            Map<ESparkVersion, Set<DistributionVersion>> supportedSparkVersions) {

        String[] results = null;
        if (supportedSparkVersions != null) {
            results = new String[supportedSparkVersions.size()];
            int conditionIndex = 0;

            for (Map.Entry<ESparkVersion, Set<DistributionVersion>> entry : supportedSparkVersions.entrySet()) {
                Set<ComponentCondition> multiComponentConditions = new LinkedHashSet<>();
                for (DistributionVersion distributionVersion : entry.getValue()) {
                    SimpleComponentCondition distribution = new SimpleComponentCondition(new BasicExpression(
                            "DISTRIBUTION", EqualityOperator.EQ, distributionVersion.distribution.getName())); //$NON-NLS-1$
                    SimpleComponentCondition version = new SimpleComponentCondition(new BasicExpression(
                            "SPARK_VERSION", EqualityOperator.EQ, distributionVersion.getVersion())); //$NON-NLS-1$
                    multiComponentConditions.add(new MultiComponentCondition(distribution, BooleanOperator.AND, version));
                }

                ComponentCondition componentCondition = buildDistributionShowIf(multiComponentConditions);
                results[conditionIndex++] = componentCondition != null ? componentCondition.getConditionString() : null;
            }
        }
        return results;
    }
}
