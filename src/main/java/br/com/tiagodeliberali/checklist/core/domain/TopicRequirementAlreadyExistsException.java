package br.com.tiagodeliberali.checklist.core.domain;

public class TopicRequirementAlreadyExistsException extends Exception {
    public TopicRequirementAlreadyExistsException(TopicRequirement requirement) {
        super(String.format("Requirement already exists: " + requirement));
    }
}
