package org.talend.repository.hadoopcluster.ui.provider;


public enum EDatabriksLocalProvider {
    AWS("AWS"),
    AZURE("Azure");

    private String providerName;

    EDatabriksLocalProvider(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }
}
