package org.talend.hadoop.distribution.cdp;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class CDPPlugin extends Plugin {

    private static CDPPlugin instance;

    public static CDPPlugin getInstance() {
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
