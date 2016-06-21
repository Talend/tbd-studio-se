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
package org.talend.lineage.cloudera;

import java.util.List;
import java.util.Map;

/**
 * A model to represent Talend components visible on the designer. The model captures all of the metadata associated to
 * a Talend component.
 * 
 * For a given Talend component we generate a instance of this class with : the component's name, the component's output
 * schema, the list of input components (downstream from the component), and The list of output components (upstream
 * from the component).
 * 
 * Provider specific mappers (Cloudera Navigator, Atlas, ...) consume instaces of LineageNode to create their own
 * provider specific lineages.
 * 
 */
public class NavigatorNode {

    /*
     * Component name
     */
    private String              name;

    /*
     * Component output schema
     */
    private Map<String, String> schema;

    /*
     * List of downstream components
     */
    private List<String>        inputNodes;

    /*
     * List of upstream components
     */
    private List<String>        outputNodes;

    /*
     * Component's metadata
     */
    private Map<String, Object> metadata;

    public NavigatorNode(String name, Map<String, String> schema, List<String> inputNodes, List<String> outputNodes) {
        this.name = name;
        this.schema = schema;
        this.inputNodes = inputNodes;
        this.outputNodes = outputNodes;
    }

    public List<String> getInputNodes() {
        return this.inputNodes;
    }

    public void setInputNodes(List<String> inputNodes) {
        this.inputNodes = inputNodes;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOutputNodes() {
        return this.outputNodes;
    }

    public void setOutputNodes(List<String> outputNodes) {
        this.outputNodes = outputNodes;
    }

    public Map<String, String> getSchema() {
        return this.schema;
    }

    public void setSchema(Map<String, String> schema) {
        this.schema = schema;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "[" + name + "|" + schema + "|" + inputNodes + "|" + outputNodes + "]";
    }
}
