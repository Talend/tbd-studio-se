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
package org.talend.hadoop.distribution.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * DOC ggu class global comment. Detailled comment
 */
public final class DistributionHelper {

    public static boolean doSupportService(IHDistributionVersion distributionVersion, String service) {
        if (distributionVersion instanceof DistributionVersion && service != null) {
            HadoopComponent dadoopComponent = ((DistributionVersion) distributionVersion).hadoopComponent;
            if (dadoopComponent != null) {
                try {
                    final Class<?> clazz = Class.forName(service, false, HadoopComponent.class.getClassLoader());
                    return clazz.isInstance(dadoopComponent);
                } catch (ClassNotFoundException e) {
                    // if not found, nothing to do
                }
            }
        }
        return false;
    }

    public static boolean doSupportMethod(IHDistributionVersion distributionVersion, String method) throws Exception {
        Map<String, Boolean> doSupportMethods = doSupportMethods(distributionVersion, method);
        Boolean support = doSupportMethods.get(method);
        return support != null && support;
    }

    public static Map<String, Boolean> doSupportMethods(IHDistributionVersion distributionVersion, String... methods)
            throws Exception {
        if (distributionVersion instanceof DistributionVersion && methods != null && methods.length > 0) {
            HadoopComponent hadoopComponent = ((DistributionVersion) distributionVersion).hadoopComponent;
            return doSupportMethods(hadoopComponent, methods);
        }
        return Collections.emptyMap();
    }

    public static boolean doSupportMethod(HadoopComponent hadoopComponent, String method) throws Exception {
        Map<String, Boolean> doSupportMethods = doSupportMethods(hadoopComponent, method);
        Boolean support = doSupportMethods.get(method);
        return support != null && support;
    }

    public static Map<String, Boolean> doSupportMethods(HadoopComponent hadoopComponent, String... methods) throws Exception {
        Map<String, Boolean> resultsMap = new HashMap<String, Boolean>();
        if (hadoopComponent != null) {
            for (String m : methods) {
                Method method = findMethod(hadoopComponent.getClass(), m);
                if (method != null) {
                    try {
                        method.setAccessible(true);
                        Object is = method.invoke(hadoopComponent);
                        if (is instanceof Boolean) {
                            resultsMap.put(m, ((Boolean) is));
                        }
                    } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        // ignore
                    }
                } else {
                    throw new NoSuchMethodException(
                            "The distribution " + hadoopComponent.getDistributionName() + " with the version " + hadoopComponent.getVersion() //$NON-NLS-1$ //$NON-NLS-2$
                                    + " is not supported in this component. Please check your configuration."); //$NON-NLS-1$
                }

            }
        }
        return resultsMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Method findMethod(Class baseClazz, String methodName) {
        Method method = null;
        if (baseClazz != null) {
            try {
                method = baseClazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException | SecurityException e) {
                // ignore
            }
            if (method == null) { // can't find in current base class, try parent class.
                method = findMethod(baseClazz.getSuperclass(), methodName);
            }
        }
        return method;
    }
}
