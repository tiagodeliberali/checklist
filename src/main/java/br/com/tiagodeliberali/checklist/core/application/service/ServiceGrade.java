package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.service.Answer;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ServiceGrade {
    private double grade;
    private Map<String, ServiceThemeInfo> themesInfo;

    public ServiceGrade() {
        themesInfo = new HashMap<>();
    }

    public static ServiceGrade build(Checklist checklist, ServiceInfo service) {
        ServiceGrade serviceGrade = new ServiceGrade();
        serviceGrade.setGrade(checklist.calculate(service).grade().doubleValue());

        checklist.getThemeIterator().forEachRemaining(theme -> {
            ServiceThemeInfo themeInfo = new ServiceThemeInfo();
            themeInfo.setGrade(theme.calculate(service).grade().doubleValue());

            theme.getTopics().values().forEach(topic -> {
                ServiceTopicInfo topicInfo = new ServiceTopicInfo();
                Grade topicGrade = service.getAnswer(topic.getName())
                        .map(answer -> topic.getGrade(answer))
                        .orElse(Grade.MIN);

                topicInfo.setGrade(topicGrade.grade().doubleValue());

                service.getAnswer(topic.getName())
                        .map(answer -> topic.getUnusedRequirements(answer))
                        .orElse(topic.getUnusedRequirements(Answer.create(topic.getName())))
                        .forEach(requirement -> topicInfo
                                .getUnusedRequirements().put(
                                        requirement.name().id(),
                                        requirement.grade().grade().doubleValue()));

                themeInfo.getTopicsInfo().put(topic.getName().id(), topicInfo);
            });

            serviceGrade.getThemesInfo().put(theme.getName().name(), themeInfo);
        });

        return serviceGrade;
    }
}

