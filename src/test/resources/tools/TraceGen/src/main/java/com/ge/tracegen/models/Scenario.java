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

import java.util.List;
import java.util.Set;

public class Scenario {

    private String id;
    private String title;
    private boolean isAutomated;
    private Set<String> additionalAnnotations;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public boolean isAutomated() {
        return isAutomated;
    }

    public void setAutomated( boolean automated ) {
        isAutomated = automated;
    }

    public Set<String> getAdditionalAnnotations() {
        return additionalAnnotations;
    }

    public void setAdditionalAnnotations( Set<String> additionalAnnotations ) {
        this.additionalAnnotations = additionalAnnotations;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", isAutomated=" + isAutomated +
                ", additionalAnnotations=" + additionalAnnotations +
                '}';
    }

}
