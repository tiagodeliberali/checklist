package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import lombok.Getter;

@Getter
public class Requirement implements CalculableEntity {
    private final EntityId id;
    private final RequirementName name;
    private final Grade grade;

    public Requirement(RequirementName name, Grade grade) {
        this.name = name;
        this.grade = grade;

        id = EntityId.from(name);
    }

    public static Requirement create(Grade grade, RequirementName name) {
        return new Requirement(name, grade);
    }

    @Override
    public EntityId getId() {
        return id;
    }

    @Override
    public Grade calculate(ServiceInfo service) {
        return null;
    }
}
