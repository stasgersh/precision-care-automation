/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.pages.utils;

import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.utils.Utils.waitMillis;
import static net.serenitybdd.core.Serenity.getDriver;

public class DragAndDropUtils {

    public static void dragAndDropToElement( WebElementFacade element, WebElementFacade moveToThisElement ) {
        Actions actions = new Actions( getDriver() );

        actions.clickAndHold( element )
               .pause( Duration.ofMillis( 500 ) )
               .moveByOffset( 0, 10 )       // these 2 moveByOffsets are workaround for 'moveToElement' to work properly
               .moveByOffset( 0, -10 )      // without these offsets, moving is performed in wrong way (up <-> down)
               .moveToElement( moveToThisElement )
               .release()
               .build().perform();

        waitMillis( 500 );  // wait for finishing drag and drop animation
    }

}
