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
package org.talend.hadoop.distribution.component;

/**
 * Interface that exposes specific Sqoop methods.
 *
 */
public interface SqoopComponent extends MRComponent {

    /**
     * @return true if the distribution does support the file storage for the database password in Java API mode.
     */
    public boolean doJavaAPISupportStorePasswordInFile();

    /**
     * @return true if the distribution does support the target directory deletion for the Sqoop Import (in JAVA API
     * mode).
     */
    public boolean doJavaAPISqoopImportSupportDeleteTargetDir();

    /**
     * @return true if the distribution does support the table exclusion for the Sqoop Import All Tables (in JAVA API
     * mode).
     */
    public boolean doJavaAPISqoopImportAllTablesSupportExcludeTable();

    /**
     * @return true if the distribution does support parquet output format (sqoop >= 1.4.6)
     */
    public boolean doSupportParquetOutput();

    /**
     * @return true if the distribution is impacted by the bug https://issues.apache.org/jira/browse/SQOOP-2995. Have to
     * set a init value to the new map type key "customtool.options.jsonmap". But the key hasn't been used.
     */
    public boolean isImpactedBySqoop2995();

    /**
     * @return true if the distribution does support fetch password from file
     */
    public boolean doSupportFetchPasswordFromFile();

}
