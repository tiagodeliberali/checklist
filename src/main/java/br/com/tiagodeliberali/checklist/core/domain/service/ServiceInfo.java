package br.com.tiagodeliberali.checklist.core.domain.service;

import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import br.com.tiagodeliberali.checklist.core.domain.checklist.TopicName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class ServiceInfo {
    @Getter
    private final String repo;
    private final Map<TopicName, Answer> answers;

    public static ServiceInfo create(String repo) {
        return new ServiceInfo(repo, new HashMap<>());
    }

    public void addTopic(TopicName topicName) {
        if (!answers.containsKey(topicName)) {
            answers.put(topicName, Answer.create(topicName));
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
