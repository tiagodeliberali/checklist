package br.com.tiagodeliberali.checklist.core.domain.checklist;

public class EntityNotFoundException extends Throwable {
    public EntityNotFoundException(EntityId id) {
        super("Entity not found by it: " + id);
    }
}
