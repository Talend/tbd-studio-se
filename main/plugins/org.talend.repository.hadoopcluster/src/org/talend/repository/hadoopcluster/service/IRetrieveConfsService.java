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
package org.talend.repository.hadoopcluster.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * created by ycbai on 2015年6月1日 Detailled comment
 *
 */
public interface IRetrieveConfsService {

    public final static String CONF_FILE_EXT = ".xml"; //$NON-NLS-1$

    public void reload();

    public Map<String, Map<String, String>> getConfsMap() throws MalformedURLException;

    public String getConfValue(String confType, String key) throws Exception;

    public List<String> getAllServices() throws Exception;

    public String exportConfs(List<String> services) throws Exception;

    public void applyFilter(List<String> filterProperties);

}
