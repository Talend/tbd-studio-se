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
import com.cloudera.nav.sdk.model.entities.DatasetField;

/**
 * 
 * Represents input component's schemas as Cloudera Navigator entities
 *
 */
@MClass(model = "talend_field")
public class TalendField extends DatasetField {

    public TalendField() {
        super();
        setSourceType(SourceType.SDK);
        setNamespace(GeneratorID.CLOUDERA_NAVIGATOR_APPLICATION_NAMESPACE);
    }

    public TalendField(String name, String type) {
        this();
        setName(name);
        setDataType(type);
    }

    @Override
    public String toString() {
        return "{\"name\":\"" + getName() + "\", \"dataType\":\"" + getDataType() + "\"}";
    }
}
