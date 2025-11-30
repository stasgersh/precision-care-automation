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

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ge.onchron.verif.model.enums.EventType;
import lombok.Getter;

import static com.ge.onchron.verif.TestSystemParameters.TIMELINE_AXIS_TOOLTIP_DATE_FORMAT;
import static com.ge.onchron.verif.utils.ReplaceUtils.replaceDatePlaceholders;

public class TimelineEvent {

    @Getter
    private EventType eventType;
    @Getter
    private String textOnEventPoint;
    @Getter
    private String nameLabel;
    private String dateOnTimelineAxis;
    @Getter
    private Point position;
    @Getter
    private String swimlane;
    @Getter
    private List<Badge> badges = new ArrayList<>();

    public TimelineEvent() {
    }

    public TimelineEvent( EventType eventType ) {
        this.eventType = eventType;
    }

    public TimelineEvent( EventType eventType, String nameLabel ) {
        this.eventType = eventType;
        this.nameLabel = nameLabel;

        if ( eventType.equals( EventType.MARKER ) ) {
            this.setTextOnEventPoint( "" );
        } else {
            String eventNumInCluster = nameLabel.split( " " )[0];
            this.setTextOnEventPoint( eventNumInCluster );
        }
    }

    public TimelineEvent( EventType eventType, String nameLabel, String dateOnTimelineAxis ) {
        this( eventType, nameLabel );
        this.dateOnTimelineAxis = dateOnTimelineAxis;
    }

    public void setEventType( EventType eventType ) {
        this.eventType = eventType;
    }

    public void setTextOnEventPoint( String textOnEventPoint ) {
        this.textOnEventPoint = textOnEventPoint;
    }

    public void setNameLabel( String nameLabel ) {
        this.nameLabel = nameLabel;
    }

    public String getDateOnTimelineAxis() {
        return this.dateOnTimelineAxis == null ? null : replaceDatePlaceholders( this.dateOnTimelineAxis, TIMELINE_AXIS_TOOLTIP_DATE_FORMAT );
    }

    public void setDateOnTimelineAxis( String dateOnTimelineAxis ) {
        this.dateOnTimelineAxis = dateOnTimelineAxis;
    }

    public void setPosition( Point position ) {
        this.position = position;
    }

    public void setSwimlane( String swimlane ) {
        this.swimlane = swimlane;
    }

    public void setBadges( List<Badge> badges ) {
        this.badges = badges;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        TimelineEvent that = (TimelineEvent) o;
        return eventType == that.eventType &&
                Objects.equals( textOnEventPoint, that.textOnEventPoint ) &&
                Objects.equals( nameLabel, that.nameLabel ) &&
                Objects.equals( getDateOnTimelineAxis(), that.getDateOnTimelineAxis() );
    }

    @Override
    public String toString() {
        return toString( false );
    }

    public String toString( boolean replacePlaceholders) {
        String date = replacePlaceholders ? getDateOnTimelineAxis() : dateOnTimelineAxis;
        return STR."TimelineEvent{eventType=\{eventType}, textOnEventPoint='\{textOnEventPoint}', nameLabel='\{nameLabel}', dateOnTimelineAxis='\{ date }', position=\{position}, swimlane='\{swimlane}', badges=\{badges}}";
    }

}
