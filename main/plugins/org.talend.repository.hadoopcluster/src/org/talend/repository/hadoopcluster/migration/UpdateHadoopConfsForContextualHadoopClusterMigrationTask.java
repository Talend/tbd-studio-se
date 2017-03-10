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

import java.util.Date;
import java.util.GregorianCalendar;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 *
 * created by ycbai on 2017年3月10日 Detailled comment
 *
 */
public class UpdateHadoopConfsForContextualHadoopClusterMigrationTask extends AbstractItemMigrationTask {

    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2017, 3, 10, 12, 0, 0);
        return gc.getTime();
    }

    @Override
    public ExecutionResult execute(Item item) {
        if (item instanceof HadoopClusterConnectionItem) {
            boolean isModified = false;
            HadoopClusterConnectionItem hcItem = (HadoopClusterConnectionItem) item;
            try {
                isModified = HadoopConfsUtils.updateContextualHadoopConfs(hcItem);
                if (isModified) {
                    ProxyRepositoryFactory.getInstance().save(hcItem, true);
                    return ExecutionResult.SUCCESS_WITH_ALERT;
                }
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
                return ExecutionResult.FAILURE;
            }
        }
        return ExecutionResult.NOTHING_TO_DO;
    }

}
