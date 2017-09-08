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
package org.talend.repository.nosql.db.util.neo4j;

import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.reflection.NoSQLReflection;

/**
 * 
 * created by ycbai on 2014年10月28日 Detailled comment
 *
 */
public abstract class ExecutionUnitWithTransaction {

    public Object execute(Object db) throws NoSQLServerException {
        Object result = null;
        try {
            Object tx = NoSQLReflection.invokeMethod(db, "beginTx", //$NON-NLS-1$
                    new Object[0]);
            try {
                result = run();
                NoSQLReflection.invokeMethod(tx, "success", //$NON-NLS-1$
                        new Object[0]);
            } finally {
                NoSQLReflection.invokeMethod(tx, "close", //$NON-NLS-1$
                        new Object[0]);
            }
        } catch (Exception e) {
            throw new NoSQLServerException(e);
        }

        return result;
    }

    protected abstract Object run() throws Exception;

}
