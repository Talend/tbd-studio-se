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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jettison.json.JSONObject;
import org.talend.lineage.common.AbstractLineageCreator;
import org.talend.lineage.common.ILineageCreator;

public class AtlasLineageCreator extends AbstractLineageCreator implements ILineageCreator {

    private static final org.apache.log4j.Logger LOG  = org.apache.log4j.Logger.getLogger(AtlasLineageCreator.class);

    @Nullable
    private Referenceable                        jobRef;

    private List<Referenceable>                  refs = new ArrayList<>();

    // only for testing / debugging purposes
    // the get re-initialized on each invocation of sendToLineageProvider
    private Map<String, Pair<Referenceable, Id>> persistedComponents;

    private Map<String, Pair<Referenceable, Id>> persistedArtificialComponents;

    private Map<String, Pair<Referenceable, Id>> persistedJobs;

    private final TalendAtlasClient              client;

    /**
     *
     * @param url
     * @param login
     * @param password
     */
    public AtlasLineageCreator(final String url) {
        this.client = new TalendAtlasClient(url);
    }

    @Override
    public void addJobInfo(Map<String, Object> jobMetadata) {
        Map<String, Object> values = new HashMap<>();

        values.put("name", jobMetadata.containsKey("name") ? jobMetadata.get("name").toString() : "Talend Job");
        values.put("description",
                jobMetadata.containsKey("description") ? jobMetadata.get("description").toString() : "Talend Job");
        values.put("purpose", jobMetadata.containsKey("purpose") ? jobMetadata.get("purpose").toString() : "Talend Job");
        values.put("author", jobMetadata.containsKey("author") ? jobMetadata.get("author").toString() : "Talend");
        values.put("version", jobMetadata.containsKey("version") ? jobMetadata.get("version").toString() : "");
        values.put("jobType", jobMetadata.containsKey("jobType") ? jobMetadata.get("jobType").toString() : "");
        values.put("framework", jobMetadata.containsKey("framework") ? jobMetadata.get("framework").toString() : "");
        values.put("status", jobMetadata.containsKey("status") ? jobMetadata.get("status").toString() : "FINISHED");

        long creationDate = 0;
        long lastModificationDate = 0;
        long startTime = 0;
        long endTime = 0;

        try {
            if (jobMetadata.containsKey("creationDate")) {
                creationDate = Long.parseLong(jobMetadata.get("creationDate").toString().trim());
            }
            if (jobMetadata.containsKey("lastModificationDate")) {
                creationDate = Long.parseLong(jobMetadata.get("lastModificationDate").toString().trim());
            }
            if (jobMetadata.containsKey("startTime")) {
                creationDate = Long.parseLong(jobMetadata.get("startTime").toString().trim());
            }
            if (jobMetadata.containsKey("endTime")) {
                creationDate = Long.parseLong(jobMetadata.get("endTime").toString().trim());
            }
        } catch (NumberFormatException nfe) {
            // Nothing
        }

        values.put("creationDate", creationDate);
        values.put("lastModificationDate", lastModificationDate);
        values.put("startTime", startTime);
        values.put("endTime", endTime);

        // Notice that the job components are not put in creation, since we don't have
        // the ids of the components yet, it is sendToLineageProvider who does it
        this.jobRef = TalendModelFactory.buildTalendJob(values);

    }

    @Override
    public void addNodeToLineage(final String name, final Map<String, String> schema, final List<String> inputNodes,
            final List<String> outputNodes, final Map<String, Object> metadata) {
        // Do not create lineage nodes for components with empty schemas (tConfiguration, ...)
        if (schema.size() > 0) {
            Referenceable ref = TalendModelFactory.buildTalendComponent(name, schema, metadata, inputNodes, outputNodes);
            this.refs.add(ref);
        }
    }

    @Override
    public void sendToLineageProvider(final Boolean dieOnError) {
        try {
            // First we persist the instances
            Map<String, Pair<Referenceable, Id>> persistedArtificialInstances = persistWithArtificialComponents(this.refs);
            // then with the ids we persist the job info
            if (jobRef != null) {
                List<Id> instancesIds = new ArrayList<>();
                // Notice that we don't add the artificial instances to avoid
                // type + visualization problems
                for (Pair<Referenceable, Id> pair : this.persistedComponents.values()) {
                    instancesIds.add(pair.getRight());
                }
                jobRef.set("components", instancesIds);

                Id idJobRef = client.persistInstanceWithLog(jobRef);
                String jobName = (String) jobRef.get("name");
                this.persistedJobs = new HashMap<>();
                this.persistedJobs.put(jobName, Pair.of(jobRef, idJobRef));

                // we create an artificial instance to link the job with the components
                Referenceable artificialRef = TalendModelFactory.buildTalendArtificialComponent(jobName, Arrays.asList(idJobRef),
                        instancesIds);
                Id idArtificialRef = client.persistInstanceWithLog(artificialRef);
            }
        } catch (Throwable e) {
            if (dieOnError) {
                throw new RuntimeException(e);
            } else {
                LOG.error(e);
            }
        }
    }

    /**
     * Persists the refs and with the obtained Ids it creates:
     * <ul>
     * <li>the corresponding types for the Talend Model (if they don't exist)</li>
     * <li>the artificial components to connect the refs (they are persisted too)</li>
     * </ul>
     * 
     * @param refs
     * @return the instance corresponding to the artificial nodes
     */
    private Map<String, Pair<Referenceable, Id>> persistWithArtificialComponents(final List<Referenceable> refs)
            throws Exception {
        findOrCreateTypes(TalendModelFactory.TALEND_CLASS_TYPES);

        Map<String, Pair<Referenceable, Id>> persistedInstances = persist(refs);
        // Now we create the artificial components to connect the references (edges)
        List<Referenceable> artificialRefs = TalendModelFactory.buildTalendArtificialComponents(persistedInstances);
        // We persist the artificial processes
        Map<String, Pair<Referenceable, Id>> persistedArtificialInstances = persist(artificialRefs);
        // This is done for debugging/test purposes
        this.persistedComponents = persistedInstances;
        this.persistedArtificialComponents = persistedArtificialInstances;
        return persistedArtificialInstances;
    }

    /**
     * Persists the refs in Atlas, it assumes that the refs have different names (identity)
     * 
     * @param refs
     * @return
     */
    private Map<String, Pair<Referenceable, Id>> persist(final List<Referenceable> refs) throws Exception {
        Map<String, Pair<Referenceable, Id>> persistedEntities = new HashMap<>();
        for (final Referenceable ref : refs) {
            // We persisted with the modified Ref if it was modified
            Id idRef = client.persistInstanceWithLog(ref);
            String name = ref.getValuesMap().get("name").toString();
            persistedEntities.put(name, Pair.of(ref, idRef));
        }
        return persistedEntities;
    }

    /**
     * Creates the type definitions or ensure that the default types exist
     * 
     * @param types
     */
    private void findOrCreateTypes(final Collection<String> types) throws AtlasServiceException {
        Collection<String> missingTypes = client.getMissingTypes(types);
        if (!missingTypes.isEmpty()) {
            // notice that we force the creation of the types if they don't exist
            TypesDef typesDef = TalendModelFactory.buildTypesDef(missingTypes);
            JSONObject typesIds = client.persistTypes(typesDef);
        }
    }

    // Methods below are only for debugging / unit testing purposes

    protected void logPersistedEntities() {
        logPersistedEntities("persistedJobs:", this.persistedJobs);
        logPersistedEntities("persistedEntities:", this.persistedComponents);
        logPersistedEntities("persistedArtificialEntities:", this.persistedArtificialComponents);
    }

    private void logPersistedEntities(final String msg, @Nullable
    final Map<String, Pair<Referenceable, Id>> entries) {
        LOG.debug(msg);
        if (entries != null) {
            for (Map.Entry<String, Pair<Referenceable, Id>> entry : entries.entrySet()) {
                Referenceable ref = entry.getValue().getLeft();
                Id id = entry.getValue().getRight();
                String name = (String) ref.get("name");
                String typeName = ref.getTypeName();
                LOG.debug(name + " [" + typeName + "] : " + id._getId());
                if ("tArtificialComponent".equals(typeName)) {
                    List<String> inputs = (List<String>) ref.get("inputs");
                    List<String> outputs = (List<String>) ref.get("outputs");
                    LOG.debug(name + " inputs  : [" + inputs + "]");
                    LOG.debug(name + " outputs : [" + outputs + "]");
                }
            }
        }
    }

    protected List<Referenceable> getRefs() {
        return refs;
    }

    @Nullable
    protected Referenceable getJobRef() {
        return jobRef;
    }

    protected Map<String, Pair<Referenceable, Id>> getPersistedComponents() {
        return persistedComponents;
    }

    protected Map<String, Pair<Referenceable, Id>> getPersistedArtificialComponents() {
        return persistedArtificialComponents;
    }

    protected Map<String, Pair<Referenceable, Id>> getPersistedJobs() {
        return persistedJobs;
    }
}
