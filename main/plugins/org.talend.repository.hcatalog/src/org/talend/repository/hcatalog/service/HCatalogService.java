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
package org.talend.repository.hcatalog.service;

import org.talend.core.hadoop.IHCatalogService;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;

/**
 * created by Talend on Apr 16, 2015 Detailled comment
 *
 */
public class HCatalogService implements IHCatalogService {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.hadoop.IHCatalogService#getHCatalogType()
     */
    @Override
    public ERepositoryObjectType getHCatalogType() {
        return HCatalogRepositoryNodeType.HCATALOG;
    }

}
