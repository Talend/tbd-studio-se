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
package org.talend.designer.pigmap;

import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.talend.commons.ui.runtime.expressionbuilder.IExpressionDataBean;
import org.talend.core.service.IPigMapService;
import org.talend.core.ui.proposal.PigProposalProvider;
import org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData;
import org.talend.designer.pigmap.editor.PigMapGraphicViewer;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.parts.PigMapDataEditPart;
import org.talend.designer.pigmap.parts.directedit.ExpressionCellEditor;
import org.talend.designer.pigmap.parts.directedit.ExpressionProposalProvider;
import org.talend.designer.pigmap.ui.tabs.MapperManager;
import org.talend.designer.pigmap.util.PigFunctionParser;
import org.talend.designer.rowgenerator.data.AbstractTalendFunctionParser;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class PigMapService implements IPigMapService {

    private PigMapData pigMapData;

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.service.IDesignerPigMapService#createExpressionProposalProvider()
     */
    @Override
    public ExpressionProposalProvider createExpressionProposalProvider(IExpressionDataBean dataBean) {
        MapperManager mapperManager = null;
        IContentProposalProvider[] contentProposalProviders = new IContentProposalProvider[0];
        if (dataBean != null && dataBean instanceof ExpressionCellEditor) {
            mapperManager = ((ExpressionCellEditor) dataBean).getMapperManager();
        } else if (pigMapData != null) {
            for (Object object : pigMapData.eAdapters()) {
                if (object != null && object instanceof PigMapDataEditPart) {
                    PigMapDataEditPart externalPart = ((PigMapDataEditPart) object);
                    if (externalPart != null && externalPart.getViewer() instanceof PigMapGraphicViewer) {
                        mapperManager = ((PigMapGraphicViewer) externalPart.getViewer()).getMapperManager();
                    }
                }
            }
        }
        if (mapperManager != null) {
            contentProposalProviders = new IContentProposalProvider[] { new PigProposalProvider(mapperManager
                    .getMapperComponent().getProcess(), mapperManager.getMapperComponent().getOriginalNode()) };
            ExpressionProposalProvider provider = new ExpressionProposalProvider(mapperManager, contentProposalProviders);
            provider.init();
            return provider;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.service.IPigMapService#setPigMapData(org.talend.designer.core.model.utils.emf.talendfile.
     * AbstractExternalData)
     */
    @Override
    public void setPigMapData(AbstractExternalData externalData) {
        if (externalData != null && externalData instanceof PigMapData) {
            this.pigMapData = (PigMapData) externalData;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.service.IPigMapService#pigFunctionParser()
     */
    @Override
    public AbstractTalendFunctionParser pigFunctionParser() {
        return new PigFunctionParser();
    }
}
