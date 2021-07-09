package org.talend.designer.hdfsbrowse.hadoop.service.check.provider;

import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.hadoop.HadoopClassLoaderFactory2;
import org.talend.core.hadoop.conf.EHadoopConfProperties;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.AbstractCheckedServiceProvider;


public class CheckedKnoxResourceManagerProvider extends AbstractCheckedServiceProvider {

    @Override
    protected Object check(HadoopServiceProperties serviceProperties, ClassLoader classLoader) throws Exception {
        
        String knoxUrl = serviceProperties.getKnoxURL();
        String knoxUser = serviceProperties.getKnoxUser();
        String knoxPassword = serviceProperties.getKnoxPassword();
        
        Object knoxYarn = ReflectionUtils.newInstance(
                "org.talend.bigdata.launcher.knox.yarn.KnoxYarn", classLoader, new Object[] {knoxUser, knoxPassword,knoxUrl}, String.class, String.class, String.class); //$NON-NLS-1$
        
        Object response = ReflectionUtils.invokeMethod(knoxYarn, "checkService", new Object[0]); //$NON-NLS-1$

        return response; //$NON-NLS-1$
    }

    @Override
    protected ClassLoader getClassLoader(HadoopServiceProperties serviceProperties) {
        ClassLoader loader = null;
        if (serviceProperties.isCustom()) {
            String clusterId = null;
            if (serviceProperties.isUseCustomConfs() && serviceProperties.isSetHadoopConf()) {
                clusterId = serviceProperties.getRelativeHadoopClusterId();
            }
            loader = HadoopClassLoaderFactory2.getHadoopCustomClassLoader(serviceProperties.getUid(), clusterId,
                    EHadoopCategory.HDFS, serviceProperties.getCustomJars(), serviceProperties.isUseKrb());
        } else {
            loader = HadoopClassLoaderFactory2.getHDFSClassLoader(serviceProperties.getRelativeHadoopClusterId(),
                    serviceProperties.getDistribution(), serviceProperties.getVersion(), serviceProperties.isUseKrb());
            loader = addCustomConfsJarIfNeeded(loader, serviceProperties, EHadoopCategory.HDFS);
        }
        return loader;
    }

}
