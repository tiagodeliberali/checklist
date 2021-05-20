package br.com.tiagodeliberali.checklist.core.domain;

public class RequirementNotFoundException extends Exception {
    public RequirementNotFoundException(RequirementName id) {
        super(String.format("Requirement not found: " + id));
    }
}
