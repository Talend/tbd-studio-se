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
package org.talend.lineage.cloudera.entity;

import java.util.List;

import org.talend.lineage.cloudera.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MProperty;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;

/**
 * Base model to represent Talend components as a Cloudera Navigator entities
 * 
 */
@MClass(model = "talend")
public abstract class TalendEntity extends Entity {

    private String       entityId;

    private final String jobId;

    @MProperty
    private String       link;

    public TalendEntity(String jobId, String componentName) {
        this.entityId = GeneratorID.generateEntityID(jobId, componentName);
        setName(componentName);
        setNamespace(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE);
        this.jobId = jobId;
    }

    @Override
    public String generateId() {
        return getEntityId();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TABLE;
    }

    @Override
    public SourceType getSourceType() {
        return SourceType.SDK;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getJobId() {
        return this.jobId;
    }

    /**
     * Connects a the talend entity to its input/output using relations
     */
    public abstract void connectToEntity(List<String> inputs, List<String> outputs);
}
