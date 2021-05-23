package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

public interface ManipulateServiceInfoUseCase {
    ServiceInfo loadServiceInfo(String name) throws FailedToLoadException;
    void save(ServiceInfo serviceInfo);
}
