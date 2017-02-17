// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.nosql.db.common.neo4j;

/**
 *
 * created by ycbai on Jul 22, 2014 Detailled comment
 *
 */
public interface INeo4jConstants {

    public final static String NEO4J_SCHEMA = "NEO4J_SCHEMA"; //$NON-NLS-1$

    public final static String DEFAULT_SERVER_URL = "http://localhost:7474/db/data"; //$NON-NLS-1$

    public final static String CYPHER = "CYPHER"; //$NON-NLS-1$

    public final static String SCHEMA_COLUMN = "SCHEMA_COLUMN"; //$NON-NLS-1$

    public final static String RETURN_PARAMETER = "RETURN_PARAMETER"; //$NON-NLS-1$

    public final static String QUERY = "QUERY"; //$NON-NLS-1$

    public final static String NEO4J_1_X_X = "NEO4J_1_X_X"; //$NON-NLS-1$

    public final static String NEO4J_2_1_X = "NEO4J_2_1_X"; //$NON-NLS-1$

    public final static String NEO4J_2_2_X = "NEO4J_2_2_X"; //$NON-NLS-1$

    public final static String NEO4J_2_3_X = "NEO4J_2_3_X"; //$NON-NLS-1$

    public final static String[] DBVERSIONS = { NEO4J_1_X_X, NEO4J_2_1_X, NEO4J_2_2_X, NEO4J_2_3_X };
}
