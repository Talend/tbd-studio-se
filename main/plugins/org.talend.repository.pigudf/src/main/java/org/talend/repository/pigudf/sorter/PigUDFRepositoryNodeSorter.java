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
package org.talend.repository.pigudf.sorter;

import org.talend.repository.model.RepositoryNode;
import org.talend.repository.pigudf.tester.PigudfNodeTester;
import org.talend.repository.viewer.sorter.CodeRepositoryNodeSorter;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class PigUDFRepositoryNodeSorter extends CodeRepositoryNodeSorter {

    private PigudfNodeTester pigudfTester = new PigudfNodeTester();

    /**
     * always on bottom
     */
    @Override
    protected int compareNode(RepositoryNode n1, RepositoryNode n2) {
        if (pigudfTester.isPigudfTopNode(n1)) {
            return 1;
        }
        if (pigudfTester.isPigudfTopNode(n2)) {
            return -1;
        }
        return 0;
    }

}
