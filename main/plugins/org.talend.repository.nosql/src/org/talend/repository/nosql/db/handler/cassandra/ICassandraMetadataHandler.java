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
package org.talend.repository.nosql.db.handler.cassandra;

import java.util.List;
import java.util.Set;

import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.exceptions.NoSQLServerException;

/**
 * created by ycbai on 2014年11月26日 Detailled comment
 *
 */
public interface ICassandraMetadataHandler {

    public boolean checkConnection(NoSQLConnection connection) throws NoSQLServerException;

    public List<String> getKeySpaceNames(NoSQLConnection connection) throws NoSQLServerException;

    public Set<String> getColumnFamilyNames(NoSQLConnection connection) throws NoSQLServerException;

    public Set<String> getColumnFamilyNames(NoSQLConnection connection, String ksName) throws NoSQLServerException;

    public Set<String> getSuperColumnFamilyNames(NoSQLConnection connection) throws NoSQLServerException;

    public Set<String> getSuperColumnFamilyNames(NoSQLConnection connection, String ksName) throws NoSQLServerException;

    public List<Object> getColumns(NoSQLConnection connection, String ksName, String cfName) throws NoSQLServerException;

    public String getColumnName(NoSQLConnection connection, Object column) throws NoSQLServerException;

    public String getColumnTalendType(Object column) throws NoSQLServerException;

    public String getColumnDbType(Object column) throws NoSQLServerException;

    public void closeConnections() throws NoSQLServerException;

}
