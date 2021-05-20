package br.com.tiagodeliberali.checklist.core.domain;

public record Theme(int weight, String name) {
    public static Theme create(int weight, String name) {
        return new Theme(weight, name);
    }

    public Theme {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be a positive number");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Description must be a filled string");
        }
    }
}
