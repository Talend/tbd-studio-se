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
package org.talend.designer.pigmap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.utils.loader.MyURLClassLoader;
import org.talend.commons.ui.utils.loader.MyURLClassLoader.IAssignableClassFilter;
import org.talend.commons.utils.generation.JavaUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ILibraryManagerService;
import org.talend.core.ILibraryManagerUIService;
import org.talend.core.model.general.Project;
import org.talend.core.model.repository.ResourceModelUtils;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.pigmap.PigMapConstants;
import org.talend.designer.pigmap.PigMapPlugin;
import org.talend.designer.rowgenerator.data.AbstractTalendFunctionParser;
import org.talend.designer.rowgenerator.data.Function;
import org.talend.designer.rowgenerator.data.FunctionManager;
import org.talend.repository.ProjectManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class PigFunctionParser extends AbstractTalendFunctionParser {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.rowgenerator.data.AbstractFunctionParser#parse()
     */
    @Override
    public void parse() {
        typeMethods.clear();
        try {
            Bundle b = Platform.getBundle(PigMapPlugin.PLUGIN_ID);
            URL fileUrl = FileLocator.toFileURL(FileLocator.find(b, new Path("resources/" + "PigExpressionBuilder.xml"), null)); //$NON-NLS-1$ //$NON-NLS-2$
            File pigFile = new File(fileUrl.getFile());
            if (!pigFile.exists()) {
                throw new FileNotFoundException();
            }
            // use dom parse it
            useDomParse(fileUrl.getFile());
            // parse datafu.jar
            datafuJarParse();
            // parse Pig UDF Functions
            super.parse();
            // add the var user define Functions
            addVarUserDefineFunctions();
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    private void addVarUserDefineFunctions() {
        Map<String, String> defineFunctionsAlias = MapDataHelper.getDefinefunctionsalias();
        for (String alias : defineFunctionsAlias.keySet()) {
            StringBuffer strTemp = new StringBuffer();
            strTemp.append(alias).append(" is an alias of ").append(defineFunctionsAlias.get(alias)); //$NON-NLS-1$
            strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
            strTemp.append("{talendTypes} String"); //$NON-NLS-1$
            strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
            strTemp.append("{Category}" + PigMapConstants.USER_DEFINE_FUNCTIONS); //$NON-NLS-1$
            strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
            strTemp.append("{param}");//$NON-NLS-1$
            strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
            strTemp.append("{example}"); //$NON-NLS-1$
            String newAliasNoQuotes = TalendQuoteUtils.removeQuotesIfExist(alias);
            parseJavaCommentToFunctions(strTemp.toString(), newAliasNoQuotes, newAliasNoQuotes, newAliasNoQuotes, true);
        }
    }

    @Override
    protected Function parseJavaCommentToFunctions(String str, String className, String fullName, String funcName,
            boolean isSystem) {
        Function function = super.parseJavaCommentToFunctions(str, className, fullName, funcName, isSystem);
        // Pig UDF Functions set a default category
        if (function != null && !isSystem) {
            String category = parseCategoryType(str);
            function.setCategory(PigMapConstants.PIG_UDF_FUNCTIONS);
            function.setPreview(category);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.rowgenerator.data.AbstractTalendFunctionParser#getPackageFragment()
     */
    @Override
    protected String getPackageFragment() {
        return JavaUtils.JAVA_PIGUDF_DIRECTORY;
    }

    /**
     * 
     * DOC Comment method "useDomParse".
     * 
     * @param fileUrl
     */
    public void useDomParse(String fileUrl) {
        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dombuilder = domfac.newDocumentBuilder();
            InputStream is = new FileInputStream(fileUrl);
            Document doc = dombuilder.parse(is);
            Element root = doc.getDocumentElement();
            NodeList functions = root.getChildNodes();
            if (functions != null) {
                for (int i = 0; i < functions.getLength(); i++) {
                    // category
                    Node category = functions.item(i);
                    if (category.getNodeType() == Node.ELEMENT_NODE) {
                        String categoryName = category.getAttributes().getNamedItem("name").getNodeValue(); //$NON-NLS-1$
                        // function
                        for (Node function = category.getFirstChild(); function != null; function = function.getNextSibling()) {
                            //
                            StringBuffer strTemp = new StringBuffer();
                            String functionName = null, syntax = null, usage = null;
                            if (function.getNodeType() == Node.ELEMENT_NODE) {
                                functionName = function.getAttributes().getNamedItem("name").getNodeValue(); //$NON-NLS-1$
                                strTemp.append(functionName);
                                for (Node node = function.getFirstChild(); node != null; node = node.getNextSibling()) {
                                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                                        // Desc
                                        if (node.getNodeName().equals("Desc")) { //$NON-NLS-1$
                                            String desc = node.getFirstChild().getNodeValue();
                                            strTemp.append(": " + desc); //$NON-NLS-1$
                                        }
                                        // Syntax
                                        if (node.getNodeName().equals("Syntax")) { //$NON-NLS-1$
                                            syntax = node.getFirstChild().getNodeValue();
                                        }
                                        // Usage
                                        if (node.getNodeName().equals("Usage")) { //$NON-NLS-1$
                                            usage = node.getFirstChild().getNodeValue();
                                        }
                                    }
                                }
                                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                                strTemp.append("{talendTypes} String"); //$NON-NLS-1$
                                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                                strTemp.append("{Category}" + categoryName); //$NON-NLS-1$
                                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                                strTemp.append("{param}" + usage); //$NON-NLS-1$
                                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                                strTemp.append("{example}" + syntax); //$NON-NLS-1$
                            }
                            if (strTemp != null && !strTemp.toString().equals("")) { //$NON-NLS-1$
                                parseJavaCommentToFunctions(strTemp.toString(), categoryName, functionName, functionName, true);
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void datafuJarParse() throws Exception {
        String[] jarNames = new String[] { "pig-0.10.0.jar", "datafu-1.2.0.jar" };//$NON-NLS-1$//$NON-NLS-2$
        List<URL> listURL = new ArrayList<URL>();
        List<String> missModulesNeeded = new ArrayList<String>();
        ILibraryManagerService librairesService = (ILibraryManagerService) GlobalServiceRegister.getDefault().getService(
                ILibraryManagerService.class);
        String javaLibPath = getJavaLibPath();
        for (String jarName : jarNames) {
            if (!librairesService.contains(jarName)) {
                missModulesNeeded.add(jarName);
            }
        }
        if (!missModulesNeeded.isEmpty()) {
            // if there is no local library available, try to install it from the wizard
            if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibraryManagerUIService.class)) {
                ILibraryManagerUIService libUiService = (ILibraryManagerUIService) GlobalServiceRegister.getDefault().getService(
                        ILibraryManagerUIService.class);
                if (libUiService != null) {
                    libUiService.installModules(missModulesNeeded.toArray(new String[0]));
                }
            }
        }
        for (String jarName : jarNames) {
            librairesService.retrieve(jarName, javaLibPath, new NullProgressMonitor());
            File jar = new Path(javaLibPath).append(jarName).toFile();
            listURL.add(jar.toURL());
        }

        try {
            MyURLClassLoader cl = new MyURLClassLoader(listURL.toArray(new URL[0]));
            Class newClass = Class.forName("org.apache.pig.EvalFunc", true, cl);//$NON-NLS-1$
            Class[] classes = cl.getAssignableClasses(newClass, new AssignableClassFilter());
            for (Class classe : classes) {
                String className = classe.getSimpleName();
                StringBuffer strTemp = new StringBuffer();
                // help
                strTemp.append(className + ":" + PigMapConstants.NEWLINE_CHARACTER); //$NON-NLS-1$
                Constructor[] constructors = classe.getConstructors();
                for (Constructor constructor : constructors) {
                    // constructor
                    String constructorName = constructor.toGenericString();
                    int constructorNameIndex = constructorName.lastIndexOf(className);
                    constructorName = constructorName.substring(constructorNameIndex, constructorName.length());
                    strTemp.append(constructorName);
                    strTemp.append(FunctionManager.FUN_PARAM_SEPARATED + PigMapConstants.NEWLINE_CHARACTER);
                }
                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                strTemp.append("{talendTypes} String"); //$NON-NLS-1$
                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                strTemp.append("{Category}" + PigMapConstants.PIG_DATAFU_FUNCTIONS); //$NON-NLS-1$
                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                strTemp.append("{param}" + "");//$NON-NLS-1$//$NON-NLS-2$
                strTemp.append(PigMapConstants.NEWLINE_CHARACTER);
                strTemp.append("{example}" + ""); //$NON-NLS-1$//$NON-NLS-2$
                parseJavaCommentToFunctions(strTemp.toString(), classe.getName(), classe.getName(), className, true);
            }
        } catch (Exception ex) {
            ExceptionHandler.process(ex);
        }
    }

    public static String getJavaLibPath() {
        Project project = ProjectManager.getInstance().getCurrentProject();
        IProject physProject;
        String tmpFolder = System.getProperty("user.dir"); //$NON-NLS-1$
        try {
            physProject = ResourceModelUtils.getProject(project);
            tmpFolder = physProject.getFolder("temp").getLocation().toPortableString(); //$NON-NLS-1$
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        tmpFolder = tmpFolder + "/pigmap"; //$NON-NLS-1$
        File file = new File(tmpFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return tmpFolder + "/"; //$NON-NLS-1$
    }

    class AssignableClassFilter implements IAssignableClassFilter {

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.commons.ui.utils.loader.MyURLClassLoader.IAssignableClassFilter#filter(java.net.URL[])
         */
        @Override
        public boolean filter(URL[] urls) {
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.commons.ui.utils.loader.MyURLClassLoader.IAssignableClassFilter#filter(java.lang.Class)
         */
        @Override
        public boolean filter(Class clazz) {
            String packageName = clazz.getPackage().getName();
            // filter the package "datafu.pig"
            if (packageName != null && !packageName.startsWith(PigMapConstants.FILTER_PIG_DATAFU_FUNCTION_PACKAGE)) {
                return true;
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.talend.commons.ui.utils.loader.MyURLClassLoader.IAssignableClassFilter#filter(java.lang.String)
         */
        @Override
        public boolean filter(String clazzName) {
            // filter the package "datafu.pig"
            if (!clazzName.startsWith(PigMapConstants.FILTER_PIG_DATAFU_FUNCTION_PACKAGE)
                    || clazzName.contains(PigMapConstants.SPECIAL_CHARACTER_$)) {
                return true;
            }
            return false;
        }
    }
}
