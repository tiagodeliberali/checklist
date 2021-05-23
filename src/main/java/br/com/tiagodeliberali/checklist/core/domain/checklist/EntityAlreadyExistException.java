package br.com.tiagodeliberali.checklist.core.domain.checklist;

public class EntityAlreadyExistException extends Throwable {
    public EntityAlreadyExistException(EntityId id) {
        super("Entity with name already exists: " + id);
    }
}
