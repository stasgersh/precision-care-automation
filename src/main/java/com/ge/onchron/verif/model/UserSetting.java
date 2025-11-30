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
package com.ge.onchron.verif.model;

import java.util.Objects;

public class UserSetting {

    private String title;
    private String settingName;
    private String settingValue;

    public UserSetting() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName( String settingName ) {
        this.settingName = settingName;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue( String settingValue ) {
        this.settingValue = settingValue;
    }

    @Override
    public String toString() {
        return "UserSetting{" +
                "title='" + title + '\'' +
                ", settingName='" + settingName + '\'' +
                ", settingValue='" + settingValue + '\'' +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !(o instanceof UserSetting) ) return false;
        UserSetting that = (UserSetting) o;
        return Objects.equals( getTitle(), that.getTitle() ) &&
                Objects.equals( getSettingName(), that.getSettingName() )
                && Objects.equals( getSettingValue(), that.getSettingValue() );
    }

}
