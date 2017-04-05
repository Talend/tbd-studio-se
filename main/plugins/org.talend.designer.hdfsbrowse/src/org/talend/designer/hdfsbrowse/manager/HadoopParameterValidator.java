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

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.talend.designer.hdfsbrowse.constants.HadoopPatternConstants;

/**
 * created by ycbai on 2013-4-3 Detailled comment
 *
 */
public class HadoopParameterValidator {

    /**
     * Check whether or not the namenode uri is valid.
     * <P>
     * DOC ycbai Comment method "isValidNamenodeURI".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidNamenodeURI(String nameNodeURI) {
        try {
            URI.create(nameNodeURI);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Check whether or not the jobtracker uri is valid.
     * <p>
     * DOC ycbai Comment method "isValidJobtrackerURI".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidJobtrackerURI(String nameNodeURI) {
        return isValid(nameNodeURI, HadoopPatternConstants.JOB_TRACKER_URI);
    }

    /**
     * Check whether or not the user name is valid.
     * <p>
     * DOC ycbai Comment method "isValidUserName".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidUserName(String userName) {
        return isValid(userName, HadoopPatternConstants.USER_NAME);
    }

    /**
     * Check whether or not the group is valid.
     * <p>
     * DOC ycbai Comment method "isValidGroup".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidGroup(String group) {
        return isValid(group, HadoopPatternConstants.WITHOUT_SPACE);
    }

    /**
     * Check whether or not the principal is valid.
     * <p>
     * DOC ycbai Comment method "isValidPrincipal".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidPrincipal(String principal) {
        return isValid(principal, HadoopPatternConstants.WITHOUT_SPACE);
    }

    public static boolean isValidMaprTPassword(String maprTPassword) {
        return isValid(maprTPassword, HadoopPatternConstants.WITHOUT_SPACE);
    }

    public static boolean isValidMaprTCluster(String maprTCluster) {
        return isValid(maprTCluster, HadoopPatternConstants.WITHOUT_SPACE);
    }

    public static boolean isValidMaprTDuration(String maprTDuration) {
        return isValid(maprTDuration, HadoopPatternConstants.WITHOUT_SPACE);
    }

    public static boolean isValidWebHDFSSSLTrustStorePassword(String trustStorePassword) {
        return isValid(trustStorePassword, HadoopPatternConstants.WITHOUT_SPACE);
    }

    /**
     * Check whether or not the realm is valid.
     * <p>
     * DOC ycbai Comment method "isValidRealm".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidRealm(String realm) {
        return isValid(realm, HadoopPatternConstants.WITHOUT_SPACE);
    }

    /**
     * Check whether or not the host name is valid.
     * <p>
     * DOC ycbai Comment method "isValidHostName".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidHostName(String hostName) {
        return isValid(hostName, HadoopPatternConstants.HOST_NAME);
    }

    /**
     * Check whether or not the host name is valid.
     * <p>
     * DOC ycbai Comment method "isValidPort".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidPort(String port) {
        return isValid(port, HadoopPatternConstants.NUMBER);
    }

    /**
     * Check whether or not the database is valid.
     * <p>
     * DOC ycbai Comment method "isValidDatabase".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidDatabase(String database) {
        return isValid(database, HadoopPatternConstants.DATABASE);
    }

    /**
     * Check whether or not the oozie end point is valid.
     * <p>
     * DOC ycbai Comment method "isValidOozieEndPoint".
     *
     * @param nameNodeURI
     * @return
     */
    public static boolean isValidOozieEndPoint(String endPoint) {
        return isValid(endPoint, HadoopPatternConstants.OOZIE_END_POINT);
    }

    public static boolean isValidHadoopConnectionName(String connectionName) {
        return isValid(connectionName, HadoopPatternConstants.HADOOP_CONNECTION_NAME);
    }

    private static boolean isValid(String input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);

        return m.matches();
    }

}
