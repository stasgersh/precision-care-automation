/*
 * -GEHC CONFIDENTIAL-
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
package com.ge.onchron.verif.converters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.core.steps.ParameterConverters;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Label;
import com.ge.onchron.verif.model.treatmentAndResponse.StudyCard;

import static com.ge.onchron.verif.converters.ColorStringConverter.COLOR_HEX_MAP;

public class StudyCardConverter extends TableListConverter implements ParameterConverters.ParameterConverter {


    private static final String STUDY_BADGE_COLUMN = "study_badge";
    private static final String STUDY_DATE_COLUMN = "study_date";
    private static final String STUDY_LABEL_COLUMN = "study_label";
    private static final String STUDY_NAME_COLUMN = "study_name";
    private static final String CLP_TEXT_COLUMN = "clp_text";
    private static final String LINK_COLUMN = "link";

    private static final String CLP_LABEL_REGEX = "\\[CLP_LABEL\\]:\\s*([^,]+)";
    private static final String CLP_TEXT_REGEX = "\\[CLP_TEXT\\]:\\s*([^,]+)";
    private static final String LINK_REGEX = "\\[LINK\\]:\\s*([^,]+)";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && StudyCard.class.isAssignableFrom( (Class) type );
    }

    @Override
    Object convertSingleValue( String s ) {
        return null;
    }

    @Override
    Object convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( STUDY_BADGE_COLUMN ) || !map.containsKey( STUDY_DATE_COLUMN ) ) {
            fail( STR."Missing mandatory column on study card table in the feature file: \{map}" );
        }
        StudyCard studyCard = new StudyCard();

        if ( map.containsKey( STUDY_BADGE_COLUMN ) ) {
            studyCard.setBadge( map.get( STUDY_BADGE_COLUMN ) );
        }
        if ( map.containsKey( STUDY_LABEL_COLUMN ) && !map.get( STUDY_LABEL_COLUMN ).equals( "<N/A>" ) ) {
            studyCard.setLabelList( extractLabels( map.get( STUDY_LABEL_COLUMN ) ) );
        }
        if ( map.containsKey( STUDY_DATE_COLUMN ) ) {
            studyCard.setDate( map.get( STUDY_DATE_COLUMN ) );
        }
        if ( map.containsKey( STUDY_NAME_COLUMN ) ) {
            studyCard.setStudyName( map.get( STUDY_NAME_COLUMN ) );
        }
        if ( map.containsKey( CLP_TEXT_COLUMN ) && !map.get( CLP_TEXT_COLUMN ).equals( "<N/A>" ) ) {
            studyCard.setClpTextList( extractData( map.get( CLP_TEXT_COLUMN ), CLP_TEXT_REGEX ) );
        }
        if ( map.containsKey( LINK_COLUMN ) && !map.get( LINK_COLUMN ).equals( "<N/A>" ) ) {
            studyCard.setButtonTextList( extractData( map.get( LINK_COLUMN ), LINK_REGEX ) );
        }
        return studyCard;
    }


    private static List<Label> extractLabels( String input ) {
        List<Label> labelList = new ArrayList<>();
        Pattern pattern = Pattern.compile( StudyCardConverter.CLP_LABEL_REGEX );
        Matcher matcher = pattern.matcher( input );
        while ( matcher.find() ) {
            Label label = new Label( matcher.group( 1 ).trim() );
            label.setColor( COLOR_HEX_MAP.get( "light blue-gray" ) );
            labelList.add( label );
        }

        return labelList;
    }

    private static List<String> extractData( String input, String regex ) {
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( input );
        while ( matcher.find() ) {
            matches.add( matcher.group( 1 ).trim() );
        }
        return matches;
    }

}

