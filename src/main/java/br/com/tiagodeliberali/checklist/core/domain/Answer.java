package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Answer {
    private final TopicId topicId;
     private final RequirementList missedRequirements;

    public static Answer create(TopicId topicId) {
        return new Answer(topicId, RequirementList.empty());
    }

    public Grade getScore() {
        return Grade.MAX.minus(missedRequirements.getGrade().grade().doubleValue());
    }

    public void addMissingRequirement(TopicRequirement missingRequirement) throws TopicRequirementAlreadyExistsException {
        missedRequirements.add(missingRequirement);
    }

    public void removeMissingRequirement(TopicRequirement missingRequirement) throws TopicRequirementNotFoundException {
        missedRequirements.remove(missingRequirement);
    }
}
