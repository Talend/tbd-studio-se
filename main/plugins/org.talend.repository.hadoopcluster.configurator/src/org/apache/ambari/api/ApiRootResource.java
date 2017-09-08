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
package org.apache.ambari.api;

import javax.ws.rs.Path;

import org.apache.ambari.api.v1.RootResourceV1;

/**
 * created by bchen on Jun 2, 2015 Detailled comment
 *
 */
/**
 * Root resource for the API. Provides access to the version-specific resources.
 */
@Path("/")
public interface ApiRootResource {

    /**
     * @return The v1 root resource.
     */
    @Path("/v1")
    RootResourceV1 getRootV1();

}
