package br.com.tiagodeliberali.checklist.core.application.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceTopicInfo implements Serializable {
    private String id;
    private String name;
    private double grade;
    private int weight;
    private boolean missing;
    private List<ServiceRequirementInfo> missedRequirements;
    private List<ServiceRequirementInfo> unusedRequirements;

    public ServiceTopicInfo() {
        missedRequirements = new ArrayList<>();
        unusedRequirements = new ArrayList<>();
    }
}
