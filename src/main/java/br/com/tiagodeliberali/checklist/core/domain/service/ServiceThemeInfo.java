package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ServiceThemeInfo implements Serializable {
    private Grade grade;
    private Map<TopicName, ServiceTopicInfo> topicsInfo;

    public ServiceThemeInfo() {
        topicsInfo = new HashMap<>();
    }
}
