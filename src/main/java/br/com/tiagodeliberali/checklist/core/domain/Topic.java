package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class Topic {
    @Getter
    private final TopicName name;

    @Getter
    private final int weigth;

    private final RequirementList requirements;

    public static Topic create(TopicName name, int weight, List<Requirement> requirements) {
        return new Topic(name, weight, RequirementList.from(new HashSet<>(requirements)));
    }

    public static Topic create(TopicName name, int weight) {
        return new Topic(name, weight, RequirementList.from(new HashSet<>()));
    }

    public Grade getMaxLoss() {
        return requirements.getGrade();
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

    public Grade getGrade(Answer answer) {
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
}
