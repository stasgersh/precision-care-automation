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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.enums.BadgeType;
import com.ge.onchron.verif.utils.TextUtils;
import java.util.Map;
import lombok.Getter;

import static com.ge.onchron.verif.TestSystemParameters.LABEL_COLORS_FROM_LABEL_PANEL;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredPlaceholderText;
import static net.serenitybdd.core.Serenity.hasASessionVariableCalled;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;

public class Badge {
    public static final String SAVED_BADGE_COLOR = "<PREVIOUSLY_SAVED_BADGE_COLOR>";
    @Getter
    private BadgeType badgeType;
    private String text;
    private String color;

    public Badge() {
    }

    public void setBadgeType( BadgeType badgeType ) {
        this.badgeType = badgeType;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public String getText() {
        return replaceStoredPlaceholderText( this.text );
    }

    public String getColor() {
        if ( color == null || !color.equals( SAVED_BADGE_COLOR ) ) {
            return this.color;
        }
        if ( !hasASessionVariableCalled( LABEL_COLORS_FROM_LABEL_PANEL ) ) {
            fail( "Label colors were not saved previously, so this label color cannot be set." );
        }
        Map<String, String> storedLabelColorsFromLabelPanel = sessionVariableCalled( LABEL_COLORS_FROM_LABEL_PANEL );
        String expectedColor = storedLabelColorsFromLabelPanel.get( this.getText() );
        assertNotNull( STR."Color vas not saved previously for label: \{this.getText()}", expectedColor );
        return expectedColor;
    }

    public void setColor( String color ) {
        this.color = color;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof Badge) ) return false;
        Badge badge = (Badge) o;
        return getBadgeType() == badge.getBadgeType() &&
                TextUtils.textCompareWithKeywords( getText(), badge.getText() );
    }

    @Override
    public String toString() {
        return STR."Badge{badgeType=\{badgeType}, text='\{text}', color='\{color}'}";
    }

}
