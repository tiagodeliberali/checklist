package br.com.tiagodeliberali.checklist.core.domain;

public class TopicRequirementNotFoundException extends Exception {
    public TopicRequirementNotFoundException(RequirementName id) {
        super(String.format("Requirement not found: " + id));
    }
}
