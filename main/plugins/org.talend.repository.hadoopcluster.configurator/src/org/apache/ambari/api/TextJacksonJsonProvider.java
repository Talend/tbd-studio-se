// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
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
import com.fasterxml.jackson.jaxrs.cfg.Annotations;
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

    protected boolean hasMatchingMediaType(MediaType mediaType) {
        if (mediaType != null) {
            // need to compare type and subtype
            // after HDP3.0,will include charset => Content-Type: text/plain;charset=utf-8
            if (MediaType.TEXT_PLAIN_TYPE.getType().equalsIgnoreCase(mediaType.getType())
                    && MediaType.TEXT_PLAIN_TYPE.getSubtype().equalsIgnoreCase(mediaType.getSubtype())) {
                return true;
            }
        }
        return super.hasMatchingMediaType(mediaType);
    }
}
