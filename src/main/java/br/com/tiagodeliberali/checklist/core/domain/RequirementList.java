package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RequirementList {
    private final List<Requirement> requirements;

    public static RequirementList from(List<Requirement> list) {
        return new RequirementList(list);
    }

    public static RequirementList empty() {
        return new RequirementList(new ArrayList<>());
    }

    public void remove(RequirementId id) throws TopicRequirementNotFoundException {
        Optional<Requirement> requirement = requirements.stream().filter(x -> x.getId() == id).findFirst();
        if (requirement.isEmpty()) {
            throw new TopicRequirementNotFoundException(id);
        }

        requirements.remove(requirement.get());
    }

    public void add(Requirement requirement) throws TopicRequirementAlreadyExistsException {
        if (requirements.contains(requirement)) {
            throw new TopicRequirementAlreadyExistsException(requirement);
        }

        requirements.add(requirement);
    }

    public int count() {
        return requirements.size();
    }

    public Grade getGrade(RequirementId id) {
        double result = requirements.stream()
                .filter(x -> x.getId() == id)
                .mapToDouble(x -> x.getGrade().grade().doubleValue())
                .sum();

        return Grade.MIN.plus(result);
    }

    public Grade getGrade() {
        double result = requirements.stream()
                .mapToDouble(x -> x.getGrade().grade().doubleValue())
                .sum();

        return Grade.MIN.plus(result);
    }
}
