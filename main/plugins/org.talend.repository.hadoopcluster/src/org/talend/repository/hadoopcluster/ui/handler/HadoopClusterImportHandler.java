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
package org.talend.repository.hadoopcluster.ui.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.repository.items.importexport.handlers.imports.ImportRepTypeHandler;
import org.talend.repository.items.importexport.handlers.model.ImportItem;
import org.talend.repository.items.importexport.manager.ResourcesManager;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HadoopClusterImportHandler extends ImportRepTypeHandler {

    public HadoopClusterImportHandler() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.ImportRepTypeHandler#findRelatedImportItems(org.eclipse
     * .core.runtime.IProgressMonitor,
     * org.talend.repository.items.importexport.ui.wizard.imports.managers.ResourcesManager,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord[])
     */
    @Override
    public List<ImportItem> findRelatedImportItems(IProgressMonitor monitor, ResourcesManager resManager, ImportItem importItem,
            ImportItem[] allImportItemRecords) throws Exception {

        List<ImportItem> relatedItemRecords = new ArrayList<ImportItem>();
        relatedItemRecords.addAll(super.findRelatedImportItems(monitor, resManager, importItem, allImportItemRecords));
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            IHadoopClusterService hadoopClusterService = (IHadoopClusterService) GlobalServiceRegister.getDefault().getService(
                    IHadoopClusterService.class);
            final Item item = importItem.getItem();
            if (hadoopClusterService != null && hadoopClusterService.isHadoopClusterItem(item)) {
                resolveItem(resManager, importItem);
                HadoopClusterConnection hcConnection = (HadoopClusterConnection) ((HadoopClusterConnectionItem) item)
                        .getConnection();
                String clusterId = item.getProperty().getId();
                for (ImportItem ir : allImportItemRecords) {
                    resolveItem(resManager, ir);
                    Item subItem = ir.getItem();
                    String hcId = null;
                    if (subItem instanceof HadoopSubConnectionItem) {
                        hcId = ((HadoopSubConnection) ((HadoopSubConnectionItem) subItem).getConnection())
                                .getRelativeHadoopClusterId();
                    } else if (subItem instanceof DatabaseConnectionItem) {
                        hcId = ((DatabaseConnection) ((DatabaseConnectionItem) subItem).getConnection()).getParameters().get(
                                ConnParameterKeys.CONN_PARA_KEY_HADOOP_CLUSTER_ID);
                    }
                    if (clusterId.equals(hcId)) {
                        if (subItem instanceof HadoopSubConnectionItem) {
                            EList<String> connectionList = hcConnection.getConnectionList();
                            String subItemId = subItem.getProperty().getId();
                            if (!connectionList.contains(subItemId)) {
                                connectionList.add(subItemId);
                            }
                        }
                        relatedItemRecords.add(ir);
                    }
                }
            }
        }
        return relatedItemRecords;
    }

}
