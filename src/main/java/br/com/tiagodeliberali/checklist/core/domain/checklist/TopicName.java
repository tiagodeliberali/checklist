package br.com.tiagodeliberali.checklist.core.domain.checklist;

public record TopicName(String name) {
    public TopicName {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
    }
}
