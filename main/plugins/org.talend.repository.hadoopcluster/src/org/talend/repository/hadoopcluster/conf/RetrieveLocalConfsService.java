// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.conf;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.dom4j.Document;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.io.FilesUtils;
import org.talend.core.hadoop.conf.EHadoopConfs;
import org.talend.hadoop.distribution.model.DistributionVersion;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * created by ycbai on 2015年6月1日 Detailled comment
 *
 */
public class RetrieveLocalConfsService implements IRetrieveConfsService {

    private String confsDirPath;

    private Map<String, Map<String, String>> confsMap;

    private List<String> filterProps = new ArrayList<>();

    DistributionVersion distributionVersion;

    public RetrieveLocalConfsService(String confsDirPath, DistributionVersion distributionVersion) {
        this.confsDirPath = confsDirPath;
        this.distributionVersion = distributionVersion;
    }

    @Override
    public Map<String, Map<String, String>> getConfsMap() throws MalformedURLException {
        if (confsMap != null) {
            return confsMap;
        }
        confsMap = new LinkedHashMap<>();
        List<File> confFiles = FilesUtils.getFilesFromFolderByName(new File(confsDirPath), null, new String[] { CONF_FILE_EXT },
                null, true);
        Map<String, File> confFilesMap = new HashMap<>();
        for (File confFile : confFiles) {
            confFilesMap.put(confFile.getName(), confFile);
        }
        EHadoopConfs[] confs = EHadoopConfs.values();

        List<String> supportConfs = getSupportConfsList();
        for (EHadoopConfs conf : confs) {
            if (supportConfs != null && !supportConfs.contains(conf.getName())) {
                continue;
            }
            String[] confFileNames = conf.getConfFiles();
            Configuration configuration = new Configuration();
            boolean addedResource = false;
            for (String confFileName : confFileNames) {
                File confFile = confFilesMap.get(confFileName);
                if (confFile != null) {
                    configuration.addResource(confFile.toURI().toURL());
                    addedResource = true;
                } else {
                    addedResource = false;
                }
            }
            if (addedResource) {
                confsMap.put(conf.getName(), configurationToMap(configuration));
            }
        }
        return confsMap;
    }

    private List<String> getSupportConfsList() {
        try {
            List<String> supportConfs = new ArrayList<String>();
            String confStr = distributionVersion.getDefaultConfig(distributionVersion.getDistribution().getName());
            JSONArray jsonArray = new JSONObject(confStr).getJSONArray("HADOOP_CONFIG_LIST"); //$NON-NLS-1$
            for (int i = 0; i < jsonArray.length(); i++) {
                Object obj = jsonArray.get(i);
                if (obj instanceof String) {
                    supportConfs.add((String) obj);
                }
            }
            if (!supportConfs.isEmpty()) {
                return supportConfs;
            }
        } catch (JSONException e) {
            ExceptionHandler.process(e);
        }
        return null;
    }

    private Map<String, String> configurationToMap(Configuration configuration) {
        Map<String, String> cMap = new HashMap<>();
        Iterator<Entry<String, String>> confsIter = configuration.iterator();
        while (confsIter.hasNext()) {
            Entry<String, String> confsEntry = confsIter.next();
            if (filterProps != null && filterProps.contains(confsEntry.getKey())) {
                continue;
            }
            cMap.put(confsEntry.getKey(), confsEntry.getValue());
        }
        return cMap;
    }

    @Override
    public void reload() {
        confsMap = null;
    }

    @Override
    public String getConfValue(String confType, String key) throws Exception {
        Map<String, String> configuration = getConfsMap().get(confType);
        if (configuration != null) {
            return configuration.get(key);
        }
        return null;
    }

    @Override
    public List<String> getAllServices() throws Exception {
        List<String> services = new ArrayList<>();
        Set<String> confs = getConfsMap().keySet();
        services.addAll(confs);
        return services;
    }

    @Override
    public String exportConfs(List<String> services) throws Exception {
        final Set<String> confs = new HashSet<>();
        for (String service : services) {
            EHadoopConfs conf = EHadoopConfs.getConfByName(service);
            confs.addAll(Arrays.asList(conf.getConfFiles()));
        }
        if (confs.size() > 0) {
            File sourceFolder = new File(confsDirPath);
            String targetFolderPath = HadoopConfsUtils.getConfsSitesTempFolder();
            File targetFolder = new File(targetFolderPath);
            final FileFilter sourceFileFilter = new FileFilter() {

                @Override
                public boolean accept(File file) {
                    return confs.contains(file.getName());
                }
            };
            FilesUtils.copyFolder(sourceFolder, targetFolder, true, null, sourceFileFilter, true);
            applyFilterInConfFile(targetFolder, filterProps);
            return targetFolderPath;
        }
        return null;
    }

    @Override
    public void applyFilter(List<String> filterProperties) {
        filterProps = filterProperties;
    }

    private void applyFilterInConfFile(File confFile, List<String> filterProperties) {
        if (confFile == null || filterProperties == null || filterProperties.size() == 0) {
            return;
        }
        if (confFile.isDirectory()) {
            File[] confFiles = confFile.listFiles();
            for (File file : confFiles) {
                applyFilterInConfFile(file, filterProperties);
            }
        }
        if (confFile.isFile()) {
            boolean modified = false;
            Document doc = HadoopConfsResolveUtils.readConfFile(confFile);
            for (String property : filterProperties) {
                boolean removed = HadoopConfsResolveUtils.removePropertyElement(doc, property);
                if (!modified && removed) {
                    modified = true;
                }
            }
            if (modified) {
                HadoopConfsResolveUtils.writeConfFile(confFile, doc);
            }
        }
    }

}
