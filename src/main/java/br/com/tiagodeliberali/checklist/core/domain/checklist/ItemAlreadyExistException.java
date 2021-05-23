package br.com.tiagodeliberali.checklist.core.domain.checklist;

public class ItemAlreadyExistException extends Throwable {
    public ItemAlreadyExistException(EntityId id) {
        super("Id already exists: " + id);
    }
}
