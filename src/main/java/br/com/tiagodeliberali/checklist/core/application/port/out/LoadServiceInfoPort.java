package br.com.tiagodeliberali.checklist.core.application.port.out;

import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

public interface LoadServiceInfoPort {
    ServiceInfo load(String serviceName) throws JsonFileNotFound, JsonFileNotParsed;
}
