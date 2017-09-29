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
package org.talend.hadoop.distribution.helper;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.MapRDBComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.constants.Constant;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HadoopDistributionsHelper {

    private static Collection<ServiceReference<HadoopComponent>> hadoopDistributions;

    /**
     * for Hadoop Distributions.
     * 
     * @link HadoopComponent
     */
    public static final DistributionsManager HADOOP = new DistributionsManager(HadoopComponent.class.getName(), null, true);

    /**
     * for HDFS Distributions.
     * 
     * @link HDFSComponent
     */
    public static final DistributionsManager HDFS = new DistributionsManager(HDFSComponent.class.getName(), null, true);

    /**
     * for MapReduce Distributions.
     * 
     * @link MRComponent
     */
    public static final DistributionsManager MR = new DistributionsManager(MRComponent.class.getName(), null, true);

    /**
     * for HBase Distributions.
     * 
     * @link HBaseComponent
     */
    public static final DistributionsManager HBASE = new DistributionsManager(HBaseComponent.class.getName(), null, true);

    /**
     * for Maprdb Distributions.
     * 
     * @link MaprdbComponent
     */
    public static final DistributionsManager MAPRDB = new DistributionsManager(MapRDBComponent.class.getName(), null, true);

    /**
     * for HCatalog Distributions.
     * 
     * @link HCatalogComponent
     */
    public static final DistributionsManager HCATALOG = new DistributionsManager(HCatalogComponent.class.getName(), null, true);

    /**
     * for Spark Streaming Distributions.
     * 
     * @link SparkStreamingComponent
     */
    public static final DistributionsManager SPARK_STREAMING = new DistributionsManager(SparkStreamingComponent.class.getName(),
            null, true);

    /**
     * for Spark Batch Distributions.
     * 
     * @link SparkBatchComponent
     */
    public static final DistributionsManager SPARK_BATCH = new DistributionsManager(SparkBatchComponent.class.getName(), null,
            true);

    /**
     * for Hive Distributions.
     * 
     * @link HiveComponent
     */
    public static final DistributionsManager HIVE = new DistributionsManager(HiveComponent.class.getName(), null, true);

    /**
     * for Impala Distributions.
     * 
     * @link ImpalaComponent
     */
    public static final DistributionsManager IMPALA = new DistributionsManager(ImpalaComponent.class.getName(), null, true);

    private static String cacheVersion = "Initial Cache Version"; //$NON-NLS-1$

    static {
        ServiceListener serviceListener = new ServiceListener() {

            @Override
            public void serviceChanged(ServiceEvent event) {
                cacheVersion = String.valueOf(System.currentTimeMillis());
                if (event.getType() == ServiceEvent.REGISTERED) {
                    ServiceReference<? extends Object> sr = event.getServiceReference();
                    if (sr != null) {
                        BundleContext bc = getBundleContext();
                        Object obj = bc.getService(sr);
                        if (obj instanceof HadoopComponent) {
                            hadoopDistributions = null;
                        }
                    }
                } else if (event.getType() == ServiceEvent.UNREGISTERING) {
                    ServiceReference<? extends Object> sr = event.getServiceReference();
                    if (sr != null) {
                        BundleContext bc = getBundleContext();
                        Object obj = bc.getService(sr);
                        if (obj instanceof HadoopComponent) {
                            hadoopDistributions = null;
                        }
                    }
                }
            }
        };
        try {
            getBundleContext().addServiceListener(serviceListener, "(" //$NON-NLS-1$
                    + Constants.OBJECTCLASS + "=" //$NON-NLS-1$
                    + HadoopComponent.class.getName() + ")"); //$NON-NLS-1$
        } catch (InvalidSyntaxException e) {
            ExceptionHandler.process(e);
        }
    }

    /**
     *
     * Builds a {@link HadoopComponent} distribution.
     *
     * @param pDistribution The name of the distribution
     * @param pVersion The name of the version
     * @return an implementation of {@link HadoopComponent}.
     * @throws Exception
     */
    public static HadoopComponent buildDistribution(String pDistribution, String pVersion) throws Exception {
        final BundleContext bc = FrameworkUtil.getBundle(DistributionFactory.class).getBundleContext();

        // find hadoop components
        if (hadoopDistributions == null) {
            synchronized (HadoopDistributionsHelper.class) {
                if (hadoopDistributions == null) {
                    try {
                        hadoopDistributions = bc.getServiceReferences(HadoopComponent.class, null);
                    } catch (InvalidSyntaxException e) {
                        CommonExceptionHandler.process(e);
                    }
                }
            }
        }

        for (ServiceReference<HadoopComponent> sr : hadoopDistributions) {
            HadoopComponent np = bc.getService(sr);
            String thatDistribution = np.getDistribution();
            if (Constant.DISTRIBUTION_CUSTOM.equals(thatDistribution) && thatDistribution.equals(pDistribution)) {
                return np;
            }
            if (thatDistribution != null && thatDistribution.equals(pDistribution)) {
                String thatVersion = np.getVersion();
                if (thatVersion != null && thatVersion.equals(pVersion)) {
                    return np;
                }
            }
        }
        throw new Exception("The distribution " + pDistribution + " with the version " + pVersion + " doesn't exist."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    private static BundleContext getBundleContext() {
        return FrameworkUtil.getBundle(DistributionFactory.class).getBundleContext();
    }

    public static String getCacheVersion() {
        return cacheVersion;
    }
}
