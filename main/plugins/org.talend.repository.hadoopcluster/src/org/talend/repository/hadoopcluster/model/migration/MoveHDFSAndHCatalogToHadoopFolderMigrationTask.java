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
package org.talend.repository.hadoopcluster.model.migration;

import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.model.general.Project;
import org.talend.core.model.migration.AbstractProjectMigrationTask;
import org.talend.core.repository.constants.FileConstants;

/**
 * created by ycbai on 2013-2-28 Detailled comment
 * 
 */
public class MoveHDFSAndHCatalogToHadoopFolderMigrationTask extends AbstractProjectMigrationTask {

    private final static String OLD_HDFS_FOLDER_PATH = "/metadata/hdfs"; //$NON-NLS-1$

    private final static String OLD_HCATALOG_FOLDER_PATH = "/metadata/hcatalog"; //$NON-NLS-1$

    private final static String NEW_HDFS_FOLDER_PATH = "/metadata/hadoop/hdfs"; //$NON-NLS-1$

    private final static String NEW_HCATALOG_FOLDER_PATH = "/metadata/hadoop/hcatalog"; //$NON-NLS-1$

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2013, 3, 1, 12, 0, 0);
        return gc.getTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.migration.IMigrationTask#execute()
     */
    @Override
    public ExecutionResult execute(Project project) {
        try {
            IProject iPro = ResourceUtils.getProject(project);
            IFolder oldHDFSFolder = iPro.getFolder(OLD_HDFS_FOLDER_PATH);
            IFolder newHDFSFolder = iPro.getFolder(NEW_HDFS_FOLDER_PATH);
            moveFiles(oldHDFSFolder, newHDFSFolder);
            ResourceUtils.deleteResource(oldHDFSFolder);
            IFolder oldHCatalogFolder = iPro.getFolder(OLD_HCATALOG_FOLDER_PATH);
            IFolder newHCatalogFolder = iPro.getFolder(NEW_HCATALOG_FOLDER_PATH);
            moveFiles(oldHCatalogFolder, newHCatalogFolder);
            ResourceUtils.deleteResource(oldHCatalogFolder);
            return ExecutionResult.SUCCESS_NO_ALERT;
        } catch (Exception e) {
            ExceptionHandler.process(e);
            return ExecutionResult.FAILURE;
        }
    }

    private void moveFiles(IFolder oldFilesFolder, IFolder newFilesFolder) throws PersistenceException {
        if (oldFilesFolder.exists()) {
            if (!newFilesFolder.exists()) {
                ResourceUtils.createFolder(newFilesFolder);
            }
            IProject project = newFilesFolder.getProject();
            for (IResource fileCurrent : ResourceUtils.getMembers(oldFilesFolder)) {
                if (fileCurrent instanceof IFile) {
                    IFile file = (IFile) fileCurrent;
                    String fileExt = file.getFileExtension();
                    if (FileConstants.PROPERTIES_EXTENSION.equals(fileExt)) {
                        String name = fileCurrent.getName().replace(fileExt, ""); //$NON-NLS-1$
                        // take all the files starting by the same name
                        for (IResource filesToMove : ResourceUtils.getMembers(oldFilesFolder)) {
                            String fileName = filesToMove.getName();
                            if (filesToMove instanceof IFile && fileName.startsWith(name)) {
                                IPath finalPath = newFilesFolder.getFullPath().append(fileName);
                                if (project.getWorkspace().getRoot().getFile(finalPath).exists()) {
                                    // file exist...
                                } else {
                                    ResourceUtils.moveResource(filesToMove, finalPath);
                                }
                            }
                        }
                    }
                } else if (fileCurrent instanceof IFolder) {
                    moveFiles((IFolder) fileCurrent, newFilesFolder);
                }
            }
        }
    }

}
