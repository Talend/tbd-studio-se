// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
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
 * Interface that exposes specific Map/Reduce methods.
 *
 */
public interface MRComponent extends HadoopComponent {

    /**
     * @return true if the distribution executes its M/R job through WebHCat. It returns false if the distribution
     * executes through the resource manager.
     */
    public boolean isExecutedThroughWebHCat();

    /**
     * @return true if the distribution is compatible with cloudera navigator (usually, cloudera distribution > 4.3).
     */
    public boolean doSupportClouderaNavigator();

    /**
     * @return true if the distribution supports the "cross platform submission" Hadoop property.
     */
    public boolean doSupportCrossPlatformSubmission();

    /**
     * @return true if the distribution supports the user impersonation when submitting a job.
     */
    public boolean doSupportImpersonation();

    /**
     * @return the yarn application classpath of the distribution. This method won't be used for @link{EHadoopVersion}
     * HADOOP_1
     */
    public String getYarnApplicationClasspath();

    /**
     * @return true if the distribution supports a custom MapReduce Application ClassPath
     */
    public boolean doSupportCustomMRApplicationCP();

    /**
     * @return The custom MapReduce Application ClassPath
     */
    public String getCustomMRApplicationCP();

    /**
     * @return true if the distribution supports sending lineage data to Apache Atlas.
     */
    public boolean doSupportAtlas();

    /**
     * @return Cloudera Navigator API version. Supported by the cloudera Navigator installed by the distribution (CDH)
     */
    public int getClouderaNavigatorAPIVersion();

    /**
     * @return true if the distribution supports/requires basic username/password authentification
     */
    public boolean doSupportBasicAtlasAuthentification();
    
    /**
     * @return true if avro and avro-mapred are >= 1.7.6 and 
     * supports that DEFAULT_DEFLATE_LEVEL is in avro instead of avro-mapred
     */
    public boolean doSupportAvroDeflateProperties();

}
