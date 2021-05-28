package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.application.port.in.CalculateGradesUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateGradeService implements CalculateGradesUseCase {
    private final LoadChecklistPort loadChecklistPort;
    private final LoadServiceInfoPort loadServiceInfoPort;

    @Autowired
    public CalculateGradeService(LoadChecklistPort loadChecklistPort,
                                 LoadServiceInfoPort loadServiceInfoPort) {
        this.loadChecklistPort = loadChecklistPort;
        this.loadServiceInfoPort = loadServiceInfoPort;
    }

    @Override
    public ServiceGrade calculate(String checklistName, String serviceName) throws FailedToLoadException {
        Checklist checklist = loadChecklistPort.load(checklistName);
        ServiceInfo service = loadServiceInfoPort.load(serviceName);

        return ServiceGrade.build(checklist, service);
    }

    @Override
    public List<ServiceGradeHeader> calculateAll(String checklistName) throws FailedToLoadException {
        Checklist checklist = loadChecklistPort.load(checklistName);

        List<ServiceGradeHeader> list = new ArrayList<>();
        for (String s : loadServiceInfoPort.loadAll()) {
            ServiceInfo service = loadServiceInfoPort.load(s);
            ServiceGradeHeader build = ServiceGrade.buildHeader(checklist, service);
            list.add(build);
        }
        return list;
    }
}
