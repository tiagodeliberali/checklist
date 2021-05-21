package br.com.tiagodeliberali.checklist.adapter.out.persistence;

import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import org.springframework.stereotype.Component;

@Component
public class LoadChecklistDisk implements LoadChecklistPort {
    @Override
    public Checklist load() {
        return null;
    }
}
