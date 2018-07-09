package org.talend.hadoop.distribution.hdpx;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class HortonworksDynamicDistributionPlugin extends Plugin {

    private static HortonworksDynamicDistributionPlugin instance;

    public static HortonworksDynamicDistributionPlugin getInstance() {
        return instance;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        super.start(bundleContext);
        instance = this;
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        super.stop(bundleContext);
    }

}
