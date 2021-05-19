package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class Topic {
    private final TopicId id;
    private final Theme theme;
    private final String description;
    private final RequirementList requirements;

    public static Topic create(String description, Theme theme, List<TopicRequirement> requirements) {
        return new Topic(
                new TopicId(UUID.randomUUID()), theme, description, RequirementList.from(new ArrayList<>(requirements)));
    }

    public static Topic create(String description, Theme theme) {
        return new Topic(new TopicId(UUID.randomUUID()), theme, description, RequirementList.from(new ArrayList<>()));
    }

    public Grade getMaxLoss() {
        return requirements.getGrade();
    }

    public int getRequirementsCount() {
        return requirements.count();
    }

    public void addRequirement(Grade grade, String description) throws TopicRequirementAlreadyExistsException {
        requirements.add(TopicRequirement.create(grade, description));
    }

    public void removeRequirement(Grade grade, String description) throws TopicRequirementNotFoundException {
        requirements.remove(TopicRequirement.create(grade, description));
    }
}
