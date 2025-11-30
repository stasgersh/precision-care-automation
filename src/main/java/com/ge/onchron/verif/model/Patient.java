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

public class Patient {

    private String name;
    private String PID;

    public Patient( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getPID() {
        return PID;
    }

    public void setPID( String PID ) {
        this.PID = PID;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Patient patient = (Patient) o;
        return name.equals( patient.name ) && PID.equals( patient.PID );
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", MRN='" + PID + '\'' +
                '}';
    }

}
