package org.talend.repository.hcatalog.ui.viewer.seeker;

import java.util.List;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hcatalog.node.HCatalogRepositoryNodeType;
import org.talend.repository.metadata.seeker.AbstractMetadataRepoViewSeeker;

/**
 * created by ycbai on 2013-1-28 Detailled comment
 * 
 */
public class HCatalogRepositorySeeker extends AbstractMetadataRepoViewSeeker {

    @Override
    protected List<ERepositoryObjectType> getValidationTypes() {
        List<ERepositoryObjectType> validationTypes = super.getValidationTypes();
        validationTypes.add(HCatalogRepositoryNodeType.HCATALOG);

        return validationTypes;
    }

}
