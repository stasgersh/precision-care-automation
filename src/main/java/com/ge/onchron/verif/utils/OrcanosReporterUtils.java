/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.node.ArrayNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.node.ObjectNode;

public class OrcanosReporterUtils {

    public static String prepareDescription( String scenarioName, Set<String> metaData ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        String traceability = metaData.stream().filter( k -> k.startsWith( "Traceability" ) ).findFirst().orElse( null );
        if ( traceability != null ) {
            ArrayNode traceNode = mapper.createArrayNode();
            Arrays.asList( traceability.split( ":" )[1].split( "," ) ).forEach( traceNode::add );
            metaData.remove( traceability );
            rootNode.set( "Traceability", traceNode );
        }

        String path = metaData.stream().filter( k -> k.startsWith( "Path" ) ).findFirst().orElse( null );
        if ( path != null ) {
            rootNode.put( "Path", path.replace( "_", " " ).split( ":" )[1] );
            metaData.remove( path );
        }

        String rawDesc = metaData.stream()
                                 .filter( k -> k.startsWith( "Description" ) )
                                 .findFirst()
                                 .orElse( STR."@Description:\{scenarioName.replace( ",", "@comma@" )}" );
        metaData.remove( rawDesc );
        List<String> descList = Arrays.stream( rawDesc.split( ":", 2 )[1].split( "," ) ).collect( Collectors.toList() );
        while ( descList.size() < 3 ) {
            descList.addFirst( "" );
        }
        String preReq = descList.getFirst().isBlank() ? "" : STR."<strong>Pre-Requisite:</strong><br/>\{descList.getFirst()}<br/>";
        String desc = descList.get( 1 ).isBlank() ? "" : STR."<strong>Verification Description:</strong><br/>\{descList.get( 1 )}<br/>";
        String verify = STR."<strong>Verify That:</strong><br/>\{descList.get( 2 )}";
        String description = (preReq + desc + verify).replace( "@comma@", "," );
        rootNode.put( "Description", description.replace( "_", " " ) );

        ObjectNode additionalParams = mapper.createObjectNode();
        for ( String key : metaData ) {
            if ( !key.contains( ":" ) ) continue;
            String[] keyVal = key.replace( "_", " " ).split( ":" );
            additionalParams.put( keyVal[0], keyVal[1] );
        }
        boolean isManual = metaData.contains( "manual" );
        additionalParams.put( "Test Execution Type", isManual ? "Manual" : "Automated" );
        String autoStatus = isManual ? "Manual" : metaData.contains( "NotImplemented" ) ? "No" : "Yes";
        additionalParams.put( "Automated", autoStatus);

        rootNode.set( "AdditionalParams", additionalParams );
        return mapper.writeValueAsString( rootNode );
    }

}
