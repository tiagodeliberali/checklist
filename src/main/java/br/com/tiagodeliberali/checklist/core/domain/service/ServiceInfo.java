package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceInfo {
    @Getter
    private final String repo;
    private final Map<TopicName, Answer> answers;

    private ServiceInfo(String repo) {
        this.repo = repo;
        answers = new HashMap<>();
    }

    public static ServiceInfo create(String repo) {
        return new ServiceInfo(repo);
    }

    public void addTopic(TopicName topicName) {
        if (!answers.containsKey(topicName)) {
            answers.put(topicName, Answer.create());
        }
    }

    public void addRequirement(TopicName topicName, EntityId requirementId) {
        addTopic(topicName);

        answers.get(topicName).addMissingRequirement(requirementId);
    }

    public int count() {
        return answers.size();
    }

    public Optional<Answer> getAnswer(TopicName id) {
        return Optional.ofNullable(answers.get(id));
    }
}
