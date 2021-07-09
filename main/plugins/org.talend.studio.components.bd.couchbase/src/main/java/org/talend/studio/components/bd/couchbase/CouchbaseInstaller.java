// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.studio.components.bd.couchbase;

import org.talend.sdk.component.studio.util.TCKComponentInstallerTask;

/**
 * @author bhe created on Jul 1, 2021
 *
 */
public class CouchbaseInstaller extends TCKComponentInstallerTask {

    @Override
    protected Class<? extends TCKComponentInstallerTask> getInstallerClass() {
        return CouchbaseInstaller.class;
    }

}
