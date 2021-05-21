package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Theme {
    private final ThemeName name;
    private final int weight;
    private final Map<TopicName, Topic> topics;

    public static Theme create(ThemeName name, int weight) {
        return new Theme(name, weight, new HashMap<>());
    }

    public void add(Topic topic) {
        topics.put(topic.getName(), topic);
    }

    public int count() {
        return topics.size();
    }

    public Topic get(TopicName topicName) {
        return topics.get(topicName);
    }

    public void addRequirement(TopicName topicName, Requirement requirement)
            throws RequirementAlreadyExistsException, TopicNotFoundException {

        if (!topics.containsKey(topicName)) {
            throw new TopicNotFoundException(topicName);
        }
        topics.get(topicName).addRequirement(requirement);
    }

    public Grade calculate(ServiceInfo service) {
        double total = 0;
        int count = 0;
        for (Topic topic: topics.values()) {
            count += topic.getWeigth();
            total += service.getAnswer(topic.getName())
                    .map(answer -> topic.getGrade(answer))
                    .orElse(Grade.MIN)
                    .grade()
                    .doubleValue() * topic.getWeigth();
        }

        return Grade.from(total / count);
    }

    public Set<Requirement> getUnusedRequirements(ServiceInfo service) {
        Set<Requirement> unusedRequirements = new HashSet<>();

        for (Topic topic: topics.values()) {
            service.getAnswer(topic.getName())
                .map(answer -> topic.getUnusedRequirements(answer))
                .ifPresent(x -> unusedRequirements.addAll(x));
        }

        return unusedRequirements;
    }
}
