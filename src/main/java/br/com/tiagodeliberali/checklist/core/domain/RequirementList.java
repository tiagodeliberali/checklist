package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RequirementList {
    private final List<Requirement> requirements;

    public static RequirementList from(List<Requirement> list) {
        return new RequirementList(list);
    }

    public static RequirementList empty() {
        return new RequirementList(new ArrayList<>());
    }

    public void remove(Requirement requirement) throws TopicRequirementNotFoundException {
        if (!requirements.contains(requirement)) {
            throw new TopicRequirementNotFoundException(requirement);
        }

        requirements.remove(requirement);
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

    public Grade getGrade() {
        double result = requirements.stream()
                .mapToDouble(x -> x.grade().grade().doubleValue())
                .sum();

        return Grade.MIN.plus(result);
    }
}
