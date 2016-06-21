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

import org.talend.lineage.cloudera.util.GeneratorID;

import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.annotations.MClass;
import com.cloudera.nav.sdk.model.annotations.MRelation;
import com.cloudera.nav.sdk.model.entities.Dataset;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.entities.HdfsEntity;
import com.cloudera.nav.sdk.model.relations.RelationRole;
import com.google.common.base.Preconditions;

/**
 * Cloudera Navigator specific model to represent an input/output component. The created dataset is linked to the
 * input/output component HDFS folder.
 *
 * Note : Currently TalendDatasets represent tFileXXXInput/Output components. Other input/output components (tMysqlXXX,
 * tFixedFlow, tLogRow ...) do not have an input/output TalendDataset.
 */
@MClass(model = "talend_dataset")
public class TalendDataset extends Dataset {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TalendDataset.class);

    @MRelation(role = RelationRole.PHYSICAL)
    private Entity                         dataContainer;

    private String                         componentName;

    private String                         generatedId;

    public TalendDataset(String name, String componentName, String jobId) {
        super();
        setSourceType(SourceType.SDK);
        setNamespace(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE);
        setName(name);
        this.componentName = componentName;

        this.generatedId = GeneratorID.generateDatasetID(jobId, componentName);
        LOG.debug("Dataset:" + componentName + " " + jobId + ": " + generatedId); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @Override
    public String generateId() {
        return generatedId;
    }

    public String getComponentName() {
        return this.componentName;
    }

    public Entity getDataContainer() {
        return dataContainer;
    }

    public void setDataContainer(HdfsEntity hdfsDir) {
        Preconditions.checkArgument(hdfsDir.getEntityType() == EntityType.DIRECTORY);
        this.dataContainer = hdfsDir;
    }
}
