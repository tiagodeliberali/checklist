package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class ServiceInfo {
    private final String repo;
    private final Map<TopicId, Answer> answers;

    public static ServiceInfo create(String repo) {
        return new ServiceInfo(repo, new HashMap<>());
    }

    public void addTopic(TopicId topicId) {
        if (!answers.containsKey(topicId)) {
            answers.put(topicId, Answer.create(topicId));
        }
    }

    public int count() {
        return answers.size();
    }

    public Optional<Answer> getAnswer(TopicId id) {
        return Optional.ofNullable(answers.get(id));
    }
}
