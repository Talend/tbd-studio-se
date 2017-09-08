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
package org.talend.repository.hadoopcluster.conf;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.talend.commons.exception.ExceptionHandler;

/**
 * created by ycbai on 2016年6月23日 Detailled comment
 *
 */
public class HadoopConfsResolveUtils {

    private static final String PROPERTY_XPATH = "/configuration/property"; //$NON-NLS-1$

    public static boolean removePropertyElement(File confFile, String propertyName) {
        try {
            Document doc = readConfFile(confFile);
            boolean removed = removePropertyElement(doc, propertyName);
            if (removed) {
                writeConfFile(confFile, doc);
                return true;
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return false;
    }

    public static boolean removePropertyElement(Document doc, String propertyName) {
        Element propertyElement = getPropertyElement(doc, propertyName);
        if (propertyElement != null) {
            propertyElement.getParent().remove(propertyElement);
            return true;
        }
        return false;
    }

    public static Element getPropertyElement(File confFile, String propertyName) {
        if (confFile == null || propertyName == null) {
            return null;
        }
        Element element = null;
        try {
            Document doc = readConfFile(confFile);
            element = getPropertyElement(doc, propertyName);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return element;
    }

    @SuppressWarnings("rawtypes")
    public static Element getPropertyElement(Document doc, String propertyName) {
        if (doc == null || propertyName == null) {
            return null;
        }
        Element element = null;
        try {
            List selectNodes = doc.selectNodes(PROPERTY_XPATH);
            Iterator nodesIter = selectNodes.iterator();
            while (nodesIter.hasNext()) {
                Object node = nodesIter.next();
                if (!(node instanceof Element)) {
                    continue;
                }
                Element propertyElement = (Element) node;
                String pName = getPropertyName(propertyElement);
                if (propertyName.equals(pName)) {
                    element = propertyElement;
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return element;
    }

    public static String getPropertyName(Element propertyElement) {
        if (propertyElement == null) {
            return null;
        }
        Iterator nameIter = propertyElement.elementIterator("name"); //$NON-NLS-1$
        while (nameIter.hasNext()) {
            Object nameNode = nameIter.next();
            if (!(nameNode instanceof Element)) {
                continue;
            }
            Element nameElement = (Element) nameNode;
            return nameElement.getText();
        }
        return null;
    }

    public static List<Element> getAllPropertyElements(File confFile) {
        List<Element> elements = new ArrayList<>();
        if (confFile == null) {
            return elements;
        }
        try {
            Document doc = readConfFile(confFile);
            elements = getAllPropertyElements(doc);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return elements;
    }

    public static List<Element> getAllPropertyElements(Document doc) {
        List<Element> elements = new ArrayList<>();
        if (doc == null) {
            return elements;
        }
        try {
            List selectNodes = doc.selectNodes(PROPERTY_XPATH);
            Iterator nodesIter = selectNodes.iterator();
            while (nodesIter.hasNext()) {
                Object node = nodesIter.next();
                if (!(node instanceof Element)) {
                    continue;
                }
                elements.add((Element) node);
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return elements;
    }

    public static void writeConfFile(File file, Document doc) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileWriter(file), format);
            writer.write(doc);
            writer.close();
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    public static Document readConfFile(File file) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(file);
        } catch (DocumentException e) {
            ExceptionHandler.process(e);
        }
        return document;
    }

}
