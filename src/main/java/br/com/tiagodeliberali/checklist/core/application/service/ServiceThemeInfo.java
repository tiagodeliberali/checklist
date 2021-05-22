package br.com.tiagodeliberali.checklist.core.application.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ServiceThemeInfo implements Serializable {
    private String name;
    private double grade;
    private int weight;
    private List<ServiceTopicInfo> topicsInfo;

    public ServiceThemeInfo() {
        topicsInfo = new ArrayList<>();
    }

    public Optional<ServiceTopicInfo> getTopic(String name) {
        return topicsInfo.stream().filter(x -> x.getName().equals(name)).findFirst();
    }
}
