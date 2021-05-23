package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@AllArgsConstructor
public class Answer {
    private final TopicName topicName;
    private final Set<EntityId> missedRequirements;

    public static Answer create(TopicName topicName) {
        return new Answer(topicName, new HashSet<>());
    }

    public void addMissingRequirement(EntityId requirementId) {
        missedRequirements.add(requirementId);
    }

    public Iterator<EntityId> getIterator() {
        return missedRequirements.iterator();
    }
}
