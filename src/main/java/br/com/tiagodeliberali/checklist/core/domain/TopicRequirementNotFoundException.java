package br.com.tiagodeliberali.checklist.core.domain;

public class TopicRequirementNotFoundException extends Exception {
    public TopicRequirementNotFoundException(RequirementId id) {
        super(String.format("Requirement not found: " + id));
    }
}
