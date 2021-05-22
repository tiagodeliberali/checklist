package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class RequirementList {
    private final Set<Requirement> requirements;

    public static RequirementList from(Set<Requirement> list) {
        return new RequirementList(list);
    }

    public void remove(RequirementName name) throws RequirementNotFoundException {
        Optional<Requirement> requirement = requirements.stream()
                .filter(x -> x.name().equals(name))
                .findFirst();

        if (requirement.isEmpty()) {
            throw new RequirementNotFoundException(name);
        }

        requirements.remove(requirement.get());
    }

    public void add(Requirement requirement) throws RequirementAlreadyExistsException {
        Optional<Requirement> foundRequirement = requirements.stream()
                .filter(x -> x.name().equals(requirement.name()))
                .findFirst();

        if (foundRequirement.isPresent()) {
            throw new RequirementAlreadyExistsException(requirement);
        }

        requirements.add(requirement);
    }

    public int count() {
        return requirements.size();
    }

    public Grade getGrade(RequirementName name) {
        double result = requirements.stream()
                .filter(x -> x.name().equals(name))
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

    public Set<Requirement> getMissingRequirements(Set<RequirementName> names) {
        Set<Requirement> missingRequirements = new HashSet<>();

        for (Requirement requirement: requirements) {
            if (!names.contains(requirement.name())) {
                missingRequirements.add(requirement);
            }
        }

        return missingRequirements;
    }

    public Set<Requirement> getRequirements(Set<RequirementName> names) {
        Set<Requirement> existingRequirements = new HashSet<>();

        for (Requirement requirement: requirements) {
            if (names.contains(requirement.name())) {
                existingRequirements.add(requirement);
            }
        }

        return existingRequirements;
    }
}
