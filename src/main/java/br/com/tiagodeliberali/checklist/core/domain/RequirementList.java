package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class RequirementList {
    private final Set<Requirement> requirements;

    public static RequirementList from(Set<Requirement> list) {
        return new RequirementList(list);
    }

    public void remove(RequirementName name) throws TopicRequirementNotFoundException {
        Optional<Requirement> requirement = requirements.stream()
                .filter(x -> x.name() == name)
                .findFirst();

        if (requirement.isEmpty()) {
            throw new TopicRequirementNotFoundException(name);
        }

        requirements.remove(requirement.get());
    }

    public void add(Requirement requirement) throws TopicRequirementAlreadyExistsException {
        Optional<Requirement> foundRequirement = requirements.stream()
                .filter(x -> x.name() == requirement.name())
                .findFirst();

        if (foundRequirement.isPresent()) {
            throw new TopicRequirementAlreadyExistsException(requirement);
        }

        requirements.add(requirement);
    }

    public int count() {
        return requirements.size();
    }

    public Grade getGrade(RequirementName name) {
        double result = requirements.stream()
                .filter(x -> x.name() == name)
                .mapToDouble(x -> x.grade().grade().doubleValue())
                .sum();

        return Grade.MIN.plus(result);
    }

    public Grade getGrade() {
        double result = requirements.stream()
                .mapToDouble(x -> x.grade().grade().doubleValue())
                .sum();

        return Grade.MIN.plus(result);
    }
}
