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
package org.talend.designer.pigmap.parts.directedit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class ExpressionProposalProvider implements IContentProposalProvider {

    private List<EntryContentProposal> proposalsInside = new ArrayList<EntryContentProposal>();

    private final IContentProposalProvider[] otherContentProposalProviders;

    private MapperManager mapperManager;

    public ExpressionProposalProvider(MapperManager mapperManager, IContentProposalProvider[] otherContentProposalProviders) {
        this.otherContentProposalProviders = otherContentProposalProviders;
        this.mapperManager = mapperManager;
    }

    public void init() {
        PigMapData mapData = mapperManager.getExternalData();
        for (int i = 0; i < mapData.getInputTables().size(); i++) {
            InputTable inputTable = mapData.getInputTables().get(i);
            getProposalsInside(inputTable.getNodes(), proposalsInside);
        }
    }

    private void getProposalsInside(List<? extends AbstractNode> nodes, List<EntryContentProposal> proposalsInside) {
        for (AbstractNode absNode : nodes) {
            if (absNode instanceof TableNode) {
                TableNode tableNode = (TableNode) absNode;
                proposalsInside.add(new EntryContentProposal(mapperManager, tableNode));
            }
        }
    }

    @Override
    public IContentProposal[] getProposals(String contents, int position) {
        List<IContentProposal> proposals = new ArrayList<IContentProposal>();
        proposals.addAll(proposalsInside);
        for (IContentProposalProvider contentProposalProvider : otherContentProposalProviders) {
            proposals.addAll(Arrays.asList(contentProposalProvider.getProposals(contents, position)));
        }
        IContentProposal[] res = new IContentProposal[proposals.size()];
        res = proposals.toArray(res);
        return res;
    }
}
