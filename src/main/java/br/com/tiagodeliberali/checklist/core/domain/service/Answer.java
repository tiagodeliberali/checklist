package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
public class Answer {
    private final TopicName topicName;
     private final List<EntityId> missedRequirements;

    public static Answer create(TopicName topicName) {
        return new Answer(topicName, new ArrayList<>());
    }

    public void addMissingRequirement(EntityId requirementId) {
        missedRequirements.add(requirementId);
    }

    public void removeMissingRequirement(EntityId requirementId) {
        missedRequirements.remove(requirementId);
    }

    public Iterator<EntityId> getIterator() {
        return missedRequirements.iterator();
    }
}
