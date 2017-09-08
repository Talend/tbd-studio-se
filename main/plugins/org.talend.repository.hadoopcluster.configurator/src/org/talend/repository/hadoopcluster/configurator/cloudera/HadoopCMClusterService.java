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
package org.talend.repository.hadoopcluster.configurator.cloudera;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.BadRequestException;

import org.apache.cxf.jaxrs.ext.multipart.InputStreamDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;

import com.cloudera.api.v3.ServicesResourceV3;

/**
 * created by bchen on May 28, 2015 Detailled comment
 *
 */
public class HadoopCMClusterService implements HadoopClusterService {

    private static final String SUPPORT_FILE = "site.xml"; //$NON-NLS-1$

    private ServicesResourceV3 cluster;

    private String serviceName;

    private Map<String, Configuration> confs;// only contains *-site.xml

    /**
     * DOC bchen HadoopCMClusterService constructor comment.
     * 
     * @param cluster
     */
    public HadoopCMClusterService(String serviceName, ServicesResourceV3 cluster, List<String> blacklistParams) {
        this.serviceName = serviceName;
        this.cluster = cluster;
        init(blacklistParams);
    }

    /**
     * DOC bchen Comment method "init".
     */
    private void init(List<String> blacklistParams) {
        confs = new HashMap<>();
        InputStreamDataSource clientConfig = null;
        try {
            clientConfig = cluster.getClientConfig(serviceName);
        } catch (BadRequestException e) {
            // e.printStackTrace();
            // ignore the exception, because some service don't contains configuration
        }
        if (clientConfig == null) {
            return;
        }
        File directory = new File(System.getProperty("java.io.tmpdir"), "Talend_Hadoop_Wizard_" + serviceName //$NON-NLS-1$ //$NON-NLS-2$
                + String.valueOf(new Date().getTime()) + Thread.currentThread().getId());
        try {
            ZipInputStream zipInputStream = new ZipInputStream(clientConfig.getInputStream());
            ZipEntry configInputZipEntry = null;
            while ((configInputZipEntry = zipInputStream.getNextEntry()) != null) {
                String configFile = configInputZipEntry.getName();
                if (configFile.contains("/")) { //$NON-NLS-1$
                    configFile = configFile.substring(configFile.lastIndexOf("/") + 1, configFile.length()); //$NON-NLS-1$
                }
                if (!configFile.endsWith(SUPPORT_FILE)) {
                    continue;
                }
                directory.mkdirs();
                File file = new File(directory, configFile);
                BufferedWriter configOutput = null;
                try {
                    int read;
                    configOutput = new BufferedWriter(new FileWriter(file));
                    while (zipInputStream.available() > 0) {
                        if ((read = zipInputStream.read()) != -1) {
                            configOutput.write(read);
                        }
                    }
                } finally {
                    if (configOutput != null) {
                        configOutput.close();
                    }
                }
                Configuration conf = new Configuration(false);
                conf.addResource(new Path(file.toURI()));
                if (blacklistParams != null && blacklistParams.size() > 0) {
                    Configuration filteredConf = new Configuration(false);
                    Iterator<Entry<String, String>> iterator = conf.iterator();
                    while (iterator.hasNext()) {
                        Entry<String, String> next = iterator.next();
                        if (blacklistParams.contains(next.getKey())) {
                            continue;
                        }
                        filteredConf.set(next.getKey(), next.getValue());
                    }
                    conf = filteredConf;
                }
                confs.put(configFile, conf);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        for (String key : confs.keySet()) {
            confMapping.putAll(getConfiguration(key));
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
     * @see org.talend.repository.hadoopcluster.configurator.HadoopClusterService#getConfiguration(java.lang.String)
     */
    private Map<String, String> getConfiguration(String confName) {
        Configuration conf = confs.get(confName);
        return conf.getValByRegex(".*"); //$NON-NLS-1$;
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
        File confFile = new File(folderPath, confName);
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
        return confs.keySet();
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
