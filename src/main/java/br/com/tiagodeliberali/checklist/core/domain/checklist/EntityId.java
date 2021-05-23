package br.com.tiagodeliberali.checklist.core.domain.checklist;

public record EntityId (String id) {
    public EntityId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
    }

    public static EntityId from(ThemeName name) {
        return new EntityId(name.name()
                .toLowerCase()
                .replaceAll(" ", "")
                .substring(0, 5));
    }
}
