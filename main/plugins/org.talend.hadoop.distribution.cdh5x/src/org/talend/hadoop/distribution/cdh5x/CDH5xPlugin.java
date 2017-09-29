package org.talend.hadoop.distribution.cdh5x;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class CDH5xPlugin extends Plugin {

    private static CDH5xPlugin instance;

    public static CDH5xPlugin getInstance() {
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
