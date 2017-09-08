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
package org.talend.oozie.scheduler.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marvin Wang for managing the traces for jobs.
 */
public class TOozieJobTraceManager {

	private Map<String, String> traces;

	private static TOozieJobTraceManager instance;

	private TOozieJobTraceManager() {
		traces = new HashMap<String, String>();
	}

	public static TOozieJobTraceManager getInstance() {
		if (instance == null)
			instance = new TOozieJobTraceManager();
		return instance;
	}

	public void putTrace(String jobId, String trace) {
		traces.put(jobId, trace);
	}

	public String getTrace(String jobId) {
		return traces.get(jobId);
	}

	public void removeTrace(String jobId) {
		traces.remove(jobId);
	}

	void clear() {
		traces.clear();
	}
}
