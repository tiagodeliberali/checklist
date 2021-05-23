package br.com.tiagodeliberali.checklist.core.domain.checklist;

import br.com.tiagodeliberali.checklist.core.domain.Grade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NodeInfo<ITEM extends CalculableEntity> {
    protected Map<EntityId, ITEM> nodes = new HashMap<>();

    public int count() {
        return nodes.size();
    }

    public void add(ITEM item) throws EntityAlreadyExistException {
        if (nodes.containsKey(item.getId())) {
            throw new EntityAlreadyExistException(item.getId());
        }
        nodes.put(item.getId(), item);
    }

    public void remove(EntityId id) throws EntityNotFoundException {
        if (!nodes.containsKey(id)) {
            throw new EntityNotFoundException(id);
        }

        nodes.remove(id);
    }

    public ITEM get(EntityId id) {
        return nodes.get(id);
    }

    public Iterator<ITEM> getIterator() {
        return nodes.values().iterator();
    }

    public Grade calculate(ServiceInfo service) {
        return Grade.MIN;
    }
}
