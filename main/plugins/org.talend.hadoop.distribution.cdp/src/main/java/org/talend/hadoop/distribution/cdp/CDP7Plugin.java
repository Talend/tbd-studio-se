package org.talend.hadoop.distribution.cdp;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class CDP7Plugin extends Plugin {

    private static CDP7Plugin instance;

    public static CDP7Plugin getInstance() {
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
