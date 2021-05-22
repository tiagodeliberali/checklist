package br.com.tiagodeliberali.checklist.core.application.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ServiceThemeInfo implements Serializable {
    private double grade;
    private Map<String, ServiceTopicInfo> topicsInfo;

    public ServiceThemeInfo() {
        topicsInfo = new HashMap<>();
    }
}
