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
package org.talend.lineage.cloudera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Instant;
import org.talend.lineage.cloudera.entity.TalendDataset;
import org.talend.lineage.cloudera.util.ClouderaAPIUtil;
import org.talend.lineage.cloudera.util.ClouderaFieldConvertor;
import org.talend.lineage.common.AbstractLineageCreator;
import org.talend.lineage.common.ILineageCreator;

import com.cloudera.nav.sdk.client.NavigatorPlugin;
import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.Source;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.Entity;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.entities.FileFormat;
import com.cloudera.nav.sdk.model.entities.HdfsEntity;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Cloudera Navigator lineage creator.
 * 
 * An instance of this class is created at the beginning of a Talend job that uses Cloudera Navigator. The main role of
 * this class is to hide the Cloudera Navigator implementation details from being directly called in the studio.
 *
 */
public class LineageCreator extends AbstractLineageCreator implements ILineageCreator {

    // TODO : the PluginConfigurationAPI is going to change its name on version 1.1,
    // I keep the variable here for compatibility
    public static final String             APP_URL                = "application_url";                                      //$NON-NLS-1$

    public static final String             FILE_FORMAT            = "file_format";                                          //$NON-NLS-1$

    public static final String             METADATA_URI           = "metadata_parent_uri";                                  //$NON-NLS-1$

    public static final String             NAMESPACE              = "namespace";                                            //$NON-NLS-1$

    public static final String             NAV_URL                = "navigator_url";                                        //$NON-NLS-1$

    public static final String             USERNAME               = "username";                                             //$NON-NLS-1$

    public static final String             PASSWORD               = "password";                                             //$NON-NLS-1$

    public static final String             AUTOCOMMIT             = "autocommit";                                           //$NON-NLS-1$

    public static final String             DISABLE_SSL_VALIDATION = "disable_ssl_validation";                               //$NON-NLS-1$

    public static final String             API_VERSION            = "navigator_api_version";                                //$NON-NLS-1$

    // minimum supported version : Cloudera Navigator 2.4.0
    public static final int                DEFAULT_API_VERSION    = 8;                                                      // $NON-NLS-1$

    private static org.apache.log4j.Logger LOG                    = org.apache.log4j.Logger.getLogger(LineageCreator.class);

    private String                         jobName;

    private String                         projectName;

    private Source                         fileSystem;

    private NavigatorPlugin                plugin;

    private List<NavigatorNode>            inputNavigatorNodes    = new ArrayList<NavigatorNode>();

    private List<Entity>                   datasets               = new ArrayList<Entity>();

    private Instant                        creationInstant;

    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName, String projectName) {
        this(clientApplicationUrl, navigatorUrl, metadataUri, username, password, jobName, projectName, false);
    }

    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName, String projectName, Boolean autoCommit) {
        this(clientApplicationUrl, navigatorUrl, metadataUri, username, password, jobName, projectName, autoCommit, false,
                DEFAULT_API_VERSION);
    }

    /**
     * The constructor creates an instance of NavigatorPlugin to manage all of the requests/responses to/from Cloudera
     * Navigator
     */
    public LineageCreator(String clientApplicationUrl, String navigatorUrl, String metadataUri, String username, String password,
            String jobName, String projectName, Boolean autoCommit, Boolean disableValidationSSL, int apiVersion) {
        Map<String, Object> configurationMap = new HashMap<String, Object>();
        configurationMap.put(LineageCreator.APP_URL, clientApplicationUrl);
        configurationMap.put(LineageCreator.METADATA_URI, metadataUri);
        configurationMap.put(LineageCreator.USERNAME, username);
        configurationMap.put(LineageCreator.PASSWORD, password);
        // File Format allow only JSON for the moment
        configurationMap.put(LineageCreator.FILE_FORMAT, "JSON"); //$NON-NLS-1$
        configurationMap.put(LineageCreator.NAMESPACE, "Talend"); //$NON-NLS-1$

        // TODO the next two options are not fully integrated on the PluginConfigurationFactory API.
        // They will be soon.
        if (disableValidationSSL) {
            configurationMap.put(LineageCreator.DISABLE_SSL_VALIDATION, "true"); //$NON-NLS-1$
        }
        if (autoCommit) {
            configurationMap.put(LineageCreator.AUTOCOMMIT, "true"); //$NON-NLS-1$
        }

        configurationMap.put(LineageCreator.API_VERSION, apiVersion); // $NON-NLS-1$

        // Extract Navigator URL
        configurationMap.put(LineageCreator.NAV_URL, ClouderaAPIUtil.extractNavigatorURL(navigatorUrl));

        this.plugin = NavigatorPlugin.fromConfigMap(configurationMap);
        // We call the Cloudera Navigator API using the client
        // This call will fail if Cloudera Navigator is not available
        // TODO: Wrap this call with the same die on error that we use when writing the entities
        this.fileSystem = this.plugin.getClient().getOnlySource(SourceType.HDFS);
        this.jobName = jobName;
        this.projectName = projectName;
        this.creationInstant = new Instant();
    }

    /**
     * Creates a LineageNode to represent a Talend Graphical Node. The Talend Graphical Node must have a schema or it
     * will not get created. The LienageNode is then added to the list of nodes associated with this Lineage creator
     * (which is associated with a single job)
     *
     * @param componentName The component name
     * @param schema An Hashmap containing the union of any output column name and column type from the component.
     * @param inputNodes The list of the nodes before the current node, defined by their unique name
     * @param outputNodes The list of the nodes after the current node, defined by their unique name
     * @param metadata components metadata
     */
    public void addNodeToLineage(String name, Map<String, String> schema, List<String> inputNodes, List<String> outputNodes,
            Map<String, Object> metadata) {
        if (schema.size() > 0) {
            this.inputNavigatorNodes.add(new NavigatorNode(name, schema, inputNodes, outputNodes));
        }
    }

    /**
     * Creates a TalendDataSet with the data retrieved from an input/output tFileInputXXX Talend Graphical Node.
     *
     * @param schema The Hashmap of column name and column type of the component.
     * @param componentName The component name
     * @param fileSystemPath The HDFS patht to the file where the component read/write data
     * @param fileFormat The file format as a String (ie: "CSV", "XML", "JSON", ...)
     */
    public void addDataset(Map<String, String> schema, String componentName, String fileSystemPath, String fileFormat) {
        addDataset(schema, componentName, fileSystemPath, FileFormat.valueOf(fileFormat));
    }

    /**
     * Creates a TalendDataSet with the data retrieved from an input/output tFileInputOutputXXX Talend Graphical Node.
     *
     * @param schema The Hashmap of column name and column type of the component.
     * @param componentName The component name
     * @param fileSystemPath The HDFS path to the file where the component read/write data
     * @param fileFormat The file format as a FileFormat
     */
    public void addDataset(Map<String, String> schema, String componentName, String fileSystemPath, FileFormat fileFormat) {
        HdfsEntity container = new HdfsEntity(fileSystemPath, EntityType.DIRECTORY, this.fileSystem.getIdentity());

        TalendDataset dataset = new TalendDataset(ClouderaAPIUtil.getDatasetName(fileSystemPath), componentName,
                this.jobName + this.projectName);
        dataset.setDataContainer(container);
        dataset.setFileFormat(fileFormat);
        dataset.setFields(ClouderaFieldConvertor.convertToTalendField(schema));
        dataset.addTags(ImmutableList.of("Talend", this.jobName)); //$NON-NLS-1$
        dataset.setCreated(this.creationInstant);

        this.datasets.add(dataset);
    }

    /**
     * Calls the Cloudera Navigator Mapper to map Lineage entities to Cloudera Navigator entities and writes them to
     * Cloudera Navigator using NavigatorPlugin.
     *
     * @param dieOnError throw or not a RuntimeException on error
     */
    public void sendToLineageProvider(Boolean dieOnError) {
        try {
            // Start by writing the datasets
            ResultSet results = this.plugin.write(this.datasets);
            if (results.hasErrors()) {
                throw new RuntimeException(results.toString());
            }

            // Create Cloudera navigator mapper
            EntityMapper tem = new EntityMapper(this.inputNavigatorNodes, this.jobName + this.projectName);
            tem.addTag(this.jobName);

            if (LOG.isDebugEnabled()) {
                for (NavigatorNode nn : this.inputNavigatorNodes) {
                    LOG.debug(nn);
                }
            }

            // Map Lineage nodes to Cloudera navigator entities
            List<Entity> entities = tem.map();
            LOG.debug(tem.toString());

            // Write the rest of the entities (processing components)
            results = this.plugin.write(entities);
            if (results.hasErrors()) {
                throw new RuntimeException(results.toString());
            }
        } catch (RuntimeException e) {
            if (dieOnError) {
                throw e;
            } else {
                LOG.error(e);
            }
        }

    }

}
