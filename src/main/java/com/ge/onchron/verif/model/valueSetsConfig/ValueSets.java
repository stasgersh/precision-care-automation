package com.ge.onchron.verif.model.valueSetsConfig;

import java.util.Objects;

public class ValueSets {

    public String url;
    public String description;
    public Compose compose;
    public String version;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ValueSets that = (ValueSets) o;
        return Objects.equals(url, that.url)
                && Objects.equals(description, that.description)
                && Objects.equals(compose, that.compose)
                && Objects.equals(version, that.version);
    }

}
