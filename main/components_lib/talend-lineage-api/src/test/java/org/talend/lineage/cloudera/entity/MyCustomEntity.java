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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Instant;

import com.cloudera.nav.sdk.model.CustomIdGenerator;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MProperty;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.relations.RelationRole;
import com.google.common.base.Preconditions;

@MClass(model = "myCustomEntity")
public class MyCustomEntity extends Entity {

    @MProperty
    private Instant started;

    @MProperty
    private Instant ended;

    @MRelation(role = RelationRole.TARGET)
    public List<EndPointProxy> targetProxies;

    @MRelation(role = RelationRole.SOURCE)
    public List<EndPointProxy> sourceProxies;

    @MProperty
    private String link;

    @MProperty
    private int index;

    @MProperty
    private String steward;

    public MyCustomEntity(String namespace, String name) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(namespace));
        Preconditions.checkArgument(StringUtils.isNotEmpty(name));

        setNamespace(namespace);
        setName(name);

        targetProxies = new ArrayList<EndPointProxy>();
        sourceProxies = new ArrayList<EndPointProxy>();

        this.setIdentity(CustomIdGenerator.generateIdentity(getNamespace(), getName()));
    }

    public List<EndPointProxy> getTargetProxies() {
        return targetProxies;
    }

    public List<EndPointProxy> getSourceProxies() {
        return sourceProxies;
    }
    
    @Override
    public String generateId() {
        return CustomIdGenerator.generateIdentity(getNamespace(), getName());
    }

    @Override
    public String getIdentity() {
        return CustomIdGenerator.generateIdentity(getNamespace(), getName());
    }

    @Override
    public SourceType getSourceType() {
        return SourceType.SDK;
    }
    
    @Override
    public EntityType getEntityType() {
        return EntityType.DATASET;
    }

    public String getLink() {
        return link;
    }

    public Instant getStarted() {
        return started;
    }

    public Instant getEnded() {
        return ended;
    }

    public void setStarted(Instant started) {
        this.started = started;
    }

    public void setEnded(Instant ended) {
        this.ended = ended;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSteward() {
        return steward;
    }

    public void setSteward(String steward) {
        this.steward = steward;
    }
}
