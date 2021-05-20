package br.com.tiagodeliberali.checklist.core.domain;

public class RequirementAlreadyExistsException extends Exception {
    public RequirementAlreadyExistsException(Requirement requirement) {
        super(String.format("Requirement already exists: " + requirement));
    }
}
