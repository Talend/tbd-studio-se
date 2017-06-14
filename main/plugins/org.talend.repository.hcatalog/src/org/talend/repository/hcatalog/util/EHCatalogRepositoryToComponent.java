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

package org.talend.repository.hcatalog.util;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHCatalogRepositoryToComponent {

    DISTRIBUTION("DISTRIBUTION", "DISTRIBUTION"), //$NON-NLS-1$ //$NON-NLS-2$

    HCAT_VERSION("HCAT_VERSION", "HCAT_VERSION"), //$NON-NLS-1$ //$NON-NLS-2$

    TEMPLETON_HOST("TEMPLETON_HOST", "TEMPLETON_HOST"), //$NON-NLS-1$ //$NON-NLS-2$

    TEMPLETON_PORT("TEMPLETON_PORT", "TEMPLETON_PORT"), //$NON-NLS-1$ //$NON-NLS-2$

    USERNAME("USERNAME", "USERNAME"), //$NON-NLS-1$ //$NON-NLS-2$

    DATABASE_NAME("DATABASE_NAME", "DATABASE_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    TABLE_NAME("TABLE_NAME", "TABLE_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    PARTITION_NAME("PARTITION_NAME", "PARTITION_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    USE_KRB("USE_KRB", "USE_KRB"), //$NON-NLS-1$ //$NON-NLS-2$

    KRB_PRINC("KRB_PRINC", "KRB_PRINC"), //$NON-NLS-1$ //$NON-NLS-2$

    KRB_REALM("KRB_REALM", "KRB_REALM"), //$NON-NLS-1$ //$NON-NLS-2$

    NAMENODE_PRINCIPAL("NAMENODE_PRINCIPAL", "NAMENODE_PRINCIPAL"), //$NON-NLS-1$ //$NON-NLS-2$

    USE_KEYTAB("USE_KEYTAB", "USE_KEYTAB"), //$NON-NLS-1$//$NON-NLS-2$

    KEYTAB_PRINCIPAL("KEYTAB_PRINCIPAL", "PRINCIPAL"), //$NON-NLS-1$//$NON-NLS-2$

    KEYTAB_PATH("KEYTAB_PATH", "KEYTAB_PATH"), //$NON-NLS-1$//$NON-NLS-2$

    ROWSEPARATOR("ROWSEPARATOR", "ROWSEPARATOR"), //$NON-NLS-1$ //$NON-NLS-2$

    FIELDSEPARATOR("FIELDSEPARATOR", "FIELDSEPARATOR"), //$NON-NLS-1$ //$NON-NLS-2$

    LOCAL("LOCAL", "LOCAL"), //$NON-NLS-1$ //$NON-NLS-2$

    MAPREDUCE("MAPREDUCE", "MAPREDUCE"), //$NON-NLS-1$ //$NON-NLS-2$

    PIG_VERSION("PIG_VERSION", "PIG_VERSION"), //$NON-NLS-1$ //$NON-NLS-2$

    MAPRED_JOB_TRACKER("MAPRED_JOB_TRACKER", "JOBTRACKER"), //$NON-NLS-1$ //$NON-NLS-2$

    MAPRED_RESOURCE_MANAGER("MAPRED_RESOURCE_MANAGER", "RESOURCE_MANAGER"), //$NON-NLS-1$ //$NON-NLS-2$

    FS_DEFAULT_NAME("FS_DEFAULT_NAME", "FS_DEFAULT_NAME"), //$NON-NLS-1$ //$NON-NLS-2$

    LOAD("LOAD", "LOAD"), //$NON-NLS-1$ //$NON-NLS-2$

    STORE("STORE", "STORE"), //$NON-NLS-1$ //$NON-NLS-2$

    HADOOP_CUSTOM_JARS("HADOOP_CUSTOM_JARS", "HADOOP_CUSTOM_JARS"), //$NON-NLS-1$ //$NON-NLS-2$

    JOBTRACKER_PRINCIPAL("JOBTRACKER_PRINCIPAL", "JOBTRACKER_PRINCIPAL"), //$NON-NLS-1$ //$NON-NLS-2$

    RESOURCE_MANAGER("RESOURCE_MANAGER", "RESOURCE_MANAGER"), //$NON-NLS-1$ //$NON-NLS-2$

    HADOOP_ADVANCED_PROPERTIES("HADOOP_ADVANCED_PROPERTIES", "HADOOP_ADVANCED_PROPERTIES"); //$NON-NLS-1$ //$NON-NLS-2$

    ;

    public final String repositoryValue;

    public final String parameterName;

    EHCatalogRepositoryToComponent(String repositoryValue, String parameterName) {
        this.repositoryValue = repositoryValue;
        this.parameterName = parameterName;
    }

    public String getRepositoryValue() {
        return repositoryValue;
    }

    public String getParameterName() {
        return parameterName;
    }

}
