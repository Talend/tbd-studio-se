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
package org.talend.repository.hadoopcluster.configurator.ambari;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ambari.api.model.ApiConfigFile;
import org.apache.hadoop.conf.Configuration;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
public class HadoopAmbariClusterService implements HadoopClusterService {

    private static final String SUPPORT_FILE = "site"; //$NON-NLS-1$

    private Map<String, Configuration> confs;// only contains *-site.xml

    private List<ApiConfigFile> configFiles;

    /**
     * DOC bchen HadoopAmbariClusterService constructor comment.
     */
    public HadoopAmbariClusterService(List<ApiConfigFile> configFiles, List<String> blacklistParams) {
        this.configFiles = configFiles;
        init(blacklistParams);
    }

    private void init(List<String> blacklistParams) {
        confs = new HashMap<>();
        for (ApiConfigFile file : configFiles) {
            String type = file.getType();
            if (!type.endsWith(SUPPORT_FILE)) {
                continue;
            }
            Configuration conf = new Configuration(false);
            Map<String, String> properties = file.getProperties();
            for (String key : properties.keySet()) {
            	if(blacklistParams != null && blacklistParams.contains(key)){
            		continue;
            	}
                conf.set(key, properties.get(key));
            }
            confs.put(type, conf);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopClusterService#getConfiguration()
     */
    @Override
    public Map<String, String> getConfiguration() {
        Map<String, String> confMapping = new HashMap<>();
        for (ApiConfigFile file : configFiles) {
            if (file.getProperties() != null) {
                confMapping.putAll(file.getProperties());
            }
        }
        return confMapping;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.configurator.HadoopClusterService#getConfigurationByRegex(java.lang.String)
     */
    @Override
    public String getConfigurationValue(String key) {
        Map<String, String> confMapping = getConfiguration();
        return confMapping.get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.configurator.HadoopClusterService#exportConfigurationToXml(java.lang.String)
     */
    @Override
    public void exportConfigurationToXml(String folderPath) {
        for (String key : confs.keySet()) {
            exportConfigurationToXml(folderPath, key);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.hadoopcluster.configurator.HadoopClusterService#exportConfigurationToXml(java.lang.String,
     * java.lang.String)
     */
    private void exportConfigurationToXml(String folderPath, String confName) {
        Configuration conf = confs.get(confName);
        if (conf == null) {
            return;
        }
        File confFile = new File(folderPath, confName + ".xml"); //$NON-NLS-1$
        confFile.getParentFile().mkdirs();
        OutputStream os;
        try {
            os = new FileOutputStream(confFile.getAbsolutePath());
            conf.writeXml(os);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopClusterService#getConfFiles()
     */
    @Override
    public Set<String> getConfFiles() {
        Set<String> fileNames = new HashSet<>();
        for (ApiConfigFile file : configFiles) {
            fileNames.add(file.getType());
        }
        return fileNames;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopClusterService#hasConfigurations()
     */
    @Override
    public boolean hasConfigurations() {
        return confs.size() > 0;
    }

}
