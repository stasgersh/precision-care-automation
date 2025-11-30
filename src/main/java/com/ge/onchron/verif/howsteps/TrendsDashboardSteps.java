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
package com.ge.onchron.verif.howsteps;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.pages.TrendsDashboard;

public class TrendsDashboardSteps {

    private TrendsDashboard trendsDashboard;
    private static final Logger LOGGER = LoggerFactory.getLogger( TrendsDashboardSteps.class );

    public void checkTrendsDashboardVisibility( boolean visible ) {
        final boolean visibilityStatus = trendsDashboard.isVisible();
        LOGGER.info( STR."Observed trends dashboard visibility: \{visibilityStatus ? "" : "not"} visible" );
        assertThat( "The Trends Dashboard visibility is not ok.", visibilityStatus, is( equalTo( visible ) ) );
    }

    public void clickOnTimelineButton() {
        trendsDashboard.clickOnTimelineButton();
    }

    public void checkTrendsGraphOrder( List<String> expectedGraphOrder ) {
        List<String> actualGraphOrder = trendsDashboard.getTrendGraphOrder();
        LOGGER.info( STR."Observed trend graph list order" + actualGraphOrder );
        assertEquals( "The Trends graph's order is not expected.", expectedGraphOrder, actualGraphOrder );
    }
}
