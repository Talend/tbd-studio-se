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
package org.talend.hadoop.distribution.cdh5120.test.modulegroup;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.talend.hadoop.distribution.DistributionModuleGroup;
import org.talend.hadoop.distribution.cdh5120.CDH5120Constant;
import org.talend.hadoop.distribution.cdh5120.modulegroup.CDH5120MapReduceModuleGroup;

public class CDH5120MapReduceModuleGroupTest {

	@Test
	public void testModuleGroups() throws Exception {
		Map<String, String> results = new HashMap<>();
		results.put(CDH5120Constant.HDFS_MODULE_GROUP.getModuleName(), null);
		results.put(CDH5120Constant.MAPREDUCE_MODULE_GROUP.getModuleName(), null);
		results.put(CDH5120Constant.MAPREDUCE_PARQUET_MODULE_GROUP.getModuleName(), null);
		results.put(CDH5120Constant.MAPREDUCE_PARQUET_MRREQUIRED_MODULE_GROUP.getModuleName(), null);
		// results.put(CDH5120Constant.TALEND_CLOUDERA_CDH_5_12_NAVIGATOR.getModuleName(),
		// "(USE_CLOUDERA_NAVIGATOR=='true')"); //$NON-NLS-1$
		Set<DistributionModuleGroup> moduleGroups = CDH5120MapReduceModuleGroup.getModuleGroups();
		assertEquals(results.keySet().size(), moduleGroups.size());
		for (DistributionModuleGroup module : moduleGroups) {
			assertTrue("Should contain module " + module.getModuleName(), results.containsKey(module.getModuleName())); //$NON-NLS-1$
			if (results.get(module.getModuleName()) == null) {
				assertTrue("The condition of the module " + module.getModuleName() + " is not null.", //$NON-NLS-1$ //$NON-NLS-2$
						results.get(module.getModuleName()) == null);
			} else {
				assertTrue("The condition of the module " + module.getModuleName() + " is null, but it should be " //$NON-NLS-1$ //$NON-NLS-2$
						+ results.get(module.getModuleName()) + ".", results.get(module.getModuleName()) != null); //$NON-NLS-1$
				assertEquals(results.get(module.getModuleName()), module.getRequiredIf().getConditionString());
			}
		}
	}
}
