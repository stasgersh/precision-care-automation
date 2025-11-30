package com.ge.onchron.verif.model.dataQueryConfig;

import java.util.Objects;

public class DataQueryName {
        public String use;
        public String family;

        @Override
        public boolean equals(Object o) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            DataQueryName that = (DataQueryName) o;
            return Objects.equals(use, that.use)
                    && Objects.equals(family, that.family);
    }
}
