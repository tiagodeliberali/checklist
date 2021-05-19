package br.com.tiagodeliberali.checklist.core.domain;

import java.util.UUID;

public record TopicId(UUID id) {
    public TopicId {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
    }
}
