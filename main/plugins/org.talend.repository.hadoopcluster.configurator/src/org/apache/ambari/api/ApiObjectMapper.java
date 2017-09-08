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
package org.apache.ambari.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * This class is injected to give us control over the object mapper
 */
public class ApiObjectMapper extends ObjectMapper {

    public ApiObjectMapper() {
        // Print the JSON with indentation (ie. pretty print)
        configure(SerializationFeature.INDENT_OUTPUT, true);

        // Allow JAX-B annotations.
        setAnnotationIntrospector(AnnotationIntrospector.pair(getSerializationConfig().getAnnotationIntrospector(),
                new JaxbAnnotationIntrospector()));

        // Make Jackson respect @XmlElementWrapper.
        enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME);

        // Print all dates in ISO8601 format
        setDateFormat(makeISODateFormat());
    }

    public static DateFormat makeISODateFormat() {
        DateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
        iso8601.setCalendar(cal);
        return iso8601;
    }

    public static DateFormat makeDateFormat(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
        dateFormat.setCalendar(cal);
        return dateFormat;
    }
}
