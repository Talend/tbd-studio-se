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
import java.util.Arrays;

import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.types.JavaTypesManager;

/**
 * This class displays a red cross on the standard jobs, when a tHBaseInput or Output with custom timestamp option enabled
 * use a timestamp column which is not of Long type. 
 */
public class HbaseTimestampTypeProblem implements NodeProblem {
	
	private static final List<String> COMPONENTS = Arrays.asList(
			"tHBaseInput",
			"tHBaseOutput",
			"tMapRDBInput",
			"tMapRDBOutput"
	);

	@Override
	public boolean needsCheck(Node node) {
		ComponentCategory cat = ComponentCategory.getComponentCategoryFromName(node.getComponent().getType());
        if (ComponentCategory.CATEGORY_4_DI == cat) {
            String currentComponentName = node.getComponent().getName();
            return COMPONENTS.contains(currentComponentName);
        }
        return false;
	}

	@Override
	public void check(Node node) {
		String currentComponentName = node.getComponent().getName();
		
		switch(currentComponentName){
		case "tHBaseInput":
		case "tMapRDBInput":
			if((boolean) node.getElementParameter("RETRIEVE_TIMESTAMP").getValue()){
				checkTimestampInMapping(node, "MAPPING");
			}
			break;
		case "tHBaseOutput":
		case "tMapRDBOutput":
			if((boolean) node.getElementParameter("CUSTOM_TIMESTAMP_COLUMN").getValue()){
				checkTimestampInMapping(node, "FAMILIES");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void checkTimestampInMapping(Node node, String mappingVariable){
		List<IMetadataTable> metadatas = node.getMetadataList();
		if ((metadatas!=null) && (metadatas.size() > 0)) {
			IMetadataTable metadata = metadatas.get(0);
		    if (metadata != null) {
		    	List<Map<String,String>> mapping = (List<Map<String,String>>) node.getElementParameter(mappingVariable).getValue();
				
		    	List<IMetadataColumn> columns = metadata.getListColumns();
				String timestampColumn = (String) node.getElementParameter("TIMESTAMP_COLUMN").getValue();
				
				IMetadataColumn localTimestampColumn = null;
				for(int familyNum = 0 ; familyNum < mapping.size() ; familyNum++){
					IMetadataColumn localColumn = columns.get(familyNum);
					if(localColumn.getLabel().equals(timestampColumn)){
						localTimestampColumn = localColumn;
						break;
					}
				}
				if(localTimestampColumn != null && JavaTypesManager.getJavaTypeFromId(localTimestampColumn.getTalendType()) != JavaTypesManager.LONG){
					Problems.add(ProblemStatus.ERROR, node, Messages.getString("Node.checkHBaseCustomTimestamps"));
				}
		    }
		}
	}

}
