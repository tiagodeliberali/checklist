package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Requirement;
import br.com.tiagodeliberali.checklist.core.domain.checklist.ThemeName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ServiceGrade implements Serializable {
    private Grade grade;
    private Map<ThemeName, ServiceThemeInfo> themesInfo;

    public ServiceGrade() {
        themesInfo = new HashMap<>();
    }

    public static ServiceGrade build(Checklist checklist, ServiceInfo service) {
        ServiceGrade serviceGrade = new ServiceGrade();
        serviceGrade.setGrade(checklist.calculate(service));

        checklist.getThemeIterator().forEachRemaining(theme -> {
            ServiceThemeInfo themeInfo = new ServiceThemeInfo();
            themeInfo.setGrade(theme.calculate(service));

            theme.getTopics().values().forEach(topic -> {
                ServiceTopicInfo topicInfo = new ServiceTopicInfo();
                Grade topicGrade = service.getAnswer(topic.getName())
                        .map(answer -> topic.getGrade(answer))
                        .orElse(Grade.MIN);

                topicInfo.setGrade(topicGrade);

                Set<Requirement> unusedRequirements = service.getAnswer(topic.getName())
                        .map(answer -> topic.getUnusedRequirements(answer))
                        .orElse(topic.getUnusedRequirements(Answer.create(topic.getName())));

                topicInfo.setUnusedRequirements(unusedRequirements);

                themeInfo.getTopicsInfo().put(topic.getName(), topicInfo);
            });

            serviceGrade.getThemesInfo().put(theme.getName(), themeInfo);
        });

        return serviceGrade;
    }
}

