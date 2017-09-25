// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
package org.talend.designer.bigdata.problems;

import org.talend.core.model.components.ComponentCategory;
import org.talend.core.model.process.Problem.ProblemStatus;
import org.talend.designer.bigdata.i18n.Messages;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.nodes.NodeProblem;
import org.talend.designer.core.ui.views.problems.Problems;
import java.util.List;
import java.util.Map;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;

/**
 * This class displays a red cross on the standard jobs, when a tHBaseInput or Output with custom timestamp option enabled
 * use a timestamp column which is not of Long type. 
 */
public class HbaseTimestampTypeProblem implements NodeProblem {
	
	private static final String HBASE_INPUT_COMPONENT = "tHBaseInput";
	private static final String HBASE_OUTPUT_COMPONENT = "tHBaseOutput";

	@Override
	public boolean needsCheck(Node node) {
		ComponentCategory cat = ComponentCategory.getComponentCategoryFromName(node.getComponent().getType());
        if (ComponentCategory.CATEGORY_4_DI == cat) {
            String currentComponentName = node.getComponent().getName();
            if (HBASE_INPUT_COMPONENT.equals(currentComponentName) || HBASE_OUTPUT_COMPONENT.equals(currentComponentName)) {
                return true;
            }
        }
        return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void check(Node node) {
		String currentComponentName = node.getComponent().getName();
		
		switch(currentComponentName){
		case HBASE_INPUT_COMPONENT:
			if((boolean) node.getElementParameter("__RETRIEVE_TIMESTAMP__").getValue()){
				List<IMetadataTable> metadatas = node.getMetadataList();
				if ((metadatas!=null) && (metadatas.size() > 0)) {
					IMetadataTable metadata = metadatas.get(0);
				    if (metadata != null) {
				    	List<IMetadataColumn> columns = metadata.getListColumns();
				    	List<Map<String,String>> mapping = (List<Map<String,String>>) node.getElementParameter("__MAPPING__").getValue();
						String customTimestampColumn = (String) node.getElementParameter("__TIMESTAMP_COLUMN__").getValue();
						
						IMetadataColumn localTimestampColumn = null;
						for(int familyNum=0;familyNum<mapping.size();familyNum++){
							IMetadataColumn localColumn = columns.get(familyNum);
							if(localColumn.getLabel().equals(customTimestampColumn)){
								localTimestampColumn = localColumn;
								break;
							}
						}
						if(localTimestampColumn != null && JavaTypesManager.getJavaTypeFromId(localTimestampColumn.getTalendType()) == JavaTypesManager.LONG){
							Problems.add(ProblemStatus.ERROR, node, Messages.getString("Node.checkHBaseCustomTimestamps"));
						}
				    }
				}
			}
			break;
		case HBASE_OUTPUT_COMPONENT:
			if((boolean) node.getElementParameter("__CUSTOM_TIMESTAMP_COLUMN__").getValue()){
				List<IMetadataTable> metadatas = node.getMetadataList();
				if ((metadatas!=null) && (metadatas.size() > 0)) {
					IMetadataTable metadata = metadatas.get(0);
				    if (metadata != null) {
				    	List<IMetadataColumn> columns = metadata.getListColumns();
				    	List<Map<String,String>> families = (List<Map<String,String>>) node.getElementParameter("__FAMILIES__").getValue();
						String customTimestampColumn = (String) node.getElementParameter("__TIMESTAMP_COLUMN__").getValue();
						
						IMetadataColumn localTimestampColumn = null;
						for(int familyNum=0;familyNum<families.size();familyNum++){
							IMetadataColumn localColumn = columns.get(familyNum);
							if(localColumn.getLabel().equals(customTimestampColumn)){
								localTimestampColumn = localColumn;
								break;
							}
						}
						if(localTimestampColumn != null && JavaTypesManager.getJavaTypeFromId(localTimestampColumn.getTalendType()) == JavaTypesManager.LONG){
							Problems.add(ProblemStatus.ERROR, node, Messages.getString("Node.checkHBaseCustomTimestamps"));
						}
				    }
				}
			}
		}
	}

}
