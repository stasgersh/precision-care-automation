package com.ge.onchron.verif.howsteps.trials;

import com.ge.onchron.verif.pages.tabs.Trials;
import net.serenitybdd.core.PendingStepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommonTrialsViewSteps {

    private Trials trialsTab;
    private static final Logger LOGGER = LoggerFactory.getLogger( CommonTrialsViewSteps.class );

    public void checkTrialsViewVisibility( boolean isDisplayed ) {
        boolean trialTabVisible = trialsTab.isVisible();
        LOGGER.info(STR."Trials view visibility is: \{trialTabVisible }");
        assertThat( "Visibility of the Trials view is not ok.", trialTabVisible, is( equalTo( isDisplayed ) ) );
    }

    public static void performActionNotYetImplemented() {
        throw new PendingStepException("This step is not yet implemented.");
    }

}
