package com.ge.onchron.verif.howsteps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ge.onchron.verif.model.StringList;
import com.ge.onchron.verif.pages.sections.modal.OncologyFocus;
import net.thucydides.core.annotations.Steps;

public class OncologyFocusSteps {
    @Steps
    private OncologyFocus oncologyFocus;
    @Steps
    private OncologyFocus oncoModal;
    private static final Logger LOGGER = LoggerFactory.getLogger( AlertModalSteps.class );
    public void turnOnOrOffOncoToggle()
    {
        oncologyFocus.clickOnOncoFocusTab();
    }
    public void checkOncoPopUpTitle( String expectedTitle ) {
        final String observedOncoPopUpTitle = oncoModal.getHeaderText();
        LOGGER.info( STR."Observed Oncology focus popup title was: \{observedOncoPopUpTitle }" );
        assertEquals( "Oncology focus popup title is not ok.", expectedTitle, observedOncoPopUpTitle );
    }

    public void checkOncoPopUpVisibility( boolean isVisible ) {
        final boolean observedOncoPopUpVisibility = oncoModal.isVisible();
        LOGGER.info( STR."Observed Oncology focus popup visibility was: \{observedOncoPopUpVisibility}" );
        assertThat( "The Oncology focus popup visibility is not ok.", observedOncoPopUpVisibility, is( equalTo( isVisible ) ) );
    }

    public void checkOncologyFocusPopupOptions(StringList buttons)
    {
        StringList btnList = new StringList(oncoModal.getOncoFocusBtnsText());
        LOGGER.info( STR."Observed Oncology focus popup buttons: \{btnList}");
        for (String btn : btnList.getList())
            assertTrue(buttons.getList().contains(btn));
    }

    public void checkOncologyFocusPopupContent(StringList content)
    {
        StringList contentList = new StringList(oncoModal.getOncoFocusContentText());
        LOGGER.info( STR."Observed Oncology focus popup content: \{contentList}");
        for (String cnt : contentList.getList())
            assertTrue(content.getList().contains(cnt));
    }
    public void clickOnBtnInPopup(String button) {
        oncologyFocus.clickOnoncoFocusPopupBtns(button);
    }
    public void oncoFocusBtnState(Boolean state) {
        assertEquals(state, oncologyFocus.oncoFocusTabState());
    }
    public void oncoFocusTooltipTxt(String tooltipTxt){
        assertEquals(tooltipTxt, oncologyFocus.getOncoFocusTooltipDis());
    }
    public void moveToToolTip() {
        oncologyFocus.hoverToOncoFocusTooltip();
    }
}
