package br.com.tiagodeliberali.checklist.core.application.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ServiceTopicInfo implements Serializable {
    private double grade;
    private Map<String, Double> missedRequirements;
    private Map<String, Double> unusedRequirements;

    public ServiceTopicInfo() {
        missedRequirements = new HashMap<>();
        unusedRequirements = new HashMap<>();
    }
}
