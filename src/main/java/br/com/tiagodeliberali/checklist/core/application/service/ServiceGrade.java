package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.service.Answer;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ServiceGrade {
    private double grade;
    private List<ServiceThemeInfo> themesInfo;

    public ServiceGrade() {
        themesInfo = new ArrayList<>();
    }

    public Optional<ServiceThemeInfo> getTheme(String name) {
        return themesInfo.stream().filter(x -> x.getName().equals(name)).findFirst();
    }

    public static ServiceGrade build(Checklist checklist, ServiceInfo service) {
        ServiceGrade serviceGrade = new ServiceGrade();
        serviceGrade.setGrade(checklist.calculate(service).grade().doubleValue());

        checklist.getIterator().forEachRemaining(theme -> {
            ServiceThemeInfo themeInfo = new ServiceThemeInfo();
            themeInfo.setName(theme.getName().name());
            themeInfo.setWeight(theme.getWeight());
            themeInfo.setGrade(theme.calculate(service).grade().doubleValue());

            theme.getTopics().values().forEach(topic -> {
                ServiceTopicInfo topicInfo = new ServiceTopicInfo();
                Grade topicGrade = service.getAnswer(topic.getName())
                        .map(answer -> topic.getGrade(answer))
                        .orElse(Grade.MIN);

                topicInfo.setGrade(topicGrade.grade().doubleValue());
                topicInfo.setWeight(topic.getWeigth());
                topicInfo.setName(topic.getName().id());

                service.getAnswer(topic.getName())
                        .map(answer -> topic.getUnusedRequirements(answer))
                        .orElse(topic.getUnusedRequirements(Answer.create(topic.getName())))
                        .forEach(requirement -> topicInfo
                                .getUnusedRequirements().add(new ServiceRequirementInfo(
                                        requirement.name().id(),
                                        requirement.grade().grade().doubleValue())));

                service.getAnswer(topic.getName())
                        .map(answer -> topic.getMissingRequirements(answer))
                        .orElse(new HashSet<>())
                        .forEach(requirement -> topicInfo
                                .getMissedRequirements().add(new ServiceRequirementInfo(
                                        requirement.name().id(),
                                        requirement.grade().grade().doubleValue())));

                themeInfo.getTopicsInfo().add(topicInfo);
            });

            serviceGrade.getThemesInfo().add(themeInfo);
        });

        return serviceGrade;
    }
}

