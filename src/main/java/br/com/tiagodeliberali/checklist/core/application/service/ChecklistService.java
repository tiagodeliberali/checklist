package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.application.port.in.ManipulateChecklistUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.SaveChecklistPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import org.springframework.stereotype.Service;

@Service
public class ChecklistService implements ManipulateChecklistUseCase {
    private final LoadChecklistPort loadChecklistPort;
    private final SaveChecklistPort saveChecklistPort;

    public ChecklistService(LoadChecklistPort loadChecklistPort, SaveChecklistPort saveChecklistPort) {
        this.loadChecklistPort = loadChecklistPort;
        this.saveChecklistPort = saveChecklistPort;
    }

    @Override
    public Checklist loadChecklist(String name) throws FailedToLoadException {
        return loadChecklistPort.load(name);
    }

    @Override
    public void save(Checklist checklist) {
        saveChecklistPort.save(checklist);
    }
}
