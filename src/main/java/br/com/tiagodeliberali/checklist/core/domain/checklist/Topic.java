package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.Answer;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Getter
public class Topic implements CalculableEntity, WeightCalculableEntity {
    private final EntityId id;
    private final TopicName name;
    private final int weight;
    private final RequirementList requirements;

    private Topic(TopicName name, int weight) {
        this.name = name;
        this.weight = weight;
        requirements = RequirementList.from(new HashSet<>());
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
        return service.getAnswer(name).map(x -> getGrade(x)).orElse(Grade.MIN);
    }

    public int getRequirementsCount() {
        return requirements.count();
    }

    public void addRequirement(Grade grade, RequirementName name) throws RequirementAlreadyExistsException {
        addRequirement(Requirement.create(grade, name));
    }

    public void addRequirement(Requirement requirement) throws RequirementAlreadyExistsException {
        requirements.add(requirement);
    }

    public void removeRequirement(RequirementName id) throws RequirementNotFoundException {
        requirements.remove(id);
    }

    private Grade getGrade(Answer answer) {
        Grade grade = Grade.MAX;
        Iterator<RequirementName> iterator = answer.requirementsIterator();

        while (iterator.hasNext()) {
            grade = grade.minus(requirements.getGrade(iterator.next()).grade().doubleValue());
        }

        return grade;
    }

    public Set<Requirement> getUnusedRequirements(Answer answer) {
        Set<RequirementName> names = new HashSet<>();

        answer.requirementsIterator().forEachRemaining(name -> names.add(name));

        return requirements.getMissingRequirements(names);
    }

    public Set<Requirement> getMissingRequirements(Answer answer) {
        Set<RequirementName> names = new HashSet<>();

        answer.requirementsIterator().forEachRemaining(name -> names.add(name));

        return requirements.getRequirements(names);
    }

    public Grade getMaxLoss() {
        return requirements.getGrade();
    }


}
