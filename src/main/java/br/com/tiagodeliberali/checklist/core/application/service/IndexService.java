package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.application.port.in.IndexServiceUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class IndexService implements IndexServiceUseCase {
    private final LoadServiceInfoPort loadServiceInfoPort;

    @Autowired
    public IndexService(LoadServiceInfoPort loadServiceInfoPort) {
        this.loadServiceInfoPort = loadServiceInfoPort;
    }

    @Override
    public Map<String, Set<String>> getServiceByRequirements() throws FailedToLoadException {
        Map<String, Set<String>> index = new HashMap<>();

        for (String serviceName : loadServiceInfoPort.loadAll()) {
            ServiceInfo serviceInfo = loadServiceInfoPort.load(serviceName);

            serviceInfo.getIterator().forEachRemaining(topic ->
                    serviceInfo.getAnswer(topic).ifPresent(answer ->
                            answer.getIterator().forEachRemaining(requirement -> {
                                if (!index.containsKey(requirement.id())) {
                                    index.put(requirement.id(), new HashSet<>());
                                }
                                index.get(requirement.id()).add(serviceName);
                            })));
        }

        return index;
    }

    @Override
    public Set<String> getServiceByRequirementsAnd(Set<String> requirements) throws FailedToLoadException {
        Set<String> result = null;
        Map<String, Set<String>> index = getServiceByRequirements();

        for (String requirement: requirements) {
            if (result == null) {
                result = new HashSet<>(index.get(requirement));
            } else {
                result.retainAll(index.get(requirement));
            }
        }

        return result;
    }

    @Override
    public Set<String> getServiceByRequirementsOr(Set<String> requirements) throws FailedToLoadException {
        Set<String> result = new HashSet<>();
        Map<String, Set<String>> index = getServiceByRequirements();

        requirements.forEach(requirement -> result.addAll(index.get(requirement)));

        return result;
    }
}
