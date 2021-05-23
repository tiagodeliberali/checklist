package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.Answer;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Topic extends NodeInfo<Requirement> implements CalculableEntity, WeightCalculableEntity {
    private final EntityId id;
    private final TopicName name;
    private final int weight;

    private Topic(TopicName name, int weight) {
        this.name = name;
        this.weight = weight;

        id = EntityId.from(name);
    }

    public static Topic create(TopicName name, int weight) {
        return new Topic(name, weight);
    }

    @Override
    public EntityId getId() {
        return id;
    }

    @Override
    public Grade calculate(ServiceInfo service) {
        return service.getAnswer(name)
                .map(x -> getGrade(x))
                .orElse(Grade.MIN);
    }

    private Grade getGrade(Answer answer) {
        Grade grade = Grade.MAX;
        Iterator<EntityId> iterator = answer.getIterator();

        while (iterator.hasNext()) {
            grade = grade.minus(nodes.get(iterator.next()).getGrade().grade().doubleValue());
        }

        return grade;
    }

    public Set<Requirement> getUnusedRequirements(Answer answer) {
        Set<EntityId> unusedRequirements = new HashSet<>(nodes.keySet());
        unusedRequirements.removeAll(getUsedRequirements(answer));

        return nodes.values().stream()
                .filter(requirement -> unusedRequirements.contains(requirement.getId()))
                .collect(Collectors.toSet());
    }

    public Set<Requirement> getRequirements(Answer answer) {
        Set<EntityId> usedRequirements = getUsedRequirements(answer);

        return nodes.values().stream()
                .filter(requirement -> usedRequirements.contains(requirement.getId()))
                .collect(Collectors.toSet());
    }

    private Set<EntityId> getUsedRequirements(Answer answer) {
        Set<EntityId> usedRequirements = new HashSet<>();
        answer.getIterator().forEachRemaining(id -> usedRequirements.add(id));
        return usedRequirements;
    }

    public Grade getMaxLoss() {
        return Grade.MIN.plus(nodes.values().stream()
                .mapToDouble(x -> x.getGrade().grade().doubleValue())
                .sum());
    }
}
