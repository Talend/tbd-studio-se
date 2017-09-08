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
package org.talend.repository.hadoopcluster.token;

import org.eclipse.jface.preference.IPreferenceStore;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.ui.token.AutoSaveTokenCollector;
import org.talend.repository.hadoopcluster.HadoopClusterPlugin;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

import us.monoid.json.JSONObject;

/**
 * created by nrousseau on Apr 1, 2016 Detailled comment
 *
 */
public class HadoopClusterTokenCollector extends AutoSaveTokenCollector {

    private String HADOOPCLUSTER = "HADOOPCLUSTER"; //$NON-NLS-1$

    private static final String PREF_HADOOPCLUSTER_TOKEN_RECORDS = "hadoopcluster.token.records"; //$NON-NLS-1$

    /**
     * DOC nrousseau HadoopClusterTokenCollector constructor comment.
     */
    public HadoopClusterTokenCollector() {
    }

    @Override
    protected JSONObject getTokenDetailsForCurrentProject() throws Exception {
        JSONObject typesHadoop = new JSONObject();

        for (IRepositoryViewObject rvo : ProxyRepositoryFactory.getInstance()
                .getAll(ERepositoryObjectType.getType(HADOOPCLUSTER))) {
            HadoopClusterConnectionItem item = (HadoopClusterConnectionItem) rvo.getProperty().getItem();
            HadoopClusterConnection connection = (HadoopClusterConnection) item.getConnection();
            String distrib = connection.getDistribution() + "/" + connection.getDfVersion(); //$NON-NLS-1$
            int nbDbTypes = 1;
            if (typesHadoop.has(distrib)) {
                nbDbTypes = typesHadoop.getInt(distrib);
                nbDbTypes++;
            }
            typesHadoop.put(distrib, nbDbTypes);
        }

        JSONObject hadoopCluster = new JSONObject();
        JSONObject types = new JSONObject();
        types.put("types", typesHadoop);
        hadoopCluster.put(HADOOPCLUSTER, types);
        return hadoopCluster;
    }

    @Override
    protected IPreferenceStore getPreferenceStore() throws Exception {
        return HadoopClusterPlugin.getDefault().getPreferenceStore();
    }

    @Override
    protected String getPreferenceKey() throws Exception {
        return PREF_HADOOPCLUSTER_TOKEN_RECORDS;
    }

}
