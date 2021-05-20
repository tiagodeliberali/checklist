package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class Topic {
    @Getter
    private final TopicName name;

    @Getter
    private final Theme theme;

    @Getter
    private final int weigth;

    private final RequirementList requirements;

    public static Topic create(TopicName name, int weight, Theme theme, List<Requirement> requirements) {
        return new Topic(name, theme, weight, RequirementList.from(new HashSet<>(requirements)));
    }

    public static Topic create(TopicName name, int weight, Theme theme) {
        return new Topic(name, theme, weight, RequirementList.from(new HashSet<>()));
    }

    public Grade getMaxLoss() {
        return requirements.getGrade();
    }

    public int getRequirementsCount() {
        return requirements.count();
    }

    public void addRequirement(Grade grade, RequirementName name) throws TopicRequirementAlreadyExistsException {
        requirements.add(Requirement.create(grade, name));
    }

    public void removeRequirement(RequirementName id) throws TopicRequirementNotFoundException {
        requirements.remove(id);
    }

    public Grade getGrade(Answer answer) {
        Grade grade = Grade.MAX;

        answer.requirementsIterator().forEachRemaining(id ->
                grade.minus(requirements.getGrade(id).grade().doubleValue()));

        return grade;
    }
}
