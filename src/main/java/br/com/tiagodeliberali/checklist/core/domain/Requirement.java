package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Requirement {
    private final RequirementName name;
    private final Grade grade;

    public static Requirement create(Grade grade, RequirementName name) {
        return new Requirement(name, grade);
    }
}
