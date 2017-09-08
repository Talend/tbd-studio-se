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

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.ambari.api.model.ApiActualConfigsList;

/**
 * created by bchen on Jun 10, 2015 Detailled comment
 *
 */
@Produces({ MediaType.TEXT_PLAIN })
public interface ClusterResource {

    /**
     * @return The services resource handler.
     */
    @Path("/services")
    public ServicesResource getServicesResource();

    @Path("/configurations")
    public ConfigsResource getConfigsResource();

    @GET
    @Path("/services")
    public ApiActualConfigsList readActucalConfigs(
            @DefaultValue("components/host_components/HostRoles/actual_configs") @QueryParam("fields") String param);

}
