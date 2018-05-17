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
package org.talend.hadoop.distribution.component;

/**
 * Interface that exposes specific MapROJAI methods.
 *
 */
public interface MapROJAIComponent extends HadoopComponent {

    /**
     * Partial sorts can occur due to secondary index lags. This indicates if the Query Service able to explicitly sort the data.
     * More info here https://maprdocs.mapr.com/home/MapR-DB/JSON_DB/query-orderby-queries.html
     * @return
     */
    boolean doSupportExplicitSorts();
    /**
     * Json-like 'where' clauses are supported starting from OJAI 2.0 (Query org.ojai.store.Driver.newQuery(String queryJson) )
     * @return
     */
    boolean doSupportJsonQueries();
    
}
