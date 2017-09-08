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
package org.talend.hadoop.distribution.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class DefaultConfigurationManager {

    private static final String PATH_DEFAULT_CONFIG = "resources/config/default";

    private static final String CONFIG_EXT = ".json";

    public static JSONObject loadDefaultFile(Class bundleClass, String configName) {
        return loadPathFile(bundleClass, PATH_DEFAULT_CONFIG + '/' + configName + CONFIG_EXT);
    }

    public static JSONObject loadPathFile(Class bundleClass, String path) {
        try {
            Bundle b = FrameworkUtil.getBundle(bundleClass);
            URL url = FileLocator.find(b, new Path(path), null);
            if (url != null) {
                url = FileLocator.toFileURL(url);
                if (url != null) {
                    File configFile = new File(url.getPath());
                    String contents = getContents(configFile);
                    if (contents != null) {
                        return new JSONObject(contents);
                    }

                }
            }
        } catch (IOException e) {
            // ExceptionHandler.process(e);
        } catch (JSONException e) {
            // ExceptionHandler.process(e);
        }

        return null;
    }

    private static String getContents(File configFile) {
        if (configFile != null && configFile.exists()) {
            try {
                return new Scanner(configFile).useDelimiter("\\A").next(); //$NON-NLS-1$
            } catch (FileNotFoundException e) {
                // ExceptionHandler.process(e);
            }
        }
        return null;
    }

    /**
     * if the keys is empty(String[0]), return null.
     * 
     * If the keys only contain empty string (String[1]{""}), will return the whole JSON string directly.
     * 
     * if the subKeys is not leaf keys, means, contain some children values, will return the sub JSON string directly.
     * for example, existed "HBASE/PORT", but the subKeys is only "HBASE", means will return the string of JSON which
     * contained the "PORT" or others.
     * 
     */
    public static String getValue(JSONObject jsonObject, String... keys) {
        if (keys == null || jsonObject == null) {
            return null;
        }
        if (keys.length > 0) {
            String arg = keys[0];
            if (arg != null) {
                if (arg.trim().length() == 0) { // empty key, means get all
                    return jsonObject.toString();
                }
                if (jsonObject.has(arg)) {
                    try {
                        Object obj = jsonObject.get(arg);
                        if (obj != null) {
                            String[] subKeys = (String[]) ArrayUtils.remove(keys, 0);
                            if (subKeys.length == 0) { // no key yet.
                                return obj.toString();
                            } else if (obj instanceof JSONObject) {
                                return getValue((JSONObject) obj, subKeys);
                            } else if (obj instanceof JSONArray) {
                                return String.valueOf(obj); // return all array directly
                            } else {
                                return String.valueOf(obj);
                            }
                        }
                    } catch (JSONException e) {
                        //
                    }
                }
            }
        }
        return null;
    }
}
