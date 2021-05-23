package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

public interface CalculateGradesUseCase {
    ServiceGrade calculate(String checklistName, String serviceName) throws FailedToLoadException;
    Checklist loadChecklist(String name) throws FailedToLoadException;
    ServiceInfo loadServiceInfo(String name) throws FailedToLoadException;
    void save(Checklist checklist);
    void save(ServiceInfo serviceInfo);
}
