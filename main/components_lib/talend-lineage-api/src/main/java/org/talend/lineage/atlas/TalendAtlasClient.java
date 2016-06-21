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
package org.talend.lineage.atlas;

import java.util.Collection;

import org.apache.atlas.AtlasClient;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.json.InstanceSerialization;
import org.apache.atlas.typesystem.json.TypesSerialization;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jettison.json.JSONObject;

/**
 * This is a client access class for all the communication with Atlas. It encapsulates the original AtlasClient and
 * provides some missing util methods. Some of these methods are replacements for existing methods in more recent
 * versions, or may end up going upstream (to the Apache Atlas project).
 *
 * It exists to offer a stable foundation in case API changes in Apache Atlas
 */
final class TalendAtlasClient {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TalendAtlasClient.class);

    private final AtlasClient                    client;

    TalendAtlasClient(final String url) {
        client = new AtlasClient(url);
    }

    /**
     *
     * @param ref
     * @return the Id corresponding to the ref in Atlas
     * @throws Exception
     */
    public Id persistInstance(final Referenceable ref) throws Exception {
        String entityJSON = InstanceSerialization.toJson(ref, true);
        JSONObject jsonObject = client.createEntity(entityJSON);
        String guid = jsonObject.getString(AtlasClient.GUID);
        return new Id(guid, ref.getId().getVersion(), ref.getTypeName());
    }

    /**
     * This method is just a wrapper to log the invocation of AtlasUtil.createInstance It only exists for debugging
     * purposes and can be replaced with the direct call
     *
     * @param ref
     * @return
     * @throws Exception
     */
    public Id persistInstanceWithLog(final Referenceable ref) throws Exception {
        String entityJSON = InstanceSerialization.toJson(ref, true);
        LOG.debug("Submitting new entity= " + entityJSON);
        Id idRef = persistInstance(ref);
        LOG.debug("created instance for type " + ref.getTypeName() + ", guid: " + idRef);
        return idRef;
    }

    /**
     * @param typesDef object to persist
     * @return the Id corresponding to the created types
     */
    public JSONObject persistTypes(final TypesDef typesDef) throws AtlasServiceException {
        String typeAsJson = TypesSerialization.toJson(typesDef);
        return this.client.createType(typeAsJson);
    }

    /**
     * @param types collection of type names we want to check if they exist in Atlas
     * @return the collection of types that don't exist in Atlas
     */
    public Collection<String> getMissingTypes(final Collection<String> types) throws AtlasServiceException {
        Collection<String> allTypes = this.client.listTypes();
        return CollectionUtils.subtract(types, allTypes);
    }

}