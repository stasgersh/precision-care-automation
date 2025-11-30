package com.ge.onchron.verif.howsteps;

import com.ge.onchron.verif.utils.DateUtil;
import com.ge.onchron.verif.utils.Regex;
import com.ge.onchron.verif.utils.SNSProcessUtils;
import com.ge.onchron.verif.utils.SfnProcessUtils;
import com.google.common.collect.ImmutableMap;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_PATH;
import static com.ge.onchron.verif.TestSystemParameters.getSystemParameter;
import static com.ge.onchron.verif.utils.FileUtils.getAbsolutPathOfDir;
import static com.ge.onchron.verif.utils.FileUtils.readFromFileByAbsolutePath;

public class SNSCommonSteps {

    @Steps
    private RestApiSteps restApiSteps;
    @Steps
    private SfnCommonSteps sfnCommonSteps;

    private static final Logger LOGGER = LoggerFactory.getLogger(SNSCommonSteps.class);
    private static final String API_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final String ACCOUNT_REGION_REGEX = "(?<=arn:aws:states:)([^:]+:[0-9]+)";

    public void sendSnsAndSetGlobalDateForSfn(String sfnName, String patientId, String fileName, long timeOffSet) {

        LOGGER.info(STR."Sending SNS message from file \{fileName} template, to DELETE patientId \{patientId} with adjusted timestamp \{timeOffSet}");

        String correlationId = UUID.randomUUID().toString();
        String stateMachineArn = SfnProcessUtils.getInstance().getStateMachineArn(sfnName);
        String fetchedRegionWithAccountId = Regex.fetchTextGroupByRegex(ACCOUNT_REGION_REGEX, stateMachineArn, 1);

        PublishResponse publishResponse =
                SNSProcessUtils.getInstance().publishCustomJsonPayload(fetchedRegionWithAccountId, loadPayload(patientId, correlationId, fileName), correlationId);

        Optional<String> dateHeader = publishResponse.sdkHttpResponse().firstMatchingHeader("Date");
        if (dateHeader.isEmpty()) {
            throw new IllegalStateException("Missing 'Date' header in SNS response");
        }
        LOGGER.info(STR."Set global_date parameter with timeOff set of \{timeOffSet} seconds");
        String adjustedTime = DateUtil.parseDateToFormattedWithTimeOffSet(dateHeader.get(), API_DATE_PATTERN, timeOffSet);
        sfnCommonSteps.setGlobalDate(adjustedTime);
    }

    public String loadPayload(String patientId, String correlationId, String fileName) {
        return new StringSubstitutor(ImmutableMap
                .<String, Object>builder()
                .put("patientId", patientId)
                .put("correlationId",correlationId)
                .build())
                .replace(loadConfigFile(fileName));
    }

    public String loadConfigFile(final String fileName) {
        Path resourcesAbsolutPath = getAbsolutPathOfDir( getSystemParameter( TEST_DATA_PATH ) );
        Path filePath = Paths.get( resourcesAbsolutPath.toString(), STR."\{fileName}.json" );
        return readFromFileByAbsolutePath( filePath.toString() );
    }

}
