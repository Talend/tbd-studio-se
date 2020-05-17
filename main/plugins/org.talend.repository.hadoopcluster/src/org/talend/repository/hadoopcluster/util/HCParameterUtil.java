// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.util;

import org.apache.commons.lang.StringUtils;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.core.utils.JavaProcessUtil;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * created by ycbai on 2013-5-28 Detailled comment
 *
 */
public class HCParameterUtil {

    public static String getParameterRealValue(IProcess process, IElement element, String paramName) {
        return getParameterRealValue(process, element, paramName, false);
    }

    public static String getParameterRealValue(IProcess process, IElement element, String paramName, boolean useDefaultContext) {
        if (element != null && paramName != null) {
            IElementParameter param = element.getElementParameter(paramName);
            if (param != null) {
                Object o = param.getValue();
                if (o instanceof String || o instanceof Boolean || o instanceof Integer || o instanceof Long
                        || o instanceof Character) {
                    String value = String.valueOf(o);

                    if (useDefaultContext) {
                        value = JavaProcessUtil.getRealParamValue(process, value);
                    } else {
                        value = JavaProcessUtil.getRealParamValueByRunProcess(process, value);
                    }

                    if (value != null) {
                        value = TalendQuoteUtils.removeQuotes(value);
                    }

                    return value;
                }
            }
        }
        return null;
    }

    public static boolean isOverrideHadoopConfs(HadoopClusterConnection connection) {
        String strIsOverride = connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_SET_HADOOP_CONF);
        if (StringUtils.isBlank(strIsOverride)) {
            return false;
        }
        ContextType contextType = null;
        if (connection.isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection, true);
        }
        if (contextType != null) {
            strIsOverride = ContextParameterUtils.getOriginalValue(contextType, strIsOverride);
        }
        return Boolean.valueOf(strIsOverride);
    }

    public static String getHadoopConfSpecificJar(HadoopClusterConnection connection, boolean getOrigialValue) {
        String confJarPath = connection.getParameters().get(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CONF_SPECIFIC_JAR);
        if (!getOrigialValue) {
            return confJarPath;
        }
        if (StringUtils.isBlank(confJarPath)) {
            return null;
        }
        ContextType contextType = null;
        if (connection.isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection, true);
        }
        if (contextType != null) {
            confJarPath = ContextParameterUtils.getOriginalValue(contextType, confJarPath);
        }
        return confJarPath;
    }

}
