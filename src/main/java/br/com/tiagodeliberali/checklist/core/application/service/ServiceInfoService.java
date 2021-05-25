package br.com.tiagodeliberali.checklist.core.application.service;

import br.com.tiagodeliberali.checklist.core.application.port.in.ManipulateServiceInfoUseCase;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToSaveException;
import br.com.tiagodeliberali.checklist.core.application.port.out.LoadServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.application.port.out.SaveServiceInfoPort;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityId;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServiceInfoService implements ManipulateServiceInfoUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ServiceInfoService.class);

    private final LoadServiceInfoPort loadServiceInfoPort;
    private final SaveServiceInfoPort saveServiceInfoPort;

    public ServiceInfoService(LoadServiceInfoPort loadServiceInfoPort, SaveServiceInfoPort saveServiceInfoPort) {
        this.loadServiceInfoPort = loadServiceInfoPort;
        this.saveServiceInfoPort = saveServiceInfoPort;
    }

    @Override
    public ServiceInfo loadServiceInfo(String name) throws FailedToLoadException {
        return loadServiceInfoPort.load(name);
    }

    @Override
    public void create(String repo) throws EntityAlreadyExistException, FailedToSaveException {
        try {
            ServiceInfo service = loadServiceInfoPort.load(repo);

            if (service != null) {
                throw new EntityAlreadyExistException(new EntityId(repo));
            }
        } catch (FailedToLoadException e) {
            logger.warn(e.getMessage());
        }

        ServiceInfo service = ServiceInfo.create(repo);
        saveServiceInfoPort.save(service);
    }

    @Override
    public void addTopic(String repo, String topicId) throws FailedToLoadException, FailedToSaveException {
        ServiceInfo service = loadServiceInfoPort.load(repo);
        service.addTopic(new EntityId(topicId));
        saveServiceInfoPort.save(service);
    }

    @Override
    public void addRequirement(String repo, String topicId, String requirementId)
            throws FailedToLoadException, FailedToSaveException {
        ServiceInfo service = loadServiceInfoPort.load(repo);
        service.addRequirement(new EntityId(topicId), new EntityId(requirementId));
        saveServiceInfoPort.save(service);
    }

    @Override
    public void removeRequirement(String repo, String topicId, String requirementId) throws FailedToLoadException, FailedToSaveException {
        ServiceInfo service = loadServiceInfoPort.load(repo);
        service.removeRequirement(new EntityId(topicId), new EntityId(requirementId));
        saveServiceInfoPort.save(service);
    }
}
