package org.talend.lineage.cloudera;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.talend.lineage.cloudera.entity.TalendDataset;
import org.talend.lineage.cloudera.util.ClouderaAPIUtil;
import org.talend.lineage.cloudera.util.ClouderaFieldConvertor;

import com.cloudera.nav.sdk.client.writer.ResultSet;
import com.cloudera.nav.sdk.model.SourceType;
import com.cloudera.nav.sdk.model.entities.EntityType;
import com.cloudera.nav.sdk.model.entities.FileFormat;
import com.cloudera.nav.sdk.model.entities.HdfsEntity;
import com.google.common.collect.ImmutableList;

public class DriverIT {

    public static void main(String[] args) {

        LineageCreator lc = new LineageCreator("http://localhost", "http://localhost:7187/api/v12",
                "http://localhost:7187/api/v12/metadata/plugin", "admin", "admin", "Job007", "project003", true, true,
                12);

        LinkedHashMap<String, String> sch00 = new LinkedHashMap<String, String>();
        sch00.put("col1", "INT");
        sch00.put("col2", "STRING");
        sch00.put("col3", "DOUBLE");
        List<String> nn0Inputs = Arrays.asList("tXXXInputDelimited");
        List<String> nn0Outputs = Arrays.asList("tUniqRow");
        lc.addNodeToLineage("tXXXInputDelimited", sch00, nn0Inputs, nn0Outputs, new java.util.HashMap<String, Object>());

        LinkedHashMap<String, String> sch02 = new LinkedHashMap<String, String>();
        sch02.put("col1", "INT");
        sch02.put("col2", "STRING");
        sch02.put("col3", "DOUBLE");
        List<String> nn2Inputs = Arrays.asList("tXXXInputDelimited");
        List<String> nn2Outputs = Arrays.asList("tXXXOutputDelimited");
        lc.addNodeToLineage("tUniqRow", sch02, nn2Inputs, nn2Outputs, new java.util.HashMap<String, Object>());

        LinkedHashMap<String, String> sch01 = new LinkedHashMap<String, String>();
        sch01.put("col1", "INT");
        sch01.put("col2", "STRING");
        sch01.put("col3", "DOUBLE");
        List<String> nn1Inputs = Arrays.asList("tUniqRow");
        List<String> nn1Outputs = Arrays.asList("tXXXOutputDelimited");
        lc.addNodeToLineage("tXXXOutputDelimited", sch01, nn1Inputs, nn1Outputs, new java.util.HashMap<String, Object>());

        lc.addDataset(sch01, "tXXXOutputDelimited", "/tmp", "JSON");

        lc.sendToLineageProvider(false);
    }

}
