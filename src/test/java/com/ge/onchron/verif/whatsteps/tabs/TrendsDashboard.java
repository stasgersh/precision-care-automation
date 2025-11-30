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
package com.ge.onchron.verif.whatsteps.tabs;

import java.util.List;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.TrendsDashboardSteps;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.pages.utils.TableUtils.getExampleTableAsList;

public class TrendsDashboard {

    @Steps
    private TrendsDashboardSteps trendsDashboardSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Then( "the Trends dashboard $visible displayed" )
    public void checkTrendsDashboardVisibility( Boolean visible ) {
        testDefinitionSteps.addStep( "Check Trends Dashboard visibility" );
        trendsDashboardSteps.checkTrendsDashboardVisibility( visible );
        testDefinitionSteps.logEvidence( STR."The Trends dashboard is \{visible ? "" : "not"} displayed",
                STR."The Trends dashboard is \{visible ? "" : "not"} displayed (see previous logs)", true );
    }

    @Then( "the trend graphs are displayed in the following order: $trendOrderList" )
    public void checkTrendGraphWithOrders( ExamplesTable trendOrderList ) {
        testDefinitionSteps.addStep( "Check Trends graph list order" );
        List<String> trendList = getExampleTableAsList( trendOrderList );
        trendsDashboardSteps.checkTrendsGraphOrder( trendList );
        testDefinitionSteps.logEvidence( STR."The Trends graphs are displayed in the expected order",
                STR."The Trends graphs are displayed in the expected order", true );
    }

}
