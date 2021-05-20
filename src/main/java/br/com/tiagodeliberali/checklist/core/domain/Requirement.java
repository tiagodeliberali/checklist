package br.com.tiagodeliberali.checklist.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Requirement {
    private final RequirementId id;
    private final Grade grade;
    private final String description;

    public static Requirement create(Grade grade, String description) {
        return new Requirement(new RequirementId(UUID.randomUUID()), grade, description);
    }
}
