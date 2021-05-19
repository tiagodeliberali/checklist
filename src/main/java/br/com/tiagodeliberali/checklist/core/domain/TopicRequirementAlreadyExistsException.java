package br.com.tiagodeliberali.checklist.core.domain;

public class TopicRequirementAlreadyExistsException extends Exception {
    public TopicRequirementAlreadyExistsException(Requirement requirement) {
        super(String.format("Requirement already exists: " + requirement));
    }
}
