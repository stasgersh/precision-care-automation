/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.parsers.gherkin.GherkinStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import com.ge.onchron.verif.converters.OnchronParameterConverters;
import com.ge.onchron.verif.utils.ReflectionUtils;
import net.serenitybdd.jbehave.SerenityJBehave;
import net.serenitybdd.jbehave.SerenityStories;
import net.serenitybdd.jbehave.annotations.Metafilter;

import static com.ge.onchron.verif.SystemTestConstants.TEST_RESULT_DIRECTORY;
import static com.ge.onchron.verif.TestSystemParameters.UI_BASE_URL_PROPERTY;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;

@Metafilter( "-skip +stas" )  // default metafilter can be overridden by using env var, e.g.: Dmetafilter="+testing"
public class OncoCareTestSuite extends SerenityStories {

    private static final String PARAMETER_CONVERTER_PACKAGE = "com.ge.onchron.verif.converters";

    public OncoCareTestSuite() {
        setWebdriverBaseUrl();
        Locale.setDefault( Locale.US );
        this.useFormats( HTML, XML, CONSOLE, TXT );
        this.findStoriesCalled( getSystemParameter( TestSystemParameters.INCLUDED_FEATURES ) );
    }

    @Override
    public Configuration configuration() {
        List<Format> formats = Arrays.asList( HTML );
        net.thucydides.core.webdriver.DriverConfiguration thucydidesConfiguration = getSystemConfiguration();
        Configuration configuration;
        configuration = SerenityJBehave.defaultConfiguration( thucydidesConfiguration, formats, this );
        configuration.useStoryParser( new GherkinStoryParser() );
        configuration.useParameterControls( new ParameterControls().useDelimiterNamedParameters( true ) );

        configuration.useParameterConverters( new OnchronParameterConverters().addConverters( collectParameterConverters() ) );
        if ( getSystemParameter( TestSystemParameters.SAVE_RESULT ).equals( "Yes" ) ||
                getSystemParameter( TestSystemParameters.INCLUDED_FEATURES ).contains( "**/stories/**/*.feature" ) ) {
            configuration.storyReporterBuilder().withRelativeDirectory( "/" + TEST_RESULT_DIRECTORY );
        }
        return configuration;
    }

    List<ParameterConverters.ParameterConverter> collectParameterConverters() {
        List<Class<? extends ParameterConverters.ParameterConverter>> converterClasses = ReflectionUtils.getSubTypes( PARAMETER_CONVERTER_PACKAGE, ParameterConverters.ParameterConverter.class );
        converterClasses.add( ParameterConverters.FluentEnumConverter.class );
        return ReflectionUtils.createInstanceFromClassesWithDefaultConstructor( converterClasses );
    }

    private void setWebdriverBaseUrl() {
        String url = getSystemParameter( UI_BASE_URL_PROPERTY );    // get the url and replace the parameters coming from other env vars (e.g. ${ehl.box.address})
        System.setProperty( UI_BASE_URL_PROPERTY, url );
    }

}
