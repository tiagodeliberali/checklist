package br.com.tiagodeliberali.checklist.core.application.port.out;

import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

import java.util.List;

public interface LoadServiceInfoPort {
    ServiceInfo load(String serviceName) throws FailedToLoadException;
    List<String> loadAll() throws  FailedToLoadException;
}
