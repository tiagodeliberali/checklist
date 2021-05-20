package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
public class Answer {
    private final TopicId topicId;
     private final List<RequirementId> missedRequirements;

    public static Answer create(TopicId topicId) {
        return new Answer(topicId, new ArrayList<>());
    }

    public void addMissingRequirement(RequirementId requirementId) {
        missedRequirements.add(requirementId);
    }

    public void removeMissingRequirement(RequirementId requirementId) {
        missedRequirements.remove(requirementId);
    }

    public Iterator<RequirementId> requirementsIterator() {
        return missedRequirements.iterator();
    }
}
