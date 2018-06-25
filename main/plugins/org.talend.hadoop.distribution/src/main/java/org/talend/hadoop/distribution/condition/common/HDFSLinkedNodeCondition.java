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
package org.talend.hadoop.distribution.condition.common;

import org.talend.hadoop.distribution.condition.BasicExpression;
import org.talend.hadoop.distribution.condition.BooleanOperator;
import org.talend.hadoop.distribution.condition.ComponentCondition;
import org.talend.hadoop.distribution.condition.EqualityOperator;
import org.talend.hadoop.distribution.condition.MultiComponentCondition;
import org.talend.hadoop.distribution.condition.SimpleComponentCondition;
import org.talend.hadoop.distribution.constants.HDFSConstant;

/**
 * created by zafkir on 10 apr. 2018 Detailled comment
 *
 */
public class HDFSLinkedNodeCondition {

    private ComponentCondition webHDFSCondition = null;
    private ComponentCondition adlsCondition = null;
    
    public HDFSLinkedNodeCondition(String distribution, String version) {
        
        // condition: (SCHEME=='WebHDFS') AND (DB_VERSION==$version) AND (DISTRIBUTION==$distribution)
        webHDFSCondition =
                new MultiComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                        new BasicExpression(HDFSConstant.SCHEME_PARAMETER, EqualityOperator.EQ, HDFSConstant.WebHDFS)), BooleanOperator.AND,
                        new SimpleComponentCondition(new BasicExpression(HDFSConstant.VERSION_PARAMETER, EqualityOperator.EQ,
                                version))), BooleanOperator.AND, new SimpleComponentCondition(
                        new BasicExpression(HDFSConstant.DISTRIBUTION_PARAMETER, EqualityOperator.EQ, distribution)));
        
        // condition: (SCHEME=='ADLS') AND (DB_VERSION==$version) AND (DISTRIBUTION==$distribution)
        adlsCondition =
                new MultiComponentCondition(new MultiComponentCondition(new SimpleComponentCondition(
                        new BasicExpression(HDFSConstant.SCHEME_PARAMETER, EqualityOperator.EQ, HDFSConstant.ADLS)), BooleanOperator.AND,
                        new SimpleComponentCondition(new BasicExpression(HDFSConstant.VERSION_PARAMETER, EqualityOperator.EQ,
                                version))), BooleanOperator.AND, new SimpleComponentCondition(
                        new BasicExpression(HDFSConstant.DISTRIBUTION_PARAMETER, EqualityOperator.EQ, distribution)));
    }
    
    public ComponentCondition getWebHDFSCondition() {
        return webHDFSCondition;
    }
    
    public ComponentCondition getAdlsCondition() {
        return adlsCondition;
    }
}
