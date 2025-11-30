package com.ge.onchron.verif.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.onchron.verif.model.clinicalConfiguration.treatment.ClinicalTreatmentEligibilityConfig;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaConfig;
import com.ge.onchron.verif.model.clinicalConfiguration.PatientFilterConfig;
import com.ge.onchron.verif.model.criteriaCalculator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ge.onchron.verif.model.clinicalConfiguration.trial.ClinicalTrialItemMetadata.RecruitmentStatus.RECRUITING;

public class TrialConfigManagerUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrialConfigManagerUtils.class);

    private static final String TRIAL_ID = "trialDefaultId123456";
    private static final String TRIAL = "trial";
    private static final String DEFAULT_STUDY_COMPLETION = "Nov 17,2024";
    private static final String DEFAULT_NAME = "This is a default trial name";
    private static final String DEFAULT_DESCRIPTION = "This is a default trial description";
    private static final String DEFAULT_PHASE = "1";
    private static final String DEFAULT_LINK = "https://clinicaltrials.gov/study/NCT04428710?a=23";

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String serializeConfigWithCustomParams(
            final ClinicalTreatmentEligibilityConfig clinicalTreatmentEligibilityConfig) {

        try {
            return new ObjectMapper().writeValueAsString(
                    ClinicalTreatmentEligibilityConfig.builder()
                            .id(clinicalTreatmentEligibilityConfig.getId())
                            .type(clinicalTreatmentEligibilityConfig.getType())
                            .status(clinicalTreatmentEligibilityConfig.getStatus())
                            .studyCompletion(clinicalTreatmentEligibilityConfig.getStudyCompletion())
                            .name(clinicalTreatmentEligibilityConfig.getName())
                            .description(clinicalTreatmentEligibilityConfig.getDescription())
                            .phase(clinicalTreatmentEligibilityConfig.getPhase())
                            .link(clinicalTreatmentEligibilityConfig.getLink())
                            .patientFilter(getDefaultPatientsFilter())
                            .variables(getDefaultVariables())
                            .criteria(getDefaultCriteriaGender())
                            .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String serializeConfigWithCustomParams(
            final ClinicalTreatmentEligibilityConfig clinicalTreatmentEligibilityConfig,
            final ArrayList<Variable> variables,
            final ArrayList<CriteriaConfig> criteriaConfigs) {

        try {
            return new ObjectMapper().writeValueAsString(
                    ClinicalTreatmentEligibilityConfig.builder()
                            .id(clinicalTreatmentEligibilityConfig.getId())
                            .type(clinicalTreatmentEligibilityConfig.getType())
                            .status(clinicalTreatmentEligibilityConfig.getStatus())
                            .studyCompletion(clinicalTreatmentEligibilityConfig.getStudyCompletion())
                            .name(clinicalTreatmentEligibilityConfig.getName())
                            .description(clinicalTreatmentEligibilityConfig.getDescription())
                            .phase(clinicalTreatmentEligibilityConfig.getPhase())
                            .link(clinicalTreatmentEligibilityConfig.getLink())
                            .variables(variables)
                            .criteria(criteriaConfigs)
                            .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String serializeDefaultConfig() {
        try {
            return new ObjectMapper().writeValueAsString(
                    ClinicalTreatmentEligibilityConfig.builder()
                            .id(UUID.randomUUID().toString())
                            .type(TRIAL)
                            .status(RECRUITING.getStatus())
                            .studyCompletion(DateUtil.getCurrentDateTime().minusYears(1).toString())
                            .name(DEFAULT_NAME)
                            .description(DEFAULT_DESCRIPTION)
                            .phase(DEFAULT_PHASE)
                            .link(DEFAULT_LINK)
                            .variables(getDefaultVariables())
                            .criteria(getDefaultCriteriaGender())
                            .build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ClinicalTreatmentEligibilityConfig buildConfigWithOmittedCustomParams(List<String> paramsToOmit, String configType, Optional<Boolean> configId) {

        return getClinicalTreatmentEligibilityConfig(paramsToOmit, configType, configId);
    }

    public static ClinicalTreatmentEligibilityConfig buildConfigWithOmittedCustomParams(List<String> paramsToOmit,  Optional<Boolean> configId) {

        return getClinicalTreatmentEligibilityConfig(paramsToOmit, TRIAL, configId);
    }

    private static ClinicalTreatmentEligibilityConfig getClinicalTreatmentEligibilityConfig(List<String> paramsToOmit, String configType, Optional<Boolean> configId) {
        ClinicalTreatmentEligibilityConfig cleanedConfigBuilder = ClinicalTreatmentEligibilityConfig.builder().build();

        if (!paramsToOmit.contains("id")) {
            cleanedConfigBuilder.setId(configId.isPresent() ? UUID.randomUUID().toString() : TRIAL_ID );
        }
        if (!paramsToOmit.contains("type")) {
            cleanedConfigBuilder.setType(configType);
        }
        if (!paramsToOmit.contains("status")) {
            cleanedConfigBuilder.setStatus(RECRUITING.getStatus());
        }
        if (!paramsToOmit.contains("studyCompletion")) {
            cleanedConfigBuilder.setStudyCompletion(DEFAULT_STUDY_COMPLETION);
        }
        if (!paramsToOmit.contains("name")) {
            cleanedConfigBuilder.setName(DEFAULT_NAME);
        }
        if (!paramsToOmit.contains("description")) {
            cleanedConfigBuilder.setDescription(DEFAULT_DESCRIPTION);
        }
        if (!paramsToOmit.contains("phase")) {
            cleanedConfigBuilder.setPhase(DEFAULT_PHASE);
        }
        if (!paramsToOmit.contains("link")) {
            cleanedConfigBuilder.setLink(DEFAULT_LINK);
        }
        if (!paramsToOmit.contains("variables")) {
            cleanedConfigBuilder.setVariables(getDefaultVariables());
        }
        if (!paramsToOmit.contains("criteria")) {
            cleanedConfigBuilder.setCriteria(getDefaultCriteriaGender());
        }
        if (!paramsToOmit.contains("patientsFilter")) {
            cleanedConfigBuilder.setPatientFilter(getDefaultPatientsFilter());
        }
        return cleanedConfigBuilder;
    }

    public static ClinicalTreatmentEligibilityConfig generateDefaultClinicalTreatmentEligibilityWithCustomData(Map<String, String> customData) {
        return getClinicalTreatmentConfig(customData, TRIAL);
    }

    public static ClinicalTreatmentEligibilityConfig generateDefaultClinicalTreatmentEligibilityWithCustomData(Map<String, String> customData, String configType) {
        return getClinicalTreatmentConfig(customData, configType);
    }

    private static ClinicalTreatmentEligibilityConfig getClinicalTreatmentConfig(Map<String, String> customData, String configType) {
        return ClinicalTreatmentEligibilityConfig.builder()
                .id(customData.getOrDefault("id", TRIAL_ID))
                .type(customData.getOrDefault("type", configType))
                .status(customData.getOrDefault("status", RECRUITING.getStatus()))
                .studyCompletion(customData.getOrDefault("studyCompletion", DEFAULT_STUDY_COMPLETION))
                .name(customData.getOrDefault("name", DEFAULT_NAME))
                .description(customData.getOrDefault("description", DEFAULT_DESCRIPTION))
                .phase(customData.getOrDefault("phase", DEFAULT_PHASE))
                .link(customData.getOrDefault("link", "https://clinicaltrials.gov/study/NCT04428710?a=23"))
                .variables(customData.get("variables") != null ? List.of(Variable.builder().build()) : getDefaultVariables())
                .criteria(customData.get("criteria") != null ? List.of(CriteriaConfig.builder().build()) :getDefaultCriteriaGender())
                .build();
    }

    public static ClinicalTreatmentEligibilityConfig generateDefaultClinicalTreatmentEligibilityWithCustomData() {
        Map<String, String> customData = new HashMap<>();
        String generatedId = UUID.randomUUID().toString();
        return ClinicalTreatmentEligibilityConfig.builder()
                .id(customData.getOrDefault("id", STR."trialDefaultId\{generatedId}"))
                .type(customData.getOrDefault("type", TRIAL))
                .status(customData.getOrDefault("status", RECRUITING.getStatus()))
                .studyCompletion(customData.getOrDefault("studyCompletion", DEFAULT_STUDY_COMPLETION))
                .name(customData.getOrDefault("name", DEFAULT_NAME))
                .description(customData.getOrDefault("description", DEFAULT_DESCRIPTION))
                .phase(customData.getOrDefault("phase", DEFAULT_PHASE))
                .link(customData.getOrDefault("link", "https://clinicaltrials.gov/study/NCT04428710?a=23"))
                .patientFilter(getDefaultPatientsFilter())
                .variables(getDefaultVariables())
                .criteria(getDefaultCriteriaGender())
                .build();
    }

    public static String convertClinicalTreatmentEligibilityConfigToString(ClinicalTreatmentEligibilityConfig config) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static PatientFilterConfig getDefaultPatientsFilter() {
        return PatientFilterConfig.builder()
                .filter(PatientFilterConfig.Filter.builder()
                        .must(List.of(PatientFilterConfig.Must.builder()
                                .attributesGender(
                                        PatientFilterConfig.GenderCondition.builder()
                                                .eq("male")
                                                .build())
                                .build()))
                        .build())
                .build();
    }

    public static ArrayList<Variable> getDefaultVariables() {
        ArrayList<String> arraysList = new ArrayList<>();
        arraysList.add("attributes.gender");

        ArrayList<Queries> queries = new ArrayList<>();
        queries.add(Queries.builder()
                .alias("PATIENT")
                .query(Query.builder()
                        .dedup(true)
                        .from("from")
                        .show(arraysList).build()).build());

        return Stream.of(
                        Variable.builder()
                                .id("gender")
                                .usingAi(true)
                                .definition(Definition.builder()
                                        .fulfillment(Fulfillment.builder()
                                                .queries(queries)
                                                .build())
                                        .build())
                                .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<CriteriaConfig> getDefaultCriteriaGender() {
        ArrayList<String> dependencyArrayList = new ArrayList<>();
        dependencyArrayList.add("gender");

        ArrayList<CriteriaConfig.SubCriteria> subCriteria = new ArrayList<>();
        subCriteria.add(CriteriaConfig.SubCriteria.builder()
                .id("gender")
                .title("Gender is female")
                .description("Gender is female description")
                .expression("variables.gender.value[].attributes.gender|[0]== `female`")
                .explainability(CriteriaConfig.Explainability.builder()
                        .displayValue("variables.gender.value[].attributes.gender|[0]")
                        .build())
                .build());

        return Stream.of(
                        CriteriaConfig.builder()
                                .id("criteria_female")
                                .title("Gender is female")
                                .type("inclusion")
                                .missing(false)
                                .dependency(dependencyArrayList)
                                .expression("gender")
                                .subCriteria(subCriteria)
                                .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void saveConfigToFile(String jsonContent, String fileName) {
        try {
            Path path = Paths.get(fileName);
            Files.write(path, jsonContent.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
