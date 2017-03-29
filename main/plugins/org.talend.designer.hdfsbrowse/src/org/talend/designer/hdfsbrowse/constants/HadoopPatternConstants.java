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
package org.talend.designer.hdfsbrowse.constants;

/**
 * created by ycbai on 2013-2-1 Detailled comment
 *
 */
public interface HadoopPatternConstants {

    /*
     * All possible situation of Namenode Uri.
     */
    public final static String NAME_NODE_URI = "(^(file:///|maprfs:///)$)|(^(hdfs://|maprfs:///)([^:/\\\\\\s]*[^:/\\\\\\s]|(([^:/\\\\\\s]+)(:\\d+)))$)"; //$NON-NLS-1$

    /*
     * Namenode Uri with port, like "hdfs://talend-hdp-all:8020"
     */
    public final static String NAME_NODE_URI_WITH_PORT = "^(hdfs://|maprfs:///)([^:/\\\\\\s]+):(\\d+)$"; //$NON-NLS-1$

    /*
     * Namenode Uri without port, like "hdfs://talend-hdp-all"
     */
    public final static String NAME_NODE_URI_WITHOUT_PORT = "^(hdfs://|maprfs:///)([^:/\\\\\\s]*[^:\\s])$"; //$NON-NLS-1$

    /*
     * Local namenode Uri, like "file:///"
     */
    public final static String NAME_NODE_URI_LOCAL = "^(file:///|maprfs:///)$"; //$NON-NLS-1$

    /*
     * All possible situation of Jobtracker Uri.
     */
    public final static String JOB_TRACKER_URI = "local|^(([^:/\\\\\\s]+):(\\d+)|maprfs:///)$"; //$NON-NLS-1$

    /*
     * All possible situation of host name.
     */
    public final static String HOST_NAME = "[^:/\\\\\\s]+"; //$NON-NLS-1$

    /*
     * All possible situation of user name.
     */
    public final static String USER_NAME = HOST_NAME;

    /*
     * All possible situation of database.
     */
    public final static String DATABASE = HOST_NAME;

    /*
     * All possible situation of oozie end point.
     */
    public final static String OOZIE_END_POINT = "^http://([^:/\\\\\\s]*):(\\d+)/\\S+"; //$NON-NLS-1$

    public final static String WITHOUT_SPACE = "[^\\s]+"; //$NON-NLS-1$

    public final static String NUMBER = "\\d+"; //$NON-NLS-1$

    public final static String HADOOP_CONNECTION_NAME = "[^\\s\\t~!`#^&$*%@\\\\/?:;\\.()，。'￥‘”、《，》<>]+"; //$NON-NLS-1$

}
