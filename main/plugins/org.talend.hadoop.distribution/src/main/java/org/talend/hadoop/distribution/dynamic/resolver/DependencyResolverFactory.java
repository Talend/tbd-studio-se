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
package org.talend.hadoop.distribution.dynamic.resolver;

import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DependencyResolverFactory {

    private static DependencyResolverFactory instance;

    private DependencyResolverFactory() {
        // nothing to do
    }

    public static DependencyResolverFactory getInstance() {
        if (instance == null) {
            instance = new DependencyResolverFactory();
        }
        return instance;
    }

    public IDependencyResolver getDependencyResolver(DynamicConfiguration configuration) {
        String distribution = configuration.getDistribution();
        if (IClouderaDependencyResolver.DISTRIBUTION.equals(distribution)) {
            ClouderaDependencyResolver resolver = new ClouderaDependencyResolver();
            resolver.setConfiguration(configuration);
            return resolver;
        }
        return null;
    }

}
