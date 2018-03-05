// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
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
import java.util.Set;

import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.NestedComponentCondition;

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
            //
            if (previous != null) {
                return new NestedComponentCondition(previous);
            }
        }
        return null;
    }
}
