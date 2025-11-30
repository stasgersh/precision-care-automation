package com.ge.onchron.verif.model.CteAIConfig;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@EqualsAndHashCode
@Getter
@Setter
public class CriteriaRes {
    public String statement;
    public String type;
    public String response;
    public ArrayList<String> reasoning;
    public ArrayList<Provenance> provenance;

}
