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
package com.ge.tracegen.service;

import java.util.List;

import com.ge.tracegen.models.Trace;
import com.ge.tracegen.service.dataWriters.MetricsWriter;

public class MetricsGenerator {

    List<Trace> traceability;

    public MetricsGenerator( List<Trace> traceability ) {
        this.traceability = traceability;
    }

    public void writeMetricsToJson() {
        new MetricsWriter( traceability ).writeMetricsToJson();
    }

}
