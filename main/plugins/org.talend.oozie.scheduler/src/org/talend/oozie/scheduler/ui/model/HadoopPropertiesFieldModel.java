package org.talend.oozie.scheduler.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;

public class HadoopPropertiesFieldModel extends ExtendedTableModel<HashMap<String, Object>> {

    public HadoopPropertiesFieldModel(String name) {
        super(name);
        setProperties(new ArrayList<HashMap<String, Object>>());
    }

    public HadoopPropertiesFieldModel(List<HashMap<String, Object>> conditionTypeList, String name) {
        super(name);
        setProperties(conditionTypeList);
    }

    public void setProperties(List<HashMap<String, Object>> properties) {
        registerDataList((List<HashMap<String, Object>>) properties);
    }

    public HashMap<String, Object> createHadoopPropertiesType() {
        return new HashMap<String, Object>();
    }
}
