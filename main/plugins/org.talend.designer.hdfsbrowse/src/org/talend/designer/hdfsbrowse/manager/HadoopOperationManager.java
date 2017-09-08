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
package org.talend.designer.hdfsbrowse.manager;

import java.io.InputStream;
import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.talend.core.repository.model.connection.ConnectionStatus;
import org.talend.core.utils.ReflectionUtils;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.relational.TdTable;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.designer.hdfsbrowse.model.HDFSFile;
import org.talend.designer.hdfsbrowse.model.HDFSFolder;
import org.talend.designer.hdfsbrowse.model.HDFSPath;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopOperationManager {

    protected static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    protected static final String COLON = ":"; //$NON-NLS-1$

    private static HadoopOperationManager instance = new HadoopOperationManager();

    private HadoopOperationManager() {
    }

    public static HadoopOperationManager getInstance() {
        return instance;
    }

    public void loadHDFSFolderChildren(HDFSConnectionBean connection, Object fileSystem, ClassLoader classLoader,
            HDFSPath parent, String path) throws HadoopServerException {
        if (connection == null || fileSystem == null || classLoader == null || parent == null || path == null) {
            return;
        }

        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { path });
            Object[] statusList = (Object[]) ReflectionUtils.invokeMethod(fileSystem, "listStatus", new Object[] { pathObj });
            if (statusList == null) {
                return;
            }
            for (Object status : statusList) {
                if (!canAccess(connection, classLoader, status)) {
                    continue;
                }
                HDFSPath content = null;
                Object statusPath = ReflectionUtils.invokeMethod(status, "getPath", new Object[0]);
                if (statusPath == null) {
                    continue;
                }
                String pathName = (String) ReflectionUtils.invokeMethod(statusPath, "getName", new Object[0]);
                if (StringUtils.isBlank(pathName)) {
                    continue;
                }
                String absolutePath = ((URI) ReflectionUtils.invokeMethod(statusPath, "toUri", new Object[0])).toString();
                if (StringUtils.isBlank(absolutePath)) {
                    continue;
                }
                String relativePath = URI.create(absolutePath).getPath();
                if ((Boolean) ReflectionUtils.invokeMethod(status, "isDir", new Object[0])) {
                    content = new HDFSFolder(parent);
                } else {
                    content = new HDFSFile(parent);
                    content.setTable(createTable(trimFileExtention(pathName)));
                }
                content.setPath(relativePath);
                content.setValue(pathName);
                parent.addChild(content);
            }
        } catch (Exception e) {
            throw new HadoopServerException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }
    }

    public long getFileSize(Object fileSystem, ClassLoader classLoader, String filePath) throws HadoopServerException {
        long size = 0;
        try {
            Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { filePath });
            Object fileStatus = ReflectionUtils.invokeMethod(fileSystem, "getFileStatus", new Object[] { pathObj });
            size = (Long) ReflectionUtils.invokeMethod(fileStatus, "getLen", new Object[0]);
        } catch (Exception e) {
            throw new HadoopServerException(e);
        }

        return size;
    }

    public InputStream getFileContent(Object fileSystem, Object configuration, ClassLoader classLoader, String filePath)
            throws HadoopServerException {
        InputStream stream = null;
        if (fileSystem == null) {
            return null;
        }

        ClassLoader oldClassLoaderLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Object pathObj = ReflectionUtils.newInstance("org.apache.hadoop.fs.Path", classLoader, new Object[] { filePath });
            Object factory = ReflectionUtils.newInstance("org.apache.hadoop.io.compress.CompressionCodecFactory", classLoader,
                    new Object[] { configuration });
            Object codec = ReflectionUtils.invokeMethod(factory, "getCodec", new Object[] { pathObj });
            if (codec != null) {
                Object originStream = ReflectionUtils.invokeMethod(fileSystem, "open", new Object[] { pathObj });
                stream = (InputStream) ReflectionUtils.invokeMethod(codec, "createInputStream", new Object[] { originStream },
                        java.io.InputStream.class);
            } else {
                stream = (InputStream) ReflectionUtils.invokeMethod(fileSystem, "open", new Object[] { pathObj });
            }
        } catch (Exception e) {
            throw new HadoopServerException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoaderLoader);
        }

        return stream;
    }

    public InputStream getFileContent(HDFSConnectionBean connection, ClassLoader classLoader, String filePath)
            throws HadoopServerException {
        Object fileSystem = getDFS(connection, classLoader);
        if (fileSystem == null) {
            return null;
        }
        Object configuration = getConfiguration(connection, classLoader);
        return getFileContent(fileSystem, configuration, classLoader, filePath);
    }

    public ConnectionStatus testConnection(HDFSConnectionBean connection) {
        return HadoopServerUtil.testConnection(connection);
    }

    public Object getDFS(HDFSConnectionBean connectionBean) throws HadoopServerException {
        return getDFS(connectionBean, null);
    }

    public Object getDFS(HDFSConnectionBean connectionBean, ClassLoader classLoader) throws HadoopServerException {
        return HadoopServerUtil.getDFS(connectionBean, classLoader);
    }

    public Object getConfiguration(HDFSConnectionBean connectionBean, ClassLoader classLoader) throws HadoopServerException {
        return HadoopServerUtil.getConfiguration(connectionBean, classLoader);
    }

    public ClassLoader getClassLoader(HDFSConnectionBean connectionBean) {
        return HadoopServerUtil.getClassLoader(connectionBean);
    }

    private String trimFileExtention(String fileName) {
        if (fileName.indexOf(".") != -1) { //$NON-NLS-1$
            fileName = fileName.substring(0, fileName.lastIndexOf(".")); //$NON-NLS-1$
        }
        return fileName;
    }

    private TdTable createTable(String tableName) {
        TdTable table = RelationalFactory.eINSTANCE.createTdTable();
        table.setName(tableName);
        table.setLabel(tableName);

        return table;
    }

    private boolean canAccess(HDFSConnectionBean connection, ClassLoader classLoader, Object status) throws HadoopServerException {
        if (status == null) {
            return false;
        }
        String userName = null;
        String group = null;
        if (connection != null) {
            userName = connection.getUserName();
            group = connection.getGroup();
        }
        if (StringUtils.trimToNull(userName) == null) {
            userName = HadoopServerUtil.extractUsername(connection, classLoader);
        }
        if (StringUtils.trimToNull(group) == null) {
            group = HadoopServerUtil.extractGroups(connection, classLoader);
        }

        return HadoopServerUtil.hasReadAuthority(status, userName, group);
    }

}
