package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;

public interface CalculateGradesUseCase {
    ServiceGrade calculate(String checklistName, String serviceName) throws FailedToLoadException;
}
