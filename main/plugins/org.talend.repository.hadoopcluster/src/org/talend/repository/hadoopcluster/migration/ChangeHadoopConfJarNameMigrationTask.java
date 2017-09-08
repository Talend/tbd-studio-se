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
package org.talend.repository.hadoopcluster.migration;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * created by ycbai on 2016年6月15日 Detailled comment
 *
 */
public class ChangeHadoopConfJarNameMigrationTask extends AbstractItemMigrationTask {

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2016, 6, 15, 12, 0, 0);
        return gc.getTime();
    }

    @Override
    public ExecutionResult execute(Item item) {
        ILibraryManagerService libService = null;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibraryManagerService.class)) {
            libService = (ILibraryManagerService) GlobalServiceRegister.getDefault().getService(ILibraryManagerService.class);
        }
        if (libService == null) {
            return ExecutionResult.NOTHING_TO_DO;
        }
        if (item instanceof HadoopClusterConnectionItem) {
            HadoopClusterConnectionItem hcItem = (HadoopClusterConnectionItem) item;
            HadoopClusterConnection connection = (HadoopClusterConnection) hcItem.getConnection();
            if (connection.getConfFile() == null) {
                String confJarName = HadoopParameterUtil.getConfsJarDefaultName(hcItem.getProperty().getId());
                if (!libService.contains(confJarName)) {
                    return ExecutionResult.NOTHING_TO_DO;
                }
                File confsTempFolder = new File(HadoopConfsUtils.getConfsJarTempFolder());
                boolean retrieved = libService.retrieve(confJarName, confsTempFolder.getAbsolutePath(), false);
                File confJarFile = new File(confsTempFolder, confJarName);
                if (retrieved && confJarFile.exists()) {
                    try {
                        connection.setConfFile(FileUtils.readFileToByteArray(confJarFile));
                        ProxyRepositoryFactory.getInstance().save(hcItem, true);
                        // Clear cache of the new confs jar name.
                        String newConfJarName = HadoopParameterUtil.getConfsJarDefaultName(hcItem.getProperty().getLabel());
                        HadoopConfsUtils.removeFromDeployedCache(hcItem, newConfJarName);
                        return ExecutionResult.SUCCESS_WITH_ALERT;
                    } catch (Exception e) {
                        ExceptionHandler.process(e);
                        return ExecutionResult.FAILURE;
                    }
                }
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

}
