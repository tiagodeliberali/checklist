package br.com.tiagodeliberali.checklist.core.domain.checklist;

public class RequirementAlreadyExistsException extends Exception {
    public RequirementAlreadyExistsException(Requirement requirement) {
        super(String.format("Requirement already exists: " + requirement));
    }
}
