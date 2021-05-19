package br.com.tiagodeliberali.checklist.core.domain;

public class TopicRequirementNotFoundException extends Exception {
    public TopicRequirementNotFoundException(Requirement requirement) {
        super(String.format("Requirement not found: " + requirement));
    }
}
