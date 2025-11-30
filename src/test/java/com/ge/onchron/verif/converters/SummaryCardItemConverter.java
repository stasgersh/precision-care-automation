/*
 * -GEHC CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.converters;

import com.ge.onchron.verif.model.Table;
import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import com.ge.onchron.verif.model.clinicalConfiguration.RowBadgeItem;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.model.summaryCard.SummaryCard;
import com.ge.onchron.verif.model.summaryCard.SummaryCardItem;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.*;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.ParameterConverters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.model.summaryCard.enums.SummaryCardItemType.*;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static org.junit.Assert.fail;
import static org.openqa.selenium.support.Color.fromString;

public class SummaryCardItemConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String DATA_TYPE_COLUMN = "data_type";
    private static final String DATA_COLUMN = "data";
    private static final String DATA_COLOR_COLUMN = "color";

    private static final String FILE_PATH_DATA_REGEX = "\\[source\\]:(.*)";
    private static final String KEY_VALUE_DATA_REGEX = "\\[key\\]:(.*)\\[value\\]:(.*)";
    private static final String LINK_DATA_REGEX = "\\[link\\]:(.*)\\[date\\]:(.*)";

    @Override
    public boolean accept( Type type ) {
        return type instanceof Class && SummaryCardItem.class.isAssignableFrom( (Class) type );
    }

    @Override
    SummaryCard convertSingleValue( String s ) {
        fail( "Currently, it is not applicable to convert a single value to a summary card item" );
        return null;
    }

    @Override
    SummaryCardItem convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( DATA_TYPE_COLUMN ) || !map.containsKey( DATA_COLUMN ) ) {
            fail( "Missing mandatory column in the SummaryCardItem table in the feature file: " + map );
        }
        SummaryCardItem summaryCardItem = new SummaryCardItem();

        String dataType = map.get( DATA_TYPE_COLUMN );
        String data = map.get( DATA_COLUMN ).replace( "{nl}", "\n" );
        String color = null;
        if(map.containsKey(DATA_COLOR_COLUMN)){
             color = map.get( DATA_COLOR_COLUMN ).replace( "{nl}", "\n" );
        }

        switch ( dataType ) {
            case "CARD_TITLE":
                summaryCardItem.setType( CARD_TITLE );
                summaryCardItem.setValue( new SummaryCardItemDataString( data ) );
                break;
            case "NORMAL_TEXT":
                summaryCardItem.setType( NORMAL_TEXT );
                summaryCardItem.setValue( new SummaryCardItemDataString( data ) );
                break;
            case "TABLE":
                summaryCardItem.setType( TABLE );
                ExamplesTable table = getTableFromFile( data );
                summaryCardItem.setValue( new SummaryCardItemDataTable( new Table( "", table.getRows() ) ) );
                break;
            case "KEY_VALUE":
                summaryCardItem.setType( KEY_VALUE );
                Matcher keyValueMatcher = parseData( KEY_VALUE_DATA_REGEX, data );
                if ( !keyValueMatcher.find() ) {
                    fail( "Key-value data format is not ok ([key]: (.*) [value]: (.*)): " + data );
                }
                SummaryCardItemDataKeyValuePair keyValuePair = new SummaryCardItemDataKeyValuePair();
                keyValuePair.setKey( keyValueMatcher.group( 1 ).trim() );
                keyValuePair.setValue( keyValueMatcher.group( 2 ).trim() );
                summaryCardItem.setValue( keyValuePair );
                break;
            case "LINK":
                summaryCardItem.setType( LINK );
                Matcher linkWithDateMatcher = parseData( LINK_DATA_REGEX, data );
                if ( !linkWithDateMatcher.find() ) {
                    fail( "Link data format is not ok ([link]: (.*) [date]: (.*)): " + data );
                }
                SummaryCardItemDataLink linkData = new SummaryCardItemDataLink();
                linkData.setLinkString( linkWithDateMatcher.group( 1 ).trim() );
                String dateValue = linkWithDateMatcher.group( 2 ).trim();
                if ( !dateValue.equals( "N/A" ) ) {
                    linkData.setDateString( dateValue );
                }
                summaryCardItem.setValue( linkData );
                break;
            case "CLP_DATA":
                summaryCardItem.setType( CLP_DATA );
                String clpText;
                Matcher matcher = parseData( FILE_PATH_DATA_REGEX, data );
                if ( matcher.find() ) {
                    String filePath = matcher.group( 1 ).trim();
                    clpText = readFromFile( TEST_DATA_CLASSPATH + filePath );
                } else {
                    clpText = data;
                }
                SummaryCardItemDataCLP clpData = new SummaryCardItemDataCLP();
                clpData.setClpContent( clpText );
                summaryCardItem.setValue( clpData );
                break;
            case "EMPTY_STATE":
                summaryCardItem.setType( EMPTY_STATE );
                summaryCardItem.setValue( new SummaryCardItemDataString( data ) );
                if (color!=null){
                    summaryCardItem.setColor( new SummaryCardItemDataColor(fromString(color)));
                }
                break;
            case "COMMENTS":
                summaryCardItem.setType( COMMENTS );
                ExamplesTable commentsTable = getTableFromFile( data );
                List<CommentItem> commentItemList = new ArrayList<>();
                CommentItemConverter commentItemConverter = new CommentItemConverter();
                commentsTable.getRows().forEach( comment -> {
                    CommentItem commentItem = commentItemConverter.convertMultiValues( comment );
                    commentItemList.add( commentItem );
                } );
                summaryCardItem.setValue( new SummaryCardItemComment( commentItemList ) );
                break;
            case "BUTTON":
                summaryCardItem.setType( BUTTON );
                summaryCardItem.setValue( new SummaryCardItemDataString( data ) );
                break;
            case "CLINICAL_TRIAL_ROW_BADGE_DATA":
                summaryCardItem.setType( CLINICAL_TRIAL_ROW_BADGE_DATA );
                List<String> rowData = Arrays.asList(data.split(","));
                summaryCardItem.setValue(SummaryCardItemDataTrial.builder()
                        .rowBadgeItem(RowBadgeItem.builder()
                                .trialOverallMatchingScore(OverallMatchingScore.fromString(rowData.get(0)))
                                .isAiIconBadgeDisplayed(getBooleanValue(rowData.get(1)))
                                .build())
                        .build());
                break;
            case "CLINICAL_TRIAL_DESCRIPTION":
                summaryCardItem.setType(CLINICAL_TRIAL_DESCRIPTION);
                summaryCardItem.setValue(SummaryCardItemDataTrial.builder().trialDescription(data).build());
                break;
            default:
                summaryCardItem.setType( UNKNOWN_ITEM );
                summaryCardItem.setValue( new SummaryCardItemDataString( data ) );
                break;
        }
        return summaryCardItem;
    }

    private Matcher parseData( String regex, String data ) {
        Pattern pattern = Pattern.compile( regex );
        return pattern.matcher( data );
    }

    private ExamplesTable getTableFromFile( String data ) {
        Matcher tableMatcher = parseData( FILE_PATH_DATA_REGEX, data );
        if ( !tableMatcher.find() ) {
            fail( "File path format is not ok ([source]: (.*)): " + data );
        }
        String tableFilePath = tableMatcher.group( 1 ).trim();
        String tableString = readFromFile( TEST_DATA_CLASSPATH + tableFilePath );
        return new ExamplesTable( tableString );
    }

    private Boolean getBooleanValue(String data) {
        return Boolean.parseBoolean(data.trim());
    }

}
