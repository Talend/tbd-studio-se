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
package org.talend.repository.hadoopcluster.util;

import org.talend.core.model.process.IElement;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IProcess;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.utils.JavaProcessUtil;

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

}
