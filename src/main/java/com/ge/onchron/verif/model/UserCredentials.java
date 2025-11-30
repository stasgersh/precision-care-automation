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

public class UserCredentials {

    private String alias;
    private String username;
    private String password;
    private String familyName;
    private String givenName;
    private String organizationName;
    private Token token;

    public UserCredentials() {
    }

    public UserCredentials( String alias, String username, String password ) {
        this.alias = alias;
        this.username = username;
        this.password = password;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getOrganizationName(){return organizationName;}

    public void setOrganizationName( String organizationName ) {this.organizationName = organizationName;}

    public void setFamilyName( String familyName ) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName( String givenName ) {
        this.givenName = givenName;
    }

    public Token getToken() {
        return token;
    }

    public void setToken( Token token ) {
        this.token = token;
    }

    public boolean hasValidTokenWithOffset() {
        return token != null && !token.isExpiredWithOffset( 60L );
    }

    @Override
    public String toString() {
        return STR."User alias: \{this.alias}, username: \{this.username}, password: \{this.password}";
    }
}
