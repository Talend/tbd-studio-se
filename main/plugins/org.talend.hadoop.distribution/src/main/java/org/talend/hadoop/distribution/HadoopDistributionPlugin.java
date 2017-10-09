package org.talend.hadoop.distribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.librariesmanager.model.ModulesNeededProvider;

public class HadoopDistributionPlugin extends Plugin {

    public static final String PLUGIN_ID = "org.talend.hadoop.distribution"; //$NON-NLS-1$

    /**
     * Recording all defined standard modules, not include modules defined in dynamic hadoop distribution versions;<br>
     * These modules will be used by dynamic hadoop distribution if needed.
     */
    private Map<String, ModuleNeeded> existingModuleMap = new HashMap<>();

    private static HadoopDistributionPlugin instance;

    public static HadoopDistributionPlugin getInstance() {
        return instance;
    }

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        instance = this;
        try {
            List<ModuleNeeded> modules = ModulesNeededProvider.getModulesNeededForApplication();
            if (modules != null && !modules.isEmpty()) {
                for (ModuleNeeded module : modules) {
                    String id = module.getId();
                    if (id != null && !id.isEmpty()) {
                        existingModuleMap.put(id, module);
                        String name = module.getModuleName();
                        existingModuleMap.put(name, module);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    public Map<String, ModuleNeeded> getExistingModuleMap() {
        return existingModuleMap;
    }

}
