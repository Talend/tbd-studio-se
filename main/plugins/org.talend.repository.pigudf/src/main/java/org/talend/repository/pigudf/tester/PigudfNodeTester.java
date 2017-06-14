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
package org.talend.repository.pigudf.tester;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.tester.AbstractNodeTester;

/**
 * created by Administrator on 2013-3-12 Detailled comment
 * 
 */
public class PigudfNodeTester extends AbstractNodeTester {

    private static final String IS_PIG_UDF = "isPigudf"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.tester.AbstractNodeTester#testProperty(java.lang.Object, java.lang.String,
     * java.lang.Object[], java.lang.Object)
     */
    @Override
    protected Boolean testProperty(Object receiver, String property, Object[] args, Object expectedValue) {
        if (receiver instanceof RepositoryNode) {
            RepositoryNode repositoryNode = (RepositoryNode) receiver;
            if (IS_PIG_UDF.equals(property)) {
                return isPigudf(repositoryNode);
            }
        }
        return null;
    }

    public boolean isPigudf(RepositoryNode repositoryNode) {
        return isTypeNode(repositoryNode, ERepositoryObjectType.PIG_UDF);
    }

    public boolean isPigudfTopNode(RepositoryNode repositoryNode) {
        return isTypeTopNode(repositoryNode, ERepositoryObjectType.PIG_UDF);
    }

}
