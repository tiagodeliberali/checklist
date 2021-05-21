package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;

public record Requirement(RequirementName name, Grade grade) {
    public static Requirement create(Grade grade, RequirementName name) {
        return new Requirement(name, grade);
    }
}
