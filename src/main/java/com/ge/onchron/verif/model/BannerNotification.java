/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model;

import java.util.Objects;

import org.openqa.selenium.support.Color;

public class BannerNotification {

    private String message;
    private String buttonText;
    private Color backgroundColor;
    private int barSize;


    public BannerNotification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBarSize() {
        return barSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BannerNotification)) return false;
        BannerNotification that = (BannerNotification) o;

        boolean messagesMatch = Objects.equals(this.message, that.message) ||
                (this.message != null && that.message != null &&
                        (this.message.matches(that.message) || that.message.matches(this.message)));

        boolean buttonTextMatches = Objects.equals(this.buttonText, that.buttonText);

        boolean backgroundColorMatches = (this.backgroundColor == null || that.backgroundColor == null) ||
                Objects.equals(this.backgroundColor, that.backgroundColor);

        boolean barSizeMatches = (this.barSize == 0 || that.barSize == 0 || this.barSize == that.barSize);

        return messagesMatch && buttonTextMatches && backgroundColorMatches && barSizeMatches;
    }


    @Override
    public String toString() {
        return "BannerNotification{" +
                "message='" + message + '\'' +
                ", buttonText='" + buttonText + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", bannerSize=" + barSize +
                '}';
    }
}
