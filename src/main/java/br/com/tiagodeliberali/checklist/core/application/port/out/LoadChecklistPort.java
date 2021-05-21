package br.com.tiagodeliberali.checklist.core.application.port.out;

import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;

public interface LoadChecklistPort {
    Checklist load(String checklist) throws FailedToLoadException;
}
