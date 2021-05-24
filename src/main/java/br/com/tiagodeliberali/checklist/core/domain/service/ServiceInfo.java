package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ServiceInfo {
    @Getter
    private final String repo;
    private final Map<EntityId, Answer> answers;

    private ServiceInfo(String repo) {
        this.repo = repo;
        answers = new HashMap<>();
    }

    public static ServiceInfo create(String repo) {
        return new ServiceInfo(repo);
    }

    public void addTopic(EntityId topicId) {
        if (!answers.containsKey(topicId)) {
            answers.put(topicId, Answer.create());
        }
    }

    public void addRequirement(EntityId topicId, EntityId requirementId) {
        addTopic(topicId);

        answers.get(topicId).addMissingRequirement(requirementId);
    }

    public int count() {
        return answers.size();
    }

    public Iterator<EntityId> getIterator() {
        return answers.keySet().iterator();
    }

    public Optional<Answer> getAnswer(EntityId id) {
        return Optional.ofNullable(answers.get(id));
    }
}
