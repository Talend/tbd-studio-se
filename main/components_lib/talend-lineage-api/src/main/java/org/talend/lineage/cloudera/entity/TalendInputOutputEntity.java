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
package org.talend.lineage.cloudera.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.talend.lineage.cloudera.util.ClouderaAPIUtil;
import org.talend.lineage.cloudera.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.EndPointProxy;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.relations.RelationRole;

/**
 * Cloudera navigator entity to represent any Talend processing component.
 * 
 * Note : FileInput/OutputXXX components are also modeled using this class. These components (FileInput/OutputXXX) are
 * represented as datasets + TalendInputOutputEntity
 *
 */
@MClass(model = "talend")
public class TalendInputOutputEntity extends TalendEntity {

    private List<String>        previousEntitiesId;

    private List<String>        nextEntitiesId;

    @MRelation(role = RelationRole.SOURCE)
    private List<EndPointProxy> sourceProxies;

    @MRelation(role = RelationRole.TARGET)
    private List<EndPointProxy> targetProxies;

    public TalendInputOutputEntity(String jobId, String componentName) {
        super(jobId, componentName);
        sourceProxies = new ArrayList<EndPointProxy>();
        previousEntitiesId = new ArrayList<String>();
        targetProxies = new ArrayList<EndPointProxy>();
        nextEntitiesId = new ArrayList<String>();
    }

    public void addNextEntity(String entityId) {
        this.nextEntitiesId.add(entityId);
        EndPointProxy endpointProxy = new EndPointProxy(entityId, SourceType.SDK, EntityType.OPERATION_EXECUTION);
        this.targetProxies.add(endpointProxy);
    }

    public void addPreviousEntity(String entityId) {
        this.previousEntitiesId.add(entityId);
        EndPointProxy endpointProxy = new EndPointProxy(entityId, SourceType.SDK, EntityType.OPERATION_EXECUTION);
        this.sourceProxies.add(endpointProxy);
    }

    public List<EndPointProxy> getSourceProxies() {
        return sourceProxies;
    }

    public void setSourceProxies(List<EndPointProxy> sourceProxies) {
        this.sourceProxies = sourceProxies;
    }

    public List<EndPointProxy> getTargetProxies() {
        return targetProxies;
    }

    public void setTargetProxies(List<EndPointProxy> targetProxies) {
        this.targetProxies = targetProxies;
    }

    public List<String> getPreviousEntitiesId() {
        return this.previousEntitiesId;
    };

    public List<String> getNextEntitiesId() {
        return this.nextEntitiesId;
    }

    /**
     * Connects a parent entity to its input/output using SOURCE -> TARGET & TARGET -> SOURCE relations
     */
    @Override
    public void connectToEntity(List<String> inputs, List<String> outputs) {

        // File Input components should be linked with a dataset
        if (CollectionUtils.isEmpty(inputs) && ClouderaAPIUtil.isFileInputOutputComponent(this.getName())) {
            String id = GeneratorID.generateDatasetID(this.getJobId(), this.getName());
            this.addPreviousEntity(id);
        }

        for (String input : inputs) {
            // generate the id of the component to connect to
            String id = GeneratorID.generateNodeID(this.getJobId(), input);
            this.addPreviousEntity(id);
        }

        // File Output components should be linked with a dataset
        if (CollectionUtils.isEmpty(outputs) && ClouderaAPIUtil.isFileInputOutputComponent(this.getName())) {
            String id = GeneratorID.generateDatasetID(this.getJobId(), this.getName());
            this.addNextEntity(id);
        }

        for (String output : outputs) {
            // generate the id of the component to connect to
            String id = GeneratorID.generateNodeID(this.getJobId(), output);
            this.addNextEntity(id);
        }

    }

    @Override
    public String toString() {
        return this.previousEntitiesId + "---> " + getName() + " (" + getEntityId() + ")" + " --->" + this.nextEntitiesId;
    }
}
