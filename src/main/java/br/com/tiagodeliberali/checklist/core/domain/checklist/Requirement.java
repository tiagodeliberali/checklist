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

    public Requirement(EntityId id, RequirementName name, Grade grade) {
        this.name = name;
        this.grade = grade;
        this.id = id;
    }

    public static Requirement create(RequirementName name, Grade grade) {
        return new Requirement(name, grade);
    }

    public static Requirement load(String id, String name, double grade) {
        return new Requirement(new EntityId(id), new RequirementName(name), Grade.from(grade));
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
