package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.application.port.in.CalculateGradesUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceGrade;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateGradeService implements CalculateGradesUseCase {
    private final LoadChecklistPort loadChecklistPort;
    private final LoadServiceInfoPort loadServiceInfoPort;

    @Autowired
    public CalculateGradeService(LoadChecklistPort loadChecklistPort, LoadServiceInfoPort loadServiceInfoPort) {
        this.loadChecklistPort = loadChecklistPort;
        this.loadServiceInfoPort = loadServiceInfoPort;
    }

    @Override
    public ServiceGrade calculate(String serviceName) {
        Checklist checklist = loadChecklistPort.load();
        ServiceInfo service = loadServiceInfoPort.load(serviceName);

        return ServiceGrade.build(checklist, service);
    }
}
