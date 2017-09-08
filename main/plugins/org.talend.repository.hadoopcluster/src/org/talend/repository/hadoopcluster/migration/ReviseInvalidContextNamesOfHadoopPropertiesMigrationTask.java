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
package org.talend.repository.hadoopcluster.migration;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * 
 * created by ycbai on 2016年7月8日 Detailled comment
 *
 */
public class ReviseInvalidContextNamesOfHadoopPropertiesMigrationTask extends AbstractItemMigrationTask {

    private final static String PROP_VALUE_KEY = "VALUE"; //$NON-NLS-1$

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2016, 7, 8, 12, 0, 0);
        return gc.getTime();
    }

    @Override
    public ExecutionResult execute(Item item) {
        boolean modified = false;
        HadoopConnection hadoopConnection = new HadoopConnection(item);
        String hadoopProperties = hadoopConnection.getHadoopProperties();
        if (hadoopProperties != null && !hadoopProperties.isEmpty()) {
            List<Map<String, Object>> hadoopPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties, true,
                    false);
            for (Map<String, Object> propMap : hadoopPropertiesList) {
                String propValue = String.valueOf(propMap.get(PROP_VALUE_KEY));
                if (ContextParameterUtils.isContainContextParam(propValue)) {
                    propValue = propValue.substring(propValue.indexOf(ContextParameterUtils.JAVA_NEW_CONTEXT_PREFIX)
                            + ContextParameterUtils.JAVA_NEW_CONTEXT_PREFIX.length());
                    if (!ContextParameterUtils.isValidParameterName(propValue)) {
                        propMap.put(
                                PROP_VALUE_KEY,
                                ContextParameterUtils.JAVA_NEW_CONTEXT_PREFIX
                                        + ContextParameterUtils.getValidParameterName(propValue));
                        modified = true;
                    }
                }
            }
            if (modified) {
                String newHadoopProperties = HadoopRepositoryUtil.getHadoopPropertiesJsonStr(hadoopPropertiesList);
                hadoopConnection.setHadoopProperties(newHadoopProperties);
            }
        }
        if (modified) {
            try {
                ProxyRepositoryFactory.getInstance().save(item, true);
                return ExecutionResult.SUCCESS_NO_ALERT;
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
                return ExecutionResult.FAILURE;
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

    class HadoopConnection {

        private Item innerItem;

        public HadoopConnection(Item innerItem) {
            this.innerItem = innerItem;
        }

        public String getHadoopProperties() {
            String hadoopProperties = null;
            if (innerItem instanceof HadoopClusterConnectionItem) {
                HadoopClusterConnectionItem hcItem = (HadoopClusterConnectionItem) innerItem;
                HadoopClusterConnection connection = (HadoopClusterConnection) hcItem.getConnection();
                if (connection.isContextMode()) {
                    hadoopProperties = connection.getHadoopProperties();
                }
            } else if (innerItem instanceof HadoopSubConnectionItem) {
                HadoopSubConnectionItem hsItem = (HadoopSubConnectionItem) innerItem;
                HadoopSubConnection connection = (HadoopSubConnection) hsItem.getConnection();
                if (connection.isContextMode()) {
                    hadoopProperties = connection.getHadoopProperties();
                }
            }
            return hadoopProperties;
        }

        public void setHadoopProperties(String hadoopProperties) {
            if (innerItem instanceof HadoopClusterConnectionItem) {
                HadoopClusterConnectionItem hcItem = (HadoopClusterConnectionItem) innerItem;
                HadoopClusterConnection connection = (HadoopClusterConnection) hcItem.getConnection();
                connection.setHadoopProperties(hadoopProperties);
            } else if (innerItem instanceof HadoopSubConnectionItem) {
                HadoopSubConnectionItem hsItem = (HadoopSubConnectionItem) innerItem;
                HadoopSubConnection connection = (HadoopSubConnection) hsItem.getConnection();
                connection.setHadoopProperties(hadoopProperties);
            }
        }

    }

}
