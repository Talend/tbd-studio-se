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
package org.talend.repository.hdfs.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSModelUtil {

    private static Logger log = Logger.getLogger(HDFSModelUtil.class);

    /**
     * DOC ycbai Comment method "convert2HDFSConnectionBean".
     * 
     * Convert HDFSConnection to HDFSConnectionBean
     * 
     * @param connection
     * @return
     */
    public static HDFSConnectionBean convert2HDFSConnectionBean(HDFSConnection connection) {
        ContextType contextType = null;
        if (connection.isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
        }
        HDFSConnectionBean bean = new HDFSConnectionBean();
        bean.setContextType(contextType);
        try {
            HadoopClusterConnection hcConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(connection);
            if (hcConnection != null) {
                ContextType parentContextType = null;
                if (hcConnection.isContextMode()) {
                    parentContextType = ConnectionContextHelper.getContextTypeForContextMode(hcConnection);
                }
                bean.setParentContextType(parentContextType);
                BeanUtils.copyProperties(bean, hcConnection);
                Map<String, Object> properties = bean.getAdditionalProperties();
                Map<String, Set<String>> customVersionMap = HCVersionUtil.getCustomVersionMap(hcConnection);
                Iterator<Entry<String, Set<String>>> iter = customVersionMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Set<String>> entry = iter.next();
                    String groupName = entry.getKey();
                    Set<String> jars = entry.getValue();
                    if (jars != null && jars.size() > 0) {
                        properties.put(groupName, jars);
                    }
                }
            }
            bean.setUserName(connection.getUserName());
            bean.setFieldSeparator(connection.getFieldSeparator());
            bean.setRowSeparator(connection.getRowSeparator());
            bean.setRelativeHadoopClusterId(connection.getRelativeHadoopClusterId());
            Map<String, Object> configurations = bean.getConfigurations();
            List<Map<String, Object>> hadoopProperties = HadoopRepositoryUtil.getHadoopPropertiesFullList(connection,
                    connection.getHadoopProperties(), false, true);
            for (Map<String, Object> propMap : hadoopProperties) {
                String key = TalendQuoteUtils.removeQuotesIfExist(String.valueOf(propMap.get("PROPERTY"))); //$NON-NLS-1$
                String value = TalendQuoteUtils.removeQuotesIfExist(String.valueOf(propMap.get("VALUE"))); //$NON-NLS-1$
                if (StringUtils.isNotEmpty(key) && value != null) {
                    configurations.put(key, value);
                }
            }
        } catch (Exception e) {
            log.error("Convert failure from HDFSConnection to HDFSConnectionBean", e);
        }

        return bean;
    }
}
