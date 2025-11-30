/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model;

import java.util.HashMap;
import java.util.Map;

import com.ge.onchron.verif.utils.RestUtils;
import com.ge.onchron.verif.utils.TextUtils;
import io.restassured.http.Method;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

public class RestRequest {

    public static String URI_KEY = "uri";

    @Setter
    @Getter
    private Method method;
    @Setter
    @Getter
    private RequestSpecification requestSpecification;
    // for reporting purposes
    @Setter
    private Map<String, String> originalValues;
    // for reporting purposes
    @Setter
    private String payloadFileName;

    public RestRequest() {
    }

    public RestRequest( Method method, RequestSpecification requestSpecification ) {
        this.method = method;
        this.requestSpecification = requestSpecification;
        this.originalValues = new HashMap<>();
        originalValues.put( URI_KEY, ((RequestSpecificationImpl) getRequestSpecification()).getURI() );
        originalValues.put( "body", null );
        Object body = ((RequestSpecificationImpl) getRequestSpecification()).getBody();
        if ( body != null ) {
            originalValues.put( "body", body.toString() );
        }
    }

    public RestRequest( Method method, RequestSpecification requestSpecification, Map<String, String> originalValues ) {
        this.method = method;
        this.requestSpecification = requestSpecification;
        this.originalValues = originalValues;
    }

    @Override
    public String toString() {
        return toString( true );
    }

    public String toString( boolean useAlias ) {
        return toString( useAlias, Integer.MAX_VALUE );
    }

    public String toString( boolean useAlias, int charLimit ) {
        String uri = originalValues.get( URI_KEY );
        Object body = payloadFileName != null ? payloadFileName : originalValues.get( "body" );
        if ( useAlias ) {
            uri = RestUtils.getUrlAlias( uri );
        }
        return STR."\{getMethod()} request to URL \{uri} with\{
                body == null ? "out body" : STR." body:\n\{TextUtils.limitString( body.toString(), charLimit )}"}";
    }

}
