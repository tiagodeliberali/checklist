package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class ServiceInfo {
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

    public int count() {
        return answers.size();
    }

    public Optional<Answer> getAnswer(TopicName id) {
        return Optional.ofNullable(answers.get(id));
    }
}
