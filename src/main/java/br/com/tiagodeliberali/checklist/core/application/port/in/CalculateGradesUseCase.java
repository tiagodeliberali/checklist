package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.domain.service.ServiceGrade;

public interface CalculateGradesUseCase {
    ServiceGrade calculate(String serviceName);
}
