package org.talend.hadoop.distribution.synapse;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class SynapsePlugin extends Plugin {

    private static SynapsePlugin instance;

    public static SynapsePlugin getInstance() {
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
