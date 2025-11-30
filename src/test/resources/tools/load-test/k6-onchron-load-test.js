import http from 'k6/http';
import { URLSearchParams } from 'https://jslib.k6.io/url/1.0.0/index.js';
import { check, group, sleep, fail } from 'k6';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/2.4.0/dist/bundle.js";

const NUMBER_OF_USERS = __ENV.NUMBER_OF_USERS;
const NUMBER_OF_ITERATIONS = __ENV.NUMBER_OF_ITERATIONS;;

const USERS_BASE_NAME = __ENV.USERS_BASE_NAME;
const PASSWORD = __ENV.USERS_PASSWORD;
const USERS_INITIAL_PASSWORD = __ENV.USERS_INITIAL_PASSWORD;

const MAX_DURATION = __ENV.DURATION_IN_SEC;

const HTML_REPORT_FILE_PATH = __ENV.HTML_REPORT_FILE_PATH;

const HOST = __ENV.EHL_BOX_ADDRESS;
const DEPLOYMENT = `${__ENV.NAMESPACE}/${__ENV.DEPLOYMENT_NAME}`;
const ONCO_CONFIG_PATH = `${DEPLOYMENT}/config`;
const ONCO_UI_PATH = `${DEPLOYMENT}`;
const ONCO_CORE_PATH = `${DEPLOYMENT}/core`;

const PATIENT_ID = 'a0747980-7e02-4318-9ef7-0569643aed5a';  // OncoTestPatient, Eleanor470 - has 404 resources

export const options = {
    setupTimeout: '180s',   // default timeout is 60s

    insecureSkipTLSVerify: true,
    // noVUConnectionReuse: true,
    // noConnectionReuse: true,
    // noCookiesReset: true,
    scenarios: {
        contacts: {
            executor: 'per-vu-iterations',
            vus: NUMBER_OF_USERS,
            iterations: NUMBER_OF_ITERATIONS,
            maxDuration: `${MAX_DURATION}s`,
        },

        // contacts2: {
        //     executor: 'ramping-vus',
        //     startVUs: 0,
        //     stages: [
        //         { duration: '10s', target: NUMBER_OF_USERS },
        //         { duration: '30s', target: NUMBER_OF_USERS },
        //         // { duration: '10s', target: 0 },
        //     ],
        //     gracefulRampDown: '0s',
        // },
    },

    thresholds: {
        // 'http_req_duration{name:getPatientsList}': [],
        'http_req_duration{name:getTimeline}': [],
        'http_req_duration{name:getBanner}': [],
        'http_req_duration{name:getMedications}': [],
        // 'http_req_duration{name:getTrends}': [],
        // 'http_req_duration{name:getPatientHistory}': []
    },
};

export function setup() {
    // Get public configuration
    const publicConfig = getPublicConfig();

    let userTokens = {};
    // Generate token for all users
    for (let i = 1; i <= NUMBER_OF_USERS; i++) {
        const username = USERS_BASE_NAME + i;
        console.log('Actual username: ' + username)
        const accessToken = loginUser(username, publicConfig);
        userTokens[`${USERS_BASE_NAME}${i}`] = accessToken;
    }
    return userTokens;
}

export default function (userTokens) {
    const username = `onchron-perf-user-${__VU}`
    const accessToken = userTokens[`onchron-perf-user-${__VU}`]   // __VU: https://k6.io/docs/using-k6/execution-context-variables/

    group('Group of some GET endpoints', function () {
        // const resPatientsList = sendGetRequest(`${HOST}/${ONCO_CORE_PATH}/patients`, accessToken, { name: 'getPatientsList' })
        // check(resPatientsList, { 'GET /patients': (r) => r.status === 200 });

        const resTimeline = sendGetRequest(`${HOST}/${ONCO_CORE_PATH}/patients/${PATIENT_ID}/timeline`, accessToken, { name: 'getTimeline' })
        check(resTimeline, { 'GET /timeline': (r) => r.status === 200 });

        const resBanner = sendGetRequest(`${HOST}/${ONCO_CORE_PATH}/patients/${PATIENT_ID}/banner`, accessToken, { name: 'getBanner' })
        check(resBanner, { 'GET /banner': (r) => r.status === 200 });

        const resMedications = sendGetRequest(`${HOST}/${ONCO_CORE_PATH}/patients/${PATIENT_ID}/medications`, accessToken, { name: 'getMedications' })
        check(resMedications, { 'GET /medications': (r) => r.status === 200 });

        // const resTrends = sendGetRequest(`${HOST}/${ONCO_CORE_PATH}/patients/${PATIENT_ID}/trends`, accessToken, { name: 'getTrends' })
        // check(resTrends, { 'GET /trends': (r) => r.status === 200 });

        // const resPatientHistory = sendGetRequest(`${HOST}/${ONCO_CORE_PATH}/patients/${PATIENT_ID}/history`, accessToken, { name: 'getPatientHistory' })
        // check(resPatientHistory, { 'GET /history': (r) => r.status === 200 });

    });
}

export function handleSummary(data) {
    return {
        [HTML_REPORT_FILE_PATH]: htmlReport(data),
    };
}

export function sendGetRequest(path, token, tags) {
    const res = http.get(path, {
        headers: {
            'Authorization': `Bearer ${token}`,
        },
        tags: tags
    });
    return res;
}

export function getPublicConfig() {
    const resPublicConf = http.get(`${HOST}:443/${ONCO_CONFIG_PATH}/ui`, {
        headers: {
            'Content-Type': 'application/json',
            'Accept': '*/*',
        }
    });
    check(resPublicConf, { 'GET public config': (r) => r.status === 200 });

    const publicConfig = {};
    publicConfig.authUrl = resPublicConf.json('auth.authUrl');
    publicConfig.tokenUrl = resPublicConf.json('auth.tokenUrl');
    publicConfig.clientId = resPublicConf.json('auth.clientId');

    return publicConfig;
}

function loginUser(username, publicConfig) {
    // 1. Init Authorization Code Flow
    const sessionDataKey = initAuthCodeFlow(publicConfig);
    // 2. Common Auth Login Request
    const sessionDataKeyFromAuthLoginReq = commonAuthLoginRequest(sessionDataKey, username);
    // 3. Get Authorization Code
    const authCode = getAuthorizationCode(sessionDataKeyFromAuthLoginReq, publicConfig);
    // 4. Generate token
    return generateToken(authCode, publicConfig);
}

function initAuthCodeFlow(publicConfig) {
    const initAuthCodeFlowParams = new URLSearchParams([
        ['response_type', 'code'],
        ['client_id', `${publicConfig.clientId}`],
        ['redirect_uri', `${HOST}/${ONCO_UI_PATH}/`],

        ['scope', 'openid profile'],
        ['code_challenge', 'GWmkUng078tXnkHduWcJPek-K1P4dEliRjmNepN65H0'],
        ['code_challenge_method', 'S256'],
    ]);

    const cookieJar = new http.CookieJar(); // to clear cookies in each iteration: https://community.k6.io/t/clear-cookies-on-each-iteration/2081/14
    const resInitAuthCodeFlow = http.get(`${publicConfig.authUrl}?${initAuthCodeFlowParams.toString()}`, { redirects: 0, jar: cookieJar });
    check(resInitAuthCodeFlow, { 'Init Authorization Code Flow': (r) => r.status === 302 });
    const locationHeader = resInitAuthCodeFlow.headers['Location'];
    return locationHeader.match(/sessionDataKey=(.*?)&/)[1];
}

function commonAuthLoginRequest(sessionDataKey, username) {
    const commonAuthLoginRequestBody = {
        username: username,
        password: PASSWORD,
        sessionDataKey: sessionDataKey,
    };
    const resCommonAuthLoginRequest = http.post(`${HOST}/commonauth`, commonAuthLoginRequestBody, { redirects: 0 });
    check(resCommonAuthLoginRequest, { 'Common Auth Login Request': (r) => r.status === 302 });
    const locationHeaderFromAuthLoginReq = resCommonAuthLoginRequest.headers['Location'];

    let sessionDataKeyFromAuthLoginReq;
    const loginFailed = locationHeaderFromAuthLoginReq.match(/(login.fail)/);
    if (loginFailed != null) {
        // If Login failed, try initial pasword and set new password (needed when user tries to login first time)

        // Common Auth Login Request
        const commonAuthLoginRequestBody = {
            username: username,
            password: USERS_INITIAL_PASSWORD,
            sessionDataKey: sessionDataKey,
        };
        const resCommonAuthLoginRequest = http.post(`${HOST}/commonauth`, commonAuthLoginRequestBody, { redirects: 0 });
        check(resCommonAuthLoginRequest, { 'Common Auth Login Request': (r) => r.status === 302 });
        const locationHeaderFromAuthLoginReq = resCommonAuthLoginRequest.headers['Location'];
        sessionDataKeyFromAuthLoginReq = getSessionDataKey(locationHeaderFromAuthLoginReq);

        // Check if password reset needed
        const pswResetNeeded = resCommonAuthLoginRequest.headers['Location'].match(/(pwd-reset)/);
        if (pswResetNeeded != null) {
            console.log(`Password reset was performed for user: ${username}`)
            const commonAuthLoginRequestBody = {
                sessionDataKey: sessionDataKeyFromAuthLoginReq,
                CURRENT_PWD: USERS_INITIAL_PASSWORD,
                NEW_PWD: PASSWORD,
                NEW_PWD_CONFIRMATION: PASSWORD,
            };
            const resCommonAuthLoginRequest = http.post(`${HOST}/commonauth`, commonAuthLoginRequestBody, { redirects: 0 });
            check(resCommonAuthLoginRequest, { 'Password reset Common Auth Login Request': (r) => r.status === 302 });
            const locationHeaderFromAuthLoginReq = resCommonAuthLoginRequest.headers['Location'];
            sessionDataKeyFromAuthLoginReq = getSessionDataKey(locationHeaderFromAuthLoginReq);
        }
    } else {
        sessionDataKeyFromAuthLoginReq = getSessionDataKey(locationHeaderFromAuthLoginReq);
    }
    return sessionDataKeyFromAuthLoginReq;
}

function getAuthorizationCode(sessionDataKey, publicConfig) {
    const getAuthorizationCodeParams = new URLSearchParams([
        ['sessionDataKey', sessionDataKey]
    ]);
    const resGetAuthorizationCode = http.get(`${publicConfig.authUrl}?${getAuthorizationCodeParams.toString()}`, { redirects: 0 });
    check(resGetAuthorizationCode, { 'Get Authorization Code': (r) => r.status === 302 });
    const locationHeader3 = resGetAuthorizationCode.headers['Location'];
    return locationHeader3.match(/code=(.*?)&/)[1];
}

function generateToken(authCode, publicConfig) {
    const generateTokenRequestBody = {
        grant_type: 'authorization_code',
        code: authCode,
        client_id: publicConfig.clientId,
        redirect_uri: `${HOST}/${ONCO_UI_PATH}/`,
        code_verifier: 'gcDuvomcNwtLaR~sk2j-hLCWTTNVJBUgwof.VJl.35axR-YCEESd5kIBsIKFvtFh0s2VzAZRDuIDma0yTfez-FUmnupqUoflaatJEP8BH2wqjQ8.CUH98oswlntrGlp~'
    };
    const resGenerateTokenRequest = http.post(publicConfig.tokenUrl, generateTokenRequestBody);
    check(resGenerateTokenRequest, { 'Generate token': (r) => r.status === 200 });
    return resGenerateTokenRequest.json('access_token');
}

function getSessionDataKey(header) {
    return header.match(/sessionDataKey=([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})/)[1];
}
