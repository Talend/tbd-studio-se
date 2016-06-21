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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.atlas.typesystem.Referenceable;
import org.apache.atlas.typesystem.TypesDef;
import org.apache.atlas.typesystem.persistence.Id;
import org.apache.atlas.typesystem.types.AttributeDefinition;
import org.apache.atlas.typesystem.types.ClassType;
import org.apache.atlas.typesystem.types.DataTypes;
import org.apache.atlas.typesystem.types.EnumTypeDefinition;
import org.apache.atlas.typesystem.types.HierarchicalTypeDefinition;
import org.apache.atlas.typesystem.types.Multiplicity;
import org.apache.atlas.typesystem.types.StructTypeDefinition;
import org.apache.atlas.typesystem.types.TraitType;
import org.apache.atlas.typesystem.types.TypeUtils;
import org.apache.atlas.typesystem.types.utils.TypesUtil;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

final class TalendModelFactory {

    private static final String     TALEND_JOB_TYPE                  = "tJob";

    private static final String     TALEND_COMPONENT_TYPE            = "tComponent";

    private static final String     TALEND_ARTIFICIAL_COMPONENT_TYPE = "tArtificialComponent";

    // The generic Atlas Column is used to be sure that the web interface shows the schema
    private static final String     COLUMN_TYPE                      = "Column";

    // The name of the traits must be different from the one of the classes
    // and they are used to tag the things we want to show in the Atlas GUI
    private static final String     TALEND_TRAIT                     = "Talend";                                               // tag
                                                                                                                               // for
                                                                                                                               // all
                                                                                                                               // talend
                                                                                                                               // entities

    private static final String     TALEND_JOB_TRAIT                 = "TalendJob";

    private static final String     TALEND_COMPONENT_TRAIT           = "TalendComponent";

    static final Collection<String> TALEND_CLASS_TYPES               = ImmutableList.of(TALEND_JOB_TYPE, TALEND_COMPONENT_TYPE,
            TALEND_ARTIFICIAL_COMPONENT_TYPE, COLUMN_TYPE);

    private TalendModelFactory() {

    }

    /**
     *
     * @param typeNames the list of names of types to create (class names)
     * @return the type definition object
     */
    static TypesDef buildTypesDef(final Collection<String> typeNames) {
        HierarchicalTypeDefinition<TraitType> talendTraitDef = TypesUtil.createTraitTypeDef(TALEND_TRAIT, null);
        HierarchicalTypeDefinition<TraitType> talendJobTraitDef = TypesUtil.createTraitTypeDef(TALEND_JOB_TRAIT, null);
        HierarchicalTypeDefinition<TraitType> talendETLTraitDef = TypesUtil.createTraitTypeDef(TALEND_COMPONENT_TRAIT, null);

        // Default Atlas Entities
        Map<String, HierarchicalTypeDefinition<ClassType>> allClasses = new HashMap<>();

        HierarchicalTypeDefinition<ClassType> columnClassDef = TypesUtil.createClassTypeDef(COLUMN_TYPE, null,
                TypesUtil.createOptionalAttrDef("name", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("dataType", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("comment", DataTypes.STRING_TYPE));
        // Talend Entities
        // TalendDataSet -> DataSet -> Table, TalendEntity -> LoadProcess -> Process
        // TalendEntityChild -> Column -> Ref
        HierarchicalTypeDefinition<ClassType> talendJobClassDef = TypesUtil.createClassTypeDef(TALEND_JOB_TYPE,
                ImmutableList.of("DataSet"), TypesUtil.createOptionalAttrDef("description", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("purpose", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("author", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("version", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("jobType", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("framework", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("creationDate", DataTypes.LONG_TYPE),
                TypesUtil.createOptionalAttrDef("lastModificationDate", DataTypes.LONG_TYPE),
                TypesUtil.createOptionalAttrDef("status", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("startTime", DataTypes.LONG_TYPE),
                TypesUtil.createOptionalAttrDef("endTime", DataTypes.LONG_TYPE), new AttributeDefinition("components",
                        DataTypes.arrayTypeName(TALEND_COMPONENT_TYPE), Multiplicity.COLLECTION, true, null));

        HierarchicalTypeDefinition<ClassType> talendComponentClassDef = TypesUtil.createClassTypeDef(TALEND_COMPONENT_TYPE,
                ImmutableList.of("DataSet"), TypesUtil.createOptionalAttrDef("description", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("creationDate", DataTypes.LONG_TYPE),
                TypesUtil.createOptionalAttrDef("lastModificationDate", DataTypes.LONG_TYPE),
                TypesUtil.createOptionalAttrDef("startDate", DataTypes.LONG_TYPE),
                TypesUtil.createOptionalAttrDef("endDate", DataTypes.LONG_TYPE),
                new AttributeDefinition("columns", DataTypes.arrayTypeName(COLUMN_TYPE), Multiplicity.COLLECTION, true, null),
                TypesUtil.createOptionalAttrDef("script", DataTypes.STRING_TYPE),
                // These two are optional for input/output components
                TypesUtil.createOptionalAttrDef("path", DataTypes.STRING_TYPE),
                TypesUtil.createOptionalAttrDef("format", DataTypes.STRING_TYPE)

        // This would allow us to change the entity icon in the web UI
        // TypesUtil.createOptionalAttrDef("icon_url", DataTypes.STRING_TYPE);
        );
        // This is an artificial entity to model the edges in the graph given the constraints
        // to represent an entity as both DataSet and Process in Atlas 0.5
        HierarchicalTypeDefinition<ClassType> talendArtificialComponentClassDef = TypesUtil.createClassTypeDef(
                TALEND_ARTIFICIAL_COMPONENT_TYPE, ImmutableList.of("Process"),
                TypesUtil.createOptionalAttrDef("description", DataTypes.STRING_TYPE));

        allClasses.put(COLUMN_TYPE, columnClassDef);
        allClasses.put(TALEND_JOB_TYPE, talendJobClassDef);
        allClasses.put(TALEND_COMPONENT_TYPE, talendComponentClassDef);
        allClasses.put(TALEND_ARTIFICIAL_COMPONENT_TYPE, talendArtificialComponentClassDef);

        ImmutableList.Builder builder = ImmutableList.<HierarchicalTypeDefinition<ClassType>> builder();
        for (String typeName : typeNames) {
            HierarchicalTypeDefinition<ClassType> clazz = allClasses.get(typeName);
            if (clazz != null) {
                builder.add(clazz);
            }
        }
        ImmutableList<HierarchicalTypeDefinition<ClassType>> classes = builder.build();

        return TypeUtils.getTypesDef(ImmutableList.<EnumTypeDefinition> of(), ImmutableList.<StructTypeDefinition> of(),
                ImmutableList.of(talendTraitDef, talendJobTraitDef, talendETLTraitDef), classes);
    }

    /**
     *
     * @param values
     * @return
     */
    static Referenceable buildTalendJob(final Map<String, Object> values) {
        return buildReferenceable(TALEND_JOB_TYPE, values, TALEND_TRAIT, TALEND_JOB_TRAIT);
    }

    /**
     *
     * @param name
     * @param schema
     * @param metadata
     * @param inputs
     * @param outputs
     * @return
     */
    static Referenceable buildTalendComponent(final String name, Map<String, String> schema, final Map<String, Object> metadata,
            final List<String> inputs, final List<String> outputs) {
        Referenceable ref = buildReferenceable(TALEND_COMPONENT_TYPE, metadata, TALEND_TRAIT, TALEND_COMPONENT_TRAIT);
        ref.set("name", name);
        ref.set("columns", buildColumns(schema));
        ref.set("inputs", inputs);
        ref.set("outputs", outputs);
        return ref;
    }

    /**
     *
     * @param schema is a Map where each entry contains the name and the type each column
     * @return The column as a Referenceable
     */
    private static List<Referenceable> buildColumns(final Map<String, String> schema) {
        List<Referenceable> refs = new ArrayList<>();
        for (Map.Entry<String, String> entry : schema.entrySet()) {
            Referenceable ref = new Referenceable(COLUMN_TYPE);
            ref.set("name", entry.getKey());
            // In atlas 0.5 there is a bug and the dataType does not show correctly so we
            // show it in the comment
            ref.set("dataType", entry.getValue());
            ref.set("comment", entry.getValue());
            refs.add(ref);
        }
        return refs;
    }

    /**
     * This creates the artificial processes to connect the graph (edges)
     * 
     * @param persistedEntities
     * @return
     */
    static List<Referenceable> buildTalendArtificialComponents(final Map<String, Pair<Referenceable, Id>> persistedEntities) {
        Map<String, Referenceable> artificialRefs = new LinkedHashMap<>();
        for (Map.Entry<String, Pair<Referenceable, Id>> entry : persistedEntities.entrySet()) {
            Referenceable ref = entry.getValue().getLeft();
            Id id = entry.getValue().getRight();

            List<String> outputs = (List) ref.get("outputs");
            if (outputs != null && !outputs.isEmpty()) {
                for (String output : outputs) {
                    Referenceable artificialRef = artificialRefs.get(output);
                    if (artificialRef == null) {
                        Id outputId = persistedEntities.get(output).getRight();
                        artificialRef = buildTalendArtificialComponent(output, Arrays.asList(id), Arrays.asList(outputId));
                    } else {
                        List<Id> inputs = (List) artificialRef.get("inputs");
                        if (inputs != null) {
                            List<Id> newInputs = new ArrayList<>(inputs);
                            newInputs.add(id);
                            artificialRef.set("inputs", newInputs);
                        }
                    }
                    artificialRefs.put(output, artificialRef);
                }
            }
        }
        return new ArrayList<>(artificialRefs.values());
    }

    /**
     *
     * @param name
     * @param inputs
     * @param outputs
     * @return
     */
    static Referenceable buildTalendArtificialComponent(final String name, final List<Id> inputs, final List<Id> outputs) {
        Referenceable ref = new Referenceable(TALEND_ARTIFICIAL_COMPONENT_TYPE);
        ref.set("name", name);
        ref.set("inputs", inputs);
        ref.set("outputs", outputs);
        return ref;
    }

    /**
     * THis is a generic method to build a generic Referenceable
     * 
     * @param refType
     * @param metadata
     * @param traitTypes
     * @return
     */
    private static Referenceable buildReferenceable(final String refType, final Map<String, Object> metadata,
            final String... traitTypes) {
        Referenceable ref = new Referenceable(refType, traitTypes);
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            ref.set(entry.getKey(), entry.getValue());
        }
        return ref;
    }
}
