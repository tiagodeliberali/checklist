package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.SaveServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.stereotype.Component;

@Component
public class SaveServiceInfoPortDisk implements SaveServiceInfoPort {
    @Override
    public void save(ServiceInfo serviceInfo) {

    }
}
