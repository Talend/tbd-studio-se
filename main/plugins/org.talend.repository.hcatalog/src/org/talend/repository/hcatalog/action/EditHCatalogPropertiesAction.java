package org.talend.repository.hcatalog.action;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.action.common.EditHadoopNodePropertiesAction;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;

public class EditHCatalogPropertiesAction extends EditHadoopNodePropertiesAction {

    @Override
    protected ERepositoryObjectType getNodeType() {
        return HCatalogRepositoryNodeType.HCATALOG;
    }

}
