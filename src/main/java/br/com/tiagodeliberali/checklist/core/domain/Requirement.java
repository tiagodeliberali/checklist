package br.com.tiagodeliberali.checklist.core.domain;

public record Requirement(Grade grade, String description) {
    public static Requirement create(Grade grade, String description) {
        return new Requirement(grade, description);
    }
}
