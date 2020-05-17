// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.constants.databricks;

import org.talend.hadoop.distribution.i18n.Messages;
public enum EDatabriksCloudProvider {

    AWS(Messages.getString("EDatabriksCloudProvider.AWS"), "AWS"),
    AZURE(Messages.getString("EDatabriksCloudProvider.Azure"), "Azure");

    private String providerLableName;

    private String providerValue;

    EDatabriksCloudProvider(String providerName, String providerValue) {
        this.providerLableName = providerName;
        this.providerValue = providerValue;
    }

    public String getProviderLableName() {
        return providerLableName;
    }

    public String getProviderValue() {
        return providerValue;
    }
}
