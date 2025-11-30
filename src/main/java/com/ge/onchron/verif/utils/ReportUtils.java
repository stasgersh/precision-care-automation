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
package com.ge.onchron.verif.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.path.json.JsonPath;
import net.serenitybdd.core.Serenity;

import static com.ge.onchron.verif.TestSystemParameters.UI_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;

public class ReportUtils {

    private static final String UI_URL_PROPERTY = "uiUrl";
    private static final String APP_VERSION_PROPERTY = "applicationVersion";
    private static final String UI_VERSION_PROPERTY = "uiVersion";
    private static final String CORE_VERSION_PROPERTY = "coreVersion";
    private static final String ADDITIONAL_PROPERTY = "additional";

    private static final String UNKNOWN_VALUE = "Unknown";

    public static void setCustomFieldsForSerenityReport( String envConfig ) {
        JsonPath json = JsonPath.from( envConfig );
        Map<String, String> customReportParams = new HashMap<>();

        String uiUrl = getSystemParameter( UI_BASE_URL_PROPERTY );   // e.g. "https://3.249.67.132/onco/onchron-main"
        customReportParams.put( UI_URL_PROPERTY, uiUrl );

        String appVersion = json.get( APP_VERSION_PROPERTY );
        customReportParams.put( APP_VERSION_PROPERTY, appVersion );

        List<Map<String, String>> additionalParamsList = json.getList( ADDITIONAL_PROPERTY );
        if ( additionalParamsList != null ) {
            Map<String, String> additionalParams = new HashMap<>();
            additionalParamsList.forEach( additionalParam -> additionalParams.put( additionalParam.get( "key" ), additionalParam.get( "value" ) ) );

            String uiVersion = additionalParams.getOrDefault( UI_VERSION_PROPERTY, UNKNOWN_VALUE );     // e.g. "docker-release-eis/oncology-digital-budapest/onchron-ui:1.0.213"
            if ( uiVersion.contains( ":" ) ) {
                customReportParams.put( UI_VERSION_PROPERTY, uiVersion.split( ":" )[1] );
            } else {
                customReportParams.put( UI_VERSION_PROPERTY, uiVersion );
            }
            String coreVersion = additionalParams.getOrDefault( CORE_VERSION_PROPERTY, UNKNOWN_VALUE );
            if ( uiVersion.contains( ":" ) ) {
                customReportParams.put( CORE_VERSION_PROPERTY, coreVersion.split( ":" )[1] );
            } else {
                customReportParams.put( CORE_VERSION_PROPERTY, coreVersion );
            }
        } else {
            customReportParams.put( UI_VERSION_PROPERTY, UNKNOWN_VALUE );
            customReportParams.put( CORE_VERSION_PROPERTY, UNKNOWN_VALUE );
        }

        String userDir = Serenity.environmentVariables().getProperty( "user.dir" );
        Path serenityPropPath = Paths.get( userDir + File.separator + "serenity.properties" );
        String props = FileUtils.readFromFileByAbsolutePath( serenityPropPath.toString() );

        StringBuilder propsWihReportParams = new StringBuilder();
        propsWihReportParams.append( props );

        for ( Map.Entry<String, String> entry : customReportParams.entrySet() ) {
            propsWihReportParams.append( System.lineSeparator() );
            propsWihReportParams.append( "report.customfields." ).append( entry.getKey() ).append( "=" ).append( entry.getValue() );
        }

        propsWihReportParams
                .append( System.lineSeparator() )
                .append( "report.customfields.order=" + UI_URL_PROPERTY + "," + APP_VERSION_PROPERTY + "," + UI_VERSION_PROPERTY + "," + CORE_VERSION_PROPERTY );

        Path serenityReportPropPath = Paths.get( userDir + File.separator + "serenity-report.properties" );
        FileUtils.saveFile( serenityReportPropPath, propsWihReportParams.toString() );
    }

}
