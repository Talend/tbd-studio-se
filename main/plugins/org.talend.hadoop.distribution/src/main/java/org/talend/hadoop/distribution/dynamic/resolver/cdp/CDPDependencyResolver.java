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
package org.talend.hadoop.distribution.dynamic.resolver.cdp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.talend.hadoop.distribution.dynamic.resolver.AbstractDependencyResolver;

/**
 * DOC class global comment. Detailled comment
 */
public class CDPDependencyResolver extends AbstractDependencyResolver implements ICDPDependencyResolver {

    private List<Pattern> cdpDistributionPatterns = new ArrayList<>();

    @Override
    protected synchronized List<Pattern> getDistributionPatterns() {
    	 if (cdpDistributionPatterns.isEmpty()) {
             cdpDistributionPatterns.add(Pattern.compile("^(?:\\d+\\.\\d+\\.\\d+\\.)((?:\\d+\\.){3}\\d+-\\d+)$"));
         }
         return cdpDistributionPatterns;
    }

    @Override
    public String getDistribution() {
        return ICDPDependencyResolver.DISTRIBUTION;
    }
}
