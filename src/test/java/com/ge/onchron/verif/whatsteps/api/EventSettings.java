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
package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.detailedDataItems.DetailedDataItem;
import org.apache.http.HttpStatus;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.EventSettingsSteps;
import com.ge.onchron.verif.model.UserCredentials;
import net.thucydides.core.annotations.Steps;

import java.util.Map;
import java.util.stream.Collectors;

public class EventSettings {

    @Steps
    private EventSettingsSteps eventSettingsSteps;

    private TestDefinitionSteps testDefinitionSteps =
        TestDefinitionSteps.getInstance();

    @Given("[API] the following {event|events} $isMarked marked as important for [$userCredentials] user: $examplesTable")
    public void markOrUnmarkEventImportantForUser(Boolean isMarked, UserCredentials userCredentials, ExamplesTable events) {
        String eventsVal = events.getRows().stream().map(m -> String.join(", ", m.values())).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR.
            "Check following events: \{eventsVal}");
        eventSettingsSteps.markOrUnmarkEventImportantForUser(isMarked, userCredentials, events.getRows());

        final String alias = userCredentials.getAlias();
        for (Map<String, String> event: events.getRows()) {
            for (String key: event.keySet()) {
                testDefinitionSteps.logEvidence(STR."the following event \{event.get(key)} marked as important for \{ alias } user",
                    true, true, false);
            }
        }
        testDefinitionSteps.logEvidence(STR."The events \{isMarked ? "" : "not"} marked as important for \{ alias } user",
            STR."The events are \{isMarked ? "" : "not"} as important for user \{ alias }", true);
    }

    @Given("[API] the [$userCredentials] user has no labels for the following {event|events}: $examplesTable")
    public void clearLabelsForEvents(UserCredentials userCredentials, ExamplesTable events) {
        String eventsVal = events.getRows().stream().map(m -> String.join(", ", m.values())).collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR.
            "Check labels for user on following events for user \{userCredentials.getAlias()}\n\{events.getRows()}");
        eventSettingsSteps.clearLabelsForMoreEvents(userCredentials, events.getRows());

        for (Map<String, String> event: events.getRows()) {
            for (String key : event.keySet()) {
                testDefinitionSteps.logEvidence(STR."the following event \{event.get(key)} has no labels for \{userCredentials.getAlias()} user",
                    true, true, false);
            }
        }
        testDefinitionSteps.logEvidence(STR."the \{userCredentials.getAlias()} user has no labels for the following events",
            STR."request is sent without errors", true);
    }

    @Given("[API] default labels are added to the following event by the [$userCredentials] user: $examplesTable")
    @Alias("[API] labels are added to the following events by the [$userCredentials] user: $examplesTable")
    public void addLabelsToEvents(UserCredentials userCredentials, ExamplesTable eventWithLabels) {
        String eventsLabel = eventWithLabels.getRows().stream().map(Object::toString)
            .collect(Collectors.joining("\n"));
        testDefinitionSteps.addStep(STR.
            "As user \{userCredentials.getAlias()} add labels for the following events: \{eventsLabel}");
        eventSettingsSteps.addLabelsToEvents(userCredentials, eventWithLabels.getRows());
        testDefinitionSteps.logEvidence(STR."As user \{userCredentials.getAlias()} add labels for the following events: \{eventsLabel}",
            STR."request is sent without errors", true);
    }

}
