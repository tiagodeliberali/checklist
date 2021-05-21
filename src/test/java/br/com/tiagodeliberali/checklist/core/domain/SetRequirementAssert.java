package br.com.tiagodeliberali.checklist.core.domain;

import br.com.tiagodeliberali.checklist.core.domain.checklist.Requirement;
import br.com.tiagodeliberali.checklist.core.domain.checklist.RequirementName;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Optional;
import java.util.Set;

public class SetRequirementAssert extends AbstractAssert<SetRequirementAssert, Set<Requirement>> {
    public SetRequirementAssert(Set<Requirement> requirement) {
        super(requirement, SetRequirementAssert.class);
    }

    public static SetRequirementAssert assertThat(Set<Requirement> actual) {
        return new SetRequirementAssert(actual);
    }

    public SetRequirementAssert contains(RequirementName name) {
        Optional<Requirement> unused2 = actual.stream()
                .filter(x -> x.name().equals(name))
                .findFirst();

        Assertions.assertThat(unused2).isPresent();

        return this;
    }
}
