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
package org.talend.hadoop.distribution.dynamic.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.talend.core.runtime.maven.MavenUrlHelper;
import org.talend.designer.maven.aether.node.DependencyNode;
import org.talend.hadoop.distribution.dynamic.bean.IVariable;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicDistributionUtils {

    public static String fillTemplate(String templateString, String... args) {
        String fullString = templateString;
        if (args != null && 0 < args.length) {
            for (int i = 0; i < args.length; ++i) {
                String arg = args[i];
                fullString = fullString.replaceAll("\\{" + i + "\\}", arg); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return fullString;
    }

    public static String getRootVariable(String variable) throws Exception {
        List<String> groups = getVariables(variable);
        String variableTmp = variable;
        for (String group : groups) {
            String value = group.replaceAll("\\.", "_"); //$NON-NLS-1$ //$NON-NLS-2$
            variableTmp = variableTmp.replace(group, value);
        }

        String rootVariable = ""; //$NON-NLS-1$
        int index = variableTmp.indexOf("."); //$NON-NLS-1$
        if (0 < index) {
            rootVariable = variable.substring(0, index).trim();
        } else if (index < 0) {
            rootVariable = variable;
        }
        return rootVariable;
    }

    public static Object calculate(IVariable variable, String str) throws Exception {

        if (StringUtils.isEmpty(str)) {
            return str;
        }
        Object result = null;
        String subVariable = str;
        if (isExpression(subVariable)) {
            List<String> groups = getVariables(subVariable);
            int strLengh = subVariable.length();
            String resolvedVariable = subVariable;
            for (String group : groups) {
                String expression = group.substring(1, group.length() - 1).trim();
                if (group.length() == strLengh) {
                    result = getVariable(variable, expression);
                    break;
                } else {
                    expression = (String) getVariable(variable, expression);
                    resolvedVariable = resolvedVariable.replace(group, expression);
                }
            }
            if (result == null) {
                result = resolvedVariable;
            }
        } else {
            result = subVariable;
        }
        return result;
    }

    public static Object getVariable(IVariable variable, String expression) throws Exception {
        Set<Object> visitedObjects = new HashSet<>();
        return getVariable(variable, variable, expression, visitedObjects);
    }

    private static Object getVariable(IVariable root, IVariable currentVariable, String expression, Set<Object> visitedObjects)
            throws Exception {
        if (visitedObjects.contains(currentVariable)) {
            throw new UnsupportedOperationException("cicle usage!");
        }
        String rootVariable = getRootVariable(expression);
        if (StringUtils.isEmpty(rootVariable)) {
            throw new UnsupportedOperationException("No root variable found!");
        }
        String childExpression = null;
        if (rootVariable.length() < expression.length()) {
            childExpression = expression.substring(rootVariable.length() + 1).trim();
        }
        rootVariable = (String) calculate(root, rootVariable);
        Object value = currentVariable.getVariableValue(rootVariable);
        if (childExpression != null) {
            if (value == null) {
                value = null;
            } else if (value instanceof List) {
                value = getVariable(root, new ListVariable((List) value), childExpression, visitedObjects);
            } else if (value instanceof Map) {
                value = getVariable(root, new MapVariable((Map) value), childExpression, visitedObjects);
            } else if (!(value instanceof IVariable)) {
                value = null;
            } else {
                value = getVariable(root, (IVariable) value, childExpression, visitedObjects);
            }
        }
        return value;
    }

    public static boolean isExpression(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        if (str.contains("{")) { //$NON-NLS-1$
            return true;
        }
        return false;
    }

    private static List<String> getVariables(String expression) throws Exception {
        List<String> variables = new ArrayList<>();
        int level = 0;
        int start = -1;
        for (int i = 0; i < expression.length(); ++i) {
            char ch = expression.charAt(i);
            if (ch == '{') {
                if (level == 0) {
                    start = i;
                }
                ++level;
            } else if (ch == '}') {
                --level;
            } else {
                continue;
            }
            if (level == 0 && 0 <= start) {
                variables.add(expression.substring(start, i + 1));
            }
        }
        return variables;
    }

    public static String getPluginKey(String distri, String version, String module) {
        String key = "Dynamic_" + distri + "_" + version + "_" + module;
        key = key.replaceAll("\\.", "_");
        return key;
    }

    public static String getMvnUrl(DependencyNode node, String repositoryUri) {
        String classifier = node.getClassifier();
        if (StringUtils.isEmpty(classifier)) {
            classifier = null;
        }
        return MavenUrlHelper.generateMvnUrl(repositoryUri, node.getGroupId(), node.getArtifactId(), node.getVersion(), null,
                classifier);
    }

    private static class MapVariable implements IVariable {

        private Map map;

        public MapVariable(Map map) {
            this.map = map;
        }

        @Override
        public Object getVariableValue(String variable) throws Exception {
            return map.get(variable);
        }

    }

    private static class ListVariable implements IVariable {

        private List list;

        public ListVariable(List list) {
            this.list = list;
        }

        @Override
        public Object getVariableValue(String variable) throws Exception {
            // a=b,c=d
            String[] expressions = variable.split(",");
            List<Object> values = list;
            for (String expression : expressions) {
                values = getValues(values, expression);
            }
            return values;
        }

        private List<Object> getValues(List parentList, String variable) throws Exception {
            String[] expressions = variable.split("=");
            if (expressions.length <= 1) {
                throw new UnsupportedOperationException("Bad expression!");
            }
            List<Object> results = new ArrayList<>();
            if (parentList == null || parentList.isEmpty()) {
                return results;
            }
            for (Object obj : parentList) {
                if (obj instanceof IVariable) {
                    Object value = ((IVariable) obj).getVariableValue(expressions[0]);
                    if (expressions[1].equals(value)) {
                        results.add(value);
                    }
                }
            }
            return results;
        }
    }
}
