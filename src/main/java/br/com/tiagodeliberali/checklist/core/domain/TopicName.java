package br.com.tiagodeliberali.checklist.core.domain;

public record TopicName(String id) {
    public TopicName {
        if (id == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
    }
}
