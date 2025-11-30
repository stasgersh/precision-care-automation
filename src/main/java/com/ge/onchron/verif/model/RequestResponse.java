/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model;

import io.restassured.response.Response;

public class RequestResponse {

    private RestRequest restRequest;
    private Response response;

    public RequestResponse( RestRequest restRequest, Response response ) {
        this.restRequest = restRequest;
        this.response = response;
    }

    public RestRequest getRestRequest() {
        return restRequest;
    }

    public Response getResponse() {
        return response;
    }

}
