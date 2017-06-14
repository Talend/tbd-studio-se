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
package org.talend.repository.hcatalog.ui.handler;

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
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * created by ldong on Mar 17, 2015 Detailled comment
 *
 */
public class HCatalogContextHandler extends AbstractRepositoryContextHandler {

    private static final ECodeLanguage LANGUAGE = LanguageManager.getCurrentLanguage();

    @Override
    public boolean isRepositoryConType(Connection connection) {
        return connection instanceof HCatalogConnection;
    }

    @Override
    public List<IContextParameter> createContextParameters(String prefixName, Connection connection, Set<IConnParamName> paramSet) {
        List<IContextParameter> varList = new ArrayList<IContextParameter>();
        if (connection instanceof HCatalogConnection) {
            HCatalogConnection conn = (HCatalogConnection) connection;

            String paramPrefix = prefixName + ConnectionContextHelper.LINE;
            String paramName = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    EHadoopParamName hcatalogParam = (EHadoopParamName) param;
                    paramName = paramPrefix + hcatalogParam;
                    switch (hcatalogParam) {
                    case HCatalogHostName:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getHostName());
                        break;
                    case HCatalogPort:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getPort());
                        break;
                    case HCatalogUser:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getUserName());
                        break;
                    case HCatalogPassword:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getPassword(),
                                JavaTypesManager.PASSWORD);
                        break;
                    case HCatalogKerPrin:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getKrbPrincipal());
                        break;
                    case HCatalogRealm:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getKrbRealm());
                        break;
                    case HCatalogDatabase:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getDatabase());
                        break;
                    case HcataLogRowSeparator:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getRowSeparator());
                        break;
                    case HcatalogFileSeparator:
                        ConnectionContextHelper.createParameters(varList, paramName, conn.getFieldSeparator());
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
        if (connection instanceof HCatalogConnection) {
            HCatalogConnection conn = (HCatalogConnection) connection;
            String originalVariableName = prefixName + ConnectionContextHelper.LINE;
            String hadoopVariableName = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    EHadoopParamName hcatalogConnectionParam = (EHadoopParamName) param;
                    originalVariableName = prefixName + ConnectionContextHelper.LINE;
                    hadoopVariableName = originalVariableName + hcatalogConnectionParam;
                    matchContextForAttribues(conn, hcatalogConnectionParam, hadoopVariableName);
                }
            }
            String hadoopProperties = conn.getHadoopProperties();
            List<Map<String, Object>> propertiesAfterContext = transformHadoopPropertiesForContextMode(
                    HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties), prefixName);
            conn.setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(propertiesAfterContext));
        }
    }

    @Override
    public void setPropertiesForExistContextMode(Connection connection, Set<IConnParamName> paramSet,
            Map<ContextItem, List<ConectionAdaptContextVariableModel>> adaptMap) {
        if (connection == null) {
            return;
        }
        if (connection instanceof HCatalogConnection) {
            HCatalogConnection hcatalogConn = (HCatalogConnection) connection;
            ContextItem currentContext = null;
            for (IConnParamName param : paramSet) {
                if (param instanceof EHadoopParamName) {
                    String hcatalogVariableName = null;
                    EHadoopParamName hcatalogParam = (EHadoopParamName) param;
                    if (adaptMap != null && adaptMap.size() > 0) {
                        for (Map.Entry<ContextItem, List<ConectionAdaptContextVariableModel>> entry : adaptMap.entrySet()) {
                            currentContext = entry.getKey();
                            List<ConectionAdaptContextVariableModel> modelList = entry.getValue();
                            for (ConectionAdaptContextVariableModel model : modelList) {
                                if (model.getValue().equals(hcatalogParam.name())) {
                                    hcatalogVariableName = model.getName();
                                    break;
                                }
                            }
                        }
                    }
                    if (hcatalogVariableName != null) {
                        hcatalogVariableName = getCorrectVariableName(currentContext, hcatalogVariableName, hcatalogParam);
                        matchContextForAttribues(hcatalogConn, hcatalogParam, hcatalogVariableName);
                    }
                }

            }
            matchAdditionProperties(hcatalogConn, adaptMap);
        }
    }

    @Override
    protected void matchContextForAttribues(Connection conn, IConnParamName paramName, String hcatalogVariableName) {
        HCatalogConnection hcatalogConn = (HCatalogConnection) conn;
        EHadoopParamName hcatalogParam = (EHadoopParamName) paramName;
        switch (hcatalogParam) {
        case HCatalogHostName:
            hcatalogConn.setHostName(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HCatalogPort:
            hcatalogConn.setPort(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HCatalogUser:
            hcatalogConn.setUserName(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HCatalogPassword:
            hcatalogConn.setPassword(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HCatalogKerPrin:
            hcatalogConn.setKrbPrincipal(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HCatalogRealm:
            hcatalogConn.setKrbRealm(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HCatalogDatabase:
            hcatalogConn.setDatabase(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HcataLogRowSeparator:
            hcatalogConn.setRowSeparator(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        case HcatalogFileSeparator:
            hcatalogConn.setFieldSeparator(ContextParameterUtils.getNewScriptCode(hcatalogVariableName, LANGUAGE));
            break;
        default:
        }
    }

    @Override
    protected void matchAdditionProperties(Connection conn, Map<ContextItem, List<ConectionAdaptContextVariableModel>> adaptMap) {
        if (conn instanceof HCatalogConnection) {
            HCatalogConnection hadoopConn = (HCatalogConnection) conn;
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
        if (connection instanceof HCatalogConnection) {
            HCatalogConnection conn = (HCatalogConnection) connection;
            String hostName = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getHostName()));
            String port = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType, conn.getPort()));
            String userName = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getUserName()));
            String password = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getValue(conn.getPassword(), false)));
            String kerberosPrin = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getKrbPrincipal()));
            String kerberosRealm = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getKrbRealm()));
            String database = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getDatabase()));
            String rowSeparator = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getRowSeparator()));
            String fileSeparator = TalendQuoteUtils.removeQuotes(ContextParameterUtils.getOriginalValue(contextType,
                    conn.getFieldSeparator()));

            String hadoopProperties = conn.getHadoopProperties();
            List<Map<String, Object>> propertiesAfterRevert = transformContextModeToHadoopProperties(
                    HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties), contextType);
            conn.setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(propertiesAfterRevert));

            conn.setHostName(hostName);
            conn.setPort(port);
            conn.setUserName(userName);
            conn.setPassword(password);
            conn.setKrbPrincipal(kerberosPrin);
            conn.setKrbRealm(kerberosRealm);
            conn.setDatabase(database);
            conn.setRowSeparator(rowSeparator);
            conn.setFieldSeparator(fileSeparator);
        }
    }

    @Override
    public Set<String> getConAdditionPropertiesForContextMode(Connection conn) {
        Set<String> conVarList = new HashSet<String>();
        if (conn instanceof HCatalogConnection) {
            HCatalogConnection hcatalogConn = (HCatalogConnection) conn;
            conVarList = getConAdditionProperties(HadoopRepositoryUtil
                    .getHadoopPropertiesList(hcatalogConn.getHadoopProperties()));
        }
        return conVarList;
    }

}
