package br.com.tiagodeliberali.checklist.core.domain.checklist;

public record EntityId(String id) {
    public EntityId {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
    }

    public static EntityId from(ThemeName name) {
        return new EntityId(getSmallName(name.name()));
    }

    public static EntityId from(TopicName name) {
        return new EntityId(String.format("%s-%d",
                getSmallName(name.id()),
                name.id().hashCode()));
    }

    private static String getSmallName(String name) {
        return name
                .toLowerCase()
                .replaceAll(" ", "")
                .substring(0, 5);
    }
}
