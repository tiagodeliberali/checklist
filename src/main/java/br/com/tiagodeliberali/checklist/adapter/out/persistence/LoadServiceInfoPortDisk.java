package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.stereotype.Component;

@Component
public class LoadServiceInfoPortDisk implements LoadServiceInfoPort {
    @Override
    public ServiceInfo load(String serviceName) {
        return null;
    }
}
