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
package com.ge.onchron.verif.pages.utils;

import java.util.Base64;
import java.util.Set;

import org.openqa.selenium.Cookie;

import io.restassured.path.json.JsonPath;
import net.serenitybdd.core.Serenity;

public class CookieUtils {

    public static String getLoggedInUsername() {
        String accessToken = getAccessToken();
        if ( !accessToken.isEmpty() ) {
            String[] tokenParts = accessToken.split( "\\." );   // [0] header, [1] payload, [2] signature
            return new JsonPath( new String( Base64.getDecoder().decode( tokenParts[1] ) ) )
                    .get( "sub" ).toString()
                    .split( "@" )[0]; // in cloud: username@orgname
        } else {
            return "";
        }
    }

    public static String getAccessToken() {
        Set<Cookie> cookies = Serenity.getDriver().manage().getCookies();
        return cookies.stream()
                      .filter( cookie -> cookie.getName().equals( "access_token" ) )
                      .findFirst()
                      .map( Cookie::getValue )
                      .orElse( "" );
    }

}
