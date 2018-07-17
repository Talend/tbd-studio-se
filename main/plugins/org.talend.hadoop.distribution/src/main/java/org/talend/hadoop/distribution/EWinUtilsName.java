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
public enum EWinUtilsName {
	WINUTILS_HADOOP_2_6("winutils-hadoop-2.6.0.exe"),
	WINUTILS_HADOOP_2_7("winutils-hadoop-2.7.0.exe");
	
	private String winUtilsName;
	
	EWinUtilsName(String winUtilsName) {
		this.winUtilsName = winUtilsName;
	}
	
	public String getWinUtilsName() {
		return winUtilsName;
	}
	
	public String toString() {
		return winUtilsName;
	}
}
