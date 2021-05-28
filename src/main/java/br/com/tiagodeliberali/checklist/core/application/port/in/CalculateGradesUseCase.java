package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGrade;
import br.com.tiagodeliberali.checklist.core.application.service.ServiceGradeHeader;

import java.util.List;

public interface CalculateGradesUseCase {
    ServiceGrade calculate(String checklistName, String serviceName) throws FailedToLoadException;
    List<ServiceGradeHeader> calculateAll(String checklistName) throws FailedToLoadException;
}
