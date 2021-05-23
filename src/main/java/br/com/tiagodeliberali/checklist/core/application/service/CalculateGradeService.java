package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.application.port.in.CalculateGradesUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadChecklistPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.SaveChecklistPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.SaveServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.Checklist;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateGradeService implements CalculateGradesUseCase {
    private final LoadChecklistPort loadChecklistPort;
    private final LoadServiceInfoPort loadServiceInfoPort;
    private final SaveChecklistPort saveChecklistPort;
    private final SaveServiceInfoPort saveServiceInfoPort;

    @Autowired
    public CalculateGradeService(LoadChecklistPort loadChecklistPort,
                                 LoadServiceInfoPort loadServiceInfoPort,
                                 SaveChecklistPort saveChecklistPort,
                                 SaveServiceInfoPort saveServiceInfoPort) {
        this.loadChecklistPort = loadChecklistPort;
        this.loadServiceInfoPort = loadServiceInfoPort;
        this.saveChecklistPort = saveChecklistPort;
        this.saveServiceInfoPort = saveServiceInfoPort;
    }

    @Override
    public ServiceGrade calculate(String checklistName, String serviceName) throws FailedToLoadException {
        Checklist checklist = loadChecklistPort.load(checklistName);
        ServiceInfo service = loadServiceInfoPort.load(serviceName);

        return ServiceGrade.build(checklist, service);
    }

    @Override
    public Checklist loadChecklist(String name) throws FailedToLoadException {
        return loadChecklistPort.load(name);
    }

    @Override
    public ServiceInfo loadServiceInfo(String name) throws FailedToLoadException {
        return loadServiceInfoPort.load(name);
    }

    @Override
    public void save(Checklist checklist) {
        saveChecklistPort.save(checklist);
    }

    @Override
    public void save(ServiceInfo serviceInfo) {
        saveServiceInfoPort.save(serviceInfo);
    }
}
