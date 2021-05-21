package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
public class Answer {
    private final TopicName topicName;
     private final List<RequirementName> missedRequirements;

    public static Answer create(TopicName topicName) {
        return new Answer(topicName, new ArrayList<>());
    }

    public void addMissingRequirement(RequirementName requirementName) {
        missedRequirements.add(requirementName);
    }

    public void removeMissingRequirement(RequirementName requirementName) {
        missedRequirements.remove(requirementName);
    }

    public Iterator<RequirementName> requirementsIterator() {
        return missedRequirements.iterator();
    }
}
