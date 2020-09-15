package org.talend.hadoop.distribution;

public enum EParquetPackagePrefix {
    CLOUDERA(""),
    APACHE("org.apache.");

    private String packagePrefix;

    EParquetPackagePrefix(String prefix) {
        this.packagePrefix = prefix;
    }

    public String getPackagePrefix() {
        return packagePrefix;
    }

    @Override
    public String toString() {
        return packagePrefix;
    }
}
