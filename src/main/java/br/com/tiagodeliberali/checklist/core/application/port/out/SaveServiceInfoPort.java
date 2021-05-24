package br.com.tiagodeliberali.checklist.core.application.port.out;

import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

public interface SaveServiceInfoPort {
    void save(ServiceInfo serviceInfo) throws FailedToSaveException;
}
