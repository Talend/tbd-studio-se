package org.talend.hadoop.distribution;

/**
 * Describes the base name of the packages of Sqoop classes
 */
public enum ESqoopPackageName {
    COM_CLOUDERA_SQOOP("com.cloudera.sqoop"),
    ORG_APACHE_SQOOP("org.apache.sqoop");
    
    private String sqoopPackageName;

    private ESqoopPackageName(String sqoopPackageName) {
        this.sqoopPackageName = sqoopPackageName;
    }

    
    public String getSqoopPackageName() {
        return sqoopPackageName;
    }
    
    @Override
    public String toString() {
        return sqoopPackageName;
    }
}
