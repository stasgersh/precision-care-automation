/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.converters.converterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.enums.BadgeType;

import static com.ge.onchron.verif.utils.ReplaceUtils.replaceStoredPlaceholderText;

public class BadgeConverterUtils {

    public static List<Badge> getBadgesFromTableCell( String badgeListString ) {
        List<String> badgeListRawData = Arrays.asList( badgeListString.split( "," ) );
        List<Badge> eventBadges = new ArrayList<>();
        badgeListRawData.forEach( badgeData -> {
            Pattern pattern = Pattern.compile( "\\[(.*)]: (.*)" );
            Matcher matcher = pattern.matcher( badgeData );
            Badge badge = new Badge();
            while ( matcher.find() ) {
                badge.setBadgeType( BadgeType.valueOf( matcher.group( 1 ) ) );
                String expectedLabelText = matcher.group( 2 );
                expectedLabelText = !expectedLabelText.equals( "<NO_TEXT>" ) ? replaceStoredPlaceholderText( matcher.group( 2 ) ) : "";
                badge.setText( expectedLabelText );
                eventBadges.add( badge );
            }
        } );
        return eventBadges;
    }

}
