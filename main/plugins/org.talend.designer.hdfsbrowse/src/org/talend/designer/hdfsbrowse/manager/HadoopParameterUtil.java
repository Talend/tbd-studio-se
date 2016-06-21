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
package org.talend.designer.hdfsbrowse.manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.talend.designer.hdfsbrowse.constants.HadoopPatternConstants;

/**
 * created by ycbai on 2013-2-1 Detailled comment
 * 
 */
public class HadoopParameterUtil {

    public final static String DEFAULT_NAMENODE_PORT = "8020"; //$NON-NLS-1$

    public final static String CONFS_JAR_EXT = ".jar"; //$NON-NLS-1$

    public final static String CONFS_JAR_PREFIX = "hadoop-conf-"; //$NON-NLS-1$

    /**
     * DOC ycbai Comment method "getHostNameFromNameNodeURI".
     * 
     * Get host name from name node uri.
     * 
     * @param nameNodeURI
     * @return
     */
    public static String getHostNameFromNameNodeURI(String nameNodeURI) {
        String hostName = ""; //$NON-NLS-1$
        if (nameNodeURI == null) {
            return hostName;
        }

        Matcher matcher = Pattern.compile(HadoopPatternConstants.NAME_NODE_URI_WITH_PORT).matcher(nameNodeURI);
        if (matcher.find()) {
            hostName = matcher.group(2);
        }
        if (StringUtils.isEmpty(hostName)) {
            matcher = Pattern.compile(HadoopPatternConstants.NAME_NODE_URI_WITHOUT_PORT).matcher(nameNodeURI);
            if (matcher.find()) {
                hostName = matcher.group(2);
            }
        }
        if (StringUtils.isEmpty(hostName)) {
            matcher = Pattern.compile(HadoopPatternConstants.NAME_NODE_URI_LOCAL).matcher(nameNodeURI);
            if (matcher.find()) {
                hostName = "localhost"; //$NON-NLS-1$
            }
        }

        return hostName;
    }

    public static String getPortFromNameNodeURI(String nameNodeURI) {
        String port = DEFAULT_NAMENODE_PORT;
        if (nameNodeURI == null) {
            return port;
        }

        Matcher matcher = Pattern.compile(HadoopPatternConstants.NAME_NODE_URI_WITH_PORT).matcher(nameNodeURI);
        if (matcher.find()) {
            port = matcher.group(3);
        }

        return port;
    }

    public static String getConfsJarDefaultName(String uid) {
        return CONFS_JAR_PREFIX.concat(uid).concat(CONFS_JAR_EXT);
    }

}
