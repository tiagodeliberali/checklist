package br.com.tiagodeliberali.checklist.core.domain;

public record TopicRequirement(Grade grade, String description) {
    public static TopicRequirement create(Grade grade, String description) {
        return new TopicRequirement(grade, description);
    }
}
