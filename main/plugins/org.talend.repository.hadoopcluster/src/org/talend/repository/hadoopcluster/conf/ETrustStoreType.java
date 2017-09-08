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
package org.talend.repository.hadoopcluster.conf;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * created by ycbai on 2015年6月9日 Detailled comment
 *
 */
public enum ETrustStoreType {

    JKS,

    PKCS12,

    ;

    public String getName() {
        return this.name();
    }

    public static List<String> getAllTrustStoreTypeNames() {
        List<String> names = new ArrayList<>();
        ETrustStoreType[] values = values();
        for (ETrustStoreType type : values) {
            names.add(type.getName());
        }
        return names;
    }

}
