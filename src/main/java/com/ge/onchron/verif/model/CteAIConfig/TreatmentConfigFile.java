package com.ge.onchron.verif.model.CteAIConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
public class TreatmentConfigFile {
    public String id;
    public ArrayList<Criterion> criteria;
}
