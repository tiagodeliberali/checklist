package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;

public interface ManipulateChecklistUseCase {
    void save(Checklist checklist);
    Checklist loadChecklist(String name) throws FailedToLoadException;
}
