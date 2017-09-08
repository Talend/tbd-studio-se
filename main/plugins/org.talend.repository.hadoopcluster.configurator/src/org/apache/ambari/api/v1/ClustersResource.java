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

import static org.apache.ambari.api.Parameters.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.ambari.api.model.ApiClusterList;

/**
 * created by bchen on Jun 2, 2015 Detailled comment
 *
 */
@Produces({ MediaType.TEXT_PLAIN })
public interface ClustersResource {

    /**
     * Lists all known clusters.
     *
     * @return List of known clusters.
     */
    @GET
    @Path("/")
    public ApiClusterList readClusters();

    /**
     * @return The cluster resource handler.
     */
    @Path("/{clusterName}")
    public ClusterResource getClusterResource(@PathParam(CLUSTER_NAME) String clusterName);

}
