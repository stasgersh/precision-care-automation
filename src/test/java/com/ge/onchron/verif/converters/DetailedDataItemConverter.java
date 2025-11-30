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
package com.ge.onchron.verif.converters;

import java.awt.image.BufferedImage;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.jbehave.core.steps.ParameterConverters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.Badge;
import com.ge.onchron.verif.model.detailedDataItems.BadgeIndicatorItem;
import com.ge.onchron.verif.model.detailedDataItems.CLPItem;
import com.ge.onchron.verif.model.detailedDataItems.CommentInputBoxItem;
import com.ge.onchron.verif.model.detailedDataItems.CommentItem;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import com.ge.onchron.verif.model.detailedDataItems.EmptyInfoItem;
import com.ge.onchron.verif.model.detailedDataItems.LinkItem;
import com.ge.onchron.verif.model.detailedDataItems.SectionTitle;
import com.ge.onchron.verif.model.detailedDataItems.SimpleItem;
import com.ge.onchron.verif.model.detailedDataItems.TableItem;
import com.ge.onchron.verif.model.detailedDataItems.TrendItem;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.converters.ColorStringConverter.COLOR_HEX_MAP;
import static com.ge.onchron.verif.converters.converterUtils.BadgeConverterUtils.getBadgesFromTableCell;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;
import static com.ge.onchron.verif.utils.ImageUtils.readFileToBufferedImage;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceCustomParameters;
import static com.ge.onchron.verif.utils.Utils.insertSystemParameters;

public class DetailedDataItemConverter extends TableListConverter implements ParameterConverters.ParameterConverter {

    private static final String DATA_TYPE_COLUMN = "data_type";
    private static final String TITLE_COLUMN = "title";
    private static final String VALUE_COLUMN = "value";
    private static final String VALUE_FROM_FILE_COLUMN = "value_from_file";
    private static final String NA_IN_CELL = "<N/A>";

    @Override
    public boolean accept( Type type ) {
        return type.getTypeName().equals( DetailedDataItem.class.getTypeName() ) && DetailedDataItem.class.isAssignableFrom( (Class) type );
    }

    @Override
    DetailedDataItem convertSingleValue( String s ) {
        return (DetailedDataItem) convertTableWithOneItem( s );
    }

    @Override
    DetailedDataItem convertMultiValues( Map<String, String> map ) {
        if ( !map.containsKey( DATA_TYPE_COLUMN ) || !map.containsKey( TITLE_COLUMN ) ) {
            fail( "Missing mandatory column in detailed data table in the feature file: " + map );
        }
        DetailedDataItem dataItem = null;
        String dataType = map.get( DATA_TYPE_COLUMN );
        switch ( dataType ) {
            case "SECTION_TITLE":
                dataItem = new SectionTitle( map.get( TITLE_COLUMN ) );
                break;
            case "TABLE":
                dataItem = new TableItem( map.get( TITLE_COLUMN ) );
                break;
            case "LINK":
                dataItem = new LinkItem( map.get( TITLE_COLUMN ) );
                break;
            case "KEY-VALUE":
                String value = prepareValue( "KEY-VALUE", map );
                dataItem = new SimpleItem( map.get( TITLE_COLUMN ), value );
                break;
            case "CLP_INFO":
                String clpText = prepareValue( "CLP_INFO", map );
                String nlpTitle = map.get( TITLE_COLUMN ).equals( NA_IN_CELL ) ? "" : map.get( TITLE_COLUMN );
                dataItem = new CLPItem( nlpTitle, clpText, COLOR_HEX_MAP.get( "ai-blue" ) );
                break;
            case "TREND_GRAPH":
                if ( !map.containsKey( VALUE_COLUMN ) ) {
                    fail( "The 'value' column is required for TREND_GRAPH data item" );
                }
                String refImgPath = map.get( VALUE_COLUMN );
                refImgPath = insertSystemParameters( refImgPath );
                BufferedImage refBufferedImage = readFileToBufferedImage( TEST_DATA_CLASSPATH + refImgPath );
                dataItem = new TrendItem( map.get( TITLE_COLUMN ), refBufferedImage );
                break;
            case "COMMENT_INPUT":
                String inputBoxText = prepareValue( "COMMENT_INPUT", map );
                dataItem = new CommentInputBoxItem( inputBoxText );
                break;
            case "COMMENT":
                String comment = prepareValue( "COMMENT", map );
                dataItem = new CommentItem( comment );
                break;
            case "BADGE":
                String badgesData = prepareValue( "BADGE", map );
                List<Badge> eventBadges = getBadgesFromTableCell( badgesData );
                assertEquals( "Only one badge is allowed in this cell at this step.", 1, eventBadges.size() );
                dataItem = new BadgeIndicatorItem( eventBadges.get( 0 ) );
                break;
            case "EMPTY_STATE":
                String emptyStateInfo = prepareValue( "EMPTY_STATE", map );
                String color = ColorStringConverter.convertString( map.get( "color" ) ).getHex();
                dataItem = new EmptyInfoItem( emptyStateInfo, color );
                break;
            case "NOT_FOUND":
                String notFoundValue = prepareValue( "NOT_FOUND", map );
                SimpleItem simpleItem = new SimpleItem( map.get( TITLE_COLUMN ), notFoundValue );
                if ( map.containsKey( "color" ) ) {
                    String colorValue = ColorStringConverter.convertString( map.get( "color" ) ).getHex();
                    simpleItem.setColor( colorValue );
                }
                dataItem = simpleItem;
                break;
            default:
                fail( "Not known data item in the feature file: " + dataType );
                break;
        }
        return dataItem;
    }

    private String prepareValue( String dataType, Map<String, String> map ) {
        if ( !map.containsKey( VALUE_COLUMN ) && !map.containsKey( VALUE_FROM_FILE_COLUMN ) ) {
            fail( "The 'value' or 'value_from_file' column is required for " + dataType + " data item" );
        }
        String value = null;
        if ( map.containsKey( VALUE_COLUMN ) && !map.get( VALUE_COLUMN ).equals( NA_IN_CELL ) ) {
            value = map.get( VALUE_COLUMN );
        } else if ( map.containsKey( VALUE_FROM_FILE_COLUMN ) && !map.get( VALUE_FROM_FILE_COLUMN ).equals( NA_IN_CELL ) ) {
            String filePath = map.get( VALUE_FROM_FILE_COLUMN );
            value = readFromFile( TEST_DATA_CLASSPATH + filePath );
        }
        assertNotNull( "Value was not set for data item in the feature file: " + "type: " + dataType + ", map: " + map, value );
        return replaceCustomParameters( value );
    }

}
