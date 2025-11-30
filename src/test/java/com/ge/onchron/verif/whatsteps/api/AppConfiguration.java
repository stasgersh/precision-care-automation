package com.ge.onchron.verif.whatsteps.api;

import org.jbehave.core.annotations.Given;

import com.ge.onchron.verif.howsteps.AppConfigurationSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.UserCredentials;
import net.thucydides.core.annotations.Steps;

public class AppConfiguration {
    @Steps
    private AppConfigurationSteps appConfigSteps;
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "the following configuration uploaded into the App: $configFilePath" )
    public void uploadConfig(  String configFilePath ) {
        testDefinitionSteps.addStep( STR."Upload following config to the App: \{configFilePath}" );
        appConfigSteps.uploadConfig( configFilePath );
        testDefinitionSteps.logEvidence( "Config uploaded successfully", "Config uploaded successfully", true );
    }

}
