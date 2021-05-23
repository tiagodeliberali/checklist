package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.SaveChecklistPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import org.springframework.stereotype.Component;

@Component
public class SaveChecklistPortDisk implements SaveChecklistPort {
    @Override
    public void save(Checklist checklist) {

    }
}
