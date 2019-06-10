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
package org.talend.hadoop.distribution.dynamic.resolver.hdp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.talend.hadoop.distribution.dynamic.resolver.AbstractDependencyResolver;

/**
 * DOC cmeng class global comment. Detailled comment
 */
public class HortonworksDependencyResolver extends AbstractDependencyResolver implements IHortonworksDependencyResolver {

    private List<Pattern> hortonworksDistributionPatterns;

    @Override
    protected List<Pattern> getDistributionPatterns() {
        if (hortonworksDistributionPatterns == null || hortonworksDistributionPatterns.isEmpty()) {
            hortonworksDistributionPatterns = new ArrayList<>();
            Pattern hortonworksDistributionPattern = Pattern.compile("^(?:\\d+\\.\\d+\\.\\d+\\.)((\\d+\\.){3}\\d+-\\d+)$"); //$NON-NLS-1$
            hortonworksDistributionPatterns.add(hortonworksDistributionPattern);
            hortonworksDistributionPattern = Pattern.compile("^(\\d+\\.\\d+\\.\\d+\\.\\d+)$"); //$NON-NLS-1$
            hortonworksDistributionPatterns.add(hortonworksDistributionPattern);
        }
        return hortonworksDistributionPatterns;
    }

    @Override
    public String getDistribution() {
        return IHortonworksDependencyResolver.DISTRIBUTION;
    }

}
