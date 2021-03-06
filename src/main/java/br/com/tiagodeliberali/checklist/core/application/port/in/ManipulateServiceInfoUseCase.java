package br.com.tiagodeliberali.checklist.core.application.port.in;

import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToLoadException;
import br.com.tiagodeliberali.checklist.core.application.port.out.FailedToSaveException;
import br.com.tiagodeliberali.checklist.core.domain.checklist.EntityAlreadyExistException;
import br.com.tiagodeliberali.checklist.core.domain.service.ServiceInfo;

import java.util.List;

public interface ManipulateServiceInfoUseCase {
    ServiceInfo loadServiceInfo(String name) throws FailedToLoadException;
    List<String> loadAllServicesInfo() throws FailedToLoadException;
    void create(String repo) throws EntityAlreadyExistException, FailedToSaveException;
    void addTopic(String repo, String topic) throws FailedToLoadException, FailedToSaveException;
    void addRequirement(String repo, String topic, String requirement) throws FailedToLoadException, FailedToSaveException;
    void removeRequirement(String repo, String topic, String requirement) throws FailedToLoadException, FailedToSaveException;
}
