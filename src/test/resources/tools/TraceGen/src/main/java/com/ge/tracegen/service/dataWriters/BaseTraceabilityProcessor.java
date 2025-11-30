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
package com.ge.tracegen.service.dataWriters;

import java.util.ArrayList;
import java.util.List;

import com.ge.tracegen.models.Scenario;
import com.ge.tracegen.models.Trace;

public class BaseTraceabilityProcessor {

    List<Trace> traceability;

    public BaseTraceabilityProcessor( List<Trace> traceability ) {
        this.traceability = traceability;
    }

    public List<Scenario> getScenariosFromTraceabilityList() {
        List<Scenario> observedScenarios = new ArrayList<>();
        traceability.forEach( observedTrace -> {
            observedScenarios.addAll( observedTrace.getScenarios() );
        } );
        return observedScenarios;
    }

}
