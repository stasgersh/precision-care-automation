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
package com.ge.onchron.verif.model;

import lombok.Getter;
import lombok.Setter;

public class Token {

    @Setter
    @Getter
    private String accessToken;
    @Setter
    @Getter
    private String refreshToken;
    @Setter
    @Getter
    private String idToken;
    @Setter
    private long expirationTime;
    @Setter
    private long tokenIssuedTime;

    public Boolean isExpired() {
        return isExpiredWithOffset( 0L );
    }

    public Boolean isExpiredWithOffset( long offsetInSec ) {
        return (System.currentTimeMillis() / 1000L + offsetInSec) > expirationTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", idToken='" + idToken + '\'' +
                ", expirationTime=" + expirationTime +
                ", tokenIssuedTime=" + tokenIssuedTime +
                '}';
    }

}
