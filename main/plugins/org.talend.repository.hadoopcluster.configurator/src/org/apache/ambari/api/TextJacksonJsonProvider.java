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

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.Annotations;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
/*
 * for the bug https://issues.apache.org/jira/browse/AMBARI-9016, have to use this workaroud
 */
public class TextJacksonJsonProvider extends JacksonJsonProvider {

    /**
     * Default constructor, usually used when provider is automatically configured to be used with JAX-RS
     * implementation.
     */
    public TextJacksonJsonProvider() {
        super(null, BASIC_ANNOTATIONS);
    }

    /**
     * @param annotationsToUse Annotation set(s) to use for configuring data binding
     */
    public TextJacksonJsonProvider(Annotations... annotationsToUse) {
        super(null, annotationsToUse);
    }

    public TextJacksonJsonProvider(ObjectMapper mapper) {
        super(mapper, BASIC_ANNOTATIONS);
    }

    /**
     * Constructor to use when a custom mapper (usually components like serializer/deserializer factories that have been
     * configured) is to be used.
     * 
     * @param annotationsToUse Sets of annotations (Jackson, JAXB) that provider should support
     */
    public TextJacksonJsonProvider(ObjectMapper mapper, Annotations[] annotationsToUse) {
        super(mapper, annotationsToUse);
    }

    @Override
    protected boolean isJsonType(MediaType mediaType) {
        /*
         * As suggested by Stephen D, there are 2 ways to check: either being as inclusive as possible (if subtype is
         * "json"), or exclusive (major type "application", minor type "json"). Let's start with inclusive one, hard to
         * know which major types we should cover aside from "application".
         */
        if (mediaType != null) {
            // Ok: there are also "xxx+json" subtypes, which count as well
            String subtype = mediaType.getSubtype();
            return "json".equalsIgnoreCase(subtype) || subtype.endsWith("+json") || mediaType.equals(MediaType.TEXT_PLAIN_TYPE);
        }
        /*
         * Not sure if this can happen; but it seems reasonable that we can at least produce json without media type?
         */
        return true;
    }
}
