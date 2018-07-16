// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.hadoop.distribution;

/**
 * Enumeration that describes the exact Hadoop version.
 */
public enum EHadoopFullVersionStr {
	HADOOP_2_6("hadoop-2.6.0"),
	HADOOP_2_7("hadoop-2.7.0");
	
	private String fullVersion;
	
	EHadoopFullVersionStr(String fullVersion) {
		this.fullVersion = fullVersion;
	}
	
	public String getFullVersionStr() {
		return fullVersion;
	}
	
	public String toString() {
		return fullVersion;
	}
}
