// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.resolver.cdh;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.talend.hadoop.distribution.dynamic.resolver.AbstractDependencyResolver;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class ClouderaDependencyResolver extends AbstractDependencyResolver implements IClouderaDependencyResolver {

    private List<Pattern> clouderaDistributionPatterns;

    @Override
    protected synchronized List<Pattern> getDistributionPatterns() {
        if (clouderaDistributionPatterns == null || clouderaDistributionPatterns.isEmpty()) {
            clouderaDistributionPatterns = new ArrayList<>();
            // In this way the version prior to cdh should contain at least 3 characters (ex. 1.0), normally it's always the case
            Pattern clouderaDistributionPattern = Pattern.compile("(?:[^m][^r]..[Cc][Dd][Hh])([\\d]+\\.[\\d]+\\.[\\d]+$)"); //$NON-NLS-1$
            clouderaDistributionPatterns.add(clouderaDistributionPattern);
        }
        return clouderaDistributionPatterns;
    }

    @Override
    public String getDistribution() {
        return IClouderaDependencyResolver.DISTRIBUTION;
    }
}
