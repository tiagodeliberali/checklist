package br.com.tiagodeliberali.checklist.core.domain;

public record Theme(int weight, String description) {
    public static Theme create(int weight, String description) {
        return new Theme(weight, description);
    }

    public Theme {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be a positive number");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description must be a filled string");
        }
    }
}
