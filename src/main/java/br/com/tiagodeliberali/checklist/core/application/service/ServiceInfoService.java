package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.application.port.in.ManipulateServiceInfoUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.SaveServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.stereotype.Service;

@Service
public class ServiceInfoService implements ManipulateServiceInfoUseCase {
    private final LoadServiceInfoPort loadServiceInfoPort;
    private final SaveServiceInfoPort saveServiceInfoPort;

    public ServiceInfoService(LoadServiceInfoPort loadServiceInfoPort, SaveServiceInfoPort saveServiceInfoPort) {
        this.loadServiceInfoPort = loadServiceInfoPort;
        this.saveServiceInfoPort = saveServiceInfoPort;
    }

    @Override
    public ServiceInfo loadServiceInfo(String name) throws FailedToLoadException {
        return loadServiceInfoPort.load(name);
    }

    @Override
    public void save(ServiceInfo serviceInfo) {
        saveServiceInfoPort.save(serviceInfo);
    }
}
