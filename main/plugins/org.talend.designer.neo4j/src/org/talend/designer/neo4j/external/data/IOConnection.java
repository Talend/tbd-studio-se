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
package org.talend.designer.neo4j.external.data;

import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;

public class IOConnection {

    private String name;

    private IMetadataTable table;

    private EConnectionType connectionType;

    public IOConnection(IConnection connection) {
        this.name = connection.getName();
        this.table = connection.getMetadataTable();
        this.connectionType = connection.getLineStyle();
    }

    public IOConnection(IODataComponent ioDataComponent) {
        this.name = ioDataComponent.getName();
        this.table = ioDataComponent.getTable();
        this.connectionType = ioDataComponent.getConnectionType();
    }

    public String getName() {
        return name;
    }

    public IMetadataTable getTable() {
        return table;
    }

    public EConnectionType getConnectionType() {
        return connectionType;
    }
}
