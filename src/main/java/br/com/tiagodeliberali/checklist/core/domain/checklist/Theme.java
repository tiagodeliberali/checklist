package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class Theme extends NodeInfo<Topic> implements CalculableEntity, WeightCalculableEntity {
    private final EntityId id;
    private final ThemeName name;
    private final int weight;

    private Theme(ThemeName name, int weight) {
        this.name = name;
        this.weight = weight;

        id = EntityId.from(name);
    }

    public static Theme create(ThemeName name, int weight) {
        return new Theme(name, weight);
    }

    @Override
    public EntityId getId() {
        return id;
    }

    @Override
    public Grade calculate(ServiceInfo service) {
        return Calculator.weightAverage(nodes.values(), service);
    }


    public Topic getByName(TopicName topicName) {
        return nodes.values().stream()
                .filter(x -> x.getName().equals(topicName))
                .findFirst()
                .get();
    }

    public void addRequirement(TopicName topicName, Requirement requirement)
            throws RequirementAlreadyExistsException, TopicNotFoundException {

        Optional<Topic> topic = nodes.values().stream()
                .filter(x -> x.getName().equals(topicName))
                .findFirst();

        if (topic.isEmpty()) {
            throw new TopicNotFoundException(topicName);
        }

        topic.get().addRequirement(requirement);
    }

    public Set<Requirement> getUnusedRequirements(ServiceInfo service) {
        Set<Requirement> unusedRequirements = new HashSet<>();

        for (Topic topic : nodes.values()) {
            service.getAnswer(topic.getName())
                    .map(answer -> topic.getUnusedRequirements(answer))
                    .ifPresent(x -> unusedRequirements.addAll(x));
        }

        return unusedRequirements;
    }
}
