package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class Topic {
    private final TopicId id;

    @Getter
    private final Theme theme;

    private final String description;
    private final int weigth;
    private final RequirementList requirements;

    public static Topic create(String description, int weight, Theme theme, List<Requirement> requirements) {
        return new Topic(
                new TopicId(UUID.randomUUID()), theme, description, weight, RequirementList.from(new ArrayList<>(requirements)));
    }

    public static Topic create(String description, int weight, Theme theme) {
        return new Topic(new TopicId(UUID.randomUUID()), theme, description, weight, RequirementList.from(new ArrayList<>()));
    }

    public Grade getMaxLoss() {
        return requirements.getGrade();
    }

    public int getRequirementsCount() {
        return requirements.count();
    }

    public void addRequirement(Grade grade, String description) throws TopicRequirementAlreadyExistsException {
        requirements.add(Requirement.create(grade, description));
    }

    public void removeRequirement(Grade grade, String description) throws TopicRequirementNotFoundException {
        requirements.remove(Requirement.create(grade, description));
    }
}
