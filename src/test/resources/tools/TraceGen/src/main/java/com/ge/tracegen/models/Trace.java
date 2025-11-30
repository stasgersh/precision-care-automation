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
package com.ge.tracegen.models;

import java.util.ArrayList;
import java.util.List;

public class Trace {

    private String reqId;
    private String description;
    private boolean safety;
    private boolean hasLink = false;
    private List<Scenario> scenarios = new ArrayList<>();

    public Trace() {
    }

    public Trace( Trace initReq ) {
        this.reqId = initReq.getReqId();
        this.description = initReq.getDescription();
        this.safety = initReq.isSafety();
        this.hasLink = initReq.hasLink();
        this.scenarios.addAll( initReq.getScenarios() );
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId( String reqId ) {
        this.reqId = reqId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public boolean isSafety() {
        return safety;
    }

    public void setSafety( boolean safety ) {
        this.safety = safety;
    }

    public boolean hasLink() {
        return hasLink;
    }

    public void setHasLink( boolean hasLink ) {
        this.hasLink = hasLink;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void addScenarios( List<Scenario> scenarios ) {
        this.scenarios.addAll( scenarios );
    }

    public void addScenario( Scenario scenario ) {
        scenarios.add( scenario );
    }

    @Override
    public String toString() {
        return "Trace{" +
                "reqId='" + reqId + '\'' +
                ", description='" + description + '\'' +
                ", safety=" + safety +
                ", hasLink=" + hasLink +
                ", scenarios=" + scenarios +
                '}';
    }

}
