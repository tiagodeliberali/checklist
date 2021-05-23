package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Answer {
    private final Set<EntityId> missedRequirements;

    private Answer() {
        missedRequirements = new HashSet<>();
    }

    public static Answer create() {
        return new Answer();
    }

    public void addMissingRequirement(EntityId requirementId) {
        missedRequirements.add(requirementId);
    }

    public Iterator<EntityId> getIterator() {
        return missedRequirements.iterator();
    }
}
