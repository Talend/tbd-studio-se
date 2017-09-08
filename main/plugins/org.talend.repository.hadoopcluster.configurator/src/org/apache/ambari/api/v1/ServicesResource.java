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
package org.apache.ambari.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.ambari.api.model.ApiServiceList;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
@Produces({ MediaType.TEXT_PLAIN })
public interface ServicesResource {

    /**
     * Lists all known services.
     *
     * @return List of known services.
     */
    @GET
    @Path("/")
    public ApiServiceList readServices();
}
