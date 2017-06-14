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
package org.talend.repository.hdfs.ui.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.types.JavaTypesManager;
import org.talend.core.model.process.IContextParameter;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.ui.context.model.table.ConectionAdaptContextVariableModel;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.metadata.managment.ui.model.IConnParamName;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.metadata.managment.ui.wizard.context.AbstractRepositoryContextHandler;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * created by ldong on Mar 17, 2015 Detailled comment
 *
 */
public class HdfsContextHandler extends AbstractRepositoryContextHandler {

    private static final ECodeLanguage LANGUAGE = LanguageManager.getCurrentLanguage();

    @Override
    public boolean isRepositoryConType(Connection connection) {
        return connection instanceof HDFSConnection;
    }

    @Override
    public List<IContextParameter> createContextParameters(String prefixName, Connection connection, Set<IConnParamName> paramSet) {
        List<IContextParameter> varList = new ArrayList<IContextParameter>();
        if (connection instanceof HDFSConnection) {
            HDFSConnection conn = (HDFSConnection) connection;

            String paramPrefix = prefixName + ConnectionContextHelper.LINE;
            String paramName = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    EHadoopParamName hdfsParam = (EHadoopParamName) param;
                    paramName = paramPrefix + hdfsParam;
                    switch (hdfsParam) {
                    case HdfsUser:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getUserName());
                        break;
                    case HdfsRowSeparator:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getRowSeparator());
                        break;
                    case HdfsFileSeparator:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getFieldSeparator());
                        break;
                    case HdfsRowHeader:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getHeaderValue(),
                                JavaTypesManager.INTEGER);
                        break;
                    default:
                    }
                }
            }
            createHadoopPropertiesContextVariable(prefixName, varList, conn.getHadoopProperties());
        }
        return varList;
    }

    @Override
    public void setPropertiesForContextMode(String prefixName, Connection connection, Set<IConnParamName> paramSet) {
        if (connection == null) {
            return;
        }
        if (connection instanceof HDFSConnection) {
            HDFSConnection hdfsConn = (HDFSConnection) connection;
            String originalVariableName = prefixName + ConnectionContextHelper.LINE;
            String hdfsVariableName = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    EHadoopParamName hdfsConnectionParam = (EHadoopParamName) param;
                    originalVariableName = prefixName + ConnectionContextHelper.LINE;
                    hdfsVariableName = originalVariableName + hdfsConnectionParam;
                    matchContextForAttribues(hdfsConn, hdfsConnectionParam, hdfsVariableName);
                }
            }

            String hadoopProperties = hdfsConn.getHadoopProperties();
            List<Map<String, Object>> propertiesAfterContext = transformHadoopPropertiesForContextMode(
                    HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties), prefixName);
            hdfsConn.setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(propertiesAfterContext));
        }
    }

    @Override
    public void setPropertiesForExistContextMode(Connection connection, Set<IConnParamName> paramSet,
            Map<ContextItem, List<ConectionAdaptContextVariableModel>> adaptMap) {
        if (connection == null) {
            return;
        }
        if (connection instanceof HDFSConnection) {
            HDFSConnection hcatalogConn = (HDFSConnection) connection;
            ContextItem currentContext = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    String hdfsVariableName = null;
                    EHadoopParamName hdfsConnectionParam = (EHadoopParamName) param;
                    if (adaptMap != null && adaptMap.size() > 0) {
                        for (Map.Entry<ContextItem, List<ConectionAdaptContextVariableModel>> entry : adaptMap.entrySet()) {
                            currentContext = entry.getKey();
                            List<ConectionAdaptContextVariableModel> modelList = entry.getValue();
                            for (ConectionAdaptContextVariableModel model : modelList) {
                                if (model.getValue().equals(hdfsConnectionParam.name())) {
                                    hdfsVariableName = model.getName();
                                    break;
                                }
                            }
                        }
                    }
                    if (hdfsVariableName != null) {
                        hdfsVariableName = getCorrectVariableName(currentContext, hdfsVariableName, hdfsConnectionParam);
                        matchContextForAttribues(hcatalogConn, hdfsConnectionParam, hdfsVariableName);
                    }
                }

            }
            matchAdditionProperties(hcatalogConn, adaptMap);
        }
    }

    @Override
    protected void matchContextForAttribues(Connection conn, IConnParamName paramName, String hdfsVariableName) {
        HDFSConnection hdfsConn = (HDFSConnection) conn;
        EHadoopParamName hdfsParam = (EHadoopParamName) paramName;
        switch (hdfsParam) {
        case HdfsUser:
            hdfsConn.setUserName(ContextParameterUtils.getNewScriptCode(hdfsVariableName, LANGUAGE));
            break;
        case HdfsRowSeparator:
            hdfsConn.setRowSeparator(ContextParameterUtils.getNewScriptCode(hdfsVariableName, LANGUAGE));
            break;
        case HdfsFileSeparator:
            hdfsConn.setFieldSeparator(ContextParameterUtils.getNewScriptCode(hdfsVariableName, LANGUAGE));
            break;
        case HdfsRowHeader:
            hdfsConn.setHeaderValue(ContextParameterUtils.getNewScriptCode(hdfsVariableName, LANGUAGE));
            break;
        default:
        }
    }

    @Override
    protected void matchAdditionProperties(Connection conn, Map<ContextItem, List<ConectionAdaptContextVariableModel>> adaptMap) {
        if (conn instanceof HDFSConnection) {
            HDFSConnection hadoopConn = (HDFSConnection) conn;
            if (adaptMap != null && !adaptMap.isEmpty()) {
                List<Map<String, Object>> hadoopListProperties = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopConn
                        .getHadoopProperties());
                Set<String> keys = getConAdditionPropertiesForContextMode(conn);
                for (Map.Entry<ContextItem, List<ConectionAdaptContextVariableModel>> entry : adaptMap.entrySet()) {
                    List<ConectionAdaptContextVariableModel> modelList = entry.getValue();
                    for (ConectionAdaptContextVariableModel model : modelList) {
                        String propertyKey = model.getValue();
                        if (keys.contains(propertyKey)) {
                            List<Map<String, Object>> propertiesAfterContext = transformHadoopPropertiesForExistContextMode(
                                    hadoopListProperties, propertyKey, model.getName());
                            hadoopConn.setHadoopProperties(HadoopRepositoryUtil
                                    .getHadoopPropertiesJsonStr(propertiesAfterContext));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void revertPropertiesForContextMode(Connection connection, ContextType contextType) {
        if (connection instanceof HDFSConnection) {
            HDFSConnection conn = (HDFSConnection) connection;
            String hdfsUser = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getUserName()));
            String hdfsRowSeparator = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getRowSeparator()));
            String hdfsFiledSeparator = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getFieldSeparator()));
            String hdfsRowHeader = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getHeaderValue()));

            String hadoopProperties = conn.getHadoopProperties();
            List<Map<String, Object>> propertiesAfterRevert = transformContextModeToHadoopProperties(
                    HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties), contextType);
            conn.setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(propertiesAfterRevert));

            conn.setUserName(hdfsUser);
            conn.setRowSeparator(hdfsRowSeparator);
            conn.setFieldSeparator(hdfsFiledSeparator);
            conn.setHeaderValue(hdfsRowHeader);
        }
    }

    @Override
    public Set<String> getConAdditionPropertiesForContextMode(Connection conn) {
        Set<String> conVarList = new HashSet<String>();
        if (conn instanceof HDFSConnection) {
            HDFSConnection hdfsConn = (HDFSConnection) conn;
            conVarList = getConAdditionProperties(HadoopRepositoryUtil.getHadoopPropertiesList(hdfsConn.getHadoopProperties()));
        }
        return conVarList;
    }
}
