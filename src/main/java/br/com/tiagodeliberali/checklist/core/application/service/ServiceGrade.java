package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.service.Answer;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
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

            theme.getIterator().forEachRemaining(topic -> {
                Optional<Answer> optionalAnswer = service.getAnswer(topic.getId());

                ServiceTopicInfo topicInfo = new ServiceTopicInfo();
                Grade topicGrade = optionalAnswer
                        .map(answer -> topic.calculate(service))
                        .orElse(Grade.MIN);

                topicInfo.setGrade(topicGrade.grade().doubleValue());
                topicInfo.setId(topic.getId().id());
                topicInfo.setWeight(topic.getWeight());
                topicInfo.setName(topic.getName().name());
                topicInfo.setMissing(optionalAnswer.isEmpty());

                optionalAnswer
                        .map(topic::getUnusedRequirements)
                        .orElse(topic.getUnusedRequirements(Answer.create()))
                        .forEach(requirement -> topicInfo
                                .getUnusedRequirements().add(new ServiceRequirementInfo(
                                        requirement.getId().id(),
                                        requirement.getName().name(),
                                        requirement.getGrade().grade().doubleValue())));

                optionalAnswer
                        .map(topic::getRequirements)
                        .orElse(new HashSet<>())
                        .forEach(requirement -> topicInfo
                                .getMissedRequirements().add(new ServiceRequirementInfo(
                                        requirement.getId().id(),
                                        requirement.getName().name(),
                                        requirement.getGrade().grade().doubleValue())));

                topicInfo.getUnusedRequirements().sort(Comparator.comparingDouble(x -> -x.getGrade()));
                topicInfo.getMissedRequirements().sort(Comparator.comparingDouble(x -> -x.getGrade()));
                themeInfo.getTopicsInfo().add(topicInfo);
            });

            themeInfo.getTopicsInfo().sort(Comparator.comparingInt(x -> -x.getWeight()));
            serviceGrade.getThemesInfo().add(themeInfo);
        });

        serviceGrade.getThemesInfo().sort(Comparator.comparingInt(x -> -x.getWeight()));

        return serviceGrade;
    }

    public static ServiceGradeHeader buildHeader(Checklist checklist, ServiceInfo service) {
        ServiceGradeHeader serviceGrade = new ServiceGradeHeader();
        serviceGrade.setGrade(checklist.calculate(service).grade().doubleValue());
        serviceGrade.setRepo(service.getRepo());

        checklist.getIterator().forEachRemaining(theme -> {
            ServiceThemeHeader themeInfo = new ServiceThemeHeader();
            themeInfo.setName(theme.getName().name());
            themeInfo.setWeight(theme.getWeight());
            themeInfo.setGrade(theme.calculate(service).grade().doubleValue());
            serviceGrade.getThemesHeader().add(themeInfo);
        });

        return serviceGrade;
    }
}

