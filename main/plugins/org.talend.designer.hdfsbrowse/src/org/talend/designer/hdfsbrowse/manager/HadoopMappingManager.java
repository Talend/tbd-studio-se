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
package org.talend.designer.hdfsbrowse.manager;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.designer.hdfsbrowse.HDFSPlugin;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopMappingManager {

    private static HadoopMappingManager instance = new HadoopMappingManager();

    private final static String MAPPING_FILE_PATH = "resources/mapping.xml"; //$NON-NLS-1$

    private Map<String, List<String>> componentParamsMap = new HashMap<String, List<String>>();

    private HadoopMappingManager() {
        init();
    }

    public static HadoopMappingManager getInstance() {
        return instance;
    }

    private void init() {
        Bundle b = Platform.getBundle(HDFSPlugin.PLUGIN_ID);
        URL url = null;
        try {
            url = FileLocator.toFileURL(FileLocator.find(b, new Path(MAPPING_FILE_PATH), null));
            if (url != null) {
                File mappingFile = new File(url.getFile());
                if (mappingFile.exists()) {
                    SAXReader saxReader = new SAXReader();
                    Document document = saxReader.read(mappingFile);
                    Element rootElement = document.getRootElement();
                    Element map2componentEle = rootElement.element("map-component"); //$NON-NLS-1$
                    List elements = map2componentEle.elements("map"); //$NON-NLS-1$
                    for (Object obj : elements) {
                        if (obj instanceof Element) {
                            Element mapEle = (Element) obj;
                            String mapKey = mapEle.attributeValue("key"); //$NON-NLS-1$
                            if (mapKey == null) {
                                continue;
                            }
                            List<String> values = new ArrayList<String>();
                            List mapValues = mapEle.elements("value"); //$NON-NLS-1$
                            for (Object valueObj : mapValues) {
                                if (valueObj instanceof Element) {
                                    Element valueEle = (Element) valueObj;
                                    values.add(valueEle.getText());
                                }
                            }
                            componentParamsMap.put(mapKey, values);
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    public Map<String, List<String>> getComponentParamsMap() {
        return this.componentParamsMap;
    }

}
