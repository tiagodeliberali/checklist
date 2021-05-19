package br.com.tiagodeliberali.checklist.core.domain;

public class TopicRequirementNotFoundException extends Exception {
    public TopicRequirementNotFoundException(TopicRequirement requirement) {
        super(String.format("Requirement not found: " + requirement));
    }
}
